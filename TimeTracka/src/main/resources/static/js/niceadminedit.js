window.onload = function() {
	const urlParams = new URLSearchParams(window.location.search);
	const userName = urlParams.get("userName");
	const date = urlParams.get("date");

	document.getElementById("userName").value = userName;
	document.getElementById("date").value = date;
};

function submitForm() {
	const userName = document.getElementById("userName").value;
	const date = document.getElementById("date").value;
	const arrivalTime = document.getElementById("arrivalTime").value;
	const departureTime = document.getElementById("departureTime").value;

	const payload = {
		userName: userName,
		date: date,
		arrivalTime: arrivalTime,
		departureTime: departureTime
	};

	fetch("/api/attendances/update", {
		method: "POST",
		headers: {
			"Content-Type": "application/json"
		},
		body: JSON.stringify(payload)
	})
	.then(res => {
		if (!res.ok) {
			throw new Error("通信に失敗しました");
		}
		alert("更新しました");
		window.location.href = "/niceadminhome";
	})
	.catch(err => {
		console.error("エラー:", err);
		alert("更新に失敗しました");
	});
}
