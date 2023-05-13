package com.agile.demo.biz.project;

import com.agile.demo.biz.account.AccountEntity;
import com.agile.demo.biz.project.account.AccountProjectEntity;
import com.agile.demo.biz.project.account.AccountProjectRepository;
import com.agile.demo.biz.task.TaskEntity;
import com.agile.demo.biz.task.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
    public class ProjectService {

        @Autowired
        private ProjectRepository projectRepository;

        @Autowired
        private AccountProjectRepository accountprojectRepository;

    public ProjectEntity createProject(ProjectDto projectDto, String userId) { // project 생성하기

        ProjectEntity project = new ProjectEntity();
        project.setTitle(projectDto.getTitle());
        // 현재 로그인한 아이디를 넣는 방법이 있는지 알아보기
        project.setAssign(userId);
        return projectRepository.save(project);
    }

    public List<ProjectEntity> getAllProjects(String userId) { // project의 모든 내용 조회

            return projectRepository.findByAssign(userId);
        }

    public ProjectEntity getProjectByNp_seq(long np_seq) { // project를 np_seq로 조회하기
        // np_seq 값으로 프로젝트를 조회합니다.
        return projectRepository.findById(np_seq)
                .get();
    }

    public ProjectEntity updateProject(long np_seq, ProjectDto projectDto) { // project의 내용 갱신하기
        // np_seq 값으로 프로젝트를 조회합니다.
        ProjectEntity projectEntity = projectRepository.findById(np_seq).get();
        
        // 같은 프로젝트있는 사람들 중에 선택할 수 있도록 변경하기

        // 프로젝트를 업데이트합니다.
        projectEntity.setTitle(projectDto.getTitle());
        //projectEntity.setAssign(projectDto.getAssign());

        // 업데이트된 프로젝트를 저장하고 반환합니다.
        return projectRepository.save(projectEntity);
    }

        public void deleteProject(long np_seq) { // project 삭제하기
            // 프로젝트가 존재하는지 확인
            Optional<ProjectEntity> projectEntity = projectRepository.findById(np_seq);
            if (!projectEntity.isPresent()) {
                throw new EntityNotFoundException("Project not found with id " + np_seq);
            }

            // 프로젝트 삭제
            projectRepository.deleteById(np_seq);
        }


}
