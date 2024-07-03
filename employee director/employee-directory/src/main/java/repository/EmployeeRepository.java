package com.example.employeedirectory.repository;

import com.example.employeedirectory.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findByDepartment(String department);
    List<Employee> findByJoiningDateBetween(LocalDate startDate, LocalDate endDate);
    List<Employee> findByEmail(String email);
    List<Employee> findTopNByOrderBySalaryDesc(int n);
}

