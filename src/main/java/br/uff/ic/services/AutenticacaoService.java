package br.uff.ic.services;

import br.uff.ic.model.Usuario;
import br.uff.ic.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;

@Service
public class AutenticacaoService implements UserDetailsService {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    //Criando serviço de autenticação pelo login
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByEmail(username);
    }

    public Usuario updateRoles(Long userId, String role) {
        Usuario usuario = repository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
       // usuario.setRoles(roles);
        List<String> userRoles = usuario.getRoles();

        boolean contains = userRoles.stream()
                .map(papel->papel)
                .anyMatch(userRole -> userRole.equals(role));

        if(!contains){
            userRoles.add(role);
            usuario.setRoles(userRoles);
        }
        return repository.save(usuario);
    }

//    public List<String> findRolesByUserId(Long userId) {
//        Usuario usuario = repository.findById(userId)
//                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
//
//        return usuario.getRoles();
//    }

    public Usuario registrarUsuario(Usuario usuario, List<String> roles) {
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        usuario.setRoles(roles);  // Assign roles provided
        return repository.save(usuario);
    }

}
