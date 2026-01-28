package com.library.api.service;

import com.library.api.model.Branch;
import com.library.api.repository.IBranchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BranchService implements IBranchService {
    @Autowired
    private IBranchRepository branchRepo;

    @Override
    public List<Branch> getBranches() {
        return branchRepo.findAll(); // Cambia List.of() por esto
    }

    @Override
    public void saveBranch(Branch branch) {
        branchRepo.save(branch);
    }

    @Override
    public void deleteBranch(Long idBranch) {

    }

    @Override
    public Branch findBranch(Long id) {
        return branchRepo.findById(id).orElse(null);
    }
    // ... agrega el resto de métodos CRUD (getBranches, deleteBranch)
}
