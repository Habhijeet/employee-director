package com.example.employeedirectory.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String position;

    @Column(nullable = false)
    private String department;

    @Column(nullable = false)
    private BigDecimal salary;

    @Column(nullable = false)
    private LocalDate joiningDate;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private int experience;

    @Column(nullable = false)
    private double performanceScore;

    // Getters and Setters
}
