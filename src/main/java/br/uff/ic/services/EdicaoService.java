package br.uff.ic.services;

import br.uff.ic.dto.EdicaoDTO;
import br.uff.ic.model.Edicao;
import br.uff.ic.model.Evento;
import br.uff.ic.repository.EdicaoRepository;
import br.uff.ic.repository.EventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EdicaoService {

    @Autowired
    private EdicaoRepository edicaoRepository;

    @Autowired
    private EventoRepository eventoRepository;

    public EdicaoDTO criarEdicao(Long eventoId, Edicao edicao) {
        Evento evento = eventoRepository.findById(eventoId)
                .orElseThrow(() -> new IllegalArgumentException("Evento não encontrado com o id: " + eventoId));
        edicao.setEvento(evento);
        Edicao savedEdicao = edicaoRepository.save(edicao);
        return toDTO(savedEdicao);
    }

    private EdicaoDTO toDTO(Edicao edicao) {
        return new EdicaoDTO(
                edicao.getId(),
                edicao.getNumero(),
                edicao.getAno(),
                edicao.getDataInicial(),
                edicao.getDataFinal(),
                edicao.getCidade()
        );
    }
}
