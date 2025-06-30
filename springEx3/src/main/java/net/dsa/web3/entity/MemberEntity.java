package net.dsa.web3.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
// JPA(Java Persistence API) 에서 데이터베이스 테이블과 매핑되는 자바 클래스를 정의하기 위해 사용되는 어노테이션
@Entity
// Entity가 매핑될 테이블을 지정. 생략하면 클래스 이름을 테이블 이름으로 사용)
@Table(name="member3")
public class MemberEntity {
	
	/*
	 	JPA는 Java 언어에서 객체(Entity)를 관계형 데이터베이스에 영속(persist)시키기 위한 표준 API
	 	즉, Java 언어로 데이터를 DB에 영구화하기 위한 API
	 */
	@Id
	@Column(name = "id", nullable = false, length = 30)
	private String id;
	@Column(name = "pw", length = 50)
	private String pw;
	@Column(name = "name")
	private String name;
	@Column(name = "phone")
	private String phone;
	@Column(name = "address")
	private String address;

}
