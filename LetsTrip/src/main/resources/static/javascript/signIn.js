document.addEventListener("DOMContentLoaded", function () {
  // 모달 열기
  document.getElementById("findIdBtn").addEventListener("click", function (e) {
    e.preventDefault();
    openFindIdModal();
  });

  document.getElementById("findPwBtn").addEventListener("click", function (e) {
    e.preventDefault();
    openFindPwModal();
  });

  // 아이디 찾기용 변수 및 로직
  const emailInput = document.getElementById("emailInput");
  const sendCodeBtn = document.getElementById("sendChkNum");
  const emailMsg = document.getElementById("chkEmailMsg");
  const codeInput = document.getElementById("emailCodeInput");
  const chkCodeBtn = document.getElementById("chkNumCheck");
  const codeMsg = document.getElementById("chkCodeMsg");
  const nextBtn = document.querySelector("#findIdModal .next-btn");

  emailInput.addEventListener("input", () => {
    sendCodeBtn.disabled = emailInput.value.trim() === "";
  });

  sendCodeBtn.addEventListener("click", function (e) {
    e.preventDefault();
    const email = emailInput.value.trim();
    if (!email) return;

    fetch("/auth/email/send", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ email, context: "find" })
    })
      .then(res => res.json())
      .then(data => {
        if (data.status === "sent") {
          emailMsg.textContent = "인증번호를 전송했습니다.";
          emailMsg.style.color = "green";
        } else if (data.status === "not_found") {
          emailMsg.textContent = "일치하는 이메일이 없습니다.";
          emailMsg.style.color = "red";
        } else {
          emailMsg.textContent = "이메일 전송에 실패했습니다.";
          emailMsg.style.color = "red";
        }
        emailMsg.style.display = "block";
        codeInput.focus();
      })
      .catch(() => {
        emailMsg.textContent = "서버 오류가 발생했습니다.";
        emailMsg.style.color = "red";
        emailMsg.style.display = "block";
      });
  });

  chkCodeBtn.addEventListener("click", function () {
    const code = codeInput.value.trim();
    if (!code) {
      codeMsg.textContent = "인증번호를 입력해주세요.";
      codeMsg.style.color = "red";
      codeMsg.style.display = "block";
      return;
    }

    fetch("/auth/email/verify", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ code })
    })
      .then(res => res.json())
      .then(data => {
        if (data.success) {
          codeMsg.textContent = "인증되었습니다!";
          codeMsg.style.color = "green";
          nextBtn.disabled = false;
        } else {
          codeMsg.textContent = "인증번호가 일치하지 않습니다.";
          codeMsg.style.color = "red";
          nextBtn.disabled = true;
        }
        codeMsg.style.display = "block";
      });
  });

  // 비밀번호 찾기용 변수 및 로직
  const pwEmailInput = document.getElementById("PwFindEmailInput");
  const pwSendCodeBtn = document.getElementById("sendPwFindChkNum");
  const pwEmailMsg = document.getElementById("chkPwFindEmailMsg");
  const pwCodeInput = document.getElementById("PwFindEmailCodeInput");
  const pwChkCodeBtn = document.getElementById("chkPwFindNumCheck");
  const pwCodeMsg = document.getElementById("chkPwFindCodeMsg");
  const pwNextBtn = document.querySelector("#findPwModal .next-btn");

  pwEmailInput.addEventListener("input", () => {
    pwSendCodeBtn.disabled = pwEmailInput.value.trim() === "";
  });

  pwSendCodeBtn.addEventListener("click", function (e) {
    e.preventDefault();
    const email = pwEmailInput.value.trim();
    if (!email) return;

    fetch("/auth/email/send", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ email, context: "pw" })
    })
      .then(res => res.json())
      .then(data => {
        if (data.status === "sent") {
          pwEmailMsg.textContent = "인증번호를 전송했습니다.";
          pwEmailMsg.style.color = "green";
        } else if (data.status === "not_found") {
          pwEmailMsg.textContent = "일치하는 이메일이 없습니다.";
          pwEmailMsg.style.color = "red";
        } else {
          pwEmailMsg.textContent = "이메일 전송에 실패했습니다.";
          pwEmailMsg.style.color = "red";
        }
        pwEmailMsg.style.display = "block";
        pwCodeInput.focus();
      });
  });

  pwChkCodeBtn.addEventListener("click", function () {
    const code = pwCodeInput.value.trim();
    if (!code) {
      pwCodeMsg.textContent = "인증번호를 입력해주세요.";
      pwCodeMsg.style.color = "red";
      pwCodeMsg.style.display = "block";
      return;
    }

    fetch("/auth/email/verify", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ code })
    })
      .then(res => res.json())
      .then(data => {
        if (data.success) {
          pwCodeMsg.textContent = "인증되었습니다!";
          pwCodeMsg.style.color = "green";
          pwNextBtn.disabled = false;
        } else {
          pwCodeMsg.textContent = "인증번호가 일치하지 않습니다.";
          pwCodeMsg.style.color = "red";
          pwNextBtn.disabled = true;
        }
        pwCodeMsg.style.display = "block";
      });
  });
  
  
  
  // 비밀번호 조건
  
  const pwInput = document.getElementById("newPassword");
  	 const pwMsg = document.getElementById("pwCheckMsg");

  	 const pwChkInput = document.getElementById("pwChk");
  	 const pwChkMsg = document.getElementById("pwMismatchMsg");
  	 
  	 const changeBtn = document.getElementById("changePwBtn");

  	 let isPwValid = false;
  	 let isPwMatch = false;
  	 
  	pwInput.addEventListener("input", function() {
  		const pw= pwInput.value.trim();
  		
  		if (pw === "") {
  			pwMsg.textContent="";
  			pwMsg.style.display = "none";
  			    isPwValid = false;
  			    checkPwMatch();
  			return;
  		}
  		
  		const lengthValid = pw.length >= 8 && pw.length <=20;
  		const containsLetter = /[A-Za-z]/.test(pw);
  		const containsDigit = /\d/.test(pw);
  		const allowedCharsOnly = /^[A-Za-z\d!@#$%^&*]+$/.test(pw);
  		
  		
  		let messages=[];
  		
  		if (!lengthValid) {
  		     messages.push("비밀번호는 8자 이상 20자 이하로 입력해주세요.");
  		   } else if (!(containsLetter && containsDigit)) {
  		     messages.push("비밀번호에는 영문자와 숫자를 모두 포함해야 합니다.");
  		   }

  		   if (!allowedCharsOnly) {
  		     messages.push("특수문자는 !,@,#,$,%,^,&,* 만 사용할 수 있습니다.");
  		   }

  		   if (messages.length > 0) {
  		     pwMsg.innerHTML = messages.join("<br>");
  		     pwMsg.style.color = "red";
  			 pwMsg.style.display = "block";
  			 isPwValid = false;
  		   } else {
  		     pwMsg.textContent = "";
  			 pwMsg.style.display = "none";
  			 isPwValid = true;
  		   }

  		   checkPwMatch();
  	});
  	 
  	pwChkInput.addEventListener("input", checkPwMatch);
  	
  	function checkPwMatch() {
  		const pw = pwInput.value.trim();
  		   const pwChk = pwChkInput.value.trim();

  		   if (pw && pwChk && pw === pwChk) {
  		     pwChkMsg.textContent = "비밀번호가 일치합니다.";
  		     pwChkMsg.style.color = "green";
  			 pwChkMsg.style.display = "block";
  			 isPwMatch = true;
  		   } else if (pwChk.length > 0) {
  		     pwChkMsg.textContent = "비밀번호가 일치하지 않습니다.";
  		     pwChkMsg.style.color = "red";
  			 pwChkMsg.style.display = "block";
  			 isPwMatch = false;
  		   } else {
  		     pwChkMsg.textContent = "";
  			 pwChkMsg.style.display = "none";
  			 isPwMatch = false;
  		   }
  		   
  		   checkValidState();
  	}
  	
  	function checkValidState() {
  			changeBtn.disabled = !(isPwValid && isPwMatch);
  		}
});

function openFindIdModal() {
  const modal = document.getElementById("findIdModal");
  modal.style.display = "block";
}

function closeFindIdModal() {
  const modal = document.getElementById("findIdModal");
  modal.style.display = "none";
  modal.querySelectorAll("input").forEach(input => input.value = "");
  const emailMsg = document.getElementById("chkEmailMsg");
  if (emailMsg) emailMsg.style.display = "none";
  const codeMsg = document.getElementById("chkCodeMsg");
  if (codeMsg) codeMsg.style.display = "none";
  const nextBtn = document.querySelector("#findIdModal .next-btn");
  if (nextBtn) nextBtn.disabled = true;
}

function openIdPrintModal(email) {
  fetch(`/api/find-id?email=${encodeURIComponent(email)}`)
    .then(res => res.json())
    .then(data => {
      const printBox = document.querySelector(".found-id-print p");
      printBox.textContent = data.loginId || "아이디 없음";
      document.getElementById("idPrintModal").style.display = "block";
    });
}



function goToIdPrintModal() {
  const email = document.getElementById("emailInput").value.trim();
  closeFindIdModal();
  openIdPrintModal(email);
}

function closeIdPrintModal() {
  const modal = document.getElementById("idPrintModal");
  modal.style.display = "none";
}

function openFindPwModal() {
  const modal = document.getElementById("findPwModal");
  modal.style.display = "block";
}


function goToPwChangeModal() {
	const email = document.getElementById("PwFindEmailInput").value.trim();
	document.getElementById("changePwEmail").value = email;
	closeFindPwModal();
	openChangePwModal();
}

function goToFindPwModal() {
	closeIdPrintModal();
	openFindIdModal();
}


function closeFindPwModal() {
  const modal = document.getElementById("findPwModal");
  modal.style.display = "none";
  modal.querySelectorAll("input").forEach(input => input.value = "");
  const emailMsg = document.getElementById("chkPwFindEmailMsg");
    if (emailMsg) emailMsg.style.display = "none";

    const codeMsg = document.getElementById("chkPwFindCodeMsg");
    if (codeMsg) codeMsg.style.display = "none";

    const nextBtn = document.querySelector("#findPwModal .next-btn");
    if (nextBtn) nextBtn.disabled = true;
}

function openChangePwModal() {
	const modal = document.getElementById("changePwModal");
	modal.style.display = "block";
}

function closeChangePwModal() {
	const modal=document.getElementById("changePwModal");
	
	modal.style.display="none";
}