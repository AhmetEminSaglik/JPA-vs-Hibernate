package org.aes.compare.orm.business.concrete.jpa;

import org.aes.compare.orm.business.abstracts.StudentService;
import org.aes.compare.orm.model.Student;

public class StudentServiceImpJPA extends JpaImplementation<Student> implements StudentService {

    @Override
    public Student save(Student s) {
        initializeTransaction();
        getEntityManager().persist(s);
        commit();
        return s;
    }

    @Override
    public void update(Student s) {
        initializeTransaction();
        getEntityManager().merge(s);
        commit();
    }

}
