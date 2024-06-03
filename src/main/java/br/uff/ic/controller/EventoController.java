package br.uff.ic.controller;

import br.uff.ic.model.Evento;
import br.uff.ic.repository.EventoRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/evento")
public class EventoController {

    @Autowired
    private EventoRepository eventoRepository;

    @Operation(
            summary = "Cadastrar novo Evento",
            description = "Cadastra um novo Evento. Este endpoint só pode ser utilizado pelo Administrador.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Evento criado com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos"),
                    @ApiResponse(responseCode = "405", description = "Usuário não autorizado")
            })
    @PostMapping("/cadastrar")
    public Evento criarEvento(@RequestBody Evento evento) {
        return eventoRepository.save(evento);
    }

    //    @GetMapping("/ler_evento/{id}")
//    public Evento lerEvento(@PathVariable Long id){
//        return eventoRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Evento não encontrado com o id: " + id));
//    }
    @Operation(summary = "Listar os Eventos",
            description = "Lista os Eventos cadastrados no sistema. Este endpoint só pode ser utilizado pelo Administrador.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Sucesso"),
                    @ApiResponse(responseCode = "404", description = "Eventos não encontrados")
            })
    @GetMapping("/listar")
    public List<Evento> listarEventos() {
        return eventoRepository.findAll();
    }

    @Operation(summary = "Visualizar Pagina do Evento de acordo com a Edição",
            description = "O Usuário visita sistema\"/\"caminho do evento\"/\"ano da edição\";",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Sucesso"),
                    @ApiResponse(responseCode = "404", description = "Eventos não encontrados")
            })
    @GetMapping("/visualizar/{evento}/{anoEdicao}")
    public Evento visualizarEvento(@PathVariable Long eventoId, @PathVariable int anoEdicao) {
        return eventoRepository.findById(eventoId)
                .orElseThrow(() -> new RuntimeException("Evento não encontrado com o id: " + eventoId));
    }

    @Operation(summary = "Remover Evento",
            description = "Remove um Evento do sistema com base no ID fornecido. Este endpoint só pode ser "
                    + "utilizado pelo Administrador.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Evento deletado com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Evento não encontrado")
            })
    @DeleteMapping("/remover_evento/{id}")
    public void deletarEvento(@PathVariable long id){
        eventoRepository.deleteById(id);
    }

//
//    @PutMapping("/alterarEvento/{id}")
//    public Evento alterarEvento(@PathVariable Long id, @RequestBody Evento evento){
//        evento.setId(id);
//        return eventoRepository.save(evento);
//    }
//

}
