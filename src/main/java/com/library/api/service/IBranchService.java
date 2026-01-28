package com.library.api.service;

import com.library.api.model.Branch;
import java.util.List;

public interface IBranchService {

    // Traer todas las sedes
    public List<Branch> getBranches();

    // Guardar o editar una sede
    public void saveBranch(Branch branch);

    // Borrar una sede por ID
    public void deleteBranch(Long idBranch);

    // Buscar una sede específica
    public Branch findBranch(Long idBranch);
}