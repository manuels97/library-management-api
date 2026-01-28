package com.library.api.service;

import com.library.api.model.Branch;
import com.library.api.model.Employee;
import com.library.api.repository.IBranchRepository;
import com.library.api.repository.IEmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService implements IEmployeeService {

    @Autowired
    private IEmployeeRepository empRepo;

    @Autowired
    private IBranchRepository branchRepo;

    @Override
    public List<Employee> getEmployees() {
        return empRepo.findAll();
    }

    @Override
    public void saveEmployee(Employee employee) {
        // VALIDACIÓN: ¿La sede asignada existe?
        if (employee.getBranch() != null) {
            Long idBranch = employee.getBranch().getIdBranch();
            Branch branch = branchRepo.findById(idBranch).orElse(null);

            if (branch == null) {
                throw new RuntimeException("Cannot create employee: Branch not found.");
            }
            // Aseguramos que el objeto branch esté completo antes de guardar
            employee.setBranch(branch);
        }
        empRepo.save(employee);
    }

    @Override
    public void deleteEmployee(Long idEmployee) {
        empRepo.deleteById(idEmployee);
    }

    @Override
    public Employee findEmployee(Long idEmployee) {
        return empRepo.findById(idEmployee).orElse(null);
    }

    @Override
    public List<Employee> getEmployeesByBranch(Long idBranch) {
        return empRepo.findByBranch_IdBranch(idBranch);
    }
}