package io.zipcoder.persistenceapp.controllers;

import io.zipcoder.persistenceapp.models.Department;
import io.zipcoder.persistenceapp.service.DepartmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DepartmentController {
    DepartmentService departmentService;

    public DepartmentController(DepartmentService service) {
        departmentService = service;
    }

    @PostMapping("/department")
    public ResponseEntity<Department> create(@RequestBody Department department) {
        return new ResponseEntity<>(departmentService.create(department), HttpStatus.CREATED);
    }
}
