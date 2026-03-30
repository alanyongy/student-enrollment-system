package com.example.CourseRegistrationSystem.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "semester_sections")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings({"unused"})
public class SemesterSection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "semester_section_id")
    private Long semesterSectionId;

    @NotNull(message = "Semester cannot be null for a semester section")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "semester_id", nullable = false)
    private Semester semester;

    @NotNull(message = "Section cannot be null for a semester section")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "section_id", nullable = false)
    private Section section;

    public Long getSemesterSectionId() {
        return semesterSectionId;
    }

    public void setSemesterSectionId(Long semesterSectionId) {
        this.semesterSectionId = semesterSectionId;
    }

    public Semester getSemester() {
        return semester;
    }

    public void setSemester(Semester semester) {
        this.semester = semester;
    }

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }
}
