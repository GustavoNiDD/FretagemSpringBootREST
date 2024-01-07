package com.br.frete.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.frete.entity.Entregador;
import com.br.frete.entity.EntregadorFrete;
import com.br.frete.repository.EntregadorFreteRepository;

@Service
public class EntregadorFreteService {

    @Autowired
    private EntregadorFreteRepository entregadorFreteRepository;

    public List<EntregadorFrete> getAllEntregador(Entregador entregador) {
        return entregadorFreteRepository.findByEntregadorId(entregador.getId());
    }

    public void cancelarFrete(Long entregadorFreteId) {
        EntregadorFrete entregadorFrete = entregadorFreteRepository.findById(entregadorFreteId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid entregadorFreteId: " + entregadorFreteId));
        entregadorFreteRepository.delete(entregadorFrete);
    }

    public EntregadorFrete saveEntregadorFrete(EntregadorFrete entregadorFrete) {
        return entregadorFreteRepository.save(entregadorFrete);
    }

}
