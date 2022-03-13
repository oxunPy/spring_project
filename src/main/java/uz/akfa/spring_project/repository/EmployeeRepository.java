package uz.akfa.spring_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.akfa.spring_project.models.Employee;

import javax.websocket.server.PathParam;
import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    List<Employee> findByName(String name);

    List<Employee> findByNameAndLastName(String name, String lastName);

    // Non-native query
    @Query("select e from Employee e where e.name = :name")
    List<Employee> findByNameQuery(@PathParam("name") String name);

    // Native query
    @Query(value = "select * from employee e where e.name = :name", nativeQuery = true)
    List<Employee> findByNameQueryNative(@Param("name") String name);


    List<Employee> findAllByNameLike(String name);      // "%r%"

    List<Employee> findAllByNameStartingWith(String name);      //"%r"

    List<Employee> findAllByNameEndingWith(String name);        // "r%"


    @Query("select e from  Employee e where UPPER(e.name) like upper(concat('%', :name, '%'))")
    List<Employee> findAllByLike(@Param("name") String name);

    @Query(value = "select e from employee e where e.name like %:name%", nativeQuery = true)
    List<Employee> findAllByLikeQuery(@Param("name") String name);
}
