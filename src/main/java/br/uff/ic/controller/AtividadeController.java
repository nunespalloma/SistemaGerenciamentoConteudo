package br.uff.ic.controller;

import br.uff.ic.model.Atividade;
import br.uff.ic.model.Espaco;
import br.uff.ic.repository.AtividadeRepository;
import br.uff.ic.repository.EspacoRepository;
import br.uff.ic.services.AtividadeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("atividade")
public class AtividadeController {

    @Autowired
    private AtividadeService atividadeService;

    @Operation(
            summary = "Criar nova Atividade",
            description = "Cadastra uma nova Atividade no banco de dados do sistema. Este endpoint só pode ser utilizado pelo Organizador. O espaço onde a atividade será realizada já deve ter sido\n" +
                    "cadastrado.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Atividade criada com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos"),
                    @ApiResponse(responseCode = "403", description = "Usuário não autorizado"),
                    @ApiResponse(responseCode = "404", description = "Espaço não encontrado")
            })
    // TODO: Adicionar a anotação @PreAuthorize("hasRole('ORGANIZADOR')") para restringir o acesso a apenas usuários com a role ORGANIZADOR
    // TODO: Implrementar a lógica para verificar se o espaço informado na atividade existe no sistema
    @PostMapping("/criar")
    public ResponseEntity<?> createAtividade(@Valid @RequestBody Atividade atividade, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body("Erro de validação: " + bindingResult.getAllErrors());
        }
        Atividade createdAtividade = atividadeService.save(atividade);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAtividade);
    }

    @Operation(
            summary = "Atualizar Atividade",
            description = "Endpoint para atualizar uma atividade existente no sistema.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Atividade atualizada com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos"),
                    @ApiResponse(responseCode = "404", description = "Atividade não encontrada")
            })
    @PutMapping("/atualizar/{id}")
    public ResponseEntity<?> updateAtividade(@PathVariable Long id, @Valid @RequestBody Atividade atividade, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body("Erro de validação: " + bindingResult.getAllErrors());
        }

        atividade.setId(id);
        Atividade updatedAtividade = atividadeService.save(atividade);
        return ResponseEntity.ok(updatedAtividade);
    }

    @Operation(
            summary = "Listar todas as Atividades",
            description = "Esse Endpoint lista para o Usuário todas as Atividades cadastradas no sistema.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Sucesso"),
                    @ApiResponse(responseCode = "404", description = "Atividades não encontradas")
            })
    @GetMapping("/todas")
    public ResponseEntity<List<Atividade>> getAllAtividades() {
        List<Atividade> atividades = atividadeService.findAll();
        return ResponseEntity.ok(atividades);
    }

    @Operation(
            summary = "Detalhes sobre uma Atividade",
            description = "Esse Endpoint retorna detalhes de uma Atividade especifica com base no ID fornecido.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Sucesso"),
                    @ApiResponse(responseCode = "404", description = "Atividade não encontrada")
            })
    @GetMapping("/{id}")
    public ResponseEntity<?> getAtividadeById(@PathVariable Long id) {
        Optional<Atividade> atividade = atividadeService.findById(id);
        return atividade.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Remover Atividade",
            description = "Remove uma Atividade do sistema com base no ID fornecido. Este endpoint só pode ser utilizado pelo Organizador.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Atividade deletada com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Atividade não encontrada")
            })
    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> deleteAtividade(@PathVariable Long id) {
        atividadeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

