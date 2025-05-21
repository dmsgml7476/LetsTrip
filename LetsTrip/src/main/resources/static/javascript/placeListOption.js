
const typeSelect = document.getElementById("typeSelect");
const placeSelect = document.getElementById("placeSelect");

typeSelect.addEventListener('change', () => {
   const selectedType = typeSelect.value;
   
   
   placeSelect.innerHTML='<option value="">장소 선택</option>';
   
   placeInfo.forEach(placeInfo=>{
      if(placeInfo.categoryId===parseInt(selectedType)){
         const option = document.createElement('option');
         option.value = placeInfo.placeId;
         option.textContent=placeInfo.placeName;
         placeSelect.appendChild(option);
         
      }
   });
}); 

function saveSelection() {
  const type = document.getElementById('typeSelect').value;
  const location = document.getElementById('placeSelect').value;

  if (type && location) {
    localStorage.setItem('selectedType', typeSelect);
    localStorage.setItem('selectedLocation', placeSelect);
    alert('저장되었습니다.');
  } else {
    alert('모든 항목을 선택하세요.');
  }
}

function nextPage() {
  // 예: 다음 페이지로 이동
  window.location.href = 'placeDetail.html';
}
window.onload = function () {
    const type = localStorage.getItem('selectedType');
    const location = localStorage.getItem('selectedLocation');

    if (type && location) {
      document.getElementById('result').innerText = `유형: ${type}, 장소: ${location}`;
    } else {
      document.getElementById('result').innerText = '저장된 정보가 없습니다.';
    }
  }
  
 
  document.addEventListener("DOMContentLoaded", function () {
    const scheduleButtons = document.querySelectorAll(".schedule");

    scheduleButtons.forEach((btn) => {
      btn.addEventListener("click", function () {
        // 모든 버튼에서 active 제거
        scheduleButtons.forEach((b) => b.classList.remove("active"));

        // 현재 클릭한 버튼에 active 추가
        this.classList.add("active");
      
      });
    });
   
   const selectedDayInput = document.getElementById("selectedDayInput");

   scheduleButtons.forEach((btn) => {
     btn.addEventListener("click", function () {
       scheduleButtons.forEach((b) => b.classList.remove("active"));
       this.classList.add("active");

       const day = this.dataset.day;
       selectedDayInput.value = day;
     });
   });

   // 기본값 (1일차 선택된 상태)
   selectedDayInput.value = 1;
   
   const savedPlaces = JSON.parse(localStorage.getItem('selectedPlaces')) || [];

   savedPlaces.forEach(({ placeId, placeName, day }) => {
       const ul = document.getElementById(`day${day}List`);
       if (ul) {
           const li = document.createElement("li");
           li.textContent = placeName;
           li.setAttribute("data-place-id", placeId);
           ul.appendChild(li);
       }
   });
  });

