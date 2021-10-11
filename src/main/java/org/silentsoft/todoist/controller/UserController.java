package org.silentsoft.todoist.controller;

import org.silentsoft.todoist.util.Utils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public ResponseEntity<?> getUser() {
        return ResponseEntity.ok(Utils.getUserEntity());
    }

}
