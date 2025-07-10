
// Modal 열기
function orderFunc() {
	
	//치킨, 수량 선택하기
	const chickentype = document.querySelector("#chickenType");
	const chickentypeText = chickentype.options[chickentype.selectedIndex].text;

	const chickenPrice = document.querySelector("#chickenType").value;
	
	const deliveryRadios = document.querySelectorAll('input[name="deliveryType"]');
	let deliveryText = "";
	let deliveryPrice = 0;

	deliveryRadios.forEach(radio => {
		if (radio.checked) {
			deliveryText = radio.nextElementSibling.textContent.trim();
			deliveryPrice = parseInt(radio.value);
		}
	});
	
	const extraOptions = document.querySelectorAll('input[name="extraOption"]');
		let extraOptionsText = "";
		let optionPrice = 0;

		extraOptions.forEach(check => {
			if (check.checked) {
				extraOptionsText += check.nextElementSibling.textContent.trim() + "<br>";
				optionPrice += parseInt(check.value);
			}
		});
	
		const quantity = document.querySelector('#quantity');	
		
	const totalPrice = parseInt(chickentype.value) * parseInt(quantity.value) + deliveryPrice 
							+ optionPrice;
	
	
	
	const error = document.querySelector('#error');

		if(chickentype.value==""){
			error.innerHTML = '치킨 종류를 선택하세요';
			error.style.color = 'red';
			document.querySelector("#receiptModal").style.display = "none";
		}else if(quantity.value==""){
			error.innerHTML = '수량은 1 이상이어야 합니다';
			document.querySelector("#receiptModal").style.display = "none";
		}else{
			error.innerHTML = '';
			document.querySelector("#receiptChickenType").innerHTML = chickentypeText;
			document.querySelector("#receiptQuantity").innerHTML = quantity.value + "개";
			document.querySelector("#receiptExtras").innerHTML = extraOptionsText;
			document.querySelector("#receiptDelivery").innerHTML = deliveryText;
			document.querySelector("#receiptTotal").innerHTML = totalPrice;
			
			
			const orderform = document.querySelector('#orderForm');
			orderform.querySelector('input[name="chickenType"]').value = chickentypeText;
			orderform.querySelector('input[name="chickenPrice"]').value = chickenPrice;
			orderform.querySelector('input[name="quantity"]').value = quantity.value;
			orderform.querySelector('input[name="extraOptions"]').value = extraOptionsText.replaceAll("<br>", "\n").trim();
			orderform.querySelector('input[name="extraTotalPrice"]').value = optionPrice;
			orderform.querySelector('input[name="deliveryType"]').value = deliveryText;
			orderform.querySelector('input[name="deliveryPrice"]').value = deliveryPrice;
			orderform.querySelector('input[name="totalPrice"]').value = totalPrice;
			
			
			document.querySelector("#receiptModal").style.display = "flex";
		}
	//끝
}

// Modal 닫기
function closeModal() {
	document.querySelector("#receiptModal").style.display = "none";
}
