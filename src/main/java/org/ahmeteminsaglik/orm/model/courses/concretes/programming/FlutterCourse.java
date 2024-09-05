package org.ahmeteminsaglik.orm.model.courses.concretes.programming;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import org.ahmeteminsaglik.orm.model.courses.abstracts.ProgrammingCourse;
import org.ahmeteminsaglik.orm.model.enums.course.EnumCourse;

@Entity
@DiscriminatorValue("FLUTTER")
public class FlutterCourse extends ProgrammingCourse {

    public FlutterCourse() {
        super(EnumCourse.FLUTTER.getName(), 2.5);
    }

}
