package com.library.api.controller;

import com.library.api.model.Branch;
import com.library.api.service.IBranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/branches")
public class BranchController {

    @Autowired
    private IBranchService branchServ;

    @GetMapping("/get")
    public ResponseEntity<List<Branch>> getBranches() {
        return ResponseEntity.ok(branchServ.getBranches());
    }

    @PostMapping("/create")
    public ResponseEntity<String> saveBranch(@RequestBody Branch branch) {
        branchServ.saveBranch(branch);
        return new ResponseEntity<>("Branch created successfully", HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteBranch(@PathVariable Long id) {
        Branch branch = branchServ.findBranch(id);
        if (branch == null) {
            return new ResponseEntity<>("Branch not found", HttpStatus.NOT_FOUND);
        }
        branchServ.deleteBranch(id);
        return new ResponseEntity<>("Branch deleted successfully", HttpStatus.OK);
    }
}