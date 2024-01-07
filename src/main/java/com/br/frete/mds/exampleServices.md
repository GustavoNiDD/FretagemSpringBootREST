# Empresa Future Services

```java
public Empresa getByCnpj(String cnpj) {
    return empresaRepository.findByCnpj(cnpj) != null ? empresaRepository.findByCnpj(cnpj) : null;
}

public Empresa getByNome(String nome) {
    return empresaRepository.findByNome(nome).isPresent() ? empresaRepository.findByNome(nome).get() : null;
}
```

# Entregador Future Services

```java
public Entregador getByCpf(String cpf) {
    Optional<Entregador> entregadorOptional = entregadorRepository.findByCpf(cpf);
    if (entregadorOptional.isPresent()) {
        return entregadorOptional.get();
    }
    return null;
}

public Entregador getById(Long id) {
    Optional<Entregador> entregadorOptional = entregadorRepository.findById(id);
    if (entregadorOptional.isPresent()) {
        return entregadorOptional.get();
    }
    return null;
}

public Entregador getByNome(String nome) {
    Optional<Entregador> entregadorOptional = entregadorRepository.findByNome(nome);
    if (entregadorOptional.isPresent()) {
        return entregadorOptional.get();
    }
    return null;
}

```