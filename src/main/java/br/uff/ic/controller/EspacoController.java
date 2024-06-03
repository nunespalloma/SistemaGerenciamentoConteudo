package br.uff.ic.controller;

import br.uff.ic.model.Espaco;
import br.uff.ic.repository.EspacoRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/espaco")
public class EspacoController {

    @Autowired
    private EspacoRepository espacoRepository;

    @Operation(summary = "Cadastrar novo Espaço",
            description = "Cadastra um novo espaço no banco de dados do sistema. Requer um organizador autorizado no "
                    + "corpo da requisição.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Espaço criado com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos"),
                    @ApiResponse(responseCode = "405", description = "Usuário não autorizado")
            })
    @PostMapping("/cadastrar")
    public Espaco cadastrarEspaco(@RequestBody Espaco obj) {
        return espacoRepository.save(obj);
    }

    @Operation(summary = "Remover Espaço",
            description = "Remove um Espaço do sistema com base no ID fornecido. Este endpoint só pode ser "
                    + "utilizado pelo Organizador.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Espaço deletado com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Espaço não encontrado")
            })
    @DeleteMapping("/remover_espaco/{id}")
    public void deletarEspaco (@PathVariable long id){
        espacoRepository.deleteById(id);
    }

//    @GetMapping("/ler/{id}")
//    public Espaco lerEspaco (@PathVariable Long id){
//        return espacoRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Espaço não encontrado com o id: " + id));
//    }

//    @GetMapping("/listarEspacos")
//    public List<Espaco> listarEspacos(){
//        return espacoRepository.findAll();
//    }
//
//    @PutMapping("/alterarEspaco/{id}")
//    public Espaco alterarEspaco (@PathVariable Long id, @RequestBody Espaco espaco){
//        espaco.setId(id);
//        return espacoRepository.save(espaco);
//    }


}
