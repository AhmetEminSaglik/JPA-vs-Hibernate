package org.aes.compare.orm.model.courses.concretes;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.aes.compare.orm.model.courses.abstracts.Course;

@Entity
@Table(name = "math_course")
@DiscriminatorValue("Math")
public class MathCourse extends Course {
    public MathCourse() {
        super(4);
    }
}
