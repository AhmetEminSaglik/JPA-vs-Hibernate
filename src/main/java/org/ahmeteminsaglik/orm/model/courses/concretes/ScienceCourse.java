package org.ahmeteminsaglik.orm.model.courses.concretes;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import org.ahmeteminsaglik.orm.model.courses.abstracts.Course;
import org.ahmeteminsaglik.orm.model.enums.course.EnumCourse;

@Entity
@DiscriminatorValue("SCIENCE")
public class ScienceCourse extends Course {

    public ScienceCourse() {
        super(EnumCourse.SCIENCE.getName(), 3);
    }

}
