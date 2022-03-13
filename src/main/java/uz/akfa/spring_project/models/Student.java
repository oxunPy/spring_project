package uz.akfa.spring_project.models;


import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Student{
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = true)
    private String name;

    @Column(name = "last_name", nullable = true)
    private String lastName;

    public Student(Long id, String name, String last_name){
        this.id = id;
        this.name = name;
        this.lastName = last_name;
    }

    public Student() {

    }
}
