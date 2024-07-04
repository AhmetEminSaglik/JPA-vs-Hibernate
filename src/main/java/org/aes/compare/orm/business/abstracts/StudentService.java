package org.aes.compare.orm.business.abstracts;

import org.aes.compare.orm.model.Student;

public interface StudentService {
    Student save(Student s);

    void update(Student s);

    void resetTable();

    Student findStudentById(int id);
}
