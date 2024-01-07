package com.br.frete.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.br.frete.entity.Empresa;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Long> {

    public Empresa findByUsername(String username);

    public Empresa findByCnpj(String cnpj);

    public Optional<Empresa> findByNome(String nome);

    @Query(value = "SELECT * FROM empresas e INNER JOIN empresa_role er ON e.id = er.empresa_id WHERE e.username = :username AND e.password = :password", nativeQuery = true)
    public List<Empresa> findByUsernameAndPassword(@Param("username") String username,
            @Param("password") String password);

    @Query(value = "SELECT COUNT(*) FROM empresa_role er INNER JOIN roles r ON er.role_id = r.id WHERE er.empresa_id = :empresaId AND r.name = 'EMPRESA'", nativeQuery = true)
    public int existsByEmpresaIdAndRoleName(@Param("empresaId") Long empresaId);

}
