package com.library.api.repository;

import com.library.api.model.Branch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IBranchRepository extends JpaRepository<Branch, Long> {}
