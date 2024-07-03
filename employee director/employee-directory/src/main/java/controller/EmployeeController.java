package com.example.employeedirectory.controller;

import com.example.employeedirectory.entity.Employee;
import com.example.employeedirectory.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping
    public Employee addEmployee(@RequestBody Employee employee) {
        return employeeService.addEmployee(employee);
    }

    @PutMapping("/{id}")
    public Employee updateEmployee(@PathVariable Long id, @RequestBody Employee employee) {
        return employeeService.updateEmployee(id, employee);
    }

    @DeleteMapping("/{id}")
    public void deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
    }

    @GetMapping("/{id}")
    public Employee getEmployeeById(@PathVariable Long id) {
        return employeeService.getEmployeeById(id);
    }

    @GetMapping("/department/{department}")
    public List<Employee> getEmployeesByDepartment(@PathVariable String department) {
        return employeeService.getEmployeesByDepartment(department);
    }

    @PutMapping("/{id}/salary")
    public Employee updateEmployeeSalary(@PathVariable Long id, @RequestParam BigDecimal newSalary) {
        return employeeService.updateEmployeeSalary(id, newSalary);
    }

    @GetMapping("/total-salary/{department}")
    public BigDecimal calculateTotalSalaryByDepartment(@PathVariable String department) {
        return employeeService.calculateTotalSalaryByDepartment(department);
    }

    @GetMapping("/joining-date-range")
    public List<Employee> findEmployeesByJoiningDateRange(@RequestParam LocalDate startDate, @RequestParam LocalDate endDate) {
        return employeeService.findEmployeesByJoiningDateRange(startDate, endDate);
    }

    @PutMapping("/update-position/{id}")
    public Employee updateEmployeePositionBasedOnExperience(@PathVariable Long id) {
        return employeeService.updateEmployeePositionBasedOnExperience(id);
    }

    @GetMapping("/report/{id}")
    public String generateEmployeeReport(@PathVariable Long id) {
        return employeeService.generateEmployeeReport(id);
    }

    @GetMapping("/average-salary/{position}")
    public BigDecimal calculateAverageSalaryByPosition(@PathVariable String position) {
        return employeeService.calculateAverageSalaryByPosition(position);
    }

    @GetMapping("/promotion-eligibility/{id}")
    public boolean checkEmployeePromotionEligibility(@PathVariable Long id) {
        return employeeService.checkEmployeePromotionEligibility(id);
    }

    @PostMapping("/notification")
    public String sendNotificationToEmployees(@RequestParam String message) {
        return employeeService.sendNotificationToEmployees(message);
    }

    @GetMapping("/performance-analysis")
    public List<String> analyzeDepartmentPerformance() {
        return employeeService.analyzeDepartmentPerformance();
    }

    @PutMapping("/update-budget/{department}")
    public BigDecimal updateDepartmentBudget(@PathVariable String department) {
        return employeeService.updateDepartmentBudget(department);
    }

    @GetMapping("/top-paid")
    public List<Employee> findTopNHighestPaidEmployees(@RequestParam int n) {
        return employeeService.findTopNHighestPaidEmployees(n);
    }

    @GetMapping("/duplicates")
    public List<Employee> checkForDuplicateEmployees() {
        return employeeService.checkForDuplicateEmployees();
    }
}
