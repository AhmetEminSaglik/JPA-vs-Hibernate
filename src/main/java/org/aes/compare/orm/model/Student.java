package org.aes.compare.orm.model;

import jakarta.persistence.*;
import org.aes.compare.orm.model.courses.abstracts.Course;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "grade",nullable = false)
    private int grade;

    @ManyToMany(cascade = {CascadeType.MERGE,CascadeType.DETACH,CascadeType.REFRESH/*CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH*/})
    @JoinTable(/*name = "student_course",*/
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id"))
    private List<Course> courses;

    @OneToOne(cascade = {CascadeType.MERGE,CascadeType.DETACH,CascadeType.REFRESH,CascadeType.REMOVE})
    @JoinColumn(name = "address_id",nullable = false/*, referencedColumnName = "id"*/)
    private Address address;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<ExamResult> examResults;

    public Student() {
    }

    /* public Student(String name, int grade, List<Course> courses, Address address, List<ExamResult> examResults) {
         this.name = name;
         this.grade = grade;
         this.courses = courses;
         this.address = address;
         this.examResults = examResults;
     }
 */

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
            System.out.println("Student courses olusturuldu");
            courses = new ArrayList<>();
        }
        if (!courses.contains(course)) {
            System.out.println(getName()+"'a "+course.getClass().getSimpleName()+" eklendi");
            courses.add(course);
        } else {
            System.out.println("Course zaten ekli");
        }

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public List<ExamResult> getExamResults() {
        return examResults;
    }

    public void addExamResult(ExamResult examResult) {
        if (examResults == null) {
            examResults = new ArrayList<>();
            System.out.println("Exam result list is initialized");
        }
        if (!examResults.contains(examResult)) {
            examResults.add(examResult);
            examResult.setStudent(this);
        } else {
            System.out.println("Exam result is already added to this student");
        }

    }


    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", grade=" + grade +
                ", address=" + address +
                ", courses=" + courses +
                ", examResults=" + examResults +
                '}';
    }

    /*    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", grade=" + grade +
                ", address=" + address +
                '}';
    }*/
}
