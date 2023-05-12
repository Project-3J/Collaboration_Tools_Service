package com.agile.demo.biz.account;

import com.agile.demo.core.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "seq")
public class AccountEntity extends BaseEntity {

    @Column(unique = true)
    private String userId;
    private String password;

    private String role;

    private String name;

    private String phone;

    private String email;
}
