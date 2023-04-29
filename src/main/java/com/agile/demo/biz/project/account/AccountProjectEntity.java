package com.agile.demo.biz.project.account;

import com.agile.demo.biz.account.AccountEntity;
import com.agile.demo.biz.project.ProjectEntity;
import com.agile.demo.core.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountProjectEntity extends BaseEntity { // 어떤 사람이 어떤 프로젝트에 가입했는지를 유일하게 아는 클래스, entity
    @Id
    private Long seq;

    @OneToOne
    @JoinColumn (name="account_seq", referencedColumnName="userId") // 삭제하면 같이 삭제되는 부분추가
    private AccountEntity accounts;

    @ManyToOne
    @JoinColumn (name="project_seq", referencedColumnName="np_seq") // 삭제하면 같이 삭제되는 부분추가
    private ProjectEntity projectS;
}

