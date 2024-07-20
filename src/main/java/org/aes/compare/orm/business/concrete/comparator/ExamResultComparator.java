package org.aes.compare.orm.business.concrete.comparator;

import org.aes.compare.orm.model.ExamResult;

import java.util.Comparator;

public class ExamResultComparator implements Comparator<ExamResult> {
    private final StudentComparator studentComparator = new StudentComparator();
    private final CourseComparator courseComparator = new CourseComparator();

    @Override
    public int compare(ExamResult er1, ExamResult er2) {
        int studentCompareResult = studentComparator.compare(er1.getStudent(), er2.getStudent());
        if (studentCompareResult != 0) {
            return studentCompareResult;
        }
        return courseComparator.compare(er1.getCourse(), er2.getCourse());
    }

}
