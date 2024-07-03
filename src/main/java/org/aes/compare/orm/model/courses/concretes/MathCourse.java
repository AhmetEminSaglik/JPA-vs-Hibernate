package org.aes.compare.orm.model.courses.concretes;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.aes.compare.orm.model.EnumCourse;
import org.aes.compare.orm.model.courses.abstracts.Course;

@Entity
@DiscriminatorValue("MATH")
public class MathCourse extends Course {
    public MathCourse() {
        super(EnumCourse.MATH.getName(),4);
    }
}
