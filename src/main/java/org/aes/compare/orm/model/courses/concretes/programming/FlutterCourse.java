package org.aes.compare.orm.model.courses.concretes.programming;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import org.aes.compare.orm.model.enums.course.EnumCourse;
import org.aes.compare.orm.model.courses.abstracts.ProgrammingCourse;

@Entity
@DiscriminatorValue("FLUTTER")
public class FlutterCourse extends ProgrammingCourse {

    public FlutterCourse() {
        super(EnumCourse.FLUTTER.getName(), 2.5);
    }

}
