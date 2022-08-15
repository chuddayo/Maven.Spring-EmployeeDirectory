package io.zipcoder.persistenceapp.service;

import io.zipcoder.persistenceapp.models.Department;
import io.zipcoder.persistenceapp.repositories.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DepartmentService {

    @Autowired
    DepartmentRepository departmentRepository;
    private DepartmentRepository departmentRepository1;

    public DepartmentService (DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public Iterable<Department> index() {
        return departmentRepository.findAll();
    }

    public Department show(Integer id) {
        return departmentRepository.findById(id).get();
    }

    public Department create(Department department) {
        return departmentRepository.save(department);
    }

    public Department update(Integer id, Department newDepartment) {
        Department originalDepartment = departmentRepository.findById(id).get();
        originalDepartment.setName(newDepartment.getName());
        originalDepartment.setManagerID(newDepartment.getManagerID());
        return departmentRepository.save(originalDepartment);
    }

    public Boolean delete(Integer id) {
        departmentRepository.deleteById(id);
        return true;
    }
}
