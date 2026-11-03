// ============================================================================
// [파일 역할] AnswerDto = 답변 작성 폼에서 입력한 값을 받아오는 DTO + 검증 규칙
//
//  [연결 관계]
//   - 화면 : templates/question/detail.html 하단 "답변 작성" 폼
//            <form th:object="${answerDto}"> ... <textarea th:field="*{content}">
//   - 컨트롤러 :
//       QuestionController.detail(..., AnswerDto answerDto, ...)  → 빈 폼 객체를 화면에 넘겨줌
//       AnswerController.createAnswer(..., @Valid AnswerDto answerDto, BindingResult ...) → 입력값 받아서 검증
//   - 서비스 : AnswerService.createAnswer(question, answerDto) 에서 Answer 엔티티로 변환
// ============================================================================
package com.mysite.sbb.answer.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data                 // getter/setter/toString 등 자동 생성
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnswerDto {
    @NotEmpty(message = "내용은 필수 항목입니다.")  // 답변 내용이 비어 있으면 이 메시지가 detail.html 에 빨간 글씨로 표시됨
    private String content;                       // textarea name="content" 값이 들어옴
}
