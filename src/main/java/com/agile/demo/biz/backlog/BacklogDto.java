package com.agile.demo.biz.backlog;

import com.agile.demo.biz.project.ProjectEntity;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Getter
@Setter
public class BacklogDto {

    private Long nb_seq;

    private String title;

    private LocalDate deadline;

    private String description;

    private Long projectSeq;

    private String projectTitle;

    public void setDeadline(String deadline) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            this.deadline = LocalDate.parse(deadline, formatter);
        } catch (DateTimeParseException e) {
            // 예외 처리 코드 추가
            System.out.println("잘못된 날짜 형식입니다.");
            e.printStackTrace();
        }
    }
}
