package com.integrador.ms_users.controller;

import com.integrador.ms_users.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@CrossOrigin("*")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<?> getAll(){
        return new ResponseEntity<>(userService.getAll(), HttpStatus.OK);
    }
    @GetMapping("/name/{id}")
    public ResponseEntity<?> getAll(@PathVariable String id){
        return new ResponseEntity<>(userService.getNombreById(id), HttpStatus.OK);
    }
}
