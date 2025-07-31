package net.dsa.web6.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.extern.slf4j.Slf4j;
import net.dsa.web6.dto.PersonDTO;

@Controller
@Slf4j
public class Ajax2Controller {

	/**
	 * ajaxTest2.html 이동
	 */
	@GetMapping("ajax2")
	public String ajax2() {
		return "ajax/ajaxTest2";
	}
	
	/**
	 * parameter를 각각의 변수로 전달
	 * @param name
	 * @param age
	 * @param phone
	 */
	@ResponseBody
	@PostMapping("formSubmit1")
	public void formSubmit1(
			@RequestParam("name") String name,
			@RequestParam("age") String age,
			@RequestParam("phone") String phone) {
		log.debug("formSubmit1: {}, {}, {}", name, age, phone);
	}
	
	/**
	 * parameter를 DTO 객체로 전달받기
	 * @param PersonDTO
	 */
	@ResponseBody
	@PostMapping("formSubmit2")
	public void formSubmit2(
			// @RequestBody	파라미터	JSON > 자바 객체로 변환(요청 처리용)
			// @ReponseBody	메서드	자바 객체 > JSON으로 변환(응답 처리용)
			@RequestBody PersonDTO person) {
		log.debug("formSubmit2: {}", person);
		
	}
	
	/**
	 * parameter를 DTO 객체로 받기
	 * @param PersonDTO
	 */
	@ResponseBody
	@PostMapping("formSubmit3")
	public void formSubmit3(PersonDTO person) {
		log.debug("formSubmit3; {}", person);
	}
	
	/**
	 * 객체 리턴
	 * @return PersonDTO
	 */
	@ResponseBody
	@GetMapping("getObject")
	public PersonDTO getObject() {
		return new PersonDTO("홍길동", 20, "010-1111-2222");
	}
	
	/**
	 * 리스트 리턴
	 * @return List<PersonDTO>
	 */
	@ResponseBody
	@GetMapping("getList")
	public List<PersonDTO> getList(){
		ArrayList<PersonDTO> list = new ArrayList<>();
		list.add(new PersonDTO("홍길동", 20, "010-1111-2222"));
		list.add(new PersonDTO("강감찬", 30, "010-2222-3333"));
		list.add(new PersonDTO("이순신", 40, "010-3333-4444"));
		return list;
	}
	
	/**
	 * 배열 받기
	 * @param String[] ar or List<String>
	 */
	@ResponseBody
	@PostMapping("sendArray")
	public void sendArray(
			// @RequestParam("ar") String[] ar) {
			   @RequestParam("ar") List<String> ar) {
		for (String s : ar) {
			log.debug("배열 요소: {}", s);
		}
	}
	
	/**
	 * 단순한 데이터는 쿼리 문자열(URL encoded 방식)로 쉽게 표현하고,
	 * 복잡한 구조(객체 배열)은 JSON으로 전송해야 안정적으로 처리 가능
	 * @param List<PersonDTO>
	 * @return List<PersonDTO>
	 */
	@ResponseBody
	@PostMapping("sendObjectArray")
	public List<PersonDTO> sendObjectArray(
			@RequestBody List<PersonDTO> personList
			){
		log.debug("personList: {}", personList);
		
		return personList;
	}
	
}
