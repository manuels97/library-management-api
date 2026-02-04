package com.library.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO {
    private Long idEmployee;
    private String employeeFullName;
    private String employeePosition;
    private String branchName;
}