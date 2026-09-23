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
     * Get employees with optional search and department filter,
     * along with pagination and sorting.
     *
     * Supported query parameters:
     *
     * search     -> searches name, email, phone, department, designation
     * department -> filters employees by department
     * page       -> page number, starts from 0
     * size       -> number of records per page
     * sortBy     -> field used for sorting
     * direction  -> asc or desc
     *
     * Example:
     * GET /api/employees?search=rah&department=IT&page=0&size=5&sortBy=salary&direction=desc
     */
    @GetMapping
    public ResponseEntity<PageResponseDto<List<EmployeeResponseDto>>> getAllEmployees(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String department,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        /*
         * Define the employee fields that the API allows
         * clients to use for sorting.
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
         * Prevent clients from requesting an invalid
         * database/entity field for sorting.
         */
        if (!allowedSortFields.contains(sortBy)) {
            throw new IllegalArgumentException(
                    "Invalid sort field: " + sortBy
            );
        }

        /*
         * Page number cannot be negative.
         */
        if (page < 0) {
            throw new IllegalArgumentException(
                    "Page number cannot be negative"
            );
        }

        /*
         * Page size must be greater than zero.
         */
        if (size <= 0) {
            throw new IllegalArgumentException(
                    "Page size must be greater than zero"
            );
        }

        /*
         * Restrict the sorting direction to only ASC or DESC.
         */
        if (!direction.equalsIgnoreCase("asc")
                && !direction.equalsIgnoreCase("desc")) {

            throw new IllegalArgumentException(
                    "Invalid sort direction: " + direction
            );
        }

        /*
         * Convert the direction string into Spring's
         * Sort.Direction.
         *
         * "desc" -> descending
         * anything else -> ascending
         */
        Sort.Direction sortDirection =
                direction.equalsIgnoreCase("desc")
                        ? Sort.Direction.DESC
                        : Sort.Direction.ASC;

        /*
         * Create Pageable containing:
         * - page number
         * - page size
         * - sorting information
         */
        Sort sort = Sort.by(sortDirection, sortBy);

        Pageable pageable =
                PageRequest.of(page, size, sort);

        /*
         * Send all optional filters + pagination information
         * to the service.
         *
         * The service will dynamically build the Specification.
         */
        return ResponseEntity.ok(
                employeeService.getEmployeesWithFilters(
                        search,
                        department,
                        pageable
                )
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