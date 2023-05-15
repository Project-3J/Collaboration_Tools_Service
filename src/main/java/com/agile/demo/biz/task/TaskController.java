package com.agile.demo.biz.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @PostMapping // Task 생성하기
    public ResponseEntity<?> createTask(@RequestBody TaskDto taskDto) {
        TaskEntity taskEntity = taskService.createTask(taskDto);
        return ResponseEntity.accepted().build();
    }

    @GetMapping // Task의 모든 내용 조회하기
        public List<TaskEntity> getAllTask(@RequestHeader Map<String, String> httpHeaders) {
        return taskService.getAllTasks();
    }
//
//    @GetMapping("project/{np_seq}") // Task 출력하기 - 프로젝트별
//    public List<TaskEntity> getProjectIdTask(@PathVariable long np_seq) {
//        List<TaskEntity> taskEntity = taskService.getTaskByNp_seq(np_seq);
//        return taskEntity;
//    }

    @GetMapping("/view/{nt_seq}") // Task 조회하기 - 1개만
    public ResponseEntity<TaskEntity> getIdTask(@PathVariable long nt_seq) {
        TaskEntity taskEntity = taskService.getTaskByNt_seq(nt_seq);
        return ResponseEntity.ok(taskEntity);
    }

    @PutMapping("{nt_seq}") // Task의 내용 갱신하기
    public ResponseEntity<?> updateTask(@PathVariable long nt_seq, @RequestBody TaskDto taskDto) {
        TaskEntity taskEntity = taskService.updateTask(nt_seq, taskDto);
        return ResponseEntity.ok(taskEntity);
    }

    @DeleteMapping("{nt_seq}")
    public ResponseEntity deleteTask(@PathVariable long nt_seq){
        taskService.deleteTask(nt_seq);
        return ResponseEntity.accepted().build();
    }

}
