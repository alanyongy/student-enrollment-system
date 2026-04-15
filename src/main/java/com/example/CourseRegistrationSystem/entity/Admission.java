package com.example.CourseRegistrationSystem.entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "admissions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Admission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "admission_id")
    private Long admissionId;

    public Long getAdmissionId() {
        return admissionId;
    }

    public void setAdmissionId(Long admissionId) {
        this.admissionId = admissionId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Program getProgram() {
        return program;
    }

    public void setProgram(Program program) {
        this.program = program;
    }

    @NotNull(message = "Student cannot be null for an admission")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @NotNull(message = "Student cannot be null for an admission")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "program_id", nullable = false)
    private Program program;
}