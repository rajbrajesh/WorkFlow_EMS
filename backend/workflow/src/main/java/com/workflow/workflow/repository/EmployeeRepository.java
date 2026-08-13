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

}