package org.aes.compare.orm.model.courses.abstracts;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "programming_course")
@DiscriminatorValue("programming-course")
public abstract  class ProgrammingCourse extends Course {
    public ProgrammingCourse(double credit) {
        super(credit);
    }
}
