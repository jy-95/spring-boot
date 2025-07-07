package net.dsa.web4.service;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dsa.web4.repository.GuestRepository;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class GuestService {

	private final GuestRepository repository;
	
}
