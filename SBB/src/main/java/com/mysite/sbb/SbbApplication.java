// ============================================================================
// [파일 역할] 스프링 부트 애플리케이션의 "시작점(main)" 클래스
//  - 이 파일을 실행(Run)하면 내장 톰캣 서버가 켜지고 http://localhost:8080 으로 접속 가능
//  - 포트 번호, DB 접속 정보 등은 src/main/resources/application.yml 에서 설정
//  - 이 클래스가 있는 패키지(com.mysite.sbb)와 그 "하위 패키지"만 자동으로 스캔됨
//    → 새 Controller/Service/Repository 를 만들 때는 반드시 com.mysite.sbb 아래에 만들 것!
// ============================================================================
package com.mysite.sbb;                                         // 이 파일이 속한 패키지(폴더 경로)

import org.springframework.boot.SpringApplication;               // 스프링 부트 앱을 실행시켜 주는 클래스
import org.springframework.boot.autoconfigure.SpringBootApplication; // 자동설정 + 컴포넌트 스캔을 한 번에 켜는 어노테이션

@SpringBootApplication  // = @Configuration + @EnableAutoConfiguration + @ComponentScan
                        //   → @Controller, @Service, @Repository, @Component 붙은 클래스를 찾아 자동으로 객체(Bean) 생성
public class SbbApplication {

    // 자바 프로그램의 진입점. IntelliJ 에서 ▶ 버튼을 누르면 여기서부터 실행됨
    public static void main(String[] args) {
        SpringApplication.run(SbbApplication.class, args);       // 스프링 컨테이너 생성 + 웹서버(톰캣) 기동
    }

}
