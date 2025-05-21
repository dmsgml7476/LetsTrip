document.addEventListener("DOMContentLoaded", function () {
  const checkBtn = document.getElementById("checkIdBtn");
  const loginIdInput = document.getElementById("loginId");
  const msgBox = document.getElementById("idCheckMsg");

  const pwInput = document.getElementById("password");
  const pwMsg = document.getElementById("pwCheckMsg");

  const pwChkInput = document.getElementById("pwChk");
  const pwChkMsg = document.getElementById("pwChkCheckMsg");

  checkBtn.addEventListener("click", function () {
    const loginId = loginIdInput.value.trim();
    const regex = /^[a-z0-9_]+$/;

    if (!loginId) {
      msgBox.textContent = "아이디를 입력해주세요.";
      msgBox.style.color = "red";
      return;
    }

    if (loginId.length < 6 || loginId.length > 16) {
      msgBox.textContent = "아이디는 6자 이상 16자 이하로 입력해주세요.";
      msgBox.style.color = "red";
      return;
    }

    if (!regex.test(loginId)) {
      msgBox.textContent =
        "아이디는 소문자, 숫자, 언더바(_)만 사용할 수 있습니다.";
      msgBox.style.color = "red";
      return;
    }

    fetch(`/api/check-id?loginId=${encodeURIComponent(loginId)}`)
      .then((res) => res.json())
      .then((data) => {
        if (data.available) {
          msgBox.textContent = "사용 가능한 아이디입니다.";
          msgBox.style.color = "green";
        } else {
          msgBox.textContent = "이미 사용 중인 아이디입니다.";
          msgBox.style.color = "red";
        }
      })
      .catch(() => {
        msgBox.textContent = "서버 오류가 발생했습니다.";
        msgBox.style.color = "red";
      });
  });

  pwInput.addEventListener("input", function () {
    const pw = pwInput.value.trim();

    if (pw === "") {
      pwMsg.textContent = "";
      return;
    }

    const lengthValid = pw.length >= 8 && pw.length <= 20;
    const containsLetter = /[A-Za-z]/.test(pw);
    const containsDigit = /\d/.test(pw);
    const allowedCharsOnly = /^[A-Za-z\d!@#$%^&*]+$/.test(pw);

    let messages = [];

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
    } else {
      pwMsg.textContent = "";
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
    } else if (pwChk.length > 0) {
      pwChkMsg.textContent = "비밀번호가 일치하지 않습니다.";
      pwChkMsg.style.color = "red";
    } else {
      pwChkMsg.textContent = "";
    }
  }
  
  
  // 이메일 인증
  
  
  const emailInput = document.getElementById("emailInput");
      const emailSendMsg = document.getElementById("emailSendMsg");
      const sendBtn = document.getElementById("sendChkNum");

      const emailCodeInput = document.getElementById("emailCodeInput");
      const verifyBtn = document.getElementById("chkNumCheck");
      const verifyMsg = document.getElementById("emailVerifyMsg");

      sendBtn.addEventListener("click", () => {
        const email = emailInput.value.trim();
        if (!email) {
          emailSendMsg.textContent = "이메일을 입력해주세요.";
          emailSendMsg.style.color = "red";
          return;
        }

		fetch("/auth/email/send", {
		  method: "POST",
		  headers: { "Content-Type": "application/json" },
		  body: JSON.stringify({ 
			email: email,
			context: "signup" }),
		})
		  .then((res) => res.json())
		  .then((data) => {
		    if (data.status === "sent" || data.status === "not_found") {
		      emailSendMsg.textContent = "인증번호를 전송했습니다.";
		      emailSendMsg.style.color = "green";
		      emailCodeInput.focus(); // 여기서 포커스
		    } else if (data.status === "exists") {
		      emailSendMsg.textContent = "이미 사용 중인 이메일입니다.";
		      emailSendMsg.style.color = "red";
		    } else if (data.status === "fail") {
		      emailSendMsg.textContent = "이메일 전송에 실패했습니다.";
		      emailSendMsg.style.color = "red";
		    } else {
		      emailSendMsg.textContent = "예기치 않은 오류가 발생했습니다.";
		      emailSendMsg.style.color = "red";
		    }
		  })
		  .catch(() => {
		    emailSendMsg.textContent = "서버 오류가 발생했습니다.";
		    emailSendMsg.style.color = "red";
		  });

      });

      verifyBtn.addEventListener("click", () => {
        const code = emailCodeInput.value.trim();
        if (!code) {
          verifyMsg.textContent = "인증번호를 입력해주세요.";
          verifyMsg.style.color = "red";
          return;
        }

		fetch("/auth/email/verify", {
		  method: "POST",
		  headers: { "Content-Type": "application/json" },
		  body: JSON.stringify({ code }),
		})
		  .then((res) => res.json())
		  .then((data) => {
		    if (data.success) {
		      verifyMsg.textContent = "이메일 인증 성공.";
		      verifyMsg.style.color = "green";
		    } else {
		      verifyMsg.textContent = "인증번호가 일치하지 않습니다.";
		      verifyMsg.style.color = "red";
		    }
		  });

      });
	  
	  // 닉네임

	    const nicknameInput = document.getElementById("nicknameInput");
	    const nicknameChkMsg = document.getElementById("nicknameChkMsg");
		
		
		nicknameInput.addEventListener("blur", function () {
		  const nickname = nicknameInput.value.trim();

		  if (!nickname) {
		    nicknameChkMsg.textContent = "닉네임을 입력해주세요.";
		    nicknameChkMsg.style.color = "red";
		    return;
		  }

		  fetch(`/api/check-nickname?nickname=${encodeURIComponent(nickname)}`)
		    .then((res) => res.json())
		    .then((data) => {
		      if (data.available) {
		        nicknameChkMsg.textContent = "사용 가능한 닉네임입니다.";
		        nicknameChkMsg.style.color = "green";
		      } else {
		        nicknameChkMsg.textContent = "이미 사용 중인 닉네임입니다.";
		        nicknameChkMsg.style.color = "red";
		      }
		    })
		    .catch(() => {
		      nicknameChkMsg.textContent = "서버 오류가 발생했습니다.";
		      nicknameChkMsg.style.color = "red";
		    });
		});
  
		// 주소

		window.sample6_execDaumPostcode = function () {
		  new daum.Postcode({
		    oncomplete: function (data) {
		      let addr =
		        data.userSelectedType === "R" ? data.roadAddress : data.jibunAddress;
		      document.getElementById("sample6_address").value = addr;
		    },
		  }).open();
		};

		// 전화번호

		const telInput = document.getElementById("telInput");
		const telCheckMsg = document.getElementById("telCheckMsg");

		telInput.addEventListener("blur", function () {
		  const tel = telInput.value.trim();
		  const telRegex = /^\d{3}-\d{3,4}-\d{4}$/;

		  if (!tel) {
		    telCheckMsg.textContent = "전화번호를 입력해주세요.";
		    telCheckMsg.style.color = "red";
		    return;
		  }

		  if (!telRegex.test(tel)) {
		    telCheckMsg.textContent =
		      "전화번호 형식이 올바르지 않습니다. 예: 010-1234-5678";
		    telCheckMsg.style.color = "red";
		  } else {
		    telCheckMsg.textContent = "";
		  }
		  
		  let value = telInput.value.replace(/[^0-9]/g, ""); // 숫자만 남김

		   if (value.length <= 3) {
		     telInput.value = value;
		   } else if (value.length <= 7) {
		     telInput.value = value.replace(/(\d{3})(\d{1,4})/, "$1-$2");
		   } else {
		     telInput.value = value.replace(/(\d{3})(\d{3,4})(\d{1,4})/, "$1-$2-$3");
		   }
		});
		
		//   해시태그

		const selectedInput = document.getElementById("selectedHashtags");
		const selectedIds = new Set();

		document.querySelectorAll("#hashtagContainer .hashtag").forEach((tagElem) => {
		  tagElem.addEventListener("click", () => {
		    const tagId = tagElem.dataset.id;

		    if (tagElem.classList.contains("selected")) {
		      tagElem.classList.remove("selected");
		      selectedIds.delete(tagId);
		    } else {
		      if (selectedIds.size >= 5) {
		        alert("해시태그는 최대 5개까지 선택 가능합니다.(mbti 미포함)");
		        return;
		      }
		      tagElem.classList.add("selected");
		      selectedIds.add(tagId);
		    }

		    selectedInput.value = [...selectedIds].join(",");
		  });
		});
		
		function updateMbtiTag() {
		  const mbti =
		    document.querySelector('input[name="mbti1"]:checked')?.value +
		    document.querySelector('input[name="mbti2"]:checked')?.value +
		    document.querySelector('input[name="mbti3"]:checked')?.value +
		    document.querySelector('input[name="mbti4"]:checked')?.value;

		  if (mbti.length === 4) {
		    document.querySelectorAll(".hashtag").forEach((tagElem) => {
		      if (tagElem.textContent === `#${mbti}`) {
		        tagElem.classList.add("selected");
		        selectedIds.add(tagElem.dataset.id);
		        selectedInput.value = [...selectedIds].join(",");
		      }
		    });
		  }
		}

		// 라디오 버튼 변경 시 MBTI 해시태그 자동 선택
		document.querySelectorAll('.mbti-selector input[type="radio"]').forEach((el) => {
		  el.addEventListener("change", updateMbtiTag);
		});
		
		// 기존의 회원가입에 추가
		const form = document.querySelector("form");
		const idCheckMsg = document.getElementById("idCheckMsg");

		form.addEventListener("submit", function (e) {

		  
		  const loginId = loginIdInput.value.trim();
		  const pw = pwInput.value.trim();
		  const pwChk = pwChkInput.value.trim();
		  const email = emailInput.value.trim();
		  const code = emailCodeInput.value.trim();
		  const idMsg = idCheckMsg.textContent.trim();
		  const emailVerified = verifyMsg.textContent.includes("성공");  // 이메일 인증 성공 문구 체크

		  if (!loginId) {
		    alert("아이디를 입력해주세요.");
		    loginIdInput.focus();
		    e.preventDefault();
		    return;
		  }

		  if (idMsg === "이미 사용 중인 아이디입니다." || idMsg === "아이디를 입력해주세요." || idMsg === "") {
		    alert("아이디 중복 확인을 해주세요.");
		    loginIdInput.focus();
		    e.preventDefault();
		    return;
		  }

		  if (!pw) {
		    alert("비밀번호를 입력해주세요.");
		    pwInput.focus();
		    e.preventDefault();
		    return;
		  }

		  if (pw !== pwChk) {
		    alert("비밀번호가 일치하지 않습니다.");
		    pwChkInput.focus();
		    e.preventDefault();
		    return;
		  }

		  if (!email) {
		    alert("이메일을 입력해주세요.");
		    emailInput.focus();
		    e.preventDefault();
		    return;
		  }

		  if (!code || !emailVerified) {
		    alert("이메일 인증을 완료해주세요.");
		    emailCodeInput.focus();
		    e.preventDefault();
		    return;
		  }
		});
  
  
});
