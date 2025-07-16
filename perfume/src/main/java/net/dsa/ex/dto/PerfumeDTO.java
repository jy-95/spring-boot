package net.dsa.ex.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PerfumeDTO {

	private Integer no;
	private String name;
	private String gender;
	private Integer age;
	private String favorite_scent;
	private String favorite_brand;
	private String usage_frequency;
	private String purchase_budget;
	private String comments;
	private LocalDateTime completionTime ;
	
}
