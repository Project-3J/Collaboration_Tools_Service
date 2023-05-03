package com.agile.demo.biz.task;

import com.agile.demo.biz.account.AccountEntity;
import com.agile.demo.biz.backlog.BacklogEntity;
import com.agile.demo.biz.backlog.BacklogRepository;
import com.agile.demo.biz.project.ProjectEntity;
import com.agile.demo.biz.project.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private BacklogRepository backlogRepository;

    @Autowired
    private ProjectRepository projectRepository;

    public TaskEntity createTask(TaskDto taskDto) {

        // backlog에서 값을 조회해서 backlogEntity에서 받아옴
        Optional<BacklogEntity> backlogEntity = backlogRepository.findById(taskDto.getBacklogEntity().getSeq());
        if (!backlogEntity.isPresent()) {
            throw new EntityNotFoundException("Account not found with id " + taskDto.getBacklogEntity().getSeq()); // id를 찾을 수 없는 경우 발생
        }

        // project에서 값을 조회해서 backlogEntity에서 받아옴
        Optional<ProjectEntity> projectEntity = projectRepository.findById(taskDto.getProjectEntity().getSeq());
        if (!projectEntity.isPresent()) {
            throw new EntityNotFoundException("Account not found with id " + taskDto.getProjectEntity().getSeq()); // id를 찾을 수 없는 경우 발생
        }

        // id와 np_seq를 조회한 결과를 accountprojectDto에 넣기
        taskDto.setBacklogEntity(backlogEntity.get());
        taskDto.setProjectEntity(projectEntity.get());

        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setNt_seq(taskDto.getNt_seq());
        taskEntity.setTitle(taskDto.getTitle());
        taskEntity.setStory_progress(taskDto.getStory_progress());
        taskEntity.setDescription(taskDto.getDescription());
        taskEntity.setCreate_date(taskDto.getCreate_date()); // 현재 시간을 작성
        taskEntity.setUpdate_data(taskDto.getUpdate_date()); // 현재 시간을 작성
        taskEntity.setDeadline(taskDto.getDeadline()); // 작성자가 원하는 시간으로
        taskEntity.setPresenter(taskDto.getPresenter());
        taskEntity.setManager(taskDto.getManager());
        taskEntity.setBacklogEntity(taskDto.getBacklogEntity());
        taskEntity.setProjectEntity(taskEntity.getProjectEntity());
        // BacklogEntity와 TaskEntity는 cascade 옵션으로 인해 자동 저장됩니다.
        return taskRepository.save(taskEntity);
    }

    public List<TaskDto> getAllTasks() { // 존재하는 모든 Task 출력
        List<TaskEntity> taskEntities = taskRepository.findAll();
        List<TaskDto> taskDtos = new ArrayList<>();
        for (TaskEntity taskEntity : taskEntities) {
            TaskDto taskDto = new TaskDto();
            taskDto.setNt_seq(taskEntity.getNt_seq());
            taskDto.setTitle(taskEntity.getTitle());
            taskDto.setDescription(taskEntity.getDescription());
            taskDto.setStory_progress(taskEntity.getStory_progress());
            taskDto.setCreate_date(taskEntity.getCreate_date());
            taskDto.setUpdate_date(taskEntity.getUpdate_data());
            taskDto.setDeadline(taskEntity.getDeadline().toString());
            taskDto.setPresenter(taskEntity.getPresenter());
            taskDto.setManager(taskEntity.getManager());
            taskDto.setBacklogEntity(taskDto.getBacklogEntity());
            taskDto.setProjectEntity(taskDto.getProjectEntity());

            taskDtos.add(taskDto);
        }
        return taskDtos;
    }

    // 특정 project에서 task 조회하기
    public TaskEntity getTaskByProjectId(long np_seq) {
        // nt_seq 값으로 태스크를 조회합니다.
        return taskRepository.findById(np_seq)
                .get();
    }
    
    // 특정 id의 task 조회
    public TaskEntity getTaskById(long nt_seq) {
        // nt_seq 값으로 태스크를 조회합니다.
        return taskRepository.findById(nt_seq)
                .get();
    }

    public void deleteTask(long nt_seq) {
        // 프로젝트가 존재하는지 확인
        Optional<TaskEntity> taskEntity = taskRepository.findById(nt_seq);
        if (!taskEntity.isPresent()) {
            throw new EntityNotFoundException("Project not found with id " + nt_seq);
        }

        // 프로젝트 삭제
        taskRepository.deleteById(nt_seq);
    }
    public TaskEntity updateTask(long nt_seq, TaskDto taskDto) {
        // nt_seq 값으로 태스크를 조회합니다.
        TaskEntity taskEntity = taskRepository.findById(nt_seq).get();

        // 태스크를 업데이트합니다.
        taskEntity.setTitle(taskDto.getTitle());
        taskEntity.setDescription(taskDto.getDescription());
        taskEntity.setBacklogEntity(taskDto.getBacklogEntity());
        taskEntity.setPresenter(taskDto.getPresenter());
        taskEntity.setManager(taskDto.getManager());
        taskEntity.setUpdate_data(taskDto.getUpdate_date());
        taskEntity.setStory_progress(taskDto.getStory_progress());
        taskEntity.setAccount(taskDto.getAccount());
        taskEntity.setDeadline(taskDto.getDeadline());

        // 업데이트된 태스크를 저장하고 반환합니다.
        return taskRepository.save(taskEntity);
    }

   


}