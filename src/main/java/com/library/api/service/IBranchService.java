package com.library.api.service;

import com.library.api.dto.BranchDTO;
import com.library.api.model.Branch;
import java.util.List;

public interface IBranchService {
    // Retorna la lista de DTOs
    public List<BranchDTO> getBranches();

    public void saveBranch(Branch branch);

    public void deleteBranch(Long idBranch);

    // Retorna el DTO individual
    public BranchDTO findBranch(Long id);
}