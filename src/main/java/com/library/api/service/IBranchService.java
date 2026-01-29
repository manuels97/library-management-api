package com.library.api.service;

import com.library.api.model.Branch;
import java.util.List;

public interface IBranchService {


    public List<Branch> getBranches();


    public void saveBranch(Branch branch);


    public void deleteBranch(Long idBranch);


    public Branch findBranch(Long idBranch);
}