package br.uff.ic.services;

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

}
