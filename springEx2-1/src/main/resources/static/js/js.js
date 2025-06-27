
function validateLoginForm(event){
	const id = document.querySelector("#inputId").value;
	const pw = document.querySelector("#inputPw").value;
	const pwCheck = document.querySelector("#inputPwCheck").value;
	const name = document.querySelector("#inputName").value;
	const phone = document.querySelector("#inputPhone").value;
	
	if(id.length < 2 || id.length > 15 || is.indexOf('!','@','#','$','%','^','&','*','(',')','_','+','-','=','{','}',';',':','?')==0){
		event.preventDefault();
		alert("아이디는 3~14자 영문, 숫자, 특수문자를 포함할 수 있습니다.");
		return;
	}
	
	if(pw.length === 0 || pwCheck.length === 0 || pw!==pwCheck){
		event.preventDefault();
		alert("비밀번호가 비어 있거나 일치하지 않습니다.");
		return; 
	}
	
	if(name.length === 0){
		event.preventDefault();
		alert("이름을 입력해주세요.");
		return; 
	}
	

	if(phone.length === 0){
		event.preventDefault();
		alert("핸드폰 번호를 입력해주세요.");
		return; 
	}
	if(phone.indexOf('-') == -1){
		event.preventDefault();
		alert("잘못된 핸드폰 번호 양식입니다. (000-0000-0000)");
		return; 
	}
	
	
	
}