package com.library.api.service;

import com.library.api.dto.EmployeeDTO;
import com.library.api.exception.ResourceNotFoundException;
import com.library.api.model.Branch;
import com.library.api.model.Employee;
import com.library.api.repository.IBranchRepository;
import com.library.api.repository.IEmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService implements IEmployeeService {

    @Autowired
    private IEmployeeRepository empRepo;

    @Autowired
    private IBranchRepository branchRepo;

    @Override
    public List<EmployeeDTO> getEmployees() {
        return empRepo.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void saveEmployee(Employee employee) {
        if (employee.getBranch() != null) {
            Long idBranch = employee.getBranch().getIdBranch();
            // Cambiamos el null por nuestra excepción personalizada
            Branch branch = branchRepo.findById(idBranch)
                    .orElseThrow(() -> new ResourceNotFoundException("No se puede crear empleado: Sucursal no encontrada con ID: " + idBranch));

            employee.setBranch(branch);
        }
        empRepo.save(employee);
    }

    @Override
    public void deleteEmployee(Long idEmployee) {
        if (!empRepo.existsById(idEmployee)) {
            throw new ResourceNotFoundException("No se encontró el empleado para eliminar con ID: " + idEmployee);
        }
        empRepo.deleteById(idEmployee);
    }

    @Override
    public EmployeeDTO findEmployee(Long idEmployee) {
        Employee emp = empRepo.findById(idEmployee)
                .orElseThrow(() -> new ResourceNotFoundException("Empleado no encontrado con ID: " + idEmployee));
        return convertToDTO(emp);
    }

    @Override
    public List<EmployeeDTO> getEmployeesByBranch(Long idBranch) {
        return empRepo.findByBranch_IdBranch(idBranch)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // El "traductor" para Employee
    private EmployeeDTO convertToDTO(Employee emp) {
        EmployeeDTO dto = new EmployeeDTO();
        dto.setIdEmployee(emp.getIdEmployee());
        dto.setEmployeeFullName(emp.getFirstName() + " " + emp.getLastName());
        dto.setEmployeePosition(emp.getPosition());

        // Evitamos NullPointerException si el empleado no tiene sucursal asignada
        if (emp.getBranch() != null) {
            dto.setBranchName(emp.getBranch().getName());
        } else {
            dto.setBranchName("Sin asignar");
        }

        return dto;
    }
}