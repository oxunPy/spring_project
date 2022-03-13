package uz.akfa.spring_project.web.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.akfa.spring_project.models.Employee;
import uz.akfa.spring_project.service.EmployeeService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class EmployeeResource {

    private final EmployeeService employeeService;

    public EmployeeResource(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("/employees")
    public ResponseEntity create(@RequestBody Employee employee){
        return ResponseEntity.ok(employeeService.save(employee));
    }

    @PutMapping("employees")
    public ResponseEntity update(@RequestBody Employee employee){
        return ResponseEntity.ok(employeeService.save(employee));
    }

    @GetMapping("/employees")
    public ResponseEntity getAll(){
        List<Employee> employeeList = employeeService.findAll();
        return ResponseEntity.ok(employeeList);
    }

    @GetMapping("/employees/{name}")
    public ResponseEntity getByName(@PathVariable String name){
        List<Employee> employeeList = employeeService.findByName(name);
        return ResponseEntity.ok(employeeList);
    }

    @GetMapping("/employees/search")
    public ResponseEntity getAllByNameLike(@RequestParam String name){
        List<Employee> employeeList = employeeService.findAllByNameLike(name);
        return ResponseEntity.ok(employeeList);
    }

    @DeleteMapping("/employees/{id}")
    public ResponseEntity delete(@PathVariable Long id){
        employeeService.delete(id);
        return ResponseEntity.ok("Row has benn deleted successfully.");
    }


}
