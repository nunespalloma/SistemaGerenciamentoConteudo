package br.uff.ic.controller;

import br.uff.ic.model.Evento;
import br.uff.ic.repository.EventoRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/eventos")
public class EventoController {

    @Autowired
    private EventoRepository eventoRepository;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/")
    public Evento criarEvento (@RequestBody Evento evento){
        return eventoRepository.save(evento);
    }

    @GetMapping("/lerEvento/{id}")
    public Evento lerEvento(@PathVariable Long id){
        return eventoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Evento não encontrado com o id: " + id));
    }

    @GetMapping("/listarEventos")
    public List<Evento> listarEventos(){
        return eventoRepository.findAll();
    }

    @PutMapping("/alterarEvento/{id}")
    public Evento alterarEvento(@PathVariable Long id, @RequestBody Evento evento){
        evento.setId(id);
        return eventoRepository.save(evento);
    }

    @DeleteMapping("/deletarEvento/{id}")
    public void deletarEvento(@PathVariable long id){
        eventoRepository.deleteById(id);
    }
}
