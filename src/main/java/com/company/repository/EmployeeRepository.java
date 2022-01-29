package com.company.repository;

import com.company.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long> {

    List<Employee>findAllByName(String name);
//@Query( "select e from Employee e where e.name=:name")
    @Query(value = "select * from Employee e where e.name=:name", nativeQuery = true)
    List<Employee>findAllByNameQuery(@Param(value ="name") String name);

    List<Employee>findAllByNameLike(String name);
    List<Employee>findAllByNameStartingWith(String name);
    List<Employee>findAllByNameEndingWith(String name);
    @Query("select e from Employee e where UPPER( e.name) like upper( concat('%',:name,'%'))")
    List<Employee>findAllByLike(@Param(value = "name") String name);

    @Query(value = "select * from employees e where e.name like %:name%",nativeQuery = true)
    List<Employee>findAllByLikeNativeQuery(@Param(value = "name") String name);
}
