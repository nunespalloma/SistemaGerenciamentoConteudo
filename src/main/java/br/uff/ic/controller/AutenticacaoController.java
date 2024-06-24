package br.uff.ic.controller;


import br.uff.ic.infra.security.DadosTokenJWT;
import br.uff.ic.infra.security.TokenService;
import br.uff.ic.model.DadosAutenticacao;
import br.uff.ic.model.Usuario;
import br.uff.ic.repository.UsuarioRepository;
import br.uff.ic.services.AutenticacaoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/autenticacao")
public class AutenticacaoController {

    @Autowired
    private AutenticacaoService autenticacaoService;

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository usuarioRepository;


    @PostMapping("/login")
    public ResponseEntity efetuarLogin(@RequestBody @Valid DadosAutenticacao dados){
        var authenticationToken = new UsernamePasswordAuthenticationToken(dados.login(), dados.senha());
        var authentication = manager.authenticate(authenticationToken);
        var tokenJWT = tokenService.gerarToken((Usuario) authentication.getPrincipal());

        return ResponseEntity.ok(new DadosTokenJWT(tokenJWT));
    }


//    @PostMapping("/registrar")
//    public Usuario registerUser(@RequestBody Usuario usuario) {
//        return usuarioRepository.save(usuario);
//    }
    @PostMapping("/registrar")
    public ResponseEntity<Usuario> registerUser(@RequestBody Usuario usuario) {
    Usuario novoUsuario = autenticacaoService.registrarUsuario(usuario, List.of("ROLE_USER"));
    return ResponseEntity.ok(novoUsuario);
}

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/papeis/{userId}")
    public ResponseEntity<Usuario> updateRoles(@PathVariable Long userId, @RequestBody List<String> roles) {
        System.out.println("Update User");
        Usuario updatedUser = autenticacaoService.updateRoles(userId, roles);
        return ResponseEntity.ok(updatedUser);
    }

//    @Autowired
//    private UsuarioRepository usuarioRepository;

//    @Operation(summary = "Auntenticar Usuario",
//            description = "Autentica o usuário no sistema",
//            responses = {
//                    @ApiResponse(responseCode = "201", description = "Espaço criado com sucesso"),
//                    @ApiResponse(responseCode = "400", description = "Dados inválidos"),
//                    @ApiResponse(responseCode = "405", description = "Usuário não autorizado")
//            })
//    @PostMapping("/login")
//    public Usuario login(@RequestBody Usuario usuario) {
//        return usuarioRepository.save(usuario);
//    }

}
