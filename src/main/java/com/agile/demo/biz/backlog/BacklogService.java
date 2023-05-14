package com.agile.demo.biz.backlog;

import com.agile.demo.biz.project.ProjectEntity;
import com.agile.demo.biz.project.ProjectRepository;
import com.agile.demo.biz.project.account.AccountProjectEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BacklogService {
    @Autowired
    private BacklogRepository backlogRepository;

    @Autowired
    private ProjectRepository projectRepository;

    // backlog 생성하기
    public BacklogEntity createBacklog(BacklogDto backlogDto) {

        Optional<ProjectEntity> projectEntity = projectRepository.findById(backlogDto.getProjectSeq());
        if (!projectEntity.isPresent()) {
            throw new EntityNotFoundException("Project not found with id " + backlogDto.getProjectSeq()); // id를 찾을 수 없는 경우 발생
        }

        BacklogEntity backlogEntity = new BacklogEntity();
        backlogEntity.setProject(projectEntity.get());
        backlogEntity.setTitle(backlogDto.getTitle());
        backlogEntity.setDescription(backlogDto.getDescription());
        backlogEntity.setDeadline(backlogDto.getDeadline());
        backlogEntity.setProjectTitle(backlogDto.getProjectTitle());
//        backlogEntity.setStoryProgress(backlogDto.getStoryProgress());

        return backlogRepository.save(backlogEntity);
    }

    // backlog의 모든 내용 가져오기
    public List<BacklogEntity> getAllBacklog() {
        return backlogRepository.findAll();
    }

    // backlog에서 nb_seq로 조회
    public BacklogEntity getBacklogByNb_seq(long nb_seq) {
        // np_seq 값으로 프로젝트를 조회합니다.
        return backlogRepository.findById(nb_seq)
                .get();
    }


    public List<BacklogEntity> getBacklogByNp_seq(long np_seq) {
//        // np_seq 값으로 프로젝트를 조회합니다.
//        List<BacklogEntity>  backlogEntities = Arrays.asList(backlogRepository.findByProject_Seq(np_seq).orElse(null));
//        return backlogEntities;

        List<BacklogEntity> backlogEntities = backlogRepository.findAll().stream()
                .filter(backlog -> backlog.getProject().getSeq() == np_seq)
                .collect(Collectors.toList());
        return backlogEntities;

    }

    // backlog 내용 갱신하기
    public BacklogEntity updateBacklog(long nb_seq, BacklogDto backlogDto) {
        // nb_seq 값으로 프로젝트를 조회합니다.
        BacklogEntity backlogEntity = backlogRepository.findById(nb_seq).get();

        // 프로젝트를 업데이트합니다.
        backlogEntity.setTitle(backlogDto.getTitle());
        backlogEntity.setDescription(backlogDto.getDescription());
        backlogEntity.setDeadline(backlogDto.getDeadline());

        // 업데이트된 프로젝트를 저장하고 반환합니다.
        return backlogRepository.save(backlogEntity);
    }

    // backlog 삭제하기
    public void deleteBacklog(long nb_seq) {
        // 백로그가 존재하는지 확인
        Optional<BacklogEntity> backlogEntity = backlogRepository.findById(nb_seq);
        if (!backlogEntity.isPresent()) {
            throw new EntityNotFoundException("Backlog not found with id " + nb_seq);
        }

        // 백로그 삭제
        backlogRepository.deleteById(nb_seq);
    }

}
