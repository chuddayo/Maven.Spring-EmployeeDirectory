package io.zipcoder.persistenceapp.repositories;

import io.zipcoder.persistenceapp.models.Employee;
import org.springframework.data.repository.CrudRepository;

public interface EmployeeRepository extends CrudRepository<Employee, Integer> {
}
