package com.br.frete.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.br.frete.entity.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    public List<Usuario> findAll();

    public Usuario findByNome(String nome);

    public Usuario findById(long id);

    public void deleteById(Long id);

    public Usuario findByUsername(String username);

    public Usuario findByUsernameAndPassword(String username, String password);

    @Query(value = "SELECT COUNT(*) FROM usuario_role ur INNER JOIN roles r ON ur.role_id = r.id WHERE ur.usuario_id = :usuarioId AND r.name = 'USUARIO'", nativeQuery = true)
    public int existsByUsuarioIdAndRoleName(@Param("usuarioId") Long usuarioId);
}
