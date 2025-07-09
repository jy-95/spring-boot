/**
 * 
 */

window.onload = function(){
	let h2 = document.querySelector('h2');
	
	h2.addEventListener('click', function(){
		location.href = '/web4/guest/guestList';
	})
}

function checkForm() {
	let name  = document.querySelector("#name");
	let password = document.querySelector("#password");
	
	// 이름 5글자 이상
	if (name.value.length < 5) {
	  alert("이름은 5자 이상 입력해주세요.");
	  name.focus();
	  name.select();
	  return false;
	}
	
	// 비밀번호 5글자 이상
	if (password.value.length < 5) {
	  alert("비밀번호는 5자 이상 입력해주세요.");
	  pw.focus();
	  pw.select();
	  return false;
	}
	
	return true; // 모든 검사를 통과했을 때만 제출 허용
}