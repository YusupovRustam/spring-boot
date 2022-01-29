package com.company.resourse;

import com.company.entity.Employee;
import com.company.servic.EmployeeService;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/employee")
public class EmployeeResourse {
    @Autowired
    EmployeeService employeeService;

    @PostMapping
    public ResponseEntity save(@RequestBody Employee employee){
        Employee employee1;
        employee1 = employeeService.save(employee);
        return ResponseEntity.ok(employee1);
    }

    @GetMapping
    public ResponseEntity getAll(){
        return ResponseEntity.ok(employeeService.getList());
    }
    @GetMapping(value = "/{name}")
    public ResponseEntity findbyname(@PathVariable String name){
        return ResponseEntity.ok(employeeService.findByname(name));
    }

    @GetMapping(value = "/like")
    public ResponseEntity findAllByLike(@Param(value = "name") String name){
        return ResponseEntity.ok(employeeService.findAllByLike(name));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity update(@PathVariable(value = "id") Long id,@RequestBody Employee employee){
        employee.setId(id);
        return ResponseEntity.ok(employeeService.save(employee));
    }
    @DeleteMapping(value = "/{id}")
    public ResponseEntity delete(@PathVariable(value = "id")Long id){
        employeeService.delete(id);
        return ResponseEntity.ok("o'chirildi");
    }
}
