package uz.akfa.spring_project.web.rest;

import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.akfa.spring_project.models.Student;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class StudentController {

    @RequestMapping(value = "/student", method = RequestMethod.GET)
    public ResponseEntity getAll(){
        List<Student> students = new ArrayList<>();
        Student s1 = new Student(1L, "Jack", "Black");
        Student s2 = new Student(2L, "James", "Smith");
        Student s3 = new Student(3L, "Zach", "Gates");
        Student s4 = new Student(4L, "Kate", "Small");
        students.add(s1);
        students.add(s2);
        students.add(s3);
        students.add(s4);
        return ResponseEntity.ok(students);
    }

    @GetMapping("/students/{id}")
    public ResponseEntity getOne(@PathVariable Long id){
        return ResponseEntity.ok(new Student(id, "John", "Doe"));
    }

    @PostMapping("/students")
    public ResponseEntity create(@RequestBody Student student){
        return ResponseEntity.ok(student);
    }

    @PostMapping("/students/{id}")
    public ResponseEntity update(@PathVariable(name = "id") Long id, @RequestBody Student student){
        Student newStudent = new Student(id, student.getName(), student.getLastName());
        return ResponseEntity.ok(newStudent);
    }

    @GetMapping("/students")
    public Student getOne(@RequestParam Long id,
                           @RequestParam String name,
                           @RequestParam String lastName){
        return new Student(id, name, lastName);
    }

}
