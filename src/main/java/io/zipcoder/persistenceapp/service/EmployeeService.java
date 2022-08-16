package io.zipcoder.persistenceapp.service;

import io.zipcoder.persistenceapp.models.Department;
import io.zipcoder.persistenceapp.models.Employee;
import io.zipcoder.persistenceapp.repositories.DepartmentRepository;
import io.zipcoder.persistenceapp.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class EmployeeService {
    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    DepartmentRepository departmentRepository;

    public EmployeeService (EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Iterable<Employee> index() {
        return employeeRepository.findAll();
    }

    public Employee show(Integer id) {
        return employeeRepository.findOne(id);
    }

    public Employee create(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Employee update(Integer id, Employee newEmployeeInfo) {
        Employee currentEmployee = employeeRepository.findOne(id);
        currentEmployee.setFirstName(newEmployeeInfo.getFirstName());
        currentEmployee.setLastName(newEmployeeInfo.getLastName());
        currentEmployee.setDepartmentID(newEmployeeInfo.getDepartmentID());
        currentEmployee.setEmail(newEmployeeInfo.getEmail());
        currentEmployee.setHireDate(newEmployeeInfo.getHireDate());
        currentEmployee.setManagerID(newEmployeeInfo.getManagerID());
        currentEmployee.setPhoneNumber(newEmployeeInfo.getPhoneNumber());
        currentEmployee.setTitle(newEmployeeInfo.getTitle());
        return employeeRepository.save(currentEmployee);
    }

    public String getDepartmentName(Integer id) {
        return departmentRepository.findOne(show(id).getDepartmentID()).getName();
    }

    public List<Employee> getDirectReports(Integer id) {
        return StreamSupport.stream(index().spliterator(), false)
                .filter(e -> e.getManagerID() != null)
                .filter(e -> e.getManagerID().equals(id))
                .collect(Collectors.toList());
    }

    public List<Employee> getAllReports(Integer id) {
        List<Employee> reportsList = getDirectReports(id);
        List<Employee> subReportsList = new ArrayList<>();
        for (Employee e : reportsList) {
            subReportsList.addAll(getAllReports(e.getId()));
        }
        reportsList.addAll(subReportsList);
        return reportsList;
    }

    public List<Employee> getReportingHierarchy(Integer id) {
        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(show(id));
        // TODO breaks if the managerID is not an employeeID that exists
        while (employeeList.get(employeeList.size()-1).getManagerID() != null) {
            employeeList.add(show(employeeList.get(employeeList.size()-1).getManagerID()));
        }
        return employeeList;
    }

    public List<Employee> getBosses() {
        return StreamSupport.stream(index().spliterator(), false)
                .filter(e -> e.getManagerID() == null)
                .collect(Collectors.toList());
    }

    public List<Employee> getDepartmentEmployees(Integer departmentID) {
        return StreamSupport.stream(index().spliterator(), false)
                .filter(e -> e.getDepartmentID() != null)
                .filter(e -> e.getDepartmentID().equals(departmentID))
                .collect(Collectors.toList());
    }

    public Employee updateManager(Integer id, Integer managerID) {
        Employee newEmployeeInfo = show(id);
        newEmployeeInfo.setManagerID(managerID);
        newEmployeeInfo.setDepartmentID(show(managerID).getDepartmentID());
        return employeeRepository.save(newEmployeeInfo);
    }

    public List<Employee> mergeDepartments(Integer departmentIDA, Integer departmentIDB) {
        List<Employee> employeeListUpdates = new ArrayList<>();
        Employee deptAManager = show(departmentRepository.findOne(departmentIDA).getManagerID());
        Employee deptBManager = show(departmentRepository.findOne(departmentIDB).getManagerID());

        // Manager of A reports to B and works in B
        deptAManager.setManagerID(deptBManager.getId());
        deptAManager.setDepartmentID(departmentIDB);
        employeeRepository.save(deptAManager);
        employeeRepository.save(deptBManager);
        employeeListUpdates.add(deptAManager);
        employeeListUpdates.add(deptBManager);

        // All direct reports of A report to B
        List<Employee> directAReports = getDirectReports(deptAManager.getId());
        for (Employee e : directAReports) {
            e.setManagerID(deptBManager.getId());
            employeeRepository.save(e);
        }
        employeeListUpdates.addAll(directAReports);

        // All employees who work in A work in B
        List<Employee> deptAemployeeList = getDepartmentEmployees(departmentIDA);
        for (Employee e : deptAemployeeList) {
            e.setDepartmentID(departmentIDB);
            employeeRepository.save(e);
        }
        employeeListUpdates.addAll(deptAemployeeList);

        return employeeListUpdates;
    }

    public Boolean delete(Integer id) {
        if (show(id).getManagerID() != null) {
            Integer managerID = show(id).getManagerID();
            List<Employee> employeeList = getDirectReports(id);
            for (Employee e : employeeList) {
                e.setManagerID(managerID);
                employeeRepository.save(e);
            }
        }
        employeeRepository.delete(id);
        return true;
    }

    public Boolean deleteEmployeeList(List<Employee> employeeList) {
        for (Employee e : employeeList) {
            delete(e.getId());
        }
        return true;
    }

    public Boolean deleteEmployeesFromDept(Integer departmentID) {
        Iterable<Employee> employeeIterable = index();
        for (Employee e : employeeIterable) {
            if (e.getDepartmentID().equals(departmentID)) {
                delete(e.getId());
            }
        }
        return true;
    }

    public Boolean deleteDirectReports(Integer id) {
        deleteEmployeeList(getDirectReports(id));
        return true;
    }

    public Boolean deleteAllReports(Integer id) {
        deleteEmployeeList(getAllReports(id));
        return true;
    }
}
