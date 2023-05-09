package com.agile.demo.biz.project;

import com.agile.demo.biz.project.account.AccountProjectDto;
import com.agile.demo.biz.project.account.AccountProjectEntity;
import com.agile.demo.biz.project.account.AccountProjectService;
import com.agile.demo.core.jwt.JwtPayload;
import com.agile.demo.core.jwt.JwtUtils;
import com.agile.demo.security.CustomAuthenticationProvider;
import com.auth0.jwt.JWT;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private AccountProjectService accountProjectService;

    @Autowired
    private CustomAuthenticationProvider customAuthenticationProvider;

    @PostMapping // 프로젝트 생성하는 경우
    public ResponseEntity<?> createProject(@RequestHeader Map<String, String> httpHeaders, @RequestBody ProjectDto projectDto) {

        String jwtToken = httpHeaders.get("authorization");

        JwtPayload jwtPayload = JwtUtils.initJwtPayload(jwtToken);

        Map<String, String> result = new HashMap<>();
        result.put("userId", Objects.requireNonNull(jwtPayload).getUserName());

        String userId = result.get("userId");

        ProjectEntity projectEntity = projectService.createProject(projectDto);
        System.out.println("entity 성공");

        // AccountProject에 추가하기
        AccountProjectDto accountProjectDto  = new AccountProjectDto();
        accountProjectDto.setAccountUserId(projectEntity.getAssign());
        accountProjectDto.setProjectSeq(projectEntity.getSeq());
        accountProjectService.createAccountProject(accountProjectDto);

        return ResponseEntity.accepted().build();
    }
    

    @PostMapping("/invite") // 프로젝트에 초대하는 경우
    public ResponseEntity<?> inviteProject(@RequestBody ProjectDto projectDto) { // userId, ProjectSeq 필요
        // AccountProject도 추가하기
        AccountProjectDto accountProjectDto  = new AccountProjectDto();
        accountProjectDto.setAccountUserId(projectDto.getAssign());
        accountProjectDto.setProjectSeq(projectDto.getNp_seq());
        accountProjectService.createAccountProject(accountProjectDto);
        return ResponseEntity.accepted().build();
    }
    
    @GetMapping // Project의 모든 내용 조회하기
    public ResponseEntity getAllProjects(@RequestHeader Map<String, String> httpHeaders) {
        String jwtToken = httpHeaders.get("authorization");

        JwtPayload jwtPayload = JwtUtils.initJwtPayload(jwtToken);

        Map<String, String> result = new HashMap<>();
        result.put("userId", Objects.requireNonNull(jwtPayload).getUserName());

        String userId = result.get("userId");
        return ResponseEntity.ok().body(projectService.getAllProjects(userId)) ;
    }

    @GetMapping("{np_seq}") // Project에서 np_seq로 조회하기
    public ResponseEntity<ProjectEntity> getProjectByNp_seq(@PathVariable long np_seq) {
        ProjectEntity projectEntity =projectService.getProjectByNp_seq(np_seq);
        return ResponseEntity.ok(projectEntity);
    }

    //userId에 따라서 리스트 출력하기`
//    @GetMapping("{userId}") // Project에서 np_seq로 조회하기
//    public ResponseEntity<ProjectEntity> getProjectByUserId(@PathVariable String userId) {
//        //user가 가입한 프로젝트들이 필요한거지?
//
//        ProjectEntity projectEntity = projectService.getProjectByNp_seq(np_seq);
//        return ResponseEntity.ok(projectEntity);
//    }

    // 특정 값을의 내용을 수정
    @PutMapping("{np_seq}") // Project의 내용 갱신하기
    public ResponseEntity<?> updateProject(@PathVariable long np_seq, @RequestBody ProjectDto projectDto) {
        ProjectEntity projectEntity = projectService.updateProject(np_seq, projectDto);
        return ResponseEntity.ok(projectEntity);
    }

    @DeleteMapping("{np_seq}") // Project의 내용 삭제하기 - 연관된 Backlog, Task, AccountProject의 내용 같이 삭제
    public ResponseEntity deleteProject(@PathVariable long np_seq){
        projectService.deleteProject(np_seq);
        return ResponseEntity.accepted().build();
    }

}
