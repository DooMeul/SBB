// ============================================================================
// [파일 역할] 스프링 부트 기본 테스트 - 애플리케이션이 정상적으로 뜨는지(설정 오류 없는지)만 확인
//  - DB(MySQL)가 켜져 있어야 통과함 (application.yml 의 datasource 설정 사용)
// ============================================================================
package com.mysite.sbb;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest     // 실제 스프링 컨테이너를 띄워서 테스트
class SbbApplicationTests {

    @Test
    void contextLoads() {   // 내용 없음 → 에러 없이 실행되면 성공
    }

}
