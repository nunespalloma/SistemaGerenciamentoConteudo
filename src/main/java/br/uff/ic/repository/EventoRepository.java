package br.uff.ic.repository;

import br.uff.ic.model.Evento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventoRepository extends JpaRepository<Evento, Long> {

//    Evento findByNomeAndAnoEdicao(String evento, int anoEdicao);
}
