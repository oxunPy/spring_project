package uz.akfa.spring_project.service;

import org.springframework.stereotype.Service;
import uz.akfa.spring_project.models.Employee;
import uz.akfa.spring_project.repository.EmployeeRepository;

import java.util.List;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee save(Employee employee){
        return employeeRepository.save(employee);
    }

    public List<Employee> findAll(){
        return employeeRepository.findAll();
    }

    public Employee findById(Long id){
        return employeeRepository.findById(id).get();
    }

    public List<Employee> findByName(String name){
        return employeeRepository.findByNameQueryNative(name);
    }

    public List<Employee> findByNameAndLastName(String name, String lastName){
        return employeeRepository.findByNameAndLastName(name, lastName);
    }

    public List<Employee> findAllByNameLike(String name){
        return employeeRepository.findAllByNameLike(name);
    }

    public List<Employee> findAllByNameStartingWith(String name){
        return employeeRepository.findAllByNameStartingWith(name);
    }
    public List<Employee> findAllByNameEndingWith(String name){
        return employeeRepository.findAllByNameEndingWith(name);
    }

    public void delete(Long id){
        Employee one = employeeRepository.getOne(id);
        employeeRepository.delete(one);
    }
}
