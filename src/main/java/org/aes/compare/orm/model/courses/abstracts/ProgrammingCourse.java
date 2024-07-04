package org.aes.compare.orm.model.courses.abstracts;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
//@Table(name = "programming_courses")
@DiscriminatorValue("programming-course")
public abstract  class ProgrammingCourse extends Course {
    public ProgrammingCourse(String name, double credit) {
        super(name, credit);
    }
}
