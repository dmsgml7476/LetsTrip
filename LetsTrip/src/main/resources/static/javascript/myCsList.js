const modal = document.getElementById('csDetailModal');
const modalQuestionDate = document.getElementById('modalQuestionDate');
const modalCsOption = document.getElementById('modalCsOption');
const modalCategory = document.getElementById('modalCategory');
const modalStatus = document.getElementById('modalStatus');
const modalQuestionText = document.getElementById('modalQuestionText');
const modalAnswerText = document.querySelector('.cs-ans-text');
const modalAnswerDate = document.querySelector('.cs-ans-date');

// ✅ 한글 매핑 (옵션, 카테고리, 상태)
const csOptionMap = {
  COMPLAIN: "불편사항",
  ETC: "기타"
};

const categoryMap = {
  STORY: "스토리",
  TRAVEL: "장소",
  ETC: "기타"
};

const statusMap = {
  WAITING: "접수",
  IN_PROGRESS: "처리중",
  ANSWERED: "답변완료"
};

// ✅ 각 행 클릭 시 모달 오픈 및 데이터 세팅
document.querySelectorAll('.place-row').forEach(row => {
  row.addEventListener('click', function () {
    const csOption = this.getAttribute('data-cs-csOption');
    const category = this.getAttribute('data-cs-category');
    const status = this.getAttribute('data-cs-status');
    const questionText = this.getAttribute('data-cs-questionText');
    const questionDate = this.getAttribute('data-cs-questionDate');
    const questionTime = this.getAttribute('data-cs-questionTime');
    const answerText = this.getAttribute('data-cs-answerText');
    const answerDate = this.getAttribute('data-cs-answerDate'); // ❗ DTO에 있어야 함

    modalQuestionDate.innerHTML = `${questionDate}<br/>${questionTime}`;
    modalCsOption.textContent = csOptionMap[csOption] || csOption;
    modalCategory.textContent = categoryMap[category] || category;
    modalStatus.textContent = statusMap[status] || status;
    modalQuestionText.textContent = questionText;
    modalAnswerText.textContent = answerText || '아직 답변이 없습니다.';
    modalAnswerDate.textContent = answerDate || '-';

    modal.style.display = 'block';
  });
});

// ✅ 모달 닫기 함수
function closeCsDetailModal() {
  modal.style.display = 'none';
}