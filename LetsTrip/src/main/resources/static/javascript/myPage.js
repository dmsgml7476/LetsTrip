document.addEventListener("DOMContentLoaded", function () {
  // === 좋아요 스토리 렌더링 ===
  const likeStories = window.likeStories || [];
  let currentIndex = 0;

  function updateLikeStoryBoxes() {
    const total = likeStories.length;
    const prev = (currentIndex - 1 + total) % total;
    const current = currentIndex;
    const next = (currentIndex + 1) % total;

    const prevBox = document.getElementById("prev-story-box");
    const currBox = document.getElementById("current-story-box");
    const nextBox = document.getElementById("next-story-box");

    function render(box, data) {
      box.setAttribute("data-id", data.storyId);
      box.querySelector(".story-img img").src = data.imageUrl;
      box.querySelector(".story-nickname").textContent = data.nickname + "님의 일정";
      box.querySelector(".story-title").textContent = data.title;
      box.querySelector(".story-date").textContent = data.date;
      box.querySelector(".story-content").textContent = data.content;
    }

    if (likeStories.length >= 1) render(prevBox, likeStories[prev]);
    if (likeStories.length >= 2) render(currBox, likeStories[current]);
    if (likeStories.length >= 3) render(nextBox, likeStories[next]);
  }

  if (likeStories.length > 0) {
	updateLikeStoryBoxes();

  document.querySelector(".right").addEventListener("click", () => {
    currentIndex = (currentIndex + 1) % likeStories.length;
    updateLikeStoryBoxes();
  });

  document.querySelector(".left").addEventListener("click", () => {
    currentIndex = (currentIndex - 1 + likeStories.length) % likeStories.length;
    updateLikeStoryBoxes();
  });

  ["prev-story-box", "current-story-box", "next-story-box"].forEach(id => {
    const box = document.getElementById(id);
    if (box) {
      box.addEventListener("click", function () {
        const storyId = this.getAttribute("data-id");
        if (storyId) {
          window.location.href = `/StoryDetail?id=${storyId}`;
        }
      });
    }
  });
}
  // === 더보기 메뉴 ===
  const moreBtn = document.getElementById("moreBtn");
  const moreMenu = document.getElementById("moreMenu");

  moreBtn.addEventListener("click", function () {
    moreMenu.style.display = moreMenu.style.display === "block" ? "none" : "block";
  });

  document.addEventListener("click", function (e) {
    if (!moreBtn.contains(e.target) && !moreMenu.contains(e.target)) {
      moreMenu.style.display = "none";
    }
  });

  // === 달력 초기화 ===
  let today = new Date();
  let y = today.getFullYear();
  let m = today.getMonth();

  drawCalendar(y, m);

  document.querySelector(".minus-month").addEventListener("click", () => {
    m = m - 1 < 0 ? 11 : m - 1;
    y = m === 11 ? y - 1 : y;
    drawCalendar(y, m);
  });

  document.querySelector(".plus-month").addEventListener("click", () => {
    m = m + 1 > 11 ? 0 : m + 1;
    y = m === 0 ? y + 1 : y;
    drawCalendar(y, m);
  });

  document.querySelector(".plus-year").addEventListener("click", () => {
    y++;
    drawCalendar(y, m);
  });

  document.querySelector(".minus-year").addEventListener("click", () => {
    y--;
    drawCalendar(y, m);
  });
});

// === 날짜 유틸 ===
function parseDate(str) {
  const [yyyy, mm, dd] = str.split("-");
  return new Date(yyyy, mm - 1, dd);
}

function getDateRangeArray(start, end) {
  const dateArr = [];
  const current = new Date(start);

  while (current <= end) {
    const yyyy = current.getFullYear();
    const mm = String(current.getMonth() + 1).padStart(2, "0");
    const dd = String(current.getDate()).padStart(2, "0");
    dateArr.push(`${yyyy}-${mm}-${dd}`);
    current.setDate(current.getDate() + 1);
  }

  return dateArr;
}



const holidays = [
	"2024-01-01", "2024-02-09", "2024-02-10", "2024-02-11", "2024-02-12",
	  "2024-03-01", "2024-05-05", "2024-05-06", "2024-05-15",
	  "2024-06-06", "2024-08-15",
	  "2024-09-16", "2024-09-17", "2024-09-18",
	  "2024-10-03", "2024-10-09", "2024-12-25",
	"2025-01-01", // 신정
	"2025-02-26", // 설 전날
	"2025-02-27", // 설날
	"2025-02-28", // 설 다음날
	"2025-03-01", // 삼일절
	"2025-05-05", // 어린이날
	"2025-05-06", // 어린이날 대체공휴일 (예상)
	"2025-05-11", // 석가탄신일
	"2025-06-06", // 현충일
	"2025-08-15", // 광복절
	"2025-10-03", // 개천절
	"2025-10-05", // 추석 전날
	"2025-10-06", // 추석
	"2025-10-07",
	"2025-10-08", // 추석 다음날
	"2025-10-09", // 한글날
	"2025-12-25",  // 성탄절
	"2025-06-03",  //대통령선거
	"2026-01-01", "2026-02-16", "2026-02-17", "2026-02-18",
	"2026-03-01", "2026-03-02",
	"2026-05-05", "2026-05-24", "2026-05-25",
	"2026-06-06", "2026-08-15", "2026-08-17",
	"2026-09-24", "2026-09-25", "2026-09-26", "2026-10-05",
	"2026-10-09", "2026-12-25"
];

const lunarHolidays = [
  { year: 2025, month: 1, day: 1 },   // 설날
  { year: 2025, month: 1, day: 2 },   // 설날 다음날
  { year: 2025, month: 1, day: 0 },   // 설날 전날 (1월 30일 기준 전날)
  { year: 2025, month: 4, day: 8 },   // 석가탄신일
  { year: 2025, month: 8, day: 14 },  // 추석 전날
  { year: 2025, month: 8, day: 15 },  // 추석
  { year: 2025, month: 8, day: 16 }   // 추석 다음날
];

// === 달력 렌더링 ===
function drawCalendar(year, month) {
  let now = new Date();
  let nowD = now.getDate();

  const monthNames = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12];
  document.getElementById("currentMonth").textContent = monthNames[month];
  document.getElementById("currentYear").textContent = year;

  const firstDay = new Date(year, month, 1).getDay();
  const last = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];
  if ((year % 4 === 0 && year % 100 !== 0) || year % 400 === 0) last[1] = 29;
  const lastDate = last[month];

  let cal = document.getElementById("cal");
  let draw = "<table class='calendar'>";
  let dNum = 1;

  for (let i = 0; i < 6; i++) {
    draw += "<tr>";
    for (let k = 0; k < 7; k++) {
      if ((i === 0 && k < firstDay) || dNum > lastDate) {
        draw += "<td class='day-box'>&nbsp;</td>";
      } else {
        const yyyy = year;
        const mm = String(month + 1).padStart(2, "0");
        const dd = String(dNum).padStart(2, "0");
        const dateStr = `${yyyy}-${mm}-${dd}`;

        const isToday = (year === now.getFullYear() && month === now.getMonth() && dNum === nowD);
        const isSunday = k === 0;
        const isSaturday = k === 6;
        const isHoliday = holidays.includes(dateStr);

        const todayClass = isToday ? " today" : "";
        const sundayClass = isSunday ? " sunday" : "";
        const saturdayClass = isSaturday ? " saturday" : "";
        const holidayClass = isHoliday ? " holiday" : "";

        draw += `<td class="day-box${todayClass}${sundayClass}${saturdayClass}${holidayClass}" data-date="${dateStr}">${dNum}</td>`;
        dNum++;
      }
    }
    draw += "</tr>";
  }
  draw += "</table>";
  cal.innerHTML = draw;

  const tripDates = [];
  (window.tripPlans || []).forEach(plan => {
    const start = parseDate(plan.tripStartDate);
    const end = parseDate(plan.tripFinishDate);
    const dates = getDateRangeArray(start, end);
    tripDates.push(...dates);
  });

  tripDates.forEach(date => {
    const cell = document.querySelector(`td[data-date="${date}"]`);
    if (cell) {
      cell.style.backgroundColor = "#e3effa";
    }
  });
}
