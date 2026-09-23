package com.workflow.workflow.specification;

import com.workflow.workflow.entity.Employee;
import org.springframework.data.jpa.domain.Specification;

/**
 * Contains dynamic database filtering logic for Employee.
 *
 * Specification allows us to build query conditions dynamically
 * based on the values provided by the client.
 */
public class EmployeeSpecification {

    /**
     * Search employees across multiple fields.
     *
     * The search value is checked against:
     * - name
     * - email
     * - phone
     * - department
     * - designation
     *
     * The search is case-insensitive.
     *
     * Example:
     * search = "rah"
     *
     * This can match:
     * Rahul
     * rahul@example.com
     * etc.
     */
    public static Specification<Employee> search(String search) {

        return (root, query, criteriaBuilder) -> {

            String searchPattern = "%" + search.toLowerCase() + "%";

            return criteriaBuilder.or(
                    criteriaBuilder.like(
                            criteriaBuilder.lower(root.get("name")),
                            searchPattern
                    ),
                    criteriaBuilder.like(
                            criteriaBuilder.lower(root.get("email")),
                            searchPattern
                    ),
                    criteriaBuilder.like(
                            criteriaBuilder.lower(root.get("phone")),
                            searchPattern
                    ),
                    criteriaBuilder.like(
                            criteriaBuilder.lower(root.get("department")),
                            searchPattern
                    ),
                    criteriaBuilder.like(
                            criteriaBuilder.lower(root.get("designation")),
                            searchPattern
                    )
            );
        };
    }

    /**
     * Filter employees by department.
     *
     * Example:
     * department = "IT"
     *
     * Only employees belonging to the IT department
     * will be returned.
     */
    public static Specification<Employee> hasDepartment(String department) {

        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(
                        criteriaBuilder.lower(root.get("department")),
                        department.toLowerCase()
                );
    }
}