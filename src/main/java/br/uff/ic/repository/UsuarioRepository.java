package br.uff.ic.repository;

import br.uff.ic.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
    UserDetails findByEmail(String email);
    //essa classe/interface JPA repository faz o CRUD. o <usuario é a entidade e long é o tipo da chave primaria dessa
    // entidade
}
