// グローバル変数
let arrivals = [];
let departures = [];

// データを取得して表示する関数
function fetchAndMergeData(arrivalsUrl, departuresUrl, tableId) {
	Promise.all([
		fetch(arrivalsUrl).then(res => res.json()),  
		fetch(departuresUrl).then(res => res.json()) 
	])
	.then(([arrivalsData, departuresData]) => {
		arrivals = arrivalsData;  
		departures = departuresData;  
		displayData(arrivals, departures); // ここでテーブル更新する
	})
	.catch(error => {
		console.error("データ取得失敗:", error);
	});
}


// ページが読み込まれたときに実行
window.onload = function() {
	fetchAndMergeData("/api/arrivals", "/api/departures", "attendance-table");
};

// 検索機能
function searchData() {
	const nameSearch = document.getElementById('nameSearch').value.toLowerCase(); // 名前検索
	const dateSearch = document.getElementById('dateSearch').value; // 日付検索

	// 検索結果を保持する配列
	const filteredArrivals = arrivals.filter(item => {
		const [date, time] = item.timestamp.split("T"); // タイムスタンプを分割
		// 名前と日付でフィルタリング
		const matchesName = nameSearch ? item.userName.toLowerCase().includes(nameSearch) : true;
		const matchesDate = dateSearch ? date.includes(dateSearch) : true;

		return matchesName && matchesDate; // 両方の条件を満たす場合にtrue
	});

	// 検索結果を表示
	displayData(filteredArrivals, departures);
}

// 検索結果を表示する関数
function displayData(arrivals, departures) {
    const tableBody = document.querySelector('#attendance-table tbody');
    tableBody.innerHTML = ""; // テーブルクリア

    const departuresMap = new Map();
    const workCountMap = new Map();

    // 退勤データのマッピング
    departures.forEach(item => {
        const [date, time] = item.timestamp.split("T");
        const key = `${item.userName}_${date}`;
        departuresMap.set(key, time);
    });

    // 勤務日数カウント
    arrivals.forEach(item => {
        const [date] = item.timestamp.split("T");
        const key = `${item.userName}_${date}`;
        const hasDeparture = departuresMap.has(key);
        if (hasDeparture) {
            workCountMap.set(item.userName, (workCountMap.get(item.userName) || 0) + 1);
        }
    });

    // 表示
    arrivals.forEach(item => {
        const [date, arrivalTime] = item.timestamp.split("T");
        const key = `${item.userName}_${date}`;
        const departureTime = departuresMap.get(key) || "-";

        const row = document.createElement('tr');

        // 名前・日付・出勤・退勤
        [item.userName, date, arrivalTime, departureTime].forEach(text => {
            const td = document.createElement('td');
            td.textContent = text;
            row.appendChild(td);
        });

        // 勤務時間
        const workTimeCell = document.createElement('td');
        if (departureTime !== "-") {
            const start = new Date(`${date}T${arrivalTime}`);
            const end = new Date(`${date}T${departureTime}`);
            const diff = end - start;
            const hours = Math.floor(diff / 1000 / 60 / 60);
            const minutes = Math.floor((diff / 1000 / 60) % 60);
            workTimeCell.textContent = `${hours}時間${minutes}分`;
        } else {
            workTimeCell.textContent = "-";
        }
        row.appendChild(workTimeCell);

        // 勤務日数
        const daysCell = document.createElement('td');
        daysCell.textContent = workCountMap.get(item.userName) || 0;
        row.appendChild(daysCell);

        // 編集・削除ボタン
        const actionCell = document.createElement('td');

		// 編集ボタン
		const editBtn = document.createElement('button');
		editBtn.textContent = "編集";
		editBtn.className = "edit-button";
		editBtn.addEventListener("click", () => {
		    const id = item.id;  // IDを取得（APIのデータに存在する場合）
		    const name = encodeURIComponent(item.userName);
			const [date, arrivalTime] = item.timestamp.split("T");
		    window.location.href = `adminedit?date=${date}&name=${item.userName}`;
		});


        const deleteBtn = document.createElement('button');
        deleteBtn.textContent = "削除";
        deleteBtn.className = "delete-button";
        deleteBtn.addEventListener("click", () => {
            if (confirm(`${item.userName} の記録を削除しますか？`)) {
				// 出勤データの削除
				fetch(`/api/arrivals/delete/${item.id}`, { method: 'DELETE' })
				    .then(res => {
				        if (!res.ok) throw new Error("出勤データ削除失敗");
				    })
				    .catch(err => console.error(err));

				// 該当する退勤データのIDを探して削除
				const [date] = item.timestamp.split("T");
				const departure = departures.find(dep =>
				    dep.userName === item.userName && dep.timestamp.startsWith(date)
				);
				if (departure) {
				    fetch(`/api/departures/delete/${departure.id}`, { method: 'DELETE' })
				        .then(res => {
				            if (!res.ok) throw new Error("退勤データ削除失敗");
				        })
				        .catch(err => console.error(err));
				}
				
                row.remove();
				
            }
        });

        actionCell.appendChild(editBtn);
        actionCell.appendChild(deleteBtn);
        row.appendChild(actionCell);

        tableBody.appendChild(row);
    });
}
