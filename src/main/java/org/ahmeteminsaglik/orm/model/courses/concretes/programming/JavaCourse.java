package org.ahmeteminsaglik.orm.model.courses.concretes.programming;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import org.ahmeteminsaglik.orm.model.courses.abstracts.ProgrammingCourse;
import org.ahmeteminsaglik.orm.model.enums.course.EnumCourse;

@Entity
@DiscriminatorValue("JAVA")
public class JavaCourse extends ProgrammingCourse {

    public JavaCourse() {
        super(EnumCourse.JAVA.getName(), 3.5);
    }

}
