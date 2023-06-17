package com.agile.demo.biz.project.account;

import com.agile.demo.biz.account.AccountEntity;
import com.agile.demo.biz.project.ProjectEntity;
import com.agile.demo.core.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Data
@EqualsAndHashCode(callSuper=false) // @Data에서 경고 사라짐
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "seq")
public class AccountProjectEntity extends BaseEntity { // 어떤 사람이 어떤 프로젝트에 가입했는지를 유일하게 아는 클래스, entity

    @ManyToOne
    @JoinColumn (name="account_seq", referencedColumnName="userId") // 삭제하면 같이 삭제되는 부분추가
    private AccountEntity accounts;

    @ManyToOne
    @JoinColumn (name="project_seq", referencedColumnName="seq") // 삭제하면 같이 삭제되는 부분추가
    @JsonBackReference
    private ProjectEntity projects;
}

