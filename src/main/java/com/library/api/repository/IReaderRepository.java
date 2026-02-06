package com.library.api.repository;

import com.library.api.model.Reader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IReaderRepository extends JpaRepository<Reader, Long> {
    Reader findByDni(String dni);
}