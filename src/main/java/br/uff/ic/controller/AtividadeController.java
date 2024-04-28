package br.uff.ic.controller;

import br.uff.ic.model.Atividade;
import br.uff.ic.repository.AtividadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/AtividadeController")
public class AtividadeController {

    @Autowired
    private AtividadeRepository atividadeRepository;

    @PostMapping("/criarAtividade")
    public Atividade criarAtividade (@RequestBody Atividade atividade){
        return atividadeRepository.save(atividade);
    }

    @GetMapping("/lerAtividade/{id}")
    public Atividade lerAtividade (@PathVariable Long id){
        return atividadeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Atividade não encontrada com o id: " + id));
    }

    @GetMapping("/listarAtividades")
    public List<Atividade> listarAtividades(){
        return atividadeRepository.findAll();
    }

    @PutMapping("/alterarAtividade/{id}")
    public Atividade alterarAtividade (@PathVariable Long id, @RequestBody Atividade atividade){
        atividade.setId(id);
        return atividadeRepository.save(atividade);
    }

    @DeleteMapping("/deletarAtividade/{id}")
    public void deletarAtividade (@PathVariable long id){
        atividadeRepository.deleteById(id);
    }

}
