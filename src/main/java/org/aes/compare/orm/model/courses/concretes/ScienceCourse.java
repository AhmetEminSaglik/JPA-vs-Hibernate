package org.aes.compare.orm.model.courses.concretes;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.aes.compare.orm.model.courses.abstracts.Course;

@Entity
@Table(name = "science_course")
public class ScienceCourse extends Course {
    public ScienceCourse() {
        super(3);
    }
}
