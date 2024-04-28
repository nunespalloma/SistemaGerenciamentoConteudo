package br.uff.ic.controller;

import br.uff.ic.model.Usuario;
import br.uff.ic.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController //Transformando essa classe em uma Controladora Rest, ou seja, em uma "servlet"
@RequestMapping("/UsuarioController") // /usuarioController é o nome/endereço dessa Controladora
public class UsuarioController {

    @Autowired //faz a implementação dos métodos que o repositorio importou do JPARepository e constroi o objeto oara
    // mim, ex: faz o = new Repositorio(), e faz outras implementações necessarias automaticamente
    private UsuarioRepository usuarioRepository;

    @PostMapping("/criarUsuario") //Endpoint que utiliza método POST p/ criar Usuário de acordo com usuário recebido
    public Usuario criarUsuario (@RequestBody Usuario usuario){ //@RequestBody serve para o Spring ler o corpo da
        // solicitação HTTP, que normalmente contém dados JSON ou XML, e automaticamente converter esses dados
        // em um objeto Java correspondente
        return usuarioRepository.save(usuario);
    }

    @GetMapping("/lerUsuario/{id}") //Endpoint que utiliza método POST p/ ler um Usuário de acordo com o id recebido
    // em {id} na solicitação do endpoint
    public Usuario lerUsuario(@PathVariable Long id){ //@PathVariable é a anotação usada para vincular o valor do ID do
        // usuário fornecido na solicitação à variável id no método
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id)); //método que lança uma
        //exceção RuntimeException c/ mensagem indicando que o usuário não foi encontrado, caso o id nao seja encontrado
    }

    @GetMapping("/listarUsuarios")
    public List<Usuario> listarUsuarios(){
        return usuarioRepository.findAll();
    }

    @PutMapping("/alterarUsuario/{id}")
    public Usuario alterarUsuario(@PathVariable Long id, @RequestBody Usuario usuario){
        usuario.setId(id);//nao entendi
        return usuarioRepository.save(usuario);
    }

    @DeleteMapping("/deletarUsuario/{id}")
    public void deletarUsuario(@PathVariable long id){
        usuarioRepository.deleteById(id);
    }

}