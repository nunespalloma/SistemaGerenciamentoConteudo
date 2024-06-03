package br.uff.ic.repository;

import br.uff.ic.model.Atividade;
import org.springframework.data.jpa.repository.JpaRepository;

// interface do spring data jpa que contém métodos para manipulação de dados
public interface AtividadeRepository extends JpaRepository<Atividade, Long> {

}
