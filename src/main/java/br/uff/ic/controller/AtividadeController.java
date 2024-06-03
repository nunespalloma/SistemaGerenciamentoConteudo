package br.uff.ic.controller;

import br.uff.ic.model.Atividade;
import br.uff.ic.repository.AtividadeRepository;
import br.uff.ic.repository.EspacoRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Ajeitar controller, algumas operaçoes erradas

@RestController
@RequestMapping("/atividades")
public class AtividadeController {

    @Autowired
    private AtividadeRepository atividadeRepository;

    @Operation(summary = "Cadastrar nova Atividade",
            description = "Cadastra uma nova Atividade no banco de dados do sistema. Este endpoint só pode ser utilizado pelo Organizador. O espaço onde a atividade será realizada já deve ter sido\n" +
                    "cadastrado.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Atividade criada com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos")
            })

    @PostMapping("/cadastrarAtividade")
    public Atividade cadastrarAtividade(@RequestBody Atividade atividade) {
        return atividadeRepository.save(atividade);
    }

    @Operation(
            summary = "Listar todas as Atividades",
            description = "Endpoint para listar todas as atividades cadastradas no sistema."
    )
    @GetMapping("/listarAtividades")
    public List<Atividade> listarAtividades() {
        return atividadeRepository.findAll();
    }

//    @Operation(
//            summary = "Listar atividades por evento",
//            description = "Endpoint para listar atividades por evento."
//    )
//   // terminar implementacao
//    }


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

    @Operation(
            summary = "Deletar Atividade",
            description = "Endpoint para deletar uma atividade existente no sistema."
    )
    @DeleteMapping("/deletarAtividade/{id}")
    public void deletarAtividade(@PathVariable Long id) {
        atividadeRepository.deleteById(id);
    }


    @GetMapping("/lerAtividade/{id}")
    public Atividade lerAtividade (@PathVariable Long id){
        return atividadeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Atividade não encontrada com o id: " + id));
    }


    @PutMapping("/alterarAtividade/{id}")
    public Atividade alterarAtividade (@PathVariable Long id, @RequestBody Atividade atividade){
        atividade.setId(id);
        return atividadeRepository.save(atividade);
    }

}
