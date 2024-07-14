package org.aes.compare.orm.business.concrete.comparator;

import org.aes.compare.orm.model.courses.abstracts.Course;

import java.util.Comparator;

public class CourseComparator implements Comparator<Course> {

    @Override
    public int compare(Course c1, Course c2) {
        return c1.getName().compareTo(c2.getName());
    }
}
