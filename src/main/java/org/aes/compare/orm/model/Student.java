package org.aes.compare.orm.model;

import jakarta.persistence.*;
import org.aes.compare.orm.model.courses.abstracts.Course;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "grade",nullable = false)
    private int grade;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE,/*CascadeType.PERSIST,*/CascadeType.DETACH,CascadeType.REFRESH/*CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH*/})
    @JoinTable(/*name = "student_course",*/
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id"))
    private List<Course> courses;

    @OneToOne(cascade = {CascadeType.MERGE,CascadeType.DETACH,CascadeType.REFRESH,CascadeType.REMOVE})
    @JoinColumn(name = "address_id",nullable = false/*, referencedColumnName = "id"*/)
    private Address address;

    public Student() {
    }
    public Student(String name, int grade, Address address) {
        this.name = name;
        this.grade = grade;
        this.address = address;
    }

    public void addCourses(List<Course> courses) {
        courses.forEach(this::addCourse);
    }
    public void addCourse(Course course) {
        if (courses == null) {
            courses = new ArrayList<>();
        }
        if (!courses.contains(course)) {
            courses.add(course);
        }

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

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", grade=" + grade +
                ", address=" + address +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return id == student.id && grade == student.grade && Objects.equals(name, student.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, grade);
    }

}
