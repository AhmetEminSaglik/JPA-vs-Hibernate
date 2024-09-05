package org.ahmeteminsaglik.orm.model.courses.abstracts;

import jakarta.persistence.*;
import org.ahmeteminsaglik.orm.model.Student;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "courses", uniqueConstraints = {@UniqueConstraint(columnNames = {"name"})})
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "credit", nullable = false)
    private double credit;


    @ManyToMany(mappedBy = "courses", fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    private List<Student> students;

    public Course(Course course) {
        this.name = course.getName();
        this.credit = course.getCredit();
        this.students = course.getStudents();
        this.id = course.getId();
    }

    public Course(String name, double credit) {
        this.name = name;
        this.credit = credit;
    }

    public Course(double credit, List<Student> students) {
        this.credit = credit;
        this.students = students;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCredit() {
        return credit;
    }

    public void setCredit(double credit) {
        this.credit = credit;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public void addStudent(Student student) {
        if (students == null) {
            students = new ArrayList<>();
        }
        if (!students.contains(student)) {
            students.add(student);
            student.addCourse(this);
        }

    }

    @Override
    public String toString() {
        return getClass().getSimpleName().split("Course")[0] + "Course{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", credit=" + credit +
                '}';
    }

}
