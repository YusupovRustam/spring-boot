package com.company.servic;

import com.company.entity.Employee;
import com.company.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
    @Autowired
    EmployeeRepository employeeRepository;

    public Employee save(Employee employee){
        return employeeRepository.save(employee);
    }

    public List<Employee> getList(){
        return employeeRepository.findAll();
    }
    public List<Employee>findByname(String name){
        return employeeRepository.findAllByName(name);
    }
    public List<Employee>findAllByLike(String name){
        return employeeRepository.findAllByLike(name);
    }
    public void delete(Long id){
        employeeRepository.deleteById(id);
    }

  @Scheduled(cron = "0 58 17 * * *")
    public Employee saveSchedule(){
       Employee employee=new Employee();
       employee.setName("Asadbek");
        employee.setSurname("Soyibjonov");
        return employeeRepository.save(employee);
    }
}
