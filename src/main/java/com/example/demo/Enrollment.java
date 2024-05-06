package com.example.demo;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity(name = "Enrollment")
@Table(name = "enrollment")
public class Enrollment {
    @EmbeddedId
    private EnrollmentId id;

    @ManyToOne
    @MapsId("studentId") //the one in the EnrollmentId
    @JoinColumn(
            name = "student_id",
            foreignKey = @ForeignKey(name = "enrollment_student_id_fk")
    )
    private Student student;

    @ManyToOne
    @MapsId("courseId")
    @JoinColumn(name = "course_id",
            foreignKey = @ForeignKey(name = "enrollment_course_id_fk")
    )
    private Course course;

    @Column(name = "created_at", nullable = false, columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
    private LocalDateTime createAt;

    public Enrollment(EnrollmentId id, Student student, Course course, LocalDateTime createAt) {
        this.id = id;
        this.student = student;
        this.course = course;
        this.createAt = createAt;
    }

    public Enrollment(Student student, Course course, LocalDateTime createAt) {
        this.student = student;
        this.course = course;
        this.createAt = createAt;
    }

    public Enrollment() {

    }

    public EnrollmentId getId() {
        return id;
    }

    public void setId(EnrollmentId id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Enrollment that = (Enrollment) o;
        return Objects.equals(id, that.id) && Objects.equals(student, that.student) && Objects.equals(course, that.course);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, student, course);
    }
}
