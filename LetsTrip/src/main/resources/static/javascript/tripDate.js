
let startDate = null;
let endDate = null;
let currentYear = new Date().getFullYear();
let currentMonth = new Date().getMonth();

document.addEventListener("DOMContentLoaded", function () {
  drawCalendar(currentYear, currentMonth);

  document.querySelector(".minus-month").onclick = () => {
    currentMonth--;
    if (currentMonth < 0) {
      currentMonth = 11;
      currentYear--;
    }
    drawCalendar(currentYear, currentMonth);
  };

  document.querySelector(".plus-month").onclick = () => {
    currentMonth++;
    if (currentMonth > 11) {
      currentMonth = 0;
      currentYear++;
    }
    drawCalendar(currentYear, currentMonth);
  };

  document.querySelector(".plus-year").onclick = () => {
    currentYear++;
    drawCalendar(currentYear, currentMonth);
  };

  document.querySelector(".minus-year").onclick = () => {
    currentYear--;
    drawCalendar(currentYear, currentMonth);
  };
});

function drawCalendar(y, m) {
  const cal = document.getElementById("cal");
  const firstDay = new Date(y, m, 1).getDay();
  const lastDate = new Date(y, m + 1, 0).getDate();

  let html = "<table class='calendar'><tr>";
  const weekDays = ["일", "월", "화", "수", "목", "금", "토"];
  weekDays.forEach(day => html += `<th>${day}</th>`);
  html += "</tr>";

  let dNum = 1;
  for (let i = 0; i < 6; i++) {
    html += "<tr>";
    for (let j = 0; j < 7; j++) {
      if (i === 0 && j < firstDay) {
        html += "<td class='day-box'>&nbsp;</td>";
      } else if (dNum > lastDate) {
        html += "<td class='day-box'>&nbsp;</td>";
      } else {
        const dateStr = `${y}-${String(m + 1).padStart(2, '0')}-${String(dNum).padStart(2, '0')}`;
        html += `<td class='day-box calendar-day' data-date='${dateStr}'>${dNum}</td>`;
        dNum++;
      }
    }
    html += "</tr>";
  }
  html += "</table>";
  cal.innerHTML = html;

  document.querySelector(".current-year").textContent = `${y}년`;
  document.querySelector(".current-month").textContent = `${m + 1}월`;

  applyDateClickListeners(); // ✅ 날짜 이벤트 바인딩
}

function applyDateClickListeners() {
  const dates = document.querySelectorAll(".calendar-day");
  dates.forEach(day => {
    day.addEventListener("click", function () {
      const selectedDate = this.getAttribute("data-date");

      if (startDate === selectedDate) {
        startDate = null;
        document.getElementById("startDateInput").value = "";
        this.classList.remove("selected-end");
        return;
      }

      if (!startDate) {
        startDate = selectedDate;
        document.getElementById("startDateInput").value = startDate;
		console.log("시작일:", startDate);
        clearSelection();
        this.classList.add("selected-start");
      } else if (!endDate) {
        if (new Date(selectedDate) < new Date(startDate)) {
          clearSelection();
          startDate = selectedDate;
          document.getElementById("startDateInput").value = startDate;
		  console.log("시작일:", startDate);
          this.classList.add("selected-start");
        } else {
          endDate = selectedDate;
          document.getElementById("endDateInput").value = endDate;
		  console.log("종료일:", endDate);
          this.classList.add("selected-end");
        }
      } else {
        clearSelection();
        startDate = selectedDate;
        document.getElementById("startDateInput").value = startDate;
		console.log("시작일:", startDate);
        endDate = null;
        document.getElementById("endDateInput").value = "";
        this.classList.add("selected-start");
      }

      highlightRange();
    });
  });
}

function clearSelection() {
  document.querySelectorAll(".calendar-day").forEach(day =>
    day.classList.remove("selected-start", "selected-end", "in-range")
  );
}

function highlightRange() {
  if (startDate && endDate) {
    const start = new Date(startDate);
    const end = new Date(endDate);
    document.querySelectorAll(".calendar-day").forEach(day => {
      const date = new Date(day.getAttribute("data-date"));
      if (date > start && date < end) {
        day.classList.add("in-range");
      }
    });
  }
}
