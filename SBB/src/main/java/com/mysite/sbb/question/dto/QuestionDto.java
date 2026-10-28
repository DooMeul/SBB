package com.mysite.sbb.question.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionDto {
    @NotEmpty(message = "제목은 필수 항목입니다.")
    @Size(max = 150, message = "제목은 150자 이내로 입력하세요.")
    private String subject;

    @NotEmpty(message = "내용은 필수 항목입니다.")
    private String content;
}
