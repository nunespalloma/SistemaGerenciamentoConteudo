package br.uff.ic.controller;

import br.uff.ic.model.Edicao;
import br.uff.ic.repository.EdicaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/EdicaoController")
public class EdicaoController {

    @Autowired
    private EdicaoRepository edicaoRepository;

    @PostMapping("/criarEdicao")
    public Edicao criarEdicao (@RequestBody Edicao edicao){
        return edicaoRepository.save(edicao);
    }

    @GetMapping("/lerEdicao/{id}")
    public Edicao lerEdicao (@PathVariable Long id){
        return edicaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Edição não encontrado com o id: " + id));
    }

    @GetMapping("/listarEdicoes")
    public List<Edicao> listarEdicoes(){
        return edicaoRepository.findAll();
    }

    @PutMapping("/alterarEdicao/{id}")
    public Edicao alterarEdicao (@PathVariable Long id, @RequestBody Edicao edicao){
        edicao.setId(id);
        return edicaoRepository.save(edicao);
    }

    @DeleteMapping("/deletarEdicao/{id}")
    public void deletarEdicao (@PathVariable long id){
        edicaoRepository.deleteById(id);
    }
}
