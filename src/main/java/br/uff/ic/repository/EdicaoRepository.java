package br.uff.ic.repository;

import br.uff.ic.model.Edicao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EdicaoRepository extends JpaRepository<Edicao, Long> {
    List<Edicao> findByEventoId(Long id);
}
