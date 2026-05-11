package com.example.CourseRegistrationSystem.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;


@Entity
@Table(name = "departments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dept_id")
    private Long deptId;

    @NotBlank(message = "Department name is required")
    @Size(max = 100, message = "Department name must be at most 100 characters")
    @Column(name = "dept_name", nullable = false, length = 100, unique = true)
    private String deptName;

    @NotBlank(message = "Department email is required")
    @Email(message = "Invalid department email")
    @Size(max = 150, message = "Department email must be at most 150 characters")
    @Column(name = "dept_email", nullable = false, length = 150, unique = true)
    private String deptEmail;

    @Pattern(
            regexp = "^[0-9]{10,15}$",
            message = "Phone number must contain only digits and be 10 to 15 characters long"
    )
    @NotBlank(message = "Phone number is required")
    @Size(max = 20, message = "Phone number must be at most 20 characters")
    @Column(name = "phone_number", nullable = false, length = 20)
    private String phoneNumber;

    @NotBlank(message = "Office location is required")
    @Size(max = 100, message = "Office location must be at most 100 characters")
    @Column(name = "office_location", nullable = false, length = 100)
    private String officeLocation;

    @OneToMany(
            mappedBy = "department",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonIgnore
    private Set<Program> programs;

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getDeptEmail() {
        return deptEmail;
    }

    public void setDeptEmail(String deptEmail) {
        this.deptEmail = deptEmail;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getOfficeLocation() {
        return officeLocation;
    }

    public void setOfficeLocation(String officeLocation) {
        this.officeLocation = officeLocation;
    }

    public Set<Program> getPrograms() {
        return programs;
    }

    public void setPrograms(Set<Program> programs) {
        this.programs = programs;
    }

    public void addProgram(Program program) {
        programs.add(program);
        program.setDepartment(this);
    }

    public void removeProgram(Program program) {
        programs.remove(program);
        program.setDepartment(null);
    }
}

