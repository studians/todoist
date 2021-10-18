package org.silentsoft.todoist.controller;

import org.silentsoft.todoist.entity.TaskEntity;
import org.silentsoft.todoist.model.TaskRequest;
import org.silentsoft.todoist.repository.TaskRepository;
import org.silentsoft.todoist.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.sql.Timestamp;

@RestController
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    @RequestMapping(value = "/tasks", method = RequestMethod.GET)
    public ResponseEntity<?> getTasks() {
        return ResponseEntity.ok(taskRepository.findAllByUserId(Utils.getUserId()));
    }

    @RequestMapping(value = "/task/{taskId}", method = RequestMethod.GET)
    public ResponseEntity<?> getTask(@PathVariable("taskId") long taskId) {
        TaskEntity taskEntity = taskRepository.findByIdAndUserId(taskId, Utils.getUserId());
        if (taskEntity == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(taskEntity);
    }

    @RequestMapping(value = "/task", method = RequestMethod.POST)
    public ResponseEntity<?> postTask(@RequestBody TaskRequest taskRequest) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setUserId(Utils.getUserId());
        taskEntity.setTitle(taskRequest.getTitle());
        taskEntity.setCreatedAt(timestamp);
        taskEntity.setUpdatedAt(timestamp);
        taskEntity = taskRepository.save(taskEntity);
        return ResponseEntity.created(URI.create(String.format("/task/%d", taskEntity.getId()))).body(taskEntity);
    }

    @RequestMapping(value = "/task/{taskId}", method = RequestMethod.PUT)
    public ResponseEntity<?> putTask(@PathVariable("taskId") long taskId, @RequestBody TaskRequest taskRequest) {
        TaskEntity taskEntity = taskRepository.findByIdAndUserId(taskId, Utils.getUserId());
        if (taskEntity == null) {
            return ResponseEntity.notFound().build();
        }

        taskEntity.setTitle(taskRequest.getTitle());

        return ResponseEntity.ok(taskRepository.save(taskEntity));
    }

    @RequestMapping(value = "/task/{taskId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteTask(@PathVariable("taskId") long taskId) {
        TaskEntity taskEntity = taskRepository.findByIdAndUserId(taskId, Utils.getUserId());
        if (taskEntity == null) {
            return ResponseEntity.notFound().build();
        }

        taskRepository.deleteById(taskId);

        return ResponseEntity.ok(null);
    }

}
