package com.workflow.workflow.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO used when returning employee information
 * from the backend to the client.
 *
 * This prevents us from exposing the Entity directly.
 */
public class EmployeeResponseDto {

    private Long id;
    private String name;
    private String email;
    private String phone;
    private String department;
    private String designation;
    private LocalDate joiningDate;
    private BigDecimal salary;

    public EmployeeResponseDto() {
    }

    public EmployeeResponseDto(
            Long id,
            String name,
            String email,
            String phone,
            String department,
            String designation,
            LocalDate joiningDate,
            BigDecimal salary) {

        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.department = department;
        this.designation = designation;
        this.joiningDate = joiningDate;
        this.salary = salary;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getDepartment() {
        return department;
    }

    public String getDesignation() {
        return designation;
    }

    public LocalDate getJoiningDate() {
        return joiningDate;
    }

    public BigDecimal getSalary() {
        return salary;
    }
}