package br.uff.ic.controller;

import br.uff.ic.model.Edicao;
import br.uff.ic.model.Evento;
import br.uff.ic.model.Usuario;
import br.uff.ic.repository.EdicaoRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/edicao")
public class EdicaoController {

    @Autowired
    private EdicaoRepository edicaoRepository;

    @Operation(summary = "Cadastrar nova Edição",
               description = "Cadastra uma nova Edição no banco de dados do sistema. Requer um objeto de Edição no "
                       + "corpo da requisição. Este endpoint só pode ser utilizado pelo Administrador.",
               responses = {
                       @ApiResponse(responseCode = "201", description = "Edição criada com sucesso"),
                       @ApiResponse(responseCode = "400", description = "Dados inválidos")
               })
    @PostMapping("/cadastrar")
    public Edicao cadastrarEdicao (@RequestBody Edicao edicao){
        //Pré-condições: Evento já deve ter sido cadastrado;
        //Entrada: Receber dados da edição (ano, número, data inicial, data final, cidade);
        //Saída: Cadastrar edição, efetuando o registro das informações;
        //Restrição: Este endpoint só pode ser utilizado pelo Administrador.

        //Implementação a ser ajustada:
        return edicaoRepository.save(edicao);
    }

    @Operation(summary = "Solicitar dados das categorias da Edição",
               description = "Retorna as informações de categoria de uma edição existente com base no ID e categoria "
                       + "fornecidos. Este endpoint só pode ser utilizado pelo Organizador, o qual já deve ter sido "
                       + "autorizado.",
               responses = {
                       @ApiResponse(responseCode = "200", description = "Lista de Edições"),
                       @ApiResponse(responseCode = "404", description = "Edição não encontrada")
               })
    @GetMapping("/SolicitarDadosCategoria/{id}")
    public Edicao solicitarDadosCategoria (@PathVariable Long id, @PathVariable String categoria){
        //Entrada: Receber categoria (chamadaTrabalhos, prazosEvento, informacoesInscricoes ou listaOrganizadores);
        //Saída: Retornar os dados já cadastrados daquela categoria na Edição;
        //Restrição: Este endpoint só pode ser utilizado pelo Organizador, o qual já deve ter sido autorizado.

        //Implementação a ser ajustada:
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
                       @ApiResponse(responseCode = "404", description = "Edição não encontrada")
               })
    @PutMapping("/editar/{id}")
    public Edicao editarEdicao (@PathVariable Long id, @RequestBody Edicao edicao){
        //Entrada: Receber os atributos da Edição de acordo com a categoria escolhida;
        //Saída: efetuar o registro/alteração da informação no banco de dados;
        //Restrição: Este endpoint só pode ser utilizado pelo Organizador, o qual já deve ter sido autorizado.

        //Implementação a ser ajustada:
        edicao.setId(id);
        return edicaoRepository.save(edicao);
    }

    @Operation(summary = "Deletar Edição",
               description = "Remove uma Edição do sistema com base no ID fornecido. Este endpoint só pode ser "
                       + "utilizado pelo Administrador.",
               responses = {
                       @ApiResponse(responseCode = "204", description = "Edição deletada com sucesso"),
                       @ApiResponse(responseCode = "404", description = "Edição não encontrada")
               })
    @DeleteMapping("/deletar/{id}")
    public void deletarEdicao (@PathVariable long id){
        //Entrada: ID;
        //Fluxo: Deleção da Edição;
        //Saída: N/A;
        //Restrição: Este endpoint só pode ser utilizado pelo Administrador.

        //Implementação a ser ajustada:
        edicaoRepository.deleteById(id);
    }

    @Operation(summary = "Obter Edições de um Evento",
               description = "Retorna os detalhes das Edições específicas com base no ID de Evento fornecido. Este "
                       + "endpoint só pode ser utilizado pelo Administrador.",
               responses = {
                       @ApiResponse(responseCode = "200", description = "Lista de Edições"),
                       @ApiResponse(responseCode = "404", description = "Edição(ões) não encontrada(s)")
               })
    @GetMapping("/lerEdicoesDeUmEvento/{id}")
    public Edicao lerEdicoesDeUmEvento (@PathVariable Long id){
        //Pré-condição: Receber um evento;
        //Saída: Apresenta lista de edições cadastradas para aquele evento;
        //Restrição: Só pode ser feito pelo Administrador.

        //Implementação a ser ajustada:
        return edicaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Edição não encontrado com o id: " + id));
    }

    @Operation(summary = "Atualizar Organizador da Edição",
               description = "Atualiza a informação do organizador de uma Edição existente com base nos IDs "
                       + "de Edição e Usuário fornecidos. Este endpoint só pode ser utilizado pelo Administrador.",
               responses = {
                       @ApiResponse(responseCode = "200", description = "Edição atualizada com sucesso"),
                       @ApiResponse(responseCode = "400", description = "Dados inválidos"),
                       @ApiResponse(responseCode = "404", description = "Edição não encontrada")
               })
    @PutMapping("/configurarOrganizador/{id}")
    public Edicao configurarOrganizadorEdicao (@PathVariable Long id, @PathVariable Long usuario){
        //Entrada: Receber uma edição e um usuário;
        //Saída: Efetuar o registro de um usuário como organizador do evento;
        //Restição: Este endpoint só pode ser utilizado pelo Administrador.

        //Implementação a ser ajustada:
        Edicao edicao = lerEdicao(id);
        edicao.setId(id);
        return edicaoRepository.save(edicao);
    }

    @Operation(summary = "Obter Edição por ID",
               description = "Retorna os detalhes de uma Edição específica com base no ID fornecido.",
               responses = {
                       @ApiResponse(responseCode = "200", description = "Edição encontrada"),
                       @ApiResponse(responseCode = "404", description = "Edição não encontrada")
               })
    @GetMapping("/lerEdicao/{id}")
    public Edicao lerEdicao (@PathVariable Long id){
        return edicaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Edição não encontrado com o id: " + id));
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
