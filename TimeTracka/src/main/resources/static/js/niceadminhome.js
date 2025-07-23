window.onload = function() {
	fetchAttendanceData();
};

let allAttendanceData = [];

function fetchAttendanceData() {
	fetch("/api/attendances")
		.then(res => res.json())
		.then(data => {
			allAttendanceData = data;
			displayAttendanceTable(data);
		})
		.catch(error => {
			console.error("データ取得失敗", error);
		});
}

function displayAttendanceTable(data) {
	const tableBody = document.querySelector('#attendance-table tbody');
	tableBody.innerHTML = "";

	if (!Array.isArray(data)) {
		console.error("APIレスポンスが配列じゃない", data);
		return;
	}

	data.forEach(item => {
		const row = document.createElement("tr");

		// 名前
		const nameCell = document.createElement("td");
		nameCell.textContent = item.userName;
		row.appendChild(nameCell);

		// 日付
		const dateCell = document.createElement("td");
		dateCell.textContent = item.date;
		row.appendChild(dateCell);

		// 出勤時間
		const arrivalCell = document.createElement("td");
		arrivalCell.textContent = item.arrivalTime || "";
		row.appendChild(arrivalCell);

		// 退勤時間
		const departureCell = document.createElement("td");
		departureCell.textContent = item.departureTime || "";
		row.appendChild(departureCell);

		// 勤務時間
		const workTimeCell = document.createElement("td");
		workTimeCell.textContent = item.workTime || "-";
		row.appendChild(workTimeCell);

		// 編集ボタン
		const editCell = document.createElement("td");
		const editBtn = document.createElement("button");
		editBtn.textContent = "編集";
		editBtn.onclick = function() {
			window.location.href = `/niceadminedit?userName=${encodeURIComponent(item.userName)}&date=${item.date}`;
		};
		editCell.appendChild(editBtn);
		row.appendChild(editCell);

		// 削除ボタン
		const deleteCell = document.createElement("td");
		const deleteBtn = document.createElement("button");
		deleteBtn.textContent = "削除";
		deleteBtn.onclick = function() {
			if (confirm("本当に削除しますか？")) {
				deleteAttendance(item.arrivalId, item.departureId);
			}
		};
		deleteCell.appendChild(deleteBtn);
		row.appendChild(deleteCell);

		tableBody.appendChild(row);
	});
}

function deleteAttendance(arrivalId, departureId) {
	const url = new URL("/api/attendances", window.location.origin);
	if (arrivalId != null) url.searchParams.append("arrivalId", arrivalId);
	if (departureId != null) url.searchParams.append("departureId", departureId);

	fetch(url, {
		method: 'DELETE'
	})
	.then(() => fetchAttendanceData())
	.catch(error => {
		console.error("削除失敗", error);
	});
}

function filterTable() {
	const name = document.getElementById("nameSearch").value.trim().toLowerCase();
	const date = document.getElementById("dateSearch").value;

	const filtered = allAttendanceData.filter(item => {
		const matchName = name === "" || item.userName.toLowerCase().includes(name);
		const matchDate = date === "" || item.date === date;
		return matchName && matchDate;
	});

	displayAttendanceTable(filtered);
}

function resetFilter() {
	document.getElementById("nameSearch").value = "";
	document.getElementById("dateSearch").value = "";
	displayAttendanceTable(allAttendanceData);
}
