// グローバル変数
let arrivals = [];
let departures = [];

// データを取得して表示する関数
function fetchAndMergeData(arrivalsUrl, departuresUrl, tableId) {
    // APIから出勤と退勤データを非同期に取得
    Promise.all([
        fetch(arrivalsUrl).then(res => res.json()),  // 出勤データ
        fetch(departuresUrl).then(res => res.json()) // 退勤データ
    ])
    .then(([arrivalsData, departuresData]) => {
        arrivals = arrivalsData;  // グローバル変数に格納
        departures = departuresData;  // グローバル変数に格納

        const tableBody = document.querySelector(`#${tableId} tbody`);
        tableBody.innerHTML = ""; // テーブルを初期化

        const departuresMap = new Map(); // 退勤時間のマッピング
        const workCountMap = new Map(); // 勤務日数カウント用

        // 退勤データを名前+日付でマッピング
        departures.forEach(item => {
            const [date, departureTime] = item.timestamp.split("T"); // 日付と時間を分割
            const key = `${item.userName}_${date}`; // 名前と日付をキーに
            departuresMap.set(key, departureTime); // 退勤時間を格納
        });

        // 出勤データを処理
        arrivals.forEach(item => {
            const [date, arrivalTime] = item.timestamp.split("T"); // 日付と時間を分割
            const key = `${item.userName}_${date}`; // 名前と日付をキーに
            const departureTime = departuresMap.get(key) || "-"; // 退勤時間を取得（なければ"-"）

            const row = document.createElement("tr");

            // 名前
            const nameCell = document.createElement("td");
            nameCell.textContent = item.userName;
            row.appendChild(nameCell);

            // 日付
            const dateCell = document.createElement("td");
            dateCell.textContent = date;
            row.appendChild(dateCell);

            // 出勤時間
            const arrivalCell = document.createElement("td");
            arrivalCell.textContent = arrivalTime;
            row.appendChild(arrivalCell);

            // 退勤時間
            const departureCell = document.createElement("td");
            departureCell.textContent = departureTime;
            row.appendChild(departureCell);

            // 勤務時間の計算
            let workTimeStr = "-"; // 初期値 "-"
            if (departureTime !== "-") {
                const arrivalDateTime = new Date(`${date}T${arrivalTime}`); // 出勤時間
                const departureDateTime = new Date(`${date}T${departureTime}`); // 退勤時間
                const diffMs = departureDateTime - arrivalDateTime; // 時間差

                if (diffMs > 0) {
                    const diffHours = Math.floor(diffMs / (1000 * 60 * 60)); // 時間部分
                    const diffMinutes = Math.floor((diffMs / (1000 * 60)) % 60); // 分部分
                    workTimeStr = `${diffHours}時間${diffMinutes}分`; // 勤務時間のフォーマット
                }
            }

            // 勤務時間をテーブルに追加
            const workTimeCell = document.createElement("td");
            workTimeCell.textContent = workTimeStr;
            row.appendChild(workTimeCell);

            // 勤務日数をカウント
            if (departureTime !== "-") {
                if (!workCountMap.has(item.userName)) {
                    workCountMap.set(item.userName, 0);
                }
                workCountMap.set(item.userName, workCountMap.get(item.userName) + 1);
            }

            // 勤務日数をテーブルに表示（後で更新するためのdata-name属性）
            const workDaysCell = document.createElement("td");
            workDaysCell.setAttribute("data-name", item.userName); // 名前で更新予定
            row.appendChild(workDaysCell);

            // 行をテーブルに追加
            tableBody.appendChild(row);
        });

        // 勤務日数を行ごとに埋め直す
        document.querySelectorAll(`#${tableId} tbody td[data-name]`).forEach(cell => {
            const name = cell.getAttribute("data-name");
            cell.textContent = workCountMap.get(name) || 0; // 勤務日数を表示
        });
    })
    .catch(error => {
        console.error("データの取得・処理に失敗しました", error);
    });
}

// ページが読み込まれたときに実行
window.onload = function () {
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
    tableBody.innerHTML = ""; // 既存の行をクリア

    arrivals.forEach(item => {
        const [date, arrivalTime] = item.timestamp.split("T");
       

        const row = document.createElement('tr');

        // 名前
        const nameCell = document.createElement('td');
        nameCell.textContent = item.userName;
        row.appendChild(nameCell);

        // 日付
        const dateCell = document.createElement('td');
        dateCell.textContent = date;
        row.appendChild(dateCell);

        // 出勤時間
        const arrivalCell = document.createElement('td');
        arrivalCell.textContent = arrivalTime;
        row.appendChild(arrivalCell);

        // 退勤時間
        const departureCell = document.createElement('td');
        departureCell.textContent = departureTime;
        row.appendChild(departureCell);

        // 勤務時間
        const workTimeCell = document.createElement('td');
        const arrivalDateTime = new Date(`${date}T${arrivalTime}`);
        const departureDateTime = departureTime !== '-' ? new Date(`${date}T${departureTime}`) : null;
        const workTimeStr = departureDateTime ? `${Math.floor((departureDateTime - arrivalDateTime) / 1000 / 60 / 60)}時間 ${(departureDateTime - arrivalDateTime) / 1000 / 60 % 60}分` : "-";
        workTimeCell.textContent = workTimeStr;
        row.appendChild(workTimeCell);

        // 勤務日数
        const workDaysCell = document.createElement('td');
        workDaysCell.textContent = "-";
        row.appendChild(workDaysCell);

        tableBody.appendChild(row);
    });
}
