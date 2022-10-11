package com.ismael.Bibliotuca.Controller;

import com.ismael.Bibliotuca.entity.Biblioteca;
import com.ismael.Bibliotuca.entity.Libro;
import com.ismael.Bibliotuca.repository.BibliotecaRepository;
import com.ismael.Bibliotuca.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api/libros")
public class LibroController {
    
    @Autowired
    private BibliotecaRepository bibliotecaRepository;
    
    @Autowired
    private LibroRepository libroRepository;
    
    @PostMapping
    private ResponseEntity<Libro> createLibro(@Valid @RequestBody Libro libro) {
        
        Optional<Biblioteca> optionalBiblioteca = bibliotecaRepository.findById(libro.getBiblioteca().getId());
        
        if (! optionalBiblioteca.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        
        libro.setBiblioteca(optionalBiblioteca.get());
        
        return ResponseEntity.ok(libro);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Libro> editLibro(@Valid @RequestBody Libro libro, @PathVariable("id") Integer id) {
        try {
            Optional<Biblioteca> optionalBiblioteca = bibliotecaRepository.findById(libro.getBiblioteca().getId());
            
            if (optionalBiblioteca.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            
            Optional<Libro> optionalLibro = libroRepository.findById(id);
            
            if (optionalLibro.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            
            libro.setBiblioteca(optionalBiblioteca.get());
            libro.setId(optionalLibro.get().getId());
            libroRepository.save(libro);
            
            return ResponseEntity.ok(libro);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Libro> deleetLibro(@Valid @PathVariable("id") Integer id) {
        try {
            
            
            Optional<Libro> optionalLibro = libroRepository.findById(id);
            
            if (optionalLibro.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            
            libroRepository.delete(optionalLibro.get());
            
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    
    @GetMapping
    public ResponseEntity<Page<Libro>> getAllLibros(Pageable pageable) {
        try{
            
            return ResponseEntity.ok(libroRepository.findAll(pageable));
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Libro> getLibroById(@Valid @PathVariable("id") Integer id) {
        try{
            
            Optional<Libro> optionalLibro = libroRepository.findById(id);
            
            if(optionalLibro.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            
            return ResponseEntity.ok(optionalLibro.get());
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    
}
