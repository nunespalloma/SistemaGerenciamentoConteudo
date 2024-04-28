package br.uff.ic.controller;

import br.uff.ic.model.Espaco;
import br.uff.ic.repository.EspacoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/EspacoController")
public class EspacoController {

    @Autowired
    private EspacoRepository espacoRepository;

    @PostMapping("/criarEspaco")
    public Espaco criarEspaco (@RequestBody Espaco espaco){
        return espacoRepository.save(espaco);
    }

    @GetMapping("/lerEspaco/{id}")
    public Espaco lerEspaco (@PathVariable Long id){
        return espacoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Espaço não encontrado com o id: " + id));
    }

    @GetMapping("/listarEspacos")
    public List<Espaco> listarEspacos(){
        return espacoRepository.findAll();
    }

    @PutMapping("/alterarEspaco/{id}")
    public Espaco alterarEspaco (@PathVariable Long id, @RequestBody Espaco espaco){
        espaco.setId(id);
        return espacoRepository.save(espaco);
    }

    @DeleteMapping("/deletarEspaco/{id}")
    public void deletarEspaco (@PathVariable long id){
        espacoRepository.deleteById(id);
    }
}
