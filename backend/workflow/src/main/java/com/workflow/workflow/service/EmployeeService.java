package com.workflow.workflow.service;

import com.workflow.workflow.dto.EmployeeRequestDto;
import com.workflow.workflow.dto.EmployeeResponseDto;
import com.workflow.workflow.entity.Employee;
import com.workflow.workflow.exception.ResourceNotFoundException;
import com.workflow.workflow.repository.EmployeeRepository;
import org.springframework.stereotype.Service;
import com.workflow.workflow.exception.DuplicateResourceException;

import java.util.List;
import java.util.Optional;

/**
 * Service layer for Employee-related business operations.
 *
 * Responsibilities:
 * - Handle business logic
 * - Convert DTOs to Entities
 * - Convert Entities to DTOs
 * - Communicate with Repository
 */
@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    // Constructor injection.
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    /**
     * Get all employees.
     */
    public List<EmployeeResponseDto> getAllEmployees() {

        return employeeRepository.findAll()
                .stream()
                .map(this::convertToResponseDto)
                .toList();
    }

    /**
     * Get employee by ID.
     *
     * If the employee does not exist, throw a custom exception.
     * GlobalExceptionHandler will convert it into HTTP 404.
     */
    public EmployeeResponseDto getEmployeeById(Long id) {

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Employee not found with id: " + id
                        )
                );

        return convertToResponseDto(employee);
    }

    /**
     * Create a new employee.
     *
     * Before saving, we make sure the email is not already
     * being used by another employee.
     */
    public EmployeeResponseDto createEmployee(EmployeeRequestDto requestDto) {

        // Business rule:
        // Every employee must have a unique email.
        if (employeeRepository.existsByEmail(requestDto.getEmail())) {

            throw new DuplicateResourceException(
                    "Employee already exists with email: "
                            + requestDto.getEmail()
            );
        }

        Employee employee = convertToEntity(requestDto);

        Employee savedEmployee = employeeRepository.save(employee);

        return convertToResponseDto(savedEmployee);
    }

    /**
     * Update an existing employee.
     */
    public Optional<EmployeeResponseDto> updateEmployee(
            Long id,
            EmployeeRequestDto requestDto) {

        return employeeRepository.findById(id)
                .map(existingEmployee -> {

                    /*
                     * Check whether another employee is already
                     * using the requested email.
                     */
                    boolean emailAlreadyUsed =
                            employeeRepository.existsByEmailAndIdNot(
                                    requestDto.getEmail(),
                                    id
                            );

                    if (emailAlreadyUsed) {
                        throw new DuplicateResourceException(
                                "Employee already exists with email: "
                                        + requestDto.getEmail()
                        );
                    }

                    existingEmployee.setName(requestDto.getName());
                    existingEmployee.setEmail(requestDto.getEmail());
                    existingEmployee.setPhone(requestDto.getPhone());
                    existingEmployee.setDepartment(requestDto.getDepartment());
                    existingEmployee.setDesignation(requestDto.getDesignation());
                    existingEmployee.setJoiningDate(requestDto.getJoiningDate());
                    existingEmployee.setSalary(requestDto.getSalary());

                    Employee updatedEmployee =
                            employeeRepository.save(existingEmployee);

                    return convertToResponseDto(updatedEmployee);
                });
    }

    /**
     * Delete employee by ID.
     */
    public boolean deleteEmployee(Long id) {

        if (!employeeRepository.existsById(id)) {
            return false;
        }

        employeeRepository.deleteById(id);

        return true;
    }

    /**
     * Convert Request DTO → Entity.
     */
    private Employee convertToEntity(EmployeeRequestDto dto) {

        Employee employee = new Employee();

        employee.setName(dto.getName());
        employee.setEmail(dto.getEmail());
        employee.setPhone(dto.getPhone());
        employee.setDepartment(dto.getDepartment());
        employee.setDesignation(dto.getDesignation());
        employee.setJoiningDate(dto.getJoiningDate());
        employee.setSalary(dto.getSalary());

        return employee;
    }

    /**
     * Convert Entity → Response DTO.
     */
    private EmployeeResponseDto convertToResponseDto(Employee employee) {

        return new EmployeeResponseDto(
                employee.getId(),
                employee.getName(),
                employee.getEmail(),
                employee.getPhone(),
                employee.getDepartment(),
                employee.getDesignation(),
                employee.getJoiningDate(),
                employee.getSalary()
        );
    }
}