package br.uff.ic.controller;


import br.uff.ic.infra.security.DadosTokenJWT;
import br.uff.ic.infra.security.TokenService;
import br.uff.ic.model.DadosAutenticacao;
import br.uff.ic.model.Usuario;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AutenticacaoController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity efetuarLogin(@RequestBody @Valid DadosAutenticacao dados){
        var authenticationToken = new UsernamePasswordAuthenticationToken(dados.login(), dados.senha());
        var authentication = manager.authenticate(authenticationToken);
        var tokenJWT = tokenService.gerarToken((Usuario) authentication.getPrincipal());

        return ResponseEntity.ok(new DadosTokenJWT(tokenJWT));
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
