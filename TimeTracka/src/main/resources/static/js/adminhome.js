// APIからデータを取得して表示する関数
function fetchData(url, tableId) {
    fetch(url)
        .then(response => response.json())
        .then(data => {
            const tableBody = document.querySelector(`#${tableId} tbody`);
            tableBody.innerHTML = "";  // 既存の行を削除

            data.forEach(item => {
                const row = document.createElement("tr");

                for (const key in item) {
                    const cell = document.createElement("td");
                    cell.textContent = item[key];
                    row.appendChild(cell);
                }

                tableBody.appendChild(row);
            });
        })
        .catch(error => {
            console.error("データの取得に失敗しました", error);
        });
}

// ページ読み込み時にAPIからデータを取得
window.onload = function() {
    // 到着データと出発データの取得
    fetchData("/api/arrivals/dummy", "arrivals-table");
    fetchData("/api/departures/dummy", "departures-table");
};
