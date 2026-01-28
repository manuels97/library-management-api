package com.library.api.service;

import com.library.api.model.Employee;
import java.util.List;

public interface IEmployeeService {
    public List<Employee> getEmployees();
    public void saveEmployee(Employee employee);
    public void deleteEmployee(Long idEmployee);
    public Employee findEmployee(Long idEmployee);
    public List<Employee> getEmployeesByBranch(Long idBranch);
}