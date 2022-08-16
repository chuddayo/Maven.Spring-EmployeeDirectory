package io.zipcoder.persistenceapp.controllers;

import io.zipcoder.persistenceapp.models.Department;
import io.zipcoder.persistenceapp.service.DepartmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("/department/{id}/newmanager/{managerID}")
    public ResponseEntity<Department> updateDepartmentManager(@PathVariable Integer id, @PathVariable Integer managerID) {
        return new ResponseEntity<>(departmentService.updateDepartmentManager(id, managerID), HttpStatus.OK);
    }

    @PutMapping("department/{id}")
    public ResponseEntity<Department> update(@PathVariable Integer id, @RequestBody Department department) {
        return new ResponseEntity<>(departmentService.update(id, department), HttpStatus.OK);
    }


}
