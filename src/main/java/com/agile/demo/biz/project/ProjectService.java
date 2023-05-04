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

    public List<ProjectEntity> getAllProjects() { // entity -> dto로 값을 넣는 과정

            return projectRepository.findAll();
        }

        public void deleteProjectById(Long np_seq) {
            projectRepository.deleteById(np_seq);
        }

        public ProjectEntity createProject(ProjectDto projectDto) {

            ProjectEntity project = new ProjectEntity();
            project.setTitle(projectDto.getTitle());
            // 현재 로그인한 아이디를 넣는 방법으로, userId를 사용하는지, seq를 사용해서 저장하는지
            project.setAssign(projectDto.getAssign()); // 조회한 결과를 넣어야하는뎁!!!! accountproject의 내용이 들어갈 수 없음
            return projectRepository.save(project);
        }

    public ProjectEntity updateProject(long np_seq, ProjectDto projectDto) {
        // np_seq 값으로 프로젝트를 조회합니다.
        ProjectEntity projectEntity = projectRepository.findById(np_seq).get();
        
        // 같은 프로젝트있는 사람들 중에 선택할 수 있도록 변경하기


        // 프로젝트를 업데이트합니다.
        projectEntity.setTitle(projectDto.getTitle());
        projectEntity.setAssign(projectDto.getAssign());

        // 업데이트된 프로젝트를 저장하고 반환합니다.
        return projectRepository.save(projectEntity);
    }

        public void deleteProject(long np_seq) {
            // 프로젝트가 존재하는지 확인
            Optional<ProjectEntity> projectEntity = projectRepository.findById(np_seq);
            if (!projectEntity.isPresent()) {
                throw new EntityNotFoundException("Project not found with id " + np_seq);
            }

            // 프로젝트 삭제
            projectRepository.deleteById(np_seq);
        }

    public ProjectEntity getProjectById(long np_seq) {
        // np_seq 값으로 프로젝트를 조회합니다.
        return projectRepository.findById(np_seq)
                .get();
    }
}
