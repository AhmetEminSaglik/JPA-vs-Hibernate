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

    @Override
    public void resetTable() {
        initializeTransaction();

        getEntityManager().createQuery("DELETE FROM Student")
                .executeUpdate();
        commit();

        initializeTransaction();

        getEntityManager().createNativeQuery("ALTER TABLE students AUTO_INCREMENT = 1")
                .executeUpdate();
        commit();

    }

    @Override
    public Student findStudentById(int id) {
        initializeTransaction();
        return getEntityManager().find(Student.class,id);
    }

}
