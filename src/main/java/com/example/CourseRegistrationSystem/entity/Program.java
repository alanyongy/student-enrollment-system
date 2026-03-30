package com.example.CourseRegistrationSystem.entity;
import java.util.HashSet;
import java.util.Set;

import com.example.CourseRegistrationSystem.enums.ProgramType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "programs")
public class Program {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "program_id")
    private Long programId;

    @NotBlank(message = "Program name is required")
    @Size(max = 150, message = "Program name must be at most 150 characters")
    @Column(name = "program_name", nullable = false, length = 150)
    private String programName;

    @Size(max = 500, message = "Description must be at most 500 characters")
    @Column(name = "description", length = 500)
    private String description;

    @NotNull(message = "Department is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dept_id", nullable = false)
    private Department department;

    @NotNull(message = "Program type is required")
    @Enumerated(EnumType.STRING)
    @Column(name = "program_type", nullable = false, length = 50)
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
