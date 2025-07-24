// punch.js

function updateClock() {
	const now = new Date()
	const formatted = now.toLocaleString("ja-JP", {
		year: "numeric",
		month: "2-digit",
		day: "2-digit",
		hour: "2-digit",
		minute: "2-digit",
		second: "2-digit",
		hour12: false
	})
	document.getElementById("clock").textContent = formatted
}
setInterval(updateClock, 1000)
updateClock()

function updateAnalogClockSmooth() {
	const now = new Date()
	const ms = now.getMilliseconds()
	const sec = now.getSeconds() + ms / 1000
	const min = now.getMinutes() + sec / 60
	const hour = now.getHours() + min / 60

	const secondDeg = sec * 6
	const minuteDeg = min * 6
	const hourDeg = (hour % 12) * 30

	document.getElementById("second-hand").style.transform = `rotate(${secondDeg}deg)`
	document.getElementById("minute-hand").style.transform = `rotate(${minuteDeg}deg)`
	document.getElementById("hour-hand").style.transform = `rotate(${hourDeg}deg)`

	requestAnimationFrame(updateAnalogClockSmooth)
}
requestAnimationFrame(updateAnalogClockSmooth)

window.addEventListener("DOMContentLoaded", () => {
	if (window.location.search.includes("retry=true")) {
		const forceFlag = document.getElementById("forceFlag")
		if (forceFlag) forceFlag.value = "true"
	}
})
