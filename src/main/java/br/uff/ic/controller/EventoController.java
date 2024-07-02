package br.uff.ic.controller;


import br.uff.ic.model.Evento;
import br.uff.ic.repository.EventoRepository;
import br.uff.ic.services.EdicaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/admin/eventos")
public class EventoController {

    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private EdicaoService edicaoService;

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Cadastrar novo Evento",
            description = "Cadastra um novo Evento no banco de dados do sistema. Requer um objeto de Evento no "
                    + "corpo da requisição. Este endpoint só pode ser utilizado pelo Administrador.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Evento criado com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Dados inválidos"),
                    @ApiResponse(responseCode = "403", description = "Usuário não autorizado")
            })

    @PostMapping("")
    public Evento criarEvento (@RequestBody Evento evento){
        return eventoRepository.save(evento);
    }

    @Operation(summary = "Ler um Evento",
            description = "Ler um Evento no banco de dados do sistema. Requer um id do objeto de Evento na"
                    + "url. Este endpoint só pode ser utilizado pelo Administrador.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Evento lidos com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Dados inválidos"),
                    @ApiResponse(responseCode = "403", description = "Usuário não autorizado")
            })
    @GetMapping("/{id}")
    public Evento lerEvento(@PathVariable Long id){
        System.out.println(id);
        return eventoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Evento não encontrado com o id: " + id));
    }

    @Operation(summary = "Listar todos os Evento",
            description = "Listar todos os Evento no banco de dados do sistema. Este endpoint só pode ser utilizado pelo Administrador.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Evento criado com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Dados inválidos"),
                    @ApiResponse(responseCode = "403", description = "Usuário não autorizado")
            })
    @GetMapping("")
    public List<Evento> listarEventos(){
        return eventoRepository.findAll();
    }

    @Operation(summary = "Alterar um Evento",
            description = "Alterar um Evento no banco de dados do sistema. Requer um objeto de Evento no "
                    + "corpo da requisição. Este endpoint só pode ser utilizado pelo Administrador.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Evento alterado com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Dados inválidos"),
                    @ApiResponse(responseCode = "403", description = "Usuário não autorizado")
            })
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public Evento alterarEvento(@PathVariable Long id, @RequestBody Evento evento){
        return eventoRepository.findById(id).map(existingEvento -> {
            if (evento.getNome() != null) {
                existingEvento.setNome(evento.getNome());
            }
            if (evento.getSigla() != null) {
                existingEvento.setSigla(evento.getSigla());
            }
            if (evento.getDescricao() != null) {
                existingEvento.setDescricao(evento.getDescricao());
            }
            Evento updatedEvento = eventoRepository.save(existingEvento);
            return eventoRepository.save(existingEvento);
        }).orElseThrow(() -> new RuntimeException("Evento não encontrado com o id: " + id));
//        evento.setId(id);
//        return eventoRepository.save(evento);
    }

    @Operation(summary = "Deletar um Evento",
            description = "Deletar um Evento no banco de dados do sistema. Requer um id de um objeto de Evento na "
                    + "url. Este endpoint só pode ser utilizado pelo Administrador.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Evento deletado com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Dados inválidos"),
                    @ApiResponse(responseCode = "403", description = "Usuário não autorizado")
            })
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void deletarEvento(@PathVariable long id){
        eventoRepository.deleteById(id);
    }
}
