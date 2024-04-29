package com.example.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(StudentRepository studentRepository){
        return args -> {
            Student maria = new Student("Maria", "Jones", "maria.jones@amigoscode.edu", 18);
            Student maria2 = new Student("Maria", "Jones", "maria2.jones@amigoscode.edu", 18);
            Student ahmed = new Student("ahmed", "Ali", "ahmed.jones@amigoscode.edu", 21);
            studentRepository.saveAll(List.of(maria, maria2, ahmed));

            studentRepository
                    .findStudentByEmail("ahmed.jones@amigoscode.edu")
                    .ifPresentOrElse(
                            System.out::println,
                            () -> System.out.println("Student with email ahmed.jones@amigoscode not found"));
            studentRepository.findStudentsByFirstNameEqualsAndAgeEqualsNative("Maria", 18)
                    .forEach(System.out::println);

            System.out.println(studentRepository.deleteStudentById(2L));
        };
    }

}
