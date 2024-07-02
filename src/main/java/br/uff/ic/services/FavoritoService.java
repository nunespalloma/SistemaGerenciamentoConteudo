package br.uff.ic.service;

import br.uff.ic.model.Atividade;
import br.uff.ic.model.Usuario;
import br.uff.ic.repository.AtividadeRepository;
import br.uff.ic.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FavoritoService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private AtividadeRepository atividadeRepository;

    public Usuario toggleFavorito(Long usuarioId, Long atividadeId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado com o id: " + usuarioId));
        Atividade atividade = atividadeRepository.findById(atividadeId)
                .orElseThrow(() -> new IllegalArgumentException("Atividade não encontrada com o id: " + atividadeId));

        if (usuario.getAtividadesFavoritas().contains(atividade)) {
            usuario.getAtividadesFavoritas().remove(atividade);
        } else {
            usuario.getAtividadesFavoritas().add(atividade);
        }
        return usuarioRepository.save(usuario);
    }
}
