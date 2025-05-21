// 요소 선택
const modal = document.getElementById('csMgmtAnsModal');
const modalQuestionDate = document.getElementById('modalQuestionDate');
const modalCsOption = document.getElementById('modalCsOption');
const modalCategory = document.getElementById('modalCategory');
const modalCustomerId = document.getElementById('modalCustomerId');
const modalStatus = document.getElementById('modalStatus');
const modalQuestionText = document.getElementById('modalQuestionText');
const modalAnswer = document.getElementById('modalAnswer');
/*const statusSelect = document.querySelector('.cs-ans-status select');*/
const delayButton = document.querySelector('.cs-ans-delay');
const sendButton = document.querySelector('.cs-ans-close');

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
/*
// 상태 드롭다운에 따른 동작 제어
statusSelect.addEventListener('change', function () {
  const selected = this.value;

  if (selected === 'WAITING') {
    delayButton.disabled = true;
    sendButton.disabled = true;
    modalAnswer.disabled = true;
    modalAnswer.value = '';
  } else if (selected === 'IN_PROGRESS') {
    delayButton.disabled = false;
    sendButton.disabled = true;
    modalAnswer.disabled = true;
  } else if (selected === 'ANSWERED') {
    delayButton.disabled = true;
    sendButton.disabled = false;
    modalAnswer.disabled = false;
  }
});*/

// place-row 클릭 시 모달 오픈 및 값 주입
document.querySelectorAll('.place-row').forEach(row => {
  row.addEventListener('click', function () {
    const csOption = this.getAttribute('data-cs-csOption');
    const category = this.getAttribute('data-cs-category');
    const status = this.getAttribute('data-cs-status');
    const answer = this.getAttribute('data-cs-answerText');
	const csId = this.getAttribute('data-cs-id');
	const sendButton = document.querySelector('.cs-ans-close');
	
	
	if (status === 'ANSWERED') {
	  sendButton.textContent = '수정';
	} else {
	  sendButton.textContent = '전송';
	}
	
	document.getElementById('modalCsId').value = csId;

    // 선택된 행 표시용 (id 저장 목적)
    document.querySelectorAll('.place-row').forEach(r => r.classList.remove('selected'));
    this.classList.add('selected');

    modalQuestionDate.innerHTML =
      this.getAttribute('data-cs-questionDate') +
      "<br/>" +
      this.getAttribute('data-cs-questionTime');

    modalCsOption.textContent = csOptionMap[csOption] || csOption;
    modalCategory.textContent = categoryMap[category] || category;
    modalCustomerId.textContent = this.getAttribute('data-cs-loginId');
    modalStatus.textContent = statusMap[status] || status;
    modalQuestionText.textContent = this.getAttribute('data-cs-questionText');
    modalAnswer.value = (answer && answer.trim() !== "") ? answer : "";

/*    // 상태 셀렉트 초기화
    statusSelect.value = status;
    statusSelect.dispatchEvent(new Event('change'));*/
	
/*	if (!statusSelect.value) {
	  statusSelect.value = status;
	}*/

    modal.style.display = 'block';
  });
});




function closeCsMgmtAnsModal() {
  modal.style.display = "none";
}
