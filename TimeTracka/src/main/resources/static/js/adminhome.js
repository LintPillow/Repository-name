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
        displayData(arrivals, departures); // テーブル更新
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
    const nameSearch = document.getElementById('nameSearch').value.toLowerCase();
    const dateSearch = document.getElementById('dateSearch').value;

    const filteredArrivals = arrivals.filter(item => {
        const [date] = item.timestamp.split("T");
        const matchesName = nameSearch ? item.userName.toLowerCase().includes(nameSearch) : true;
        const matchesDate = dateSearch ? date.includes(dateSearch) : true;
        return matchesName && matchesDate;
    });

    displayData(filteredArrivals, departures);
}

// 検索結果を表示する関数
function displayData(arrivals, departures) {
    const tableBody = document.querySelector('#attendance-table tbody');
    tableBody.innerHTML = "";

    const departuresMap = new Map();
    const workCountMap = new Map();

    departures.forEach(item => {
        const [date, time] = item.timestamp.split("T");
        const key = `${item.userName}_${date}`;
        departuresMap.set(key, time);
    });

    arrivals.forEach(item => {
        const [date] = item.timestamp.split("T");
        const key = `${item.userName}_${date}`;
        if (departuresMap.has(key)) {
            workCountMap.set(item.userName, (workCountMap.get(item.userName) || 0) + 1);
        }
    });

    arrivals.forEach(item => {
        const [date, arrivalTime] = item.timestamp.split("T");
        const key = `${item.userName}_${date}`;
        const departureTime = departuresMap.get(key) || "-";

        const row = document.createElement('tr');

        [item.userName, date, arrivalTime, departureTime].forEach(text => {
            const td = document.createElement('td');
            td.textContent = text;
            row.appendChild(td);
        });

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

        const daysCell = document.createElement('td');
        daysCell.textContent = workCountMap.get(item.userName) || 0;
        row.appendChild(daysCell);

        const actionCell = document.createElement('td');
        const editBtn = document.createElement('button');
        editBtn.textContent = "編集";
        editBtn.className = "edit-btn";
        editBtn.addEventListener("click", () => {
            const [date] = item.timestamp.split("T");
            window.location.href = `/adminedit?date=${date}&name=${encodeURIComponent(item.userName)}`;
        });

        const deleteBtn = document.createElement('button');
        deleteBtn.textContent = "削除";
        deleteBtn.className = "delete-btn";
        deleteBtn.addEventListener("click", () => {
            if (confirm(`${item.userName} の記録を削除しますか？`)) {
                fetch(`/api/arrivals/delete/${item.id}`, { method: 'DELETE' })
                    .catch(err => console.error(err));

                const [date] = item.timestamp.split("T");
                const departure = departures.find(dep =>
                    dep.userName === item.userName && dep.timestamp.startsWith(date)
                );
                if (departure) {
                    fetch(`/api/departures/delete/${departure.id}`, { method: 'DELETE' })
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
