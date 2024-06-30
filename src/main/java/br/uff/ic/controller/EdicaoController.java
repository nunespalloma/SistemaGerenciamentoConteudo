package br.uff.ic.controller;

import br.uff.ic.model.Edicao;
import br.uff.ic.model.Evento;
import br.uff.ic.repository.EdicaoRepository;
import br.uff.ic.repository.EventoRepository;
import br.uff.ic.repository.UsuarioRepository;
import ch.qos.logback.core.net.SyslogOutputStream;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/edicao")
public class EdicaoController {

    @Autowired
    private EdicaoRepository edicaoRepository;

    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Operation(summary = "Cadastrar nova Edição",
               description = "Cadastra uma nova Edição no banco de dados do sistema. Requer um objeto de Edição no "
                       + "corpo da requisição. Este endpoint só pode ser utilizado pelo Administrador." +
                       "Pré-condições: Evento já deve ter sido cadastrado;",
               responses = {
                       @ApiResponse(responseCode = "201", description = "Edição criada com sucesso"),
                       @ApiResponse(responseCode = "400", description = "Dados inválidos"),
                       @ApiResponse(responseCode = "405", description = "Usuário não autorizado")
               })
    @PostMapping("/cadastrar")
    public ResponseEntity<Edicao> cadastrarEdicao(@RequestBody Edicao edicao) {
        Long eventoId = edicao.getEvento().getId();

        Optional<Evento> eventoOptional = eventoRepository.findById(eventoId);
        if (!eventoOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        Edicao savedEdicao = edicaoRepository.save(edicao);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedEdicao);
    }
    /*public Edicao cadastrarEdicao (@RequestBody Edicao edicao){
        //Implementação a ser ajustada: Pré-condições: Evento já deve ter sido cadastrado
        return edicaoRepository.save(edicao);
    }*/

    @Operation(summary = "Cadastra Organizador da Edição",
            description = "Cadastra o Organizador de uma Edição existente com base nos IDs "
                    + "de Edição e Usuário fornecidos. Este endpoint só pode ser utilizado pelo Administrador. Edição do evento já deve ter sido cadastrada.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Edição atualizada com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos"),
                    @ApiResponse(responseCode = "404", description = "Edição não encontrada"),
                    @ApiResponse(responseCode = "405", description = "Usuário não autorizado")
            })
    @PutMapping("/cadastrar_organizador/{id}/{idUsuario}")
    public Edicao configurarOrganizadorEdicao (@PathVariable Long id, @PathVariable Long idUsuario){
        //Implementação a ser ajustada: Saída: Efetuar o registro de um usuário como organizador do evento;

        Edicao edicao = lerEdicao(id);
        edicao.setId(id);
        edicao.setOrganizador(usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com o id: " + idUsuario)));
        return edicaoRepository.save(edicao);
    }

    @Operation(summary = "Ler Edições de um Evento",
            description = "Retorna os detalhes das Edições específicas com base no ID de Evento fornecido. Este "
                    + "endpoint só pode ser utilizado pelo Administrador.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Sucesso"),
                    @ApiResponse(responseCode = "404", description = "Edição(ões) não encontrada(s)"),
                    @ApiResponse(responseCode = "405", description = "Usuário não autorizado")
            })
    @GetMapping("/ler_edicao_de_evento/{id}")
    public Edicao lerEdicoesDeUmEvento (@PathVariable Long id){ //id do evento
        //Implementação a ser ajustada: Saída: Apresenta lista de edições cadastradas para aquele evento;
        return edicaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Edição não encontrado com o id: " + id));
    }

    @Operation(summary = "Solicitar dados das categorias da Edição",
               description = "Retorna as informações de categoria de uma edição existente com base no ID e categoria "
                       + "fornecidos. Este endpoint só pode ser utilizado pelo Organizador, o qual já deve ter sido "
                       + "autorizado.",
               responses = {
                       @ApiResponse(responseCode = "200", description = "Lista de Edições"),
                       @ApiResponse(responseCode = "404", description = "Edição não encontrada"),
                       @ApiResponse(responseCode = "405", description = "Usuário não autorizado")
               })
    @GetMapping("/solicitar_dados_categoria/{id}")
    public Edicao solicitarDadosCategoria (@PathVariable Long id, @PathVariable String categoria){
        //Implementação a ser ajustada:
        //Entrada: Receber categoria (chamadaTrabalhos, prazosEvento, informacoesInscricoes ou listaOrganizadores);
        //Saída: Retornar os dados já cadastrados daquela categoria na Edição;


        return edicaoRepository.findById(id)
                                .orElseThrow(() -> new RuntimeException("Edição não encontrado com o id: " + id));
    }

    @Operation(summary = "Atualizar Edição",
               description = "Atualiza as informações de uma edição existente com base no ID fornecido. Requer um "
                       + "objeto de edição no corpo da requisição. Este endpoint só pode ser utilizado pelo  "
                       + "Organizador, o qual já deve ter sido autorizado.",
               responses = {
                       @ApiResponse(responseCode = "200", description = "Edição atualizada com sucesso"),
                       @ApiResponse(responseCode = "400", description = "Dados inválidos"),
                       @ApiResponse(responseCode = "404", description = "Edição não encontrada"),
                       @ApiResponse(responseCode = "405", description = "Usuário não autorizado")
               })
    @PutMapping("/editar/{id}")
    public Edicao editarEdicao (@PathVariable Long id, @RequestBody Edicao edicao){
        //Implementação a ser ajustada:
        //Entrada: Receber os atributos da Edição de acordo com a categoria escolhida;
        //Saída: efetuar o registro/alteração da informação no banco de dados;

        edicao.setId(id);
        return edicaoRepository.save(edicao);
    }

    @Operation(summary = "Obter Edição por ID",
            description = "Retorna os detalhes de uma Edição específica com base no ID fornecido.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Edição encontrada"),
                    @ApiResponse(responseCode = "404", description = "Edição não encontrada")
            })
    @GetMapping("/ler/{id}")
    public Edicao lerEdicao (@PathVariable Long id){
        return edicaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Edição não encontrado com o id: " + id));
    }

    @Operation(summary = "Deletar Edição",
               description = "Remove uma Edição do sistema com base no ID fornecido. Este endpoint só pode ser "
                       + "utilizado pelo Administrador.",
               responses = {
                       @ApiResponse(responseCode = "204", description = "Edição deletada com sucesso"),
                       @ApiResponse(responseCode = "404", description = "Edição não encontrada")
               })
    @DeleteMapping("/remover/{id}")
    public void deletarEdicao (@PathVariable long id){
        edicaoRepository.deleteById(id);
    }

    /*

    //CRUD SIMPLIFICADO DE EDIÇÃO

    @PostMapping("/criarEdicao")
    public Edicao criarEdicao (@RequestBody Edicao edicao){
        return edicaoRepository.save(edicao);
    }

    @GetMapping("/lerEdicao/{id}")
    public Edicao lerEdicao (@PathVariable Long id){
        return edicaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Edição não encontrado com o id: " + id));
    }

    @GetMapping("/listarEdicoes")
    public List<Edicao> listarEdicoes(){
        return edicaoRepository.findAll();
    }

    @PutMapping("/alterarEdicao/{id}")
    public Edicao alterarEdicao (@PathVariable Long id, @RequestBody Edicao edicao){
        edicao.setId(id);
        return edicaoRepository.save(edicao);
    }

    @DeleteMapping("/deletarEdicao/{id}")
    public void deletarEdicao (@PathVariable long id){
        edicaoRepository.deleteById(id);
    }

     */
}
