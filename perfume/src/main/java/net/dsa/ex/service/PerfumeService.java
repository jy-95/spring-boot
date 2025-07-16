package net.dsa.ex.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dsa.ex.dto.PerfumeDTO;
import net.dsa.ex.entity.PerfumeEntity;
import net.dsa.ex.repository.PerfumeRepository;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class PerfumeService {
	
	private final PerfumeRepository pr;

	public void submit(PerfumeDTO dto) {

		PerfumeEntity entity = PerfumeEntity.builder().build();
		
		entity.setName(dto.getName());
		entity.setGender(dto.getGender());
		entity.setAge(dto.getAge());
		entity.setFavorite_scent(dto.getFavorite_scent());
		entity.setFavorite_brand(dto.getFavorite_brand());
		entity.setUsage_frequency(dto.getUsage_frequency());
		entity.setPurchase_budget(dto.getPurchase_budget());
		entity.setComments(dto.getComments());
		
		pr.save(entity);
	}

	public List<PerfumeDTO> selectAll() {
		
		Sort sort = Sort.by(
				Sort.Order.desc("gender"),
				Sort.Order.asc("age"),
				Sort.Order.desc("completionTime")
			);

		List<PerfumeEntity> entityList = pr.findAll(sort);
		List<PerfumeDTO> dtoList = new ArrayList<>();

		for (PerfumeEntity entity : entityList) {
			PerfumeDTO dto = PerfumeDTO.builder()
								 	   .no(entity.getNo())
								 	   .name(entity.getName())
								 	   .gender(entity.getGender())
								 	   .age(entity.getAge())
								 	   .favorite_scent(entity.getFavorite_scent())
								 	   .favorite_brand(entity.getFavorite_brand())
								 	   .usage_frequency(entity.getUsage_frequency())
								 	   .purchase_budget(entity.getPurchase_budget())
								 	   .comments(entity.getComments())
								 	   .completionTime(entity.getCompletionTime())
								 	   .build();
				dtoList.add(dto);
			}

			return dtoList;
		
	}

}
