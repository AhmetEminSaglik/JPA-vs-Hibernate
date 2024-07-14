package org.aes.compare.orm.model;

import jakarta.persistence.*;
import org.aes.compare.orm.model.courses.abstracts.Course;

import java.util.Objects;

@Entity
@Table(name = "exam_result")
public class ExamResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "score")
    private double score;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.MERGE})
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "student_id"/*, nullable = false*/, nullable = false)
    private Student student;

    public ExamResult() {
    }

    public ExamResult(double score, Course course, Student student) {
        this.score = score;
        this.course = course;
        setStudent(student);
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Student getStudent() {
        return student;
    }

/*    @Override
    public String toString() {
        return "ExamResult{" +
                "id=" + id +
                ", score=" + score +
                ", course='" + course + '\'' +
                '}';
    }*/

    @Override
    public String toString() {
        return "ExamResult{" +
                "id=" + id +
                ", score=" + score +
                ", course=" + course +
                ", student=" + student +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ExamResult)) return false;
        ExamResult that = (ExamResult) o;
        return id == that.id && Double.compare(score, that.score) == 0 && Objects.equals(course, that.course) && Objects.equals(student, that.student);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, score, course, student);
    }
}
