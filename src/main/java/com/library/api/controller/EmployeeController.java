package com.library.api.controller;

import com.library.api.dto.EmployeeDTO;
import com.library.api.model.Employee;
import com.library.api.service.IEmployeeService;
import jakarta.validation.Valid;
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
    public ResponseEntity<List<EmployeeDTO>> getEmployees() {
        return ResponseEntity.ok(empServ.getEmployees());
    }

    @PostMapping("/create")
    public ResponseEntity<String> saveEmployee(@Valid  @RequestBody Employee employee) {
        empServ.saveEmployee(employee);
        return new ResponseEntity<>("Employee registered successfully", HttpStatus.CREATED);
    }

    @GetMapping("/branch/{idBranch}")
    public ResponseEntity<List<EmployeeDTO>> getByBranch(@PathVariable Long idBranch) {
        return ResponseEntity.ok(empServ.getEmployeesByBranch(idBranch));
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<EmployeeDTO> findEmployee(@PathVariable Long id) {
        return ResponseEntity.ok(empServ.findEmployee(id));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Long id) {
        empServ.deleteEmployee(id);
        return ResponseEntity.ok("Employee deleted successfully");
    }
}