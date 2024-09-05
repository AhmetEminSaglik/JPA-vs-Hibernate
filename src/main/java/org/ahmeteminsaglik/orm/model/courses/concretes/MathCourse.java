package org.ahmeteminsaglik.orm.model.courses.concretes;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import org.ahmeteminsaglik.orm.model.courses.abstracts.Course;
import org.ahmeteminsaglik.orm.model.enums.course.EnumCourse;

@Entity
@DiscriminatorValue("MATH")
public class MathCourse extends Course {

    public MathCourse() {
        super(EnumCourse.MATH.getName(), 4);
    }

}
