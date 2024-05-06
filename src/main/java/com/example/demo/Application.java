package com.example.demo;

import com.github.javafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(StudentRepository studentRepository){
        return args -> {
//            generateRandomStudents(studentRepository);
//            sorting(studentRepository);
//            paging(studentRepository);
            Faker faker = new Faker();
            String firstName = faker.name().firstName();
            String lastName = faker.name().lastName();
            String email = String.format("%s.%s@illinois.edu", firstName, lastName);
            Student student = new Student(
                    firstName,
                    lastName,
                    email,
                    faker.number().numberBetween(17, 55));

            //do not need the bookRepository, we can save the book
            //because of the CASCADE in Student
            student.addBook(new Book("clean code", LocalDateTime.now().minusDays(4)));
            student.addBook(new Book("Think and Grow Rich", LocalDateTime.now()));
            student.addBook(new Book("Spring Data JPA", LocalDateTime.now().minusYears(1)));

            StudentIdCard studentIdCard = new StudentIdCard(
                    "12345678",
                    student
            );

            student.setStudentIdCard(studentIdCard);

//            student.enrollToCourse(new Course("computer scinece", "IT"));
//            student.enrollToCourse(new Course("database", "IT"));
            student.addEnrollments(new Enrollment(new EnrollmentId(1L, 1L), student, new Course("computer scinece", "IT"), LocalDateTime.now()));
            student.addEnrollments(new Enrollment(new EnrollmentId(1L, 2L), student, new Course("database", "IT"), LocalDateTime.now()));

            studentRepository.save(student);
//            studentRepository.deleteById(1L);
            studentRepository.findById(1L).ifPresent(s -> {
                System.out.println("fetch book lazy...");
                List<Book> books = student.getBooks();
                books.forEach(book -> System.out.println(
                        s.getFirstName() + "borrowed " + book.getBookName()));

            });
//            studentIdCardRepository.findById(1L)
//                    .ifPresent(System.out::println);

        };
    }

    private static void paging(StudentRepository studentRepository) {
        PageRequest pageRequest = PageRequest.of(
                0,
                5,
                Sort.by("firstName").ascending());
        Page<Student> page = studentRepository.findAll(pageRequest);
        System.out.println(page);
    }

    private static void sorting(StudentRepository studentRepository) {
        Sort sort = Sort.by("firstName").ascending().and(Sort.by("age").descending());

        studentRepository.findAll(sort)
                .forEach(student -> System.out.println(student.getFirstName() + " " + student.getAge()));
    }

    private static void generateRandomStudents(StudentRepository studentRepository) {
        Faker faker = new Faker();
        for (int i = 0; i < 20; i++) {
            String firstName = faker.name().firstName();
            String lastName = faker.name().lastName();
            String email = String.format("%s.%s@illinois.edu", firstName, lastName);
            Student student = new Student(
                    firstName,
                    lastName,
                    email,
                    faker.number().numberBetween(17, 55));
            studentRepository.save(student);
        }

    }

}
