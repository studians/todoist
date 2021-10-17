package org.silentsoft.todoist.controller;

import org.silentsoft.todoist.repository.TaskRepository;
import org.silentsoft.todoist.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    @RequestMapping(value = "/tasks", method = RequestMethod.GET)
    public ResponseEntity<?> getTasks() {
        return ResponseEntity.ok(taskRepository.findAllByUserId(Utils.getUserId()));
    }

}
