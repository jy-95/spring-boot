
// Modal 열기
function orderFunc(event) {
	
	// 객체 가져오기
	const name = document.querySelector("#name");
	const gender = document.querySelector("#gender");
	const ageInput = document.querySelector("#age");
	const age = parseInt(document.querySelector("#age").value);
	const favorite_scent = document.querySelector("#favorite_scent");
	const usage_frequency = document.querySelector("input[name='usage_frequency']:checked");
	const favorite_brand = document.querySelector("#favorite_brand");
	const purchase_budget = document.querySelector("#purchase_budget");
	const comments = document.querySelector("#comments");
	const form = document.querySelector("#submitForm");
	
	// 유효성 검사
	if (!name.value){
		event.preventDefault();
		alert("이름을 입력하세요.");
		name.focus();
		name.select();
		return false;
	}
	
	if (!gender.value || gender.value == ""){
		event.preventDefault();
		alert("성별을 입력하세요.");
		gender.focus();
		gender.select();
		return false;
	}
	
	if (isNaN(age) || age < 1){
		event.preventDefault();
		alert("올바른 나이를 입력하세요.");
		ageInput.focus();
		ageInput.select();
		return false;
	}
	
	if (!favorite_scent.value || favorite_scent.value == ""){
		event.preventDefault();
		alert("좋아하는 향을 입력하세요.");
		favorite_scent.focus();
		favorite_scent.select();
		return false;
		}
	
	if (!favorite_brand.value){
		event.preventDefault();
		alert("선호하는 브랜드를 입력하세요.");
		favorite_brand.focus();
		favorite_brand.select();
		return false;
	}
	
	// 폼 데이터 채우기
	// form.속성명 형태로 접근 > form 요소 내부에 name 속성이 있는 요소들만 가능
	form.name.value 			= name.value;
	form.gender.value 			= gender.value;
	form.age.value 				= age;
	form.favorite_scent.value 	= favorite_scent.value;
	form.usage_frequency.value = usage_frequency.value;
	form.favorite_brand.value 	= favorite_brand.value;
	form.purchase_budget.value 	= purchase_budget.value;
	form.comments.value 		= comments.value;
	
	// console.log(name.value, favorite_scent.value, purchase_budget.value);
	
	return true;
}
