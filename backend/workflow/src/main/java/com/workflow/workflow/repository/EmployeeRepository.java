package com.workflow.workflow.repository;

import com.workflow.workflow.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository layer for Employee database operations.
 *
 * JpaRepository already provides common CRUD operations such as:
 * - save()
 * - findById()
 * - findAll()
 * - deleteById()
 * - existsById()
 *
 * We don't need to write SQL for these basic operations.
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    /**
     * Checks whether an employee with the given email exists.
     *
     * Spring Data JPA generates the required query automatically
     * from the method name.
     */
    boolean existsByEmail(String email);

    /**
     * Checks whether another employee is already using
     * the given email.
     *
     * The current employee ID is excluded from the check.
     */
    boolean existsByEmailAndIdNot(String email, Long id);
}