package org.aes.compare.orm.model.courses.concretes.programming;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import org.aes.compare.orm.model.courses.abstracts.ProgrammingCourse;

@Entity
//@Table(name = "flutter")
@DiscriminatorValue("flutter")
public class FlutterCourse extends ProgrammingCourse {
    public FlutterCourse() {
        super(2.5);
    }
}
