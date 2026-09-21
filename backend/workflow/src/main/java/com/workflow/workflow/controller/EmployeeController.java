package com.workflow.workflow.controller;

import com.workflow.workflow.dto.EmployeeRequestDto;
import com.workflow.workflow.dto.EmployeeResponseDto;
import com.workflow.workflow.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import com.workflow.workflow.dto.PageResponseDto;
import org.springframework.data.domain.Sort;

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
     * Get employees with pagination and sorting.
     *
     * Query parameters:
     * - page      → page number, starting from 0
     * - size      → number of employees per page
     * - sortBy    → field by which employees should be sorted
     * - direction → asc or desc
     *
     * Examples:
     * GET /api/employees?page=0&size=5&sortBy=name&direction=asc
     * GET /api/employees?page=0&size=5&sortBy=salary&direction=desc
     */
    @GetMapping
    public ResponseEntity<PageResponseDto<List<EmployeeResponseDto>>> getAllEmployees(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        /*
         * Define the employee fields that the API allows
         * clients to use for sorting.
         *
         * This prevents invalid field names from being
         * passed directly to Spring Data JPA.
         */
        List<String> allowedSortFields = List.of(
                "id",
                "name",
                "email",
                "department",
                "designation",
                "joiningDate",
                "salary"
        );

        /*
         * Validate the requested sort field.
         *
         * If the field is not supported, return a clear
         * client-side error instead of allowing an invalid
         * database field to reach the repository.
         */
        if (!allowedSortFields.contains(sortBy)) {

            throw new IllegalArgumentException(
                    "Invalid sort field: " + sortBy
            );
        }

        /*
         * Decide the sorting direction.
         *
         * "desc" → descending
         * anything else → ascending
         */
        Sort.Direction sortDirection =
                direction.equalsIgnoreCase("desc")
                        ? Sort.Direction.DESC
                        : Sort.Direction.ASC;

        /*
         * Create Sort using the validated field and direction.
         */
        Sort sort = Sort.by(sortDirection, sortBy);

        /*
         * Create Pageable containing:
         * - page number
         * - page size
         * - sorting information
         */
        Pageable pageable =
                PageRequest.of(page, size, sort);

        /*
         * Service performs pagination + sorting.
         */
        return ResponseEntity.ok(
                employeeService.getEmployeesPaginated(pageable)
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