package org.ahmeteminsaglik.orm.business.concrete.comparator;

import org.ahmeteminsaglik.orm.model.ExamResult;

import java.util.Comparator;

public class ExamResultComparator implements Comparator<ExamResult> {
    private final StudentComparator studentComparator = new StudentComparator();
    private final CourseComparator courseComparator = new CourseComparator();

    @Override
    public int compare(ExamResult er1, ExamResult er2) {
        int courseResult = courseComparator.compare(er1.getCourse(), er2.getCourse());
        if (courseResult != 0) {
            return courseResult;
        }
        return studentComparator.compare(er1.getStudent(), er2.getStudent());
    }

}
