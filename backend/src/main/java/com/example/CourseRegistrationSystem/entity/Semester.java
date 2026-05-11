package com.example.CourseRegistrationSystem.entity;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "semesters")
public class Semester {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "semester_id")
    private Long semesterId;

    @NotBlank(message = "Term name cannot be blank")
    @Column(name = "term_name")
    private String termName;

    @NotNull(message = "Start date cannot be null")
    @Column(name = "start_date")
    private LocalDate startDate;

    @NotNull(message = "End date cannot be null")
    @Column(name = "end_date")
    private LocalDate endDate;

    @OneToMany(mappedBy = "semester", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("semester")
    private List<Section> sections;

    public Semester() {}

    public Semester(Long semesterId, String termName, LocalDate startDate, LocalDate endDate) {
        this.semesterId = semesterId;
        this.termName = termName;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Long getSemesterId() {
        return semesterId;
    }

    public void setSemesterId(Long semesterId) {
        this.semesterId = semesterId;
    }

    public String getTermName() {
        return termName;
    }

    public void setTermName(String termName) {
        this.termName = termName;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public void setSections(List<Section> sections) {
        this.sections = sections;
    }
    
    public List<Section> getSections() {
        return sections;
    }
}
