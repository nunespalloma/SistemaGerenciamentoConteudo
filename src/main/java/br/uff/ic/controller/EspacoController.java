package br.uff.ic.controller;

import br.uff.ic.model.Espaco;
import br.uff.ic.services.EspacoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/espaco")
public class EspacoController {

    @Autowired
    private EspacoService espacoService;

    @Operation(
            summary = "Criar novo Espaço",
            description = "Cadastra um novo Espaço no banco de dados do sistema. Este endpoint só pode ser utilizado pelo Organizador.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Espaço criado com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos"),
                    @ApiResponse(responseCode = "403", description = "Usuário não autorizado")
            })
    @PreAuthorize("hasRole('ORGANIZER')")
    @PostMapping("/criar")
    public ResponseEntity<Espaco> createEspaco(@Valid @RequestBody Espaco espaco, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(null);
        }

        Espaco createdEspaco = espacoService.save(espaco);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEspaco);
    }

    @Operation(
            summary = "Atualizar Espaço",
            description = "Endpoint para atualizar um espaço existente no sistema.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Espaço atualizado com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos"),
                    @ApiResponse(responseCode = "404", description = "Espaço não encontrado")
            })
    @PutMapping("/atualizar/{id}")
    public ResponseEntity<Espaco> updateEspaco(@PathVariable Long id, @Valid @RequestBody Espaco espaco, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(null);
        }

        espaco.setId(id);
        Espaco updatedEspaco = espacoService.save(espaco);
        return ResponseEntity.ok(updatedEspaco);
    }

    @Operation(
            summary = "Listar Espaços",
            description = "Endpoint para listar todos os espaços cadastrados no sistema.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Espaços listados com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Espaços não encontrados")
            })
    @GetMapping("/todos")
    public ResponseEntity<List<Espaco>> getEspacos() {
        List<Espaco> espacos = espacoService.findAll();
        return ResponseEntity.ok(espacos);
    }

    @Operation(
            summary = "Buscar Espaço por ID",
            description = "Endpoint para buscar um espaço específico no sistema.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Espaço encontrado com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Espaço não encontrado")
            })
    @GetMapping("/{id}")
    public ResponseEntity<?> getEspacoById(@PathVariable Long id) {
        Optional<Espaco> espaco = espacoService.findById(id);
        return espaco.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Deletar Espaço",
            description = "Endpoint para deletar um espaço existente no sistema.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Espaço deletado com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Espaço não encontrado")
            })
    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> deleteEspaco(@PathVariable Long id) {
        espacoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}




