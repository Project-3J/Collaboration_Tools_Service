package com.agile.demo.biz.project;

import com.agile.demo.biz.backlog.BacklogEntity;
import com.agile.demo.biz.task.TaskEntity;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@Getter
@Setter
public class ProjectDto {
    private long np_seq;

    private String title;

    private String assign;

    private LocalDate deadline;

    private List<BacklogEntity> backlogs;

    private List<TaskEntity> tasks;

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
