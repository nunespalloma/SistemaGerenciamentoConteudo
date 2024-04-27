package br.uff.ic.repository;

import br.uff.ic.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface Repositorio extends JpaRepository<Usuario, Long>{

}
