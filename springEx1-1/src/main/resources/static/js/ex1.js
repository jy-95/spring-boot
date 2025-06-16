
const Check = (event) => {
	
		const name = document.getElementById('name');
		const email = document.getElementById('email');
		const howmany = document.getElementById('longText');
		
		event.preventDefault();
		
		if(name.value == "") {
			alert("이름을 입력하세요.")
			return false;
		}
		
		if(email.value == "") {
			alert("이메일을 입력하세요.")
			return false;
				}
	      
		if(howmany.value.length<5){
			alert('5글자 이상 입력해주세요!');
			return false;
		}
		
		alert('성공')
		form.submit;
		
	    };
	
	
	
	const form = document.getElementById('myform');
    
	form.addEventListener('submit',Check);
	
	