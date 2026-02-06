package com.library.api.service;

import com.library.api.dto.EmployeeDTO;
import com.library.api.model.Employee;
import java.util.List;

public interface IEmployeeService {

    public List<EmployeeDTO> getEmployees();

    public void saveEmployee(Employee employee);

    public void deleteEmployee(Long idEmployee);

    public EmployeeDTO findEmployee(Long idEmployee);

    public List<EmployeeDTO> getEmployeesByBranch(Long idBranch);
}