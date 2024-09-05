package org.ahmeteminsaglik.orm.business.concrete.comparator;

import org.ahmeteminsaglik.orm.model.Student;

import java.util.Comparator;

public class StudentComparator implements Comparator<Student> {
    @Override
    public int compare(Student s1, Student s2) {
        return s1.getName().compareTo(s2.getName());
    }
}
