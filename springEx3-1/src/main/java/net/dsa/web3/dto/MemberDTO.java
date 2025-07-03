package net.dsa.web3.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.dsa.web3.entity.MemberEntity;


	@Data
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	public class MemberDTO {
		private String id;
		private String pw;
		private String name;
		private String phone;
		private String address;
		
		public static void convertEntity_to_DTO(MemberEntity entity, MemberDTO dto) {
			dto.setId(entity.getId());
			dto.setId(entity.getId());
			dto.setPw(entity.getPw());
			dto.setName(entity.getName());
			dto.setPhone(entity.getPhone());
			dto.setAddress(entity.getAddress());
		}
		
		public static void convertDTO_to_Entity(MemberDTO dto, MemberEntity entity) {
			entity.setId(dto.getId());
			entity.setId(dto.getId());
			entity.setPw(dto.getPw());
			entity.setName(dto.getName());
			entity.setPhone(dto.getPhone());
			entity.setAddress(dto.getAddress());
			
		}
		

		public static MemberEntity convertDTO_to_Entity2(MemberDTO dto) {
			MemberEntity entity = new MemberEntity();
			entity.setId(dto.getId());
			entity.setId(dto.getId());
			entity.setPw(dto.getPw());
			entity.setName(dto.getName());
			entity.setPhone(dto.getPhone());
			entity.setAddress(dto.getAddress());
			return entity;
			
		}

	}

