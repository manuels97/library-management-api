package com.library.api.service;

import com.library.api.dto.BranchDTO;
import com.library.api.exception.ResourceNotFoundException;
import com.library.api.model.Branch;
import com.library.api.repository.IBranchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BranchService implements IBranchService {

    @Autowired
    private IBranchRepository branchRepo;

    @Override
    public List<BranchDTO> getBranches() {
        return branchRepo.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void saveBranch(Branch branch) {
        branchRepo.save(branch);
    }

    @Override
    public void deleteBranch(Long idBranch) {
        if (!branchRepo.existsById(idBranch)) {
            throw new ResourceNotFoundException("No se encontró la sucursal para eliminar con ID: " + idBranch);
        }
        branchRepo.deleteById(idBranch);
    }

    @Override
    public BranchDTO findBranch(Long id) {
        Branch branch = branchRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sucursal no encontrada con ID: " + id));
        return convertToDTO(branch);
    }

    private BranchDTO convertToDTO(Branch branch) {
        BranchDTO dto = new BranchDTO();
        dto.setIdBranch(branch.getIdBranch());
        dto.setBranchName(branch.getName());
        dto.setBranchAddress(branch.getAddress());
        return dto;
    }
}