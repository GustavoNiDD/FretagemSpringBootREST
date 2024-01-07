package com.br.frete.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.frete.entity.Frete;
import com.br.frete.entity.StatusEntrega;
import com.br.frete.repository.FreteRepository;

@Service
public class FreteService {

    @Autowired
    private FreteRepository freteRepository;

    public List<Frete> getAll() {
        return freteRepository.findAll();
    }

    public List<Frete> getFretesWithStatusEntregadorFalse() {
        return freteRepository.findFretesWithStatusEntregadorFalse();
    }

    public List<Frete> getByStatusEntrega(StatusEntrega statusEntrega) {
        return freteRepository.findByStatusEntrega(statusEntrega);
    }

    public List<Frete> getFreteEmpresa(Long empresa) {
        return freteRepository.findByEmpresaId(empresa);
    }

    public List<Map<String, Object>> getFretesConcluidos() {
        return freteRepository.findAll().stream()
                .filter(frete -> frete.getStatusEntrega() == StatusEntrega.concluido)
                .map(frete -> {
                    Map<String, Object> freteInfo = new HashMap<>();
                    freteInfo.put("idFrete", frete.getId());
                    freteInfo.put("status", frete.getStatusEntrega());
                    return freteInfo;
                })
                .collect(Collectors.toList());
    }

    public void mudarStatusParaEmAndamento(Long freteId) {
        Frete frete = freteRepository.findById(freteId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid freteId: " + freteId));
        frete.setStatusEntrega(StatusEntrega.em_andamento);
        freteRepository.save(frete);
    }

    public void mudarStatusParaConcluido(Long freteId) {
        Frete frete = freteRepository.findById(freteId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid freteId: " + freteId));
        frete.setStatusEntrega(StatusEntrega.concluido);
        freteRepository.save(frete);
    }

    public List<Frete> getAllByEmpresaId(Long empresaId) {
        return freteRepository.findByEmpresaId(empresaId);
    }

    public Optional<Frete> getById(Long freteId) {
        return freteRepository.findById(freteId);
    }

    public Frete getById2(Long freteId) {
        return freteRepository.findById(freteId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid freteId: " + freteId));
    }

    public Optional<Frete> getFreteById(Long id) {
        return freteRepository.findById(id);
    }

    public Frete save(Frete frete) {
        return freteRepository.save(frete);
    }

    public void deleteFreteById(Long id) {
        freteRepository.deleteById(id);
    }

}
