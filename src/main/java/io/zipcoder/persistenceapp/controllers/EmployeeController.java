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

    // CREATE an employee
    @PostMapping("/employee")
    public ResponseEntity<Employee> create(@RequestBody Employee employee) {
        return new ResponseEntity<>(employeeService.create(employee), HttpStatus.CREATED);
    }

    // UPDATE employee with certain {id} to have new {managerID}
    @PutMapping("/employee/{id}/newmanager/{managerID}")
    public ResponseEntity<Employee> updateManager(@PathVariable Integer id, @PathVariable Integer managerID) {
        return new ResponseEntity<>(employeeService.updateManager(id, managerID), HttpStatus.OK);
    }

    // UPDATE employee with certain {id}
    @PutMapping("/employee/{id}")
    public ResponseEntity<Employee> update(@PathVariable Integer id, @RequestBody Employee employee) {
        return new ResponseEntity<>(employeeService.update(id, employee), HttpStatus.OK);
    }

    // GET all employees as Iterable
    @GetMapping("/employee")
    public ResponseEntity<Iterable<Employee>> index() {
        return new ResponseEntity<>(employeeService.index(), HttpStatus.OK);
    }

    // GET employee with certain {id}
    @GetMapping("/employee/{id}")
    public ResponseEntity<Employee> show(@PathVariable Integer id) {
        return new ResponseEntity<>(employeeService.show(id), HttpStatus.OK);
    }

    // GET all employees who report directly to a particular manager with {id}
    @GetMapping("/employee/{id}/directreports")
    public ResponseEntity<List<Employee>> getDirectReports(@PathVariable Integer id) {
        return new ResponseEntity<>(employeeService.getDirectReports(id), HttpStatus.OK);
    }

    // GET all employees who report directly or indirectly to a particular manager with {id}
    @GetMapping("/employee/{id}/allreports")
    public ResponseEntity<List<Employee>> getAllReports(@PathVariable Integer id) {
        return new ResponseEntity<>(employeeService.getAllReports(id), HttpStatus.OK);
    }

    // GET reporting hierarchy upwards from with employee with {id}
    @GetMapping("/employee/{id}/hierarchy")
    public ResponseEntity<List<Employee>> getReportingHierarchy(@PathVariable Integer id) {
        return new ResponseEntity<>(employeeService.getReportingHierarchy(id), HttpStatus.OK);
    }

    // GET all employees who do not report to a manager
    @GetMapping("/employee/bosses")
    public ResponseEntity<List<Employee>> getBosses() {
        return new ResponseEntity<>(employeeService.getBosses(), HttpStatus.OK);
    }

    // GET all employees who work in department with departmentID {id}
    @GetMapping("/employee/department/{id}")
    public ResponseEntity<List<Employee>> getDepartmentEmployees(@PathVariable Integer id) {
        return new ResponseEntity<>(employeeService.getDepartmentEmployees(id), HttpStatus.OK);
    }
}
