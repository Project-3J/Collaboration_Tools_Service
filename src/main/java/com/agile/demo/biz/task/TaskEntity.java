package com.agile.demo.biz.task;
import com.agile.demo.biz.backlog.BacklogEntity;
import com.agile.demo.biz.project.ProjectEntity;
import com.agile.demo.biz.project.account.AccountProjectEntity;
import com.agile.demo.core.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "NTask")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskEntity extends BaseEntity {

    @Column(nullable = false, updatable = true, length = 100)
    private String title;

    @Column(nullable = true, updatable = true)
    private Integer storyProgress;

    @Column(nullable = true, updatable = true, length = 255)
    private String description;


    // deadline 추가하기 - dropdown 형식으로 보이도록할 예정
    @Column(nullable = true, updatable = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate deadline;

    // accountProjectEntity에서 가져오도록?
    @ManyToOne
    @JoinColumn(name = "na_seq1", nullable = true)
    private AccountProjectEntity presenter;

    @ManyToOne
    @JoinColumn(name = "na_seq2", nullable = true)
    private AccountProjectEntity manager; // AccountProjectEntity에서 값을 받아오는 형태로?

    // accountProjectEntity에서 가져오도록?
//    @ManyToMany
//    @JoinColumn(name = "na_seq", nullable = true)
//    private List<AccountProjectEntity> accountrPoject;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "project_seq", referencedColumnName = "seq")
    private ProjectEntity project;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "nb_seq")
    private BacklogEntity backlogEntity;
}