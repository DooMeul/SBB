// ============================================================================
// [DI 연습 3단계(annotation)] 실행용 main
//  - car-config.xml 설정 파일로 스프링 컨테이너(ApplicationContext)를 직접 만들고,
//    컨테이너에서 OrderManager Bean 을 꺼내서 사용 (new 를 한 번도 안 씀!)
//  - 연결 파일 : src/main/resources/car-config.xml (컴포넌트 스캔 범위 지정)
//  - 결과 : 기아차 → K5
// ============================================================================
package com.mysite.sbb.common.annotation.main;

import com.mysite.sbb.common.annotation.code.CarMaker;
import com.mysite.sbb.common.annotation.code.HyundaiMaker;
import com.mysite.sbb.common.annotation.code.OrderManager;
import org.springframework.context.ApplicationContext;                        // 스프링 컨테이너 인터페이스
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;   // XML 설정 파일로 컨테이너를 만드는 클래스

public class AnnotationMain {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("car-config.xml"); // resources/car-config.xml 읽어서 컨테이너 생성
                                                                                            // → @Component/@Service 붙은 클래스들 자동 생성 + 주입

        OrderManager orderManager = (OrderManager) context.getBean("orderManager", OrderManager.class); // 이름이 "orderManager" 인 Bean 꺼내기
        System.out.println("maager : " + orderManager);   // 꺼낸 객체 출력 (스프링이 만든 객체인지 확인)
        orderManager.order();                             // 주문 실행
    }
}
