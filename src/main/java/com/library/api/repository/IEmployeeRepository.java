package com.library.api.repository;

import com.library.api.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IEmployeeRepository extends JpaRepository<Employee, Long> {
    // Método extra para buscar empleados por sede (útil para el futuro)
    List<Employee> findByBranch_IdBranch(Long idBranch);
}