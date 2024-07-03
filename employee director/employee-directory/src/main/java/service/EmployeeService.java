package com.example.employeedirectory.service;

import com.example.employeedirectory.entity.Employee;
import com.example.employeedirectory.exception.CustomException;
import com.example.employeedirectory.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    // CRUD operations
    public Employee addEmployee(Employee employee) {
        validateEmployee(employee);
        return employeeRepository.save(employee);
    }

    public Employee updateEmployee(Long id, Employee employee) {
        Employee existingEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new CustomException("Employee not found"));
        existingEmployee.setName(employee.getName());
        existingEmployee.setPosition(employee.getPosition());
        existingEmployee.setDepartment(employee.getDepartment());
        existingEmployee.setSalary(employee.getSalary());
        existingEmployee.setJoiningDate(employee.getJoiningDate());
        existingEmployee.setEmail(employee.getEmail());
        existingEmployee.setExperience(employee.getExperience());
        existingEmployee.setPerformanceScore(employee.getPerformanceScore());
        validateEmployee(existingEmployee);
        return employeeRepository.save(existingEmployee);
    }

    public void deleteEmployee(Long id) {
        Employee existingEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new CustomException("Employee not found"));
        employeeRepository.delete(existingEmployee);
    }

    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new CustomException("Employee not found"));
    }

    public List<Employee> getEmployeesByDepartment(String department) {
        return employeeRepository.findByDepartment(department);
    }

    public Employee updateEmployeeSalary(Long id, BigDecimal newSalary) {
        Employee employee = getEmployeeById(id);
        employee.setSalary(newSalary);
        return employeeRepository.save(employee);
    }

    // Logics

    // Calculate Total Salary by Department
    public BigDecimal calculateTotalSalaryByDepartment(String department) {
        List<Employee> employees = getEmployeesByDepartment(department);
        return employees.stream()
                .map(Employee::getSalary)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    // Find Employees by Joining Date Range
    public List<Employee> findEmployeesByJoiningDateRange(LocalDate startDate, LocalDate endDate) {
        return employeeRepository.findByJoiningDateBetween(startDate, endDate);
    }

    // Update Employee Position Based on Experience
    public Employee updateEmployeePositionBasedOnExperience(Long id) {
        Employee employee = getEmployeeById(id);
        if (employee.getExperience() > 5) {
            employee.setPosition("Senior " + employee.getPosition());
        }
        return employeeRepository.save(employee);
    }

    // Generate Employee Report
    public String generateEmployeeReport(Long id) {
        Employee employee = getEmployeeById(id);
        return String.format("Employee Report: \nName: %s \nPosition: %s \nDepartment: %s \nSalary: %s",
                employee.getName(), employee.getPosition(), employee.getDepartment(), employee.getSalary());
    }

    // Validate Employee Data
    private void validateEmployee(Employee employee) {
        if (employee.getName() == null || employee.getName().isEmpty()) {
            throw new CustomException("Employee name cannot be empty");
        }
        if (!employee.getEmail().contains("@")) {
            throw new CustomException("Invalid email format");
        }
        if (employee.getSalary().compareTo(BigDecimal.ZERO) <= 0) {
            throw new CustomException("Salary must be a positive number");
        }
    }

    // Calculate Average Salary by Position
    public BigDecimal calculateAverageSalaryByPosition(String position) {
        List<Employee> employees = employeeRepository.findAll();
        List<Employee> filteredEmployees = employees.stream()
                .filter(e -> e.getPosition().equals(position))
                .collect(Collectors.toList());
        BigDecimal totalSalary = filteredEmployees.stream()
                .map(Employee::getSalary)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return totalSalary.divide(BigDecimal.valueOf(filteredEmployees.size()), BigDecimal.ROUND_HALF_UP);
    }

    // Employee Promotion Eligibility Check
    public boolean checkEmployeePromotionEligibility(Long id) {
        Employee employee = getEmployeeById(id);
        return employee.getPerformanceScore() > 8.0 && employee.getExperience() > 5;
    }

    // Send Notification to Employees
    public String sendNotificationToEmployees(String message) {
        // This is a placeholder for actual notification logic
        // In a real application, this could be an email or SMS service integration
        return "Notification sent: " + message;
    }

    // Department Performance Analysis
    public List<String> analyzeDepartmentPerformance() {
        List<Employee> employees = employeeRepository.findAll();
        return employees.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment, Collectors.averagingDouble(Employee::getPerformanceScore)))
                .entrySet().stream()
                .sorted((e1, e2) -> Double.compare(e2.getValue(), e1.getValue()))
                .map(e -> String.format("Department: %s, Average Performance Score: %.2f", e.getKey(), e.getValue()))
                .collect(Collectors.toList());
    }

    // Update Department Budget Based on Employee Count
    public BigDecimal updateDepartmentBudget(String department) {
        List<Employee> employees = getEmployeesByDepartment(department);
        BigDecimal budget = BigDecimal.valueOf(employees.size()).multiply(BigDecimal.valueOf(50000));
        return budget;
    }

    // Find Top N Highest Paid Employees
    public List<Employee> findTopNHighestPaidEmployees(int n) {
        return employeeRepository.findTopNByOrderBySalaryDesc(n);
    }

    // Check for Duplicate Employees
    public List<Employee> checkForDuplicateEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        return employees.stream()
                .collect(Collectors.groupingBy(Employee::getEmail, Collectors.counting()))
                .entrySet().stream()
                .filter(e -> e.getValue() > 1)
                .map(e -> employees.stream().filter(emp -> emp.getEmail().equals(e.getKey())).collect(Collectors.toList()))
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }
}

