package io.zipcoder.persistenceapp.controllers;

import io.zipcoder.persistenceapp.models.Employee;
import io.zipcoder.persistenceapp.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class EmployeeController {
    EmployeeService employeeService;

    public EmployeeController (EmployeeService service) {
        employeeService = service;
    }

    @PostMapping("/employee")
    public ResponseEntity<Employee> create(@RequestBody Employee employee) {
        return new ResponseEntity<>(employeeService.create(employee), HttpStatus.CREATED);
    }

    @PutMapping("/employee/{id}/newmanager/{managerID}")
    public ResponseEntity<Employee> updateManager(@PathVariable Integer id, @PathVariable Integer managerID) {
        return new ResponseEntity<>(employeeService.updateManager(id, managerID), HttpStatus.OK);
    }

    @PutMapping("/employee/{id}")
    public ResponseEntity<Employee> update(@PathVariable Integer id, @RequestBody Employee employee) {
        return new ResponseEntity<>(employeeService.update(id, employee), HttpStatus.OK);
    }

    @GetMapping("/employee/{id}")
    public ResponseEntity<Employee> show(@PathVariable Integer id) {
        return new ResponseEntity<>(employeeService.show(id), HttpStatus.OK);
    }

    @GetMapping("/employee/{id}/directreports")
    public ResponseEntity<List<Employee>> getDirectReports(@PathVariable Integer id) {
        return new ResponseEntity<>(employeeService.getDirectReports(id), HttpStatus.OK);
    }

    // Get all employees who report directly or indirectly to a particular manager
    @GetMapping("/employee/{id}/allreports")
    public ResponseEntity<List<Employee>> getAllReports(@PathVariable Integer id) {
        return new ResponseEntity<>(employeeService.getAllReports(id), HttpStatus.OK);
    }

    @GetMapping("/employee/{id}/hierarchy")
    public ResponseEntity<List<Employee>> getReportingHierarchy(@PathVariable Integer id) {
        return new ResponseEntity<>(employeeService.getReportingHierarchy(id), HttpStatus.OK);
    }

    @GetMapping("/employee/bosses")
    public ResponseEntity<List<Employee>> getBosses() {
        return new ResponseEntity<>(employeeService.getBosses(), HttpStatus.OK);
    }

    @GetMapping("/employee/department/{id}")
    public ResponseEntity<List<Employee>> getDepartmentEmployees(@PathVariable Integer id) {
        return new ResponseEntity<>(employeeService.getDepartmentEmployees(id), HttpStatus.OK);
    }
}
