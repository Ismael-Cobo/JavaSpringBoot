package com.ismael.Bibliotuca.repository;

import com.ismael.Bibliotuca.entity.Biblioteca;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BibliotecaRepository extends JpaRepository<Biblioteca, Integer> {
}
