package com.workflow.workflow.repository;

import com.workflow.workflow.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    /**
     * Searches employees across multiple fields.
     *
     * The search is case-insensitive and supports partial matching.
     *
     * Example:
     * search = "rah"
     *
     * This can match:
     * - Rahul
     * - rah@example.com
     * - Department/designation containing "rah"
     */
    @Query("""
            SELECT e
            FROM Employee e
            WHERE LOWER(e.name) LIKE LOWER(CONCAT('%', :search, '%'))
               OR LOWER(e.email) LIKE LOWER(CONCAT('%', :search, '%'))
               OR LOWER(e.phone) LIKE LOWER(CONCAT('%', :search, '%'))
               OR LOWER(e.department) LIKE LOWER(CONCAT('%', :search, '%'))
               OR LOWER(e.designation) LIKE LOWER(CONCAT('%', :search, '%'))
            """)
    List<Employee> searchEmployees(@Param("search") String search);
}