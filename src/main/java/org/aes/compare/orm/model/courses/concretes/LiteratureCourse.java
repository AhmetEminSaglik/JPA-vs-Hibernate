package org.aes.compare.orm.model.courses.concretes;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.aes.compare.orm.model.EnumCourse;
import org.aes.compare.orm.model.courses.abstracts.Course;

@Entity
@DiscriminatorValue("LITERATURE")
public class LiteratureCourse extends Course {
    public LiteratureCourse() {
        super(EnumCourse.LITERATURE.getName(), 2);
    }
}
