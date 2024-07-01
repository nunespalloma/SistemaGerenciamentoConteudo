package br.uff.ic.config;

import br.uff.ic.model.Usuario;
import br.uff.ic.repository.UsuarioRepository;
import br.uff.ic.services.AutenticacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

@Configuration
public class AdminInitializer {

    @Autowired
    private AutenticacaoService autenticacaoService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Bean
    CommandLineRunner init() {
        return args -> {
            if (usuarioRepository.findByEmail("admin@example.com") == null) {
                Usuario admin = new Usuario();
                admin.setSenha("admin123");
                admin.setEmail("admin@example.com");
                admin.setNome("Admin");
                admin.setAfiliacao("System");

                autenticacaoService.registrarUsuario(admin, Collections.singletonList("ROLE_ADMIN"));
                System.out.println("Admin user created");
            }
            if (usuarioRepository.findByEmail("organizer@example.com") == null) {
                Usuario organizer = new Usuario();
                organizer.setSenha("organizer123");
                organizer.setEmail("organizer@example.com");
                organizer.setNome("Organizer");
                organizer.setAfiliacao("Events");

                autenticacaoService.registrarUsuario(organizer, Collections.singletonList("ROLE_ORGANIZER"));
                System.out.println("Organizer user created");
            }
            if (usuarioRepository.findByEmail("user@example.com") == null) {
                Usuario user = new Usuario();
                user.setSenha("user123");
                user.setEmail("user@example.com");
                user.setNome("User");
                user.setAfiliacao("Community");

                autenticacaoService.registrarUsuario(user, Collections.singletonList("ROLE_USER"));
                System.out.println("Common user created");
            }

        };
    }
}
