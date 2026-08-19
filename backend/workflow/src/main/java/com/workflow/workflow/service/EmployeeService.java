package com.workflow.workflow.service;

import com.workflow.workflow.dto.EmployeeRequestDto;
import com.workflow.workflow.dto.EmployeeResponseDto;
import com.workflow.workflow.entity.Employee;
import com.workflow.workflow.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

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
     */
    public Optional<EmployeeResponseDto> getEmployeeById(Long id) {

        return employeeRepository.findById(id)
                .map(this::convertToResponseDto);
    }

    /**
     * Create a new employee.
     */
    public EmployeeResponseDto createEmployee(EmployeeRequestDto requestDto) {

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