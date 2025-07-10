package net.dsa.ex4.service;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dsa.ex4.dto.ChickenDTO;
import net.dsa.ex4.entity.ChickenEntity;
import net.dsa.ex4.repository.ChickenRepository;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class ChickenService {
	
	private final ChickenRepository cr;
	
	public void save(ChickenDTO dto) {
		ChickenEntity entity = ChickenEntity.builder()
							   .id(dto.getId())
							   .chickenType(dto.getChickenType())
							   .chickenPrice(dto.getChickenPrice())
							   .quantity(dto.getQuantity())
							   .extraOptions(dto.getExtraOptions())
							   .extraTotalPrice(dto.getExtraTotalPrice())
							   .deliveryType(dto.getDeliveryType())
							   .deliveryPrice(dto.getDeliveryPrice())
							   .totalPrice(dto.getTotalPrice())
							   .build();
		cr.save(entity);
		log.debug("entity : {}",entity);
	}

}
