package com.library.api.controller;

import com.library.api.model.Employee;
import com.library.api.service.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private IEmployeeService empServ;

    @GetMapping("/get")
    public ResponseEntity<List<Employee>> getEmployees() {
        return ResponseEntity.ok(empServ.getEmployees());
    }

    @PostMapping("/create")
    public ResponseEntity<String> saveEmployee(@RequestBody Employee employee) {
        empServ.saveEmployee(employee);
        return new ResponseEntity<>("Employee registered successfully", HttpStatus.CREATED);
    }

    @GetMapping("/branch/{idBranch}")
    public ResponseEntity<List<Employee>> getByBranch(@PathVariable Long idBranch) {
        return ResponseEntity.ok(empServ.getEmployeesByBranch(idBranch));
    }
}