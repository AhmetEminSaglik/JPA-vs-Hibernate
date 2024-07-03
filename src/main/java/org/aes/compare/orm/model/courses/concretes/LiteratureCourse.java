package org.aes.compare.orm.model.courses.concretes;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.aes.compare.orm.model.courses.abstracts.Course;

@Entity
@Table(name = "literature_course")
@DiscriminatorValue("Literature")
public class LiteratureCourse extends Course {
    public LiteratureCourse() {
        super(2);
    }
}
