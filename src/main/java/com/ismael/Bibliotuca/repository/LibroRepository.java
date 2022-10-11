package com.ismael.Bibliotuca.repository;

import com.ismael.Bibliotuca.entity.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LibroRepository extends JpaRepository<Libro, Integer> {
}
