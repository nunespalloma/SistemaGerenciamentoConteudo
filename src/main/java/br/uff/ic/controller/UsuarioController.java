package br.uff.ic.controller;

import br.uff.ic.model.Usuario;
import br.uff.ic.repository.Repositorio;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController //Transformando essa classe em uma Controladora Rest, ou seja, em uma "servlet"
@RequestMapping("/UsuarioController") // /usuarioController é o nome/endereço dessa Controladora
public class UsuarioController {

    private Repositorio repositorio;

    @PostMapping("/criarUsuario") //Endpoint que utiliza método POST p/ criar Usuário de acordo com parametros recebidos
    public void criarUsuario (String login, String email, String nome, String afiliação){
        Usuario usuario = new Usuario();
        usuario.setLogin(login);
        usuario.setEmail(email);
        usuario.setNome(nome);
        usuario.setAfiliacao(afiliação);
        repositorio.save(usuario);
    }

    @PostMapping("/lerUsuario") //Endpoint que utiliza método POST p/ ler um Usuário de acordo com o id recebido
    public void lerUsuario(long id){
        //Usuario usuario = new Usuario();
        //procurar usuario no banco de dados
        //return usuario;
    }

    @PostMapping("/listarUsuarios")
    public void listarUsuarios(){

    }

    @PostMapping("/alterarUsuario")
    public void alterarUsuario(long id){

    }

    @PostMapping("/deletarUsuario")
    public void deletarUsuario(long id){

    }

}
