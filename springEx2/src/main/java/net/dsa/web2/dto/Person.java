package net.dsa.web2.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data	// getter, setter, toString, equals 머시기 머시기를 만들어 주는 것(종합체)
@AllArgsConstructor	//person(name, age, phone) 생성자 만들어줌
@NoArgsConstructor	// 기본 생성자 person() 만들어줌
public class Person {

	String name;
	int age;
	String phone;
}
