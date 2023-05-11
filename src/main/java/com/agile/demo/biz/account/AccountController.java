package com.agile.demo.biz.account;

import com.agile.demo.biz.backlog.BacklogDto;
import com.agile.demo.biz.backlog.BacklogEntity;
import com.agile.demo.core.jwt.JwtPayload;
import com.agile.demo.core.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.net.URI;
import java.util.*;

@Transactional
@RestController
@RequestMapping("/accounts")
public class AccountController {
    @Autowired
    private AccountService accountService;

    // 구글에서 정보 받아와서 작업 하는 이유로 -> 잠시 멈추기
    // 계정 생성시 - 정보 저장, 동일한 id로 가입할 수 없음
    @PostMapping
    public ResponseEntity<?> createAccount(@RequestBody AccountDto AccountDto) {
        // 미리 동일한 id가 있는지 확인하는 로직이 필요할지? - 동일 아이디 존재 여부 확인 안하면 넣을때 오류가 발생함!
        // => 동일한 id가 있으면 아이디가 존재한다고 ㄱㄱ
        // return ResponseEntity.실패 이런식으로 if문 안에 작성할 것
        // 회원 가입에 입력한 정보 저장
        // 비밀번호를 저장할때 암호화해서 저장해야하는지 여부 필요
        AccountEntity accountEntity = accountService.createAccount(AccountDto);

        return ResponseEntity.accepted().build();
    }

    @GetMapping // Account의 개인내용 출력
    public Optional<AccountEntity> getAllAccount(@RequestHeader Map<String, String> httpHeaders) {
        String jwtToken = httpHeaders.get("authorization");
        String userId = getUserId(jwtToken);
        return accountService.getAllAccounts(userId);
    }
    
    
    // update 하는 내용 추가 필요
    @PutMapping
    public ResponseEntity<?> updateBacklog(@RequestHeader Map<String, String> httpHeaders, @RequestBody AccountDto accountDto) {
        String jwtToken = httpHeaders.get("authorization");
        String userId = getUserId(jwtToken);
        AccountEntity accountEntity = accountService.updateAccount(userId, accountDto);
        return ResponseEntity.ok(accountEntity);
    }

    @DeleteMapping
    public void deleteAccount(@RequestHeader Map<String, String> httpHeaders){
        String jwtToken = httpHeaders.get("authorization");
        String userId = getUserId(jwtToken);
        accountService.deleteAccount(userId);
    }

    public String getUserId(String jwtToken){
        JwtPayload jwtPayload = JwtUtils.initJwtPayload(jwtToken);

        Map<String, String> result = new HashMap<>();
        result.put("userId", Objects.requireNonNull(jwtPayload).getUserName());

        return result.get("userId");
    }

}
