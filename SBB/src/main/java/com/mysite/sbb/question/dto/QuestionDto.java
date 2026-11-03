// ============================================================================
// [파일 역할] QuestionDto = 질문 등록 "입력 폼"의 값을 담아 오는 그릇(DTO: Data Transfer Object)
//  + 입력값 검증(Validation) 규칙도 여기에 작성
//
//  [연결 관계]
//   - 화면 : templates/question/inputForm.html
//            <form th:object="${questionDto}"> 의 th:field="*{subject}", th:field="*{content}" 와 필드명이 같아야 함!
//   - 컨트롤러 : QuestionController.createQuestion(@Valid QuestionDto questionDto, BindingResult ...)
//              → @Valid 가 붙어서 아래 @NotEmpty, @Size 규칙을 검사함
//   - 서비스 : QuestionService.createQuestion(QuestionDto) 에서 Question 엔티티로 변환해서 저장
//   - 필요 라이브러리 : build.gradle 의 spring-boot-starter-validation
//
//  ※ 엔티티(Question)를 폼에 직접 쓰지 않고 DTO 를 따로 만드는 이유:
//     화면 입력용 데이터와 DB 저장용 데이터를 분리해서 안전하게 관리하기 위해
// ============================================================================
package com.mysite.sbb.question.dto;

import jakarta.validation.constraints.NotEmpty;  // 빈 값("" 또는 null) 금지 검증 어노테이션
import jakarta.validation.constraints.Size;      // 글자 수 제한 검증 어노테이션
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data                 // getter + setter + toString + equals/hashCode 한 번에 생성 (폼 값 바인딩에 setter 필요)
@NoArgsConstructor    // 기본 생성자 (스프링이 폼 데이터를 담을 때 new QuestionDto() 로 먼저 만듦)
@AllArgsConstructor   // 전체 필드 생성자
@Builder              // QuestionDto.builder().subject("..").content("..").build() 사용 가능 (테스트 코드에서 사용)
public class QuestionDto {
    @NotEmpty(message = "제목은 필수 항목입니다.")               // 제목이 비어 있으면 이 메시지가 에러로 등록됨
    @Size(max = 150, message = "제목은 150자 이내로 입력하세요.") // 150자 초과 시 에러
    private String subject;   // 폼의 name="subject" 값이 자동으로 들어옴

    @NotEmpty(message = "내용은 필수 항목입니다.")               // 내용이 비어 있으면 에러
    private String content;   // 폼의 name="content" 값이 자동으로 들어옴
}
