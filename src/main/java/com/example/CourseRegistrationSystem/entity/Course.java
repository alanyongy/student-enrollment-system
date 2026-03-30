package com.example.CourseRegistrationSystem.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    private Long courseId;

    @NotBlank(message = "Course number cannot be blank")
    @Size(max = 50, message = "Course number cannot be longer than 50 characters")
    @Column(name = "course_number")
    private String courseNumber;

    @NotBlank(message = "Description cannot be blank")
    @Size(max = 255, message = "Description cannot be longer than 255 characters")
    @Column(name = "description")
    private String description;

    @NotNull(message = "Credits cannot be null")
    @Min(value = 3, message = "Credits must be at least 3")
    @Column(name = "credits")
    private Integer credits;

    @NotNull(message = "Department cannot be null")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dept_id", nullable = false)
    private Department department;

    // Weak-entity relationship: one course has many sections
    @OneToMany(
            mappedBy = "course",
            cascade = CascadeType.ALL,      // persist/remove sections with course
            orphanRemoval = true            // remove sections if detached from course
    )
    private List<Section> sections = new ArrayList<>();

    public Course() {}

    public Course(Long courseId, String courseNumber, String description, Integer credits, Department department) {
        this.courseId = courseId;
        this.courseNumber = courseNumber;
        this.description = description;
        this.credits = credits;
        this.department = department;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getCourseNumber() {
        return courseNumber;
    }

    public void setCourseNumber(String courseNumber) {
        this.courseNumber = courseNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getCredits() {
        return credits;
    }

    public void setCredits(Integer credits) {
        this.credits = credits;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public List<Section> getSections() {
        return sections;
    }

    public void setSections(List<Section> sections) {
        this.sections = sections;
    }

    // helper methods to keep both sides in sync
    public void addSection(Section section) {
        sections.add(section);
        section.setCourse(this);
    }

    public void removeSection(Section section) {
        sections.remove(section);
        section.setCourse(null);
    }
}
