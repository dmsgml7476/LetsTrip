console.log("✅ placeSave.js 실행됨");
  // 2. 중복 제거한 상위 지역만 추출해서 select 채우기
  const upperRegionSelect = document.getElementById('upperRegionSelect');
  const subRegionSelect = document.getElementById('subRegionSelect');

  // 상위 지역 중복 제거용 Set
  const upperRegions = [...new Set(regionList.map(r => r.upperRegion))];

  // 초기 상위 지역 select 태그 재구성
  upperRegionSelect.innerHTML = '<option value="">상위 지역</option>';
  upperRegions.forEach(upper => {
    const option = document.createElement('option');
    option.value = upper;
    option.textContent = upper;
    upperRegionSelect.appendChild(option);
  });

  // 상위 지역이 바뀔 때 세부 지역 갱신
  upperRegionSelect.addEventListener('change', function () {
    const selectedUpper = this.value;

    // 세부 지역 초기화
    subRegionSelect.innerHTML = '<option value="">세부 지역</option>';

    // 선택한 상위 지역의 세부 지역만 필터링하여 추가
    regionList.forEach(region => {
      if (region.upperRegion === selectedUpper) {
        const option = document.createElement('option');
        option.value = region.regionId;
        option.textContent = region.regionName;
        subRegionSelect.appendChild(option);
      }
    });
  });
  
  
  
  
  function normalizeProvinceName(address) {
	const table = {
	  "서울": "서울특별시",
	  "부산": "부산광역시",
	  "대구": "대구광역시",
	  "인천": "인천광역시",
	  "광주": "광주광역시",
	  "대전": "대전광역시",
	  "울산": "울산광역시",
	  "세종": "세종특별자치시",
	  "경기": "경기도",
	  "강원": "강원특별자치도",
	  "충북": "충청북도",
	  "충남": "충청남도",
	  "전북": "전라북도",
	  "전남": "전라남도",
	  "경북": "경상북도",
	  "경남": "경상남도",
	  "제주": "제주특별자치도"
	};

	for (let shortForm in table) {
	  if (address.startsWith(shortForm)) {
	    return address.replace(shortForm, table[shortForm]);
	  }
	}
	return address;
}
	



	
function sample6_execDaumPostcode() {
	  new daum.Postcode({
	    oncomplete: function (data) {
	      if (!data.roadAddress) {
	        alert("❗ 도로명 주소가 존재하지 않습니다. 다른 주소를 선택해 주세요.");
	        return;
	      }

	      // 도로명 주소 그대로 입력창에 반영
	      document.getElementById('sample6_address').value = data.roadAddress;

	      // 상세 주소 입력란으로 포커스 이동
	      document.getElementById('sample6_detailAddress').focus();
	    }
	  }).open();
}
  
document.getElementById('placeForm').addEventListener('submit', async function(e) {
	e.preventDefault(); // 기본 제출 막기

	const baseAddr = document.getElementById('sample6_address').value.trim();
	const detail = document.getElementById('sample6_detailAddress').value.trim();
	const fullAddress = baseAddr + ' ' + detail;

	document.getElementById('searchableAddress').value = baseAddr;
	document.getElementById('fullAddress').value = fullAddress;
	document.getElementById('hiddenUpperRegion').value = document.getElementById('upperRegionSelect').value;

	console.log("✅ 사용자용 주소:", fullAddress);

	const coords = await getCoordinatesByAddress(fullAddress);
	if (coords) {
	  document.getElementById('placeLongitude').value = parseFloat(coords.x);
	  document.getElementById('placeLatitude').value = parseFloat(coords.y);
	  console.log("✅ 좌표 변환 결과:", coords.x, coords.y);
	  this.submit(); // 수동 제출
	} else {
	  alert("❗ 좌표 변환 실패, 주소를 다시 확인해주세요.");
	}
});

async function getCoordinatesByAddress(address) {

    const REST_API_KEY = window.REST_API_KEY;
	const url = `https://dapi.kakao.com/v2/local/search/address.json?query=${encodeURIComponent(address)}`;
	
	try {
		console.log("📌 요청 시작", url);
		const response = await fetch(url, {
			method: "GET",
			headers: {
				Authorization: `KakaoAK ${REST_API_KEY}`
			}
		});
		
		const data = await response.json();
		console.log("📌 카카오 응답 데이터:", data);
		
		if (data.documents.length > 0) {
			const x = data.documents[0].x;
			const y = data.documents[0].y;
			
			return {x, y};
		} else {
			alert("좌표를 찾을 수 없습니다. 주소를 다시 확인해주세요.");
			return null;
		}
	} catch(error) {
		console.error("좌표 변환 중 오류 발생 : ", error);
		return null;
	}
}


