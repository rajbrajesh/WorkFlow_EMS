package com.workflow.workflow.service;

import com.workflow.workflow.entity.Employee;
import com.workflow.workflow.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service layer for Employee-related business operations.
 *
 * The Service layer sits between the Controller and Repository.
 *
 * Controller
 *     ↓
 * EmployeeService
 *     ↓
 * EmployeeRepository
 *     ↓
 * PostgreSQL
 */
@Service
public class EmployeeService {

    /*
     * EmployeeRepository is injected through the constructor.
     *
     * Constructor injection is preferred because:
     * 1. The dependency is required for this class to work.
     * 2. It makes the class easier to test.
     * 3. The dependency can be final.
     */
    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    /**
     * Get all employees from the database.
     */
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    /**
     * Get a single employee by ID.
     *
     * Optional is used because the employee may not exist.
     */
    public Optional<Employee> getEmployeeById(Long id) {
        return employeeRepository.findById(id);
    }

    /**
     * Create a new employee or update an existing employee.
     *
     * JpaRepository.save() handles both operations.
     */
    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    /**
     * Delete an employee using its ID.
     */
    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }
}