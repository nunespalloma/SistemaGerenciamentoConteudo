package br.uff.ic.controller;

import br.uff.ic.model.Atividade;
import br.uff.ic.repository.AtividadeRepository;
import br.uff.ic.repository.EspacoRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/atividade")
public class AtividadeController {

    @Autowired
    private AtividadeRepository atividadeRepository;

    @Autowired
    private EspacoRepository espacoRepository;

    @Operation(
            summary = "Cadastrar nova Atividade",
            description = "Cadastra uma nova Atividade no banco de dados do sistema. Este endpoint só pode ser utilizado pelo Organizador. O espaço onde a atividade será realizada já deve ter sido\n" +
                    "cadastrado.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Atividade criada com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos"),
                    @ApiResponse(responseCode = "405", description = "Usuário não autorizado")
            })

    @PostMapping("/cadastrar")
    public Atividade cadastrarAtividade(@RequestBody Atividade obj) {
        // se o espaço onde a atividade será realizada já foi cadastrado, cadastra a atividade
        return atividadeRepository.save(obj); // faz o cadastramento do obj
    }

    @Operation(
            summary = "Lista de Atividades do Evento",
            description = "Esse Endpoint lista para o Usuário todas as Atividades de um Evento cadastradas no sistema.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Sucesso"),
                    @ApiResponse(responseCode = "404", description = "Eventos não encontrados")
            })
    @GetMapping("/{idEvento}/lista_atividades")
    public List<Atividade> listarAtividades(@PathVariable Long idEvento) {
        // ajeitar implementação
        return atividadeRepository.findAll();
    }

    @Operation(
            summary = "Detalhes sobre uma Atividade",
            description = "Esse Endpoint retorna detalhes de uma Atividade especifica com base no ID fornecido.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Sucesso"),
                    @ApiResponse(responseCode = "404", description = "Eventos não encontrados")
            })
    @GetMapping("/listar/{id}")
    public Atividade listarAtividade(@PathVariable Long id) {
        return atividadeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Atividade não encontrada com o id: " + id));
    }

    @Operation(summary = "Remover Atividade",
            description = "Remove um Atividade do sistema com base no ID fornecido. Este endpoint só pode ser "
                    + "utilizado pelo Organizador.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Atividade deletada com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Atividade não encontrada")
            })
    @DeleteMapping("/remover_atividade/{id}")
    public void deletarAtividade(@PathVariable Long id) {
        atividadeRepository.deleteById(id);
    }

    // AJUSTAR


//    @Operation(
//            summary = "Atualizar Atividade",
//            description = "Endpoint para atualizar uma atividade existente no sistema."
//    )
//    @PutMapping("/atualizarAtividade/{id}")
//    public Atividade atualizarAtividade(@PathVariable Long id, @RequestBody Atividade atividadeAtualizada) {
//        Atividade atividadeExistente = atividadeRepository.findById(id).orElse(null);
//        if (atividadeExistente != null) {
//            atividadeExistente.setNome(atividadeAtualizada.getNome());
//            atividadeExistente.setDescricao(atividadeAtualizada.getDescricao());
//            // Atualize os outros atributos
//            return atividadeRepository.save(atividadeExistente);
//        }
//        return null;
//    }


//
//
//    @GetMapping("/lerAtividade/{id}")
//    public Atividade lerAtividade (@PathVariable Long id){
//        return atividadeRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Atividade não encontrada com o id: " + id));
//    }
//
//
//    @PutMapping("/alterarAtividade/{id}")
//    public Atividade alterarAtividade (@PathVariable Long id, @RequestBody Atividade atividade){
//        atividade.setId(id);
//        return atividadeRepository.save(atividade);
//    }

}
