package com.ismael.Bibliotuca.Controller;

import com.ismael.Bibliotuca.entity.Biblioteca;
import com.ismael.Bibliotuca.repository.BibliotecaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api/biblioteca")
public class BibliotecaController {
    
    @Autowired
    private BibliotecaRepository bibliotecaRepository;
    
    @PostMapping
    public ResponseEntity<Biblioteca> addBiblioteca(@Valid @RequestBody Biblioteca biblioteca) {
        
        Biblioteca bibliotecaGuardada = bibliotecaRepository.save(biblioteca);
        return ResponseEntity.ok(bibliotecaGuardada);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Biblioteca> updateBiblioteca(@PathVariable("id") Integer id, @RequestBody Biblioteca biblioteca) {
        
        Optional<Biblioteca> optionalBiblioteca = bibliotecaRepository.findById(id);
        
        if (! optionalBiblioteca.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        
        biblioteca.setId(optionalBiblioteca.get().getId());
        bibliotecaRepository.save(biblioteca);
        
        return ResponseEntity.ok(biblioteca);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Biblioteca> deleteBiblioteca(@PathVariable("id") Integer id) {
        
        try {
            Optional<Biblioteca> optionalBiblioteca = bibliotecaRepository.findById(id);
            
            if (! optionalBiblioteca.isPresent()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
            
            bibliotecaRepository.deleteById(optionalBiblioteca.get().getId());
            
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Biblioteca> getOneBiblioteca(@PathVariable("id") Integer id) {
        
        try {
            
            Optional<Biblioteca> optionalBiblioteca = bibliotecaRepository.findById(id);
            
            if (! optionalBiblioteca.isPresent()) {
                return ResponseEntity.notFound().build();
            }
            
            return ResponseEntity.ok(optionalBiblioteca.get());
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    
    @GetMapping
    public ResponseEntity<Page<Biblioteca>> getAllBiblioteca(Pageable pageable) {
        try {
             return ResponseEntity.ok(bibliotecaRepository.findAll(pageable));
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    
}
