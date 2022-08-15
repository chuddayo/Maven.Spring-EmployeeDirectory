package io.zipcoder.persistenceapp.service;

import io.zipcoder.persistenceapp.models.Employee;
import io.zipcoder.persistenceapp.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {
    @Autowired
    EmployeeRepository repository;

    public EmployeeService (EmployeeRepository employeeRepository) {
        repository = employeeRepository;
    }

    public Iterable<Employee> index() {
        return repository.findAll();
    }

    public Employee show(Integer id) {
        return repository.findById(id).get();
    }

    public Employee create(Employee employee) {
        return repository.save(employee);
    }

    public Employee update(Integer id, Employee newEmployeeInfo) {
        Employee currentEmployee = repository.findById(id).get();
        currentEmployee.setFirstName(newEmployeeInfo.getFirstName());
        currentEmployee.setLastName(newEmployeeInfo.getLastName());
        currentEmployee.setDepartmentID(newEmployeeInfo.getDepartmentID());
        currentEmployee.setEmail(newEmployeeInfo.getEmail());
        currentEmployee.setHireDate(newEmployeeInfo.getHireDate());
        currentEmployee.setManagerID(newEmployeeInfo.getManagerID());
        currentEmployee.setPhoneNumber(newEmployeeInfo.getPhoneNumber());
        currentEmployee.setTitle(newEmployeeInfo.getTitle());
        return repository.save(currentEmployee);
    }

    public Boolean delete(Integer id) {
        repository.deleteById(id);
        return true;
    }
}
