package com.example.CourseRegistrationSystem.entity;
import java.util.HashSet;
import java.util.Set;

import com.example.CourseRegistrationSystem.enums.ProgramType;
import jakarta.persistence.*;

@Entity
@Table(name = "programs")
public class Program {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "program_id")
    private Long programId;

    @Column(name = "program_name")
    private String programName;

    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dept_id", nullable = false)
    private Department department;

    @Enumerated(EnumType.STRING)
    @Column(name = "program_type")
    private ProgramType programType;

    public Program() {}

    public Program(Long programId, String programName, String description, Department department, ProgramType programType) {
        this.programId = programId;
        this.programName = programName;
        this.description = description;
        this.department = department;
        this.programType = programType;
    }

    public Long getProgramId() {
        return programId;
    }

    public void setProgramId(Long programId) {
        this.programId = programId;
    }

    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public ProgramType getProgramType() {
        return programType;
    }

    public void setProgramType(ProgramType programType) {
        this.programType = programType;
    }
}
