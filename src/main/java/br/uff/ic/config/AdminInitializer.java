package br.uff.ic.config;

import br.uff.ic.model.*;
import br.uff.ic.repository.*;
import br.uff.ic.services.AutenticacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;
import java.util.Date;

@Configuration
public class AdminInitializer {

    @Autowired
    private AutenticacaoService autenticacaoService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private EdicaoRepository edicaoRepository;

    @Autowired
    private EspacoRepository espacoRepository;

    @Autowired
    private AtividadeRepository atividadeRepository;

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
            Evento evento = new Evento();
            evento.setNome("Evento Principal");
            evento.setDescricao("Descrição do Evento Principal");
            evento.setSigla("EVP");
            eventoRepository.save(evento);

            Edicao edicao = new Edicao();
            edicao.setNumero(1);
            edicao.setAno(2024);
            edicao.setDataInicial(new Date());
            edicao.setDataFinal(new Date());
            edicao.setCidade("Cidade Exemplo");
            edicao.setEvento(evento);
            edicaoRepository.save(edicao);

            Espaco espaco = new Espaco();
            espaco.setNome("Espaço Principal");
            espacoRepository.save(espaco);

            Atividade atividade = new Atividade();
            atividade.setNome("Atividade Exemplo");
            atividade.setDescricao("Descrição da Atividade Exemplo");
            atividade.setEdicao(edicao);
            atividade.setEspaco(espaco);
            atividadeRepository.save(atividade);
        };
    }
}
