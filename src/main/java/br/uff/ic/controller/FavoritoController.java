package br.uff.ic.controller;


import br.uff.ic.infra.security.TokenService;
import br.uff.ic.model.Atividade;
import br.uff.ic.model.Usuario;
import br.uff.ic.service.FavoritoService;
import br.uff.ic.repository.AtividadeRepository;
import br.uff.ic.repository.UsuarioRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/favorito")
public class FavoritoController {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private FavoritoService favoritoService;

    @PostMapping("/{idAtividade}")
    public ResponseEntity<List<Atividade>> favoritar( HttpServletRequest request,@PathVariable Long idAtividade){
        String token = request.getHeader("Authorization").substring(7);
        var subject = tokenService.getSubject(token);
        Usuario usuario = (Usuario) usuarioRepository.findByEmail(subject);
        System.out.println(usuario.getId());;
        try {
        usuario = favoritoService.toggleFavorito(usuario.getId(), idAtividade);
            return ResponseEntity.ok(usuario.getFavoritos());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
//        return ResponseEntity.ok(usuario.getFavoritos());
    }

//    @GetMapping("/")
//    ResponseEntity<Atividade> listarAtividades(){
//
//        List<Atividade> atividades = atividadeRepo
//        return ResponseEntity.status(HttpStatus.CREATED).body(atividades);
//    }
}
