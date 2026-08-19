package com.workflow.workflow.controller;

import com.workflow.workflow.entity.Employee;
import com.workflow.workflow.service.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for Employee APIs.
 *
 * Responsibilities of this layer:
 * - Receive HTTP requests
 * - Call the appropriate Service method
 * - Return HTTP responses
 *
 * Business/database logic should NOT be written here.
 */
@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    // Constructor injection of EmployeeService.
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    /**
     * GET /api/employees
     *
     * Returns all employees.
     */
    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {

        List<Employee> employees = employeeService.getAllEmployees();

        return ResponseEntity.ok(employees);
    }

    /**
     * GET /api/employees/{id}
     *
     * Returns a single employee by ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {

        return employeeService.getEmployeeById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * POST /api/employees
     *
     * Creates a new employee.
     */
    @PostMapping
    public ResponseEntity<Employee> createEmployee(
            @RequestBody Employee employee) {

        Employee savedEmployee = employeeService.saveEmployee(employee);

        return ResponseEntity.ok(savedEmployee);
    }

    /**
     * PUT /api/employees/{id}
     *
     * Updates an existing employee.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(
            @PathVariable Long id,
            @RequestBody Employee employee) {

        return employeeService.getEmployeeById(id)
                .map(existingEmployee -> {

                    existingEmployee.setName(employee.getName());
                    existingEmployee.setEmail(employee.getEmail());
                    existingEmployee.setPhone(employee.getPhone());
                    existingEmployee.setDepartment(employee.getDepartment());
                    existingEmployee.setDesignation(employee.getDesignation());
                    existingEmployee.setJoiningDate(employee.getJoiningDate());
                    existingEmployee.setSalary(employee.getSalary());

                    Employee updatedEmployee =
                            employeeService.saveEmployee(existingEmployee);

                    return ResponseEntity.ok(updatedEmployee);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * DELETE /api/employees/{id}
     *
     * Deletes an employee by ID.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {

        if (employeeService.getEmployeeById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        employeeService.deleteEmployee(id);

        return ResponseEntity.noContent().build();
    }
}