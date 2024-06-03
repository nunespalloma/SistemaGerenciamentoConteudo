package br.uff.ic.controller;

import br.uff.ic.model.Evento;
import br.uff.ic.model.Usuario;
import br.uff.ic.repository.UsuarioRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController //Transformando essa classe em uma Controladora Rest, ou seja, em uma "servlet"
@RequestMapping("/usuario") // /usuarioController é o nome/endereço dessa Controladora
public class UsuarioController {

    @Autowired //faz a implementação dos métodos que o repositorio importou do JPARepository e constroi o objeto oara
    // mim, ex: faz o = new Repositorio(), e faz outras implementações necessarias automaticamente
    private UsuarioRepository usuarioRepository;

    @Operation(summary = "Cadastro de novo Usuário",
            description = "Cadastra um novo usuário no banco de dados do sistema.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos")
            })
    @PostMapping("/cadastrar") //Endpoint que utiliza método POST p/ criar Usuário de acordo com usuário recebido
    public Usuario criarUsuario (@RequestBody Usuario usuario){ //@RequestBody serve para o Spring ler o corpo da
        // solicitação HTTP, que normalmente contém dados JSON ou XML, e automaticamente converter esses dados
        // em um objeto Java correspondente
        return usuarioRepository.save(usuario);
    }

    @Operation(summary = "Listar os Usuários",
            description = "Lista os Usuários cadastrados no sistema. Este endpoint só pode ser utilizado pelo Administrador.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Sucesso"),
                    @ApiResponse(responseCode = "404", description = "Eventos não encontrados"),
                    @ApiResponse(responseCode = "405", description = "Usuário não autorizado")
            })
    @GetMapping("/listar")
    public List<Usuario> listarUsuarios(){
        return usuarioRepository.findAll();
    }

    @Operation(summary = "Favoritar/Desfavoritar Atividade",
            description = "Favorita ou Desfavorita uma atividade para o usuário logado.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Atividade favoritada/desfavoritada com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Atividade não encontrada")
    })
    @GetMapping("/favoritar_atividade/{idUsuario}/{idAtividade}")
    public boolean favoritarAtividade(@PathVariable Long idUsuario, @PathVariable Long idAtividade) {
        // ajeitar implementaçao
        return true;
    }



//    @GetMapping("/ler_usuario/{id}") //Endpoint que utiliza método POST p/ ler um Usuário de acordo com o id recebido
//    // em {id} na solicitação do endpoint
//    public Usuario lerUsuario(@PathVariable Long id){ //@PathVariable é a anotação usada para vincular o valor do ID do
//        // usuário fornecido na solicitação à variável id no método
//        return usuarioRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com o id: " + id)); //método que lança
//        // exceção RuntimeException c/ mensagem indicando que usuário não foi encontrado, caso id nao seja encontrado
//    }

//    @PutMapping("/alterarUsuario/{id}")
//    public Usuario alterarUsuario(@PathVariable Long id, @RequestBody Usuario usuario){
//        usuario.setId(id);//nao entendi
//        return usuarioRepository.save(usuario);
//    }
//
//    @DeleteMapping("/deletarUsuario/{id}")
//    public void deletarUsuario(@PathVariable long id){
//        usuarioRepository.deleteById(id);
//    }

}