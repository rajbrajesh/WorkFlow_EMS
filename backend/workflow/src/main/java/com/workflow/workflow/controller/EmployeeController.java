package com.workflow.workflow.controller;

import com.workflow.workflow.dto.EmployeeRequestDto;
import com.workflow.workflow.dto.EmployeeResponseDto;
import com.workflow.workflow.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for Employee APIs.
 *
 * Responsibilities:
 * - Receive HTTP requests
 * - Validate request data
 * - Call Service methods
 * - Return HTTP responses
 *
 * Business logic remains inside the Service layer.
 */
@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    /**
     * GET /api/employees
     *
     * Get all employees.
     */
    @GetMapping
    public ResponseEntity<List<EmployeeResponseDto>> getAllEmployees() {

        return ResponseEntity.ok(
                employeeService.getAllEmployees()
        );
    }


    /**
     * GET /api/employees/{id}
     *
     * Get employee by ID.
     *
     * If employee doesn't exist, the Service throws
     * ResourceNotFoundException and the global handler
     * returns HTTP 404.
     */
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponseDto> getEmployeeById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                employeeService.getEmployeeById(id)
        );
    }

    /**
     * POST /api/employees
     *
     * Create a new employee.
     *
     * @Valid tells Spring to execute the validation
     * annotations defined inside EmployeeRequestDto.
     */
    @PostMapping
    public ResponseEntity<EmployeeResponseDto> createEmployee(
            @Valid @RequestBody EmployeeRequestDto requestDto) {

        EmployeeResponseDto response =
                employeeService.createEmployee(requestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    /**
     * PUT /api/employees/{id}
     *
     * Update an existing employee.
     */
    @PutMapping("/{id}")
    public ResponseEntity<EmployeeResponseDto> updateEmployee(
            @PathVariable Long id,
            @Valid @RequestBody EmployeeRequestDto requestDto) {

        return employeeService.updateEmployee(id, requestDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * DELETE /api/employees/{id}
     *
     * Delete an employee.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(
            @PathVariable Long id) {

        boolean deleted = employeeService.deleteEmployee(id);

        if (!deleted) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}