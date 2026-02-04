package com.library.api.service;

import com.library.api.dto.EmployeeDTO;
import com.library.api.model.Employee;
import java.util.List;

public interface IEmployeeService {

    // Ahora devuelve una lista de DTOs para el controlador
    public List<EmployeeDTO> getEmployees();

    // El ingreso sigue siendo la Entidad (o podrías usar un CreateDTO más adelante)
    public void saveEmployee(Employee employee);

    public void deleteEmployee(Long idEmployee);

    // Ahora devuelve un DTO individual
    public EmployeeDTO findEmployee(Long idEmployee);

    // Filtrado por sucursal también devuelve DTOs
    public List<EmployeeDTO> getEmployeesByBranch(Long idBranch);
}