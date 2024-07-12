package org.aes.compare.orm.consoleapplication;

import org.aes.compare.orm.model.courses.abstracts.Course;
import org.aes.compare.orm.model.courses.concretes.OtherCourse;

public class CourseFacade {

    public Course save(){
        System.out.println("COURSE SAVE ISLEMI IMPLEMENT EDILMEDI HENU ");

        return new OtherCourse();
    }
}
