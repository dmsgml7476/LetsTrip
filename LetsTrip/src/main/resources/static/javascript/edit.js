
function submitProfile() {
	const form = document.getElementById('profileForm');
	form.submit();
}

function toggleMbtiEditBox() {
    const box = document.getElementById('mbtiEditBox');
	
	
    box.style.display = (box.style.display === 'none') ? 'block' : 'none';
  }
  
function combineMbti(event) {
   const mbti1 = document.querySelector('input[name="mbti1"]:checked');
   const mbti2 = document.querySelector('input[name="mbti2"]:checked');
   const mbti3 = document.querySelector('input[name="mbti3"]:checked');
   const mbti4 = document.querySelector('input[name="mbti4"]:checked');

   if (!mbti1 || !mbti2 || !mbti3 || !mbti4) {
     alert("MBTI의 4가지 항목을 모두 선택해주세요.");
     event.preventDefault(); // 제출 막기
     return;
   }

   const finalMbti = mbti1.value + mbti2.value + mbti3.value + mbti4.value;
   document.getElementById('finalMbti').value = finalMbti;
}




//해시태그 ajax 방식으로 저장
document.querySelectorAll("#hashtagContainer .hashtag").forEach((tagElem) => {
  tagElem.addEventListener("click", () => {
    const tagId = tagElem.dataset.id;
    const isSelected = tagElem.classList.contains("selected");

    // 최대 5개 제한 검사 (선택할 때만)
    if (!isSelected && document.querySelectorAll(".hashtag.selected").length >= 6) {
      alert("해시태그는 최대 5개까지 선택 가능합니다.(MBTI 미포함)");
      return;
    }

    // 서버로 요청 보내기
    fetch(isSelected ? "/mypage/hashtag/delete" : "/mypage/hashtag", {
	      method: "POST",
	      headers: {
	        "Content-Type": "application/json"
	      },
	      body: JSON.stringify({ hashtagId: tagId })
	    })
	    .then((res) => {
	      if (!res.ok) throw new Error("서버 요청 실패");
	      return res.json();
	    })
	    .then((data) => {
	      if (data.success) {
	        tagElem.classList.toggle("selected"); // UI 반영
	      }
	    })
	    .catch((err) => {
	      console.error("해시태그 저장/삭제 실패:", err);
	    });
	  });
	});
	
	
	
/* 알림 동의 비동기 저장 */


document.querySelectorAll('input[name="agreedMyTripAlerts"], input[name="agreedMyTripAlerts"], input[name="agreedCommAlerts"]').forEach(input => {
	input.addEventListener("change", ()=>{
		const alertType = input.name === "agreedMyTripAlerts" ? "TRIP" : "COMM";
		const agreed = input.value === "true";
		
		fetch("/mypage/alerts/update", {
			method: "POST",
			headers: {
				"Content-Type" : "application/json"
			},
			body: JSON.stringify({
				alertType: alertType,
				agreed: agreed
			})
		})
		.then(res=> {
			if(!res.ok) throw new Error("저장 실패");
			return res.json();
		})
		.then(data=> {
			if (data.success) {
				console.log("알림 설정 저쟝 완료");
			}
		})
		.catch(err => {
			console.error("알림 설정 저장 실패:", err);
		});
	});
});




// 비번 모달창 열고 닫기

let currentEditType = ""; 

function openPasswordEditModalONE() {
	const modal=document.getElementById("openPasswordEditModalONE");
	modal.style.display = "block";
	currentEditType = "password";
}

function closePasswordEditModalONE() {
  const modal = document.getElementById("openPasswordEditModalONE");
  modal.style.display = "none";
  
  modal.querySelectorAll("input").forEach(input => {
      input.value = "";
  });
	
  const errorMsg = document.getElementById("pwErrorMsg");
  if (errorMsg) errorMsg.style.display = "none";
}

window.addEventListener("click", function(event) {
  const modal = document.getElementById("openPasswordEditModalONE");
  if (event.target === modal) {
    modal.style.display = "none";
  }
});

// 비밀번호 확인 및 넘어가기 (비번 변경)

function setupPasswordCheck(formId, passwordInputId, errorMsgId, nextModalCallback) {
  document.getElementById(formId).addEventListener("submit", function (event) {
    event.preventDefault();
    const password = document.getElementById(passwordInputId).value;

    fetch("/mypage/chkPw", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ password })
    })
      .then(res => {
        if (!res.ok) throw new Error("서버 오류");
        return res.json();
      })
      .then(data => {
        if (data.success) {
          nextModalCallback();
        } else {
          document.getElementById(errorMsgId).style.display = "block";
        }
      })
      .catch(err => {
        console.error("비밀번호 확인 실패", err);
      });
  });
}


setupPasswordCheck("pwChkForm-password", "currentPassword-password", "pwErrorMsg-password", nextToPasswordEditModalTWO);
setupPasswordCheck("pwChkForm-info", "currentPassword-info", "pwErrorMsg-info", nextToInfoEditModalTWO);


function nextToPasswordEditModalTWO() {
	const modalOne = document.getElementById("openPasswordEditModalONE");
	  modalOne.style.display = "none";

	  modalOne.querySelectorAll("input").forEach(input => {
	    input.value = "";
	  });

	  const errorMsg = document.getElementById("pwErrorMsg");
	  if (errorMsg) errorMsg.style.display = "none";

	  const modalTwo = document.getElementById("openPasswordEditModalTWO");
	  modalTwo.style.display = "block";
}

function closePasswordEditModalTWO() {
	const modal = document.getElementById("openPasswordEditModalTWO");
	 modal.style.display = "none";
	 
	 modal.querySelectorAll("input").forEach(input => {
	       input.value = "";
	 });
	 	
	 const errorMsg = document.getElementById("pwErrorMsg");
	 if (errorMsg) errorMsg.style.display = "none";
}



// 비밀번호 조건

document.addEventListener("DOMContentLoaded", function () {
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
	
		
	// 회원정보 수정 

});



// 회원정보 수정 


function openInfoEditModalONE() {
	const modal=document.getElementById("openInfoEditModalONE");
	modal.style.display = "block";
	currentEditType="info";
}

function closeInfoEditModalONE() {
  const modal = document.getElementById("openInfoEditModalONE");
  modal.style.display = "none";
  
  modal.querySelectorAll("input").forEach(input => {
      input.value = "";
  });
	
  const errorMsg = document.getElementById("pwErrorMsg");
  if (errorMsg) errorMsg.style.display = "none";
}

window.addEventListener("click", function(event) {
  const modal = document.getElementById("openInfoEditModalONE");
  if (event.target === modal) {
    modal.style.display = "none";
  }
});


function nextToInfoEditModalTWO() {
	const modalOne = document.getElementById("openInfoEditModalONE");
	  modalOne.style.display = "none";

	  modalOne.querySelectorAll("input").forEach(input => {
	    input.value = "";
	  });

	  const errorMsg = document.getElementById("pwErrorMsg");
	  if (errorMsg) errorMsg.style.display = "none";

	  const modalTwo = document.getElementById("openInfoEditModalTWO");
	  modalTwo.style.display = "block";
}


function closeInfoEditModalTWO() {
	const modal=document.getElementById("openInfoEditModalTWO");
	modal.style.display="none";
	modal.querySelectorAll("input").forEach(input => {
	      input.value = "";
	  });
		
	  const errorMsg = document.getElementById("pwErrorMsg");
	  if (errorMsg) errorMsg.style.display = "none";
}