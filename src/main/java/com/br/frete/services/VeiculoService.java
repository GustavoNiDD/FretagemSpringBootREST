package com.br.frete.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.frete.entity.Veiculo;
import com.br.frete.repository.VeiculoRepository;

@Service
public class VeiculoService {

    @Autowired
    private VeiculoRepository veiculoRepository;

    public List<Veiculo> getAll() {
        return veiculoRepository.findAll();
    }

    public Veiculo getById(Long id) {
        return veiculoRepository.findById(id).get();
    }

    public Veiculo getByPeso(int peso) {
        return veiculoRepository.findByPeso(peso);
    }

    public Veiculo save(Veiculo veiculo) {
        return veiculoRepository.save(veiculo);
    }

    public void deleteById(Long id) {
        veiculoRepository.deleteById(id);
    }

    public void deleteAll() {
        veiculoRepository.deleteAll();
    }

}
