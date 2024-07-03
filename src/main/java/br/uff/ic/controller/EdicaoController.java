package br.uff.ic.controller;

import br.uff.ic.controller.mapping.EdicaoCadastrarJSON;
import br.uff.ic.model.Edicao;
import br.uff.ic.model.Evento;
import br.uff.ic.model.Usuario;
import br.uff.ic.repository.EdicaoRepository;
import br.uff.ic.repository.EventoRepository;
import br.uff.ic.repository.UsuarioRepository;
import br.uff.ic.services.AutenticacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/edicao")
public class EdicaoController {

    @Autowired
    private AutenticacaoService autenticacaoService;

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
                       @ApiResponse(responseCode = "403", description = "Usuário não autorizado")
               })
    @PostMapping("/cadastrar")
    public ResponseEntity<Edicao> cadastrarEdicao(@RequestBody EdicaoCadastrarJSON edicaoCadastrarJSON) {
        Optional<Evento> eventoOptional = eventoRepository.findById(edicaoCadastrarJSON.getEvento_id());

        if (!eventoOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        Edicao edicao = new Edicao();
        edicao.setNumero(edicaoCadastrarJSON.getNumero());
        edicao.setAno(edicaoCadastrarJSON.getAno());
        edicao.setDataInicial(edicaoCadastrarJSON.getDataInicial());
        edicao.setDataFinal(edicaoCadastrarJSON.getDataFinal());
        edicao.setCidade(edicaoCadastrarJSON.getCidade());
        edicao.setEvento(eventoOptional.get());

        Edicao savedEdicao = edicaoRepository.save(edicao);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedEdicao);
    }

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
    public ResponseEntity<Edicao> configurarOrganizadorEdicao(@PathVariable Long id, @PathVariable Long idUsuario) {
        Optional<Edicao> edicaoOptional = edicaoRepository.findById(id);
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(idUsuario);
        

        if (!edicaoOptional.isPresent() || !usuarioOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        autenticacaoService.updateRoles(idUsuario, "ROLE_ORGANIZER");


        Edicao edicao = edicaoOptional.get();
        List<Usuario> membros = edicao.getOrganizadores();

        boolean contains = membros.stream()
                .map(Usuario::getId)
                .anyMatch(userId -> userId == idUsuario);

        if(!contains){
            membros.add(usuarioOptional.get());
            edicao.setOrganizadores(membros);
        }

        Edicao savedEdicao = edicaoRepository.save(edicao);
        return ResponseEntity.status(HttpStatus.OK).body(savedEdicao);
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
    public ResponseEntity<List<Edicao>> lerEdicoesDeUmEvento(@PathVariable Long id) {
        if (!eventoRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        List<Edicao> edicoes = edicaoRepository.findByEventoId(id);
        if (edicoes.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(edicoes);
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
    @GetMapping("/solicitar_dados_categoria/{id}/{categoria}")
    public ResponseEntity<?> solicitarDadosCategoria(@PathVariable Long id, @PathVariable String categoria) {
        //Entrada: Receber categoria (chamadaTrabalhos, prazosEvento, informacoesInscricoes ou listaOrganizadores);
        Edicao edicao = edicaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Edição não encontrada com o id: " + id));

        switch (categoria.toLowerCase()) {
            case "chamadatrabalhos":
                return ResponseEntity.ok(edicao.getChamadaTrabalhos());

            case "prazoevento":
                return ResponseEntity.ok(edicao.getPrazoSubmissãoTrabalhos());

            case "informacoesinscricoes":
                return ResponseEntity.ok(edicao.getLinkSistemaInscricoes());

            case "listaorganizadores":
                return ResponseEntity.ok(edicao.getMembrosOrganizacao());

            default:
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Categoria inválida: " + categoria);
        }
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
    public ResponseEntity<Edicao> editarEdicao(@PathVariable Long id, @RequestBody Edicao edicao) {
        //Entrada: Receber os atributos da Edição de uma categoria colocada no body;
        Edicao edicaoExistente = edicaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Edição não encontrada com o id: " + id));

        if (edicao.getChamadaTrabalhos() != null) {
            edicaoExistente.setChamadaTrabalhos(edicao.getChamadaTrabalhos());
        }
        if (edicao.getPrazoSubmissãoTrabalhos() != null) {
            edicaoExistente.setPrazoSubmissãoTrabalhos(edicao.getPrazoSubmissãoTrabalhos());
        }
        if (edicao.getLinkSistemaInscricoes() != null) {
            edicaoExistente.setLinkSistemaInscricoes(edicao.getLinkSistemaInscricoes());
        }
        if (edicao.getMembrosOrganizacao() != null) {
            edicaoExistente.setMembrosOrganizacao(edicao.getMembrosOrganizacao());
        }

        Edicao edicaoAtualizada = edicaoRepository.save(edicaoExistente);

        return ResponseEntity.ok(edicaoAtualizada);
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
