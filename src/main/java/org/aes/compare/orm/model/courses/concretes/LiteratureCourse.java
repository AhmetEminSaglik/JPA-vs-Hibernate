package org.aes.compare.orm.model.courses.concretes;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import org.aes.compare.orm.model.enums.course.EnumCourse;
import org.aes.compare.orm.model.courses.abstracts.Course;

@Entity
@DiscriminatorValue("LITERATURE")
public class LiteratureCourse extends Course {

    public LiteratureCourse() {
        super(EnumCourse.LITERATURE.getName(), 2);
    }

}
