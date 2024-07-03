package org.aes.compare.orm.model.courses.concretes;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import org.aes.compare.orm.model.EnumCourse;
import org.aes.compare.orm.model.courses.abstracts.Course;

@Entity
@DiscriminatorValue("SCIENCE")
public class ScienceCourse extends Course {
    public ScienceCourse() {
        super(EnumCourse.SCIENCE.getName(), 3);
    }
}
