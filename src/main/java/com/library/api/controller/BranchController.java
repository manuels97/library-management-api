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

    // 1. CORREGIDO: Ahora el tipo de retorno es List<BranchDTO>
    @GetMapping("/get")
    public ResponseEntity<List<BranchDTO>> getBranches() {
        return ResponseEntity.ok(branchServ.getBranches());
    }

    @PostMapping("/create")
    public ResponseEntity<String> saveBranch(@Valid @RequestBody Branch branch) {
        branchServ.saveBranch(branch);
        return new ResponseEntity<>("Branch created successfully", HttpStatus.CREATED);
    }

    // 2. CORREGIDO: findBranch ahora devuelve BranchDTO
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteBranch(@PathVariable Long id) {
        // El Service ya lanza ResourceNotFoundException si no existe,
        // así que podemos simplificarlo a una sola línea:
        branchServ.deleteBranch(id);
        return new ResponseEntity<>("Branch deleted successfully", HttpStatus.OK);
    }

    // Opcional: Agregar un endpoint para buscar una sola sucursal
    @GetMapping("/find/{id}")
    public ResponseEntity<BranchDTO> findBranch(@PathVariable Long id) {
        return ResponseEntity.ok(branchServ.findBranch(id));
    }
}