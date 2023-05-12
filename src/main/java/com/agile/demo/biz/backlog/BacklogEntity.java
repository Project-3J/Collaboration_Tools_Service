package com.agile.demo.biz.backlog;
import com.agile.demo.biz.project.ProjectEntity;
import com.agile.demo.core.base.BaseEntity;
import com.fasterxml.jackson.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "NBacklog")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "seq")
public class BacklogEntity extends BaseEntity {

    @Column(nullable = false, updatable = true, length = 100)
    private String title;

    // deadline 추가하기 - 캘린더 형식으로 보이도록할 예정
    @Column(nullable = true, updatable = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate deadline;

    @Column(nullable = true, updatable = true, length = 255)
    private String description;

    @Column(nullable = false, updatable = true)
    private  String projectTitle;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "project_seq", referencedColumnName = "seq")
    private ProjectEntity project; // ProjectEntity의 mapper by와 이름을 맞춤

}