package org.aes.compare.orm.model.courses.concretes.programming;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import org.aes.compare.orm.model.courses.abstracts.ProgrammingCourse;

@Entity
//@Table(name = "flutter")
@DiscriminatorValue("react")
public class React extends ProgrammingCourse {
    public React() {
        super(1.5);
    }
}
