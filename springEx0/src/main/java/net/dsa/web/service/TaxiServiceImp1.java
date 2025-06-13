package net.dsa.web.service;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service("TaxiServiceImpl")
@Primary		//여러 개의 구현체 중에 가방 기본으로 사용할 객체 지정
public class TaxiServiceImp1 
	implements TransportationService {

	
	@Override
	public void move() {
//		System.out.println("콜 수신"); 			// 부가 기능
		System.out.println("택시를 운행합니다."); 	// 핵심 기능
//		System.out.println("결제 처리"); 			// 부가 기능
	}
}
