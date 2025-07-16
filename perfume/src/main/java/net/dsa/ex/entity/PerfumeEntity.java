package net.dsa.ex.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "perfume")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class PerfumeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "no")
	private Integer no;
	
	@Column(name="name", nullable = false)
	private String name;
	
	@Column(name = "gender", length = 10)
	private String gender;
	
	@Column(name = "age", nullable = false)
	private Integer age;
	
	@Column(name = "favorite_scent", length = 50)
	private String favorite_scent;
	
	@Column(name = "favorite_brand", length = 50)
	private String favorite_brand;
	
	@Column(name = "usage_frequency", length = 50)
	private String usage_frequency;
	
	@Column(name = "purchase_budget", length = 50)
	private String purchase_budget;
	
	@Column(name = "comments", length = 200)
	private String comments;
	
	@CreatedDate
    private LocalDateTime completionTime ;	// 작성 시간
}
