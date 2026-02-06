package com.library.api.controller;

import com.library.api.dto.BranchDTO;
import com.library.api.model.Branch;
import com.library.api.service.IBranchService;
import jakarta.validation.Valid;
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
    public ResponseEntity<List<BranchDTO>> getBranches() {
        return ResponseEntity.ok(branchServ.getBranches());
    }

    @PostMapping("/create")
    public ResponseEntity<String> saveBranch(@Valid @RequestBody Branch branch) {
        branchServ.saveBranch(branch);
        return new ResponseEntity<>("Branch created successfully", HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteBranch(@PathVariable Long id) {

        branchServ.deleteBranch(id);
        return new ResponseEntity<>("Branch deleted successfully", HttpStatus.OK);
    }


    @GetMapping("/find/{id}")
    public ResponseEntity<BranchDTO> findBranch(@PathVariable Long id) {
        return ResponseEntity.ok(branchServ.findBranch(id));
    }
}