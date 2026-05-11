package com.example.CourseRegistrationSystem.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "undergrads")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Undergrad extends Student {

    @NotNull(message = "Year of study cannot be null")
    @Min(value = 1, message = "Year of study must be at least 1")
    @Max(value = 4, message = "Year of study cannot be more than 4")
    @Column(name = "year_of_study")
    private Integer yearOfStudy;

    public @NotNull(message = "Year of study cannot be null") @Min(value = 1, message = "Year of study must be at least 1") @Max(value = 4, message = "Year of study cannot be more than 4") Integer getYearOfStudy() {
        return yearOfStudy;
    }

    public void setYearOfStudy(@NotNull(message = "Year of study cannot be null") @Min(value = 1, message = "Year of study must be at least 1") @Max(value = 4, message = "Year of study cannot be more than 4") Integer yearOfStudy) {
        this.yearOfStudy = yearOfStudy;
    }
}

