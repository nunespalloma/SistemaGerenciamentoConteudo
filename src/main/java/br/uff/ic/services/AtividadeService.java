package br.uff.ic.services;

import br.uff.ic.model.Atividade;
import br.uff.ic.repository.AtividadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AtividadeService {
    @Autowired
    private AtividadeRepository atividadeRepository;

    public List<Atividade> findAll() {
        return atividadeRepository.findAll();
    }

    public Optional<Atividade> findById(Long id) {
        return atividadeRepository.findById(id);
    }

    public Atividade save(Atividade atividade) {
        return atividadeRepository.save(atividade);
    }

    public void deleteById(Long id) {
        atividadeRepository.deleteById(id);
    }
}
