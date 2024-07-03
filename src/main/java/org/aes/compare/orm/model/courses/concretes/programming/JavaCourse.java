package org.aes.compare.orm.model.courses.concretes.programming;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import org.aes.compare.orm.model.courses.abstracts.ProgrammingCourse;

@Entity
//@Table(name = "flutter")
@DiscriminatorValue("java")
public class JavaCourse extends ProgrammingCourse {
    public JavaCourse() {
        super(3.5);
    }
}
