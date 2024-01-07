package com.br.frete.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.br.frete.entity.Entregador;

@Repository
public interface EntregadorRepository extends JpaRepository<Entregador, Long> {

    Optional<Entregador> findByUsername(String username);

    public List<Entregador> findByUsernameAndPassword(String username, String password);

    @Query(value = "SELECT COUNT(*) FROM entregador_role er INNER JOIN roles r ON er.role_id = r.id WHERE er.entregador_id = :entregadorId AND r.name = 'ENTREGADOR'", nativeQuery = true)
    public int existsByEntregadorIdAndRoleName(@Param("entregadorId") Long entregadorId);

    @Query("SELECT ef.id AS entregador_frete_id, " +
            "f.id AS frete_id, " +
            "pu.destinatarioKm AS distancia, " +
            "v.tipoVeiculo AS Veiculo, " +
            "(pu.destinatarioKm * v.peso) AS valor_bruto, " +
            "CASE " +
            "  WHEN pu.destinatarioKm <= 100 THEN (pu.destinatarioKm * v.peso) * 0.2 " +
            "  WHEN pu.destinatarioKm > 100 AND pu.destinatarioKm <= 200 THEN (pu.destinatarioKm * v.peso) * 0.15 " +
            "  WHEN pu.destinatarioKm > 200 AND pu.destinatarioKm <= 500 THEN (pu.destinatarioKm * v.peso) * 0.1 " +
            "  ELSE (pu.destinatarioKm * v.peso) * 0.075 " +
            "END AS taxa, " +
            "(pu.destinatarioKm * v.peso) - " +
            "CASE " +
            "  WHEN pu.destinatarioKm <= 100 THEN (pu.destinatarioKm * v.peso) * 0.2 " +
            "  WHEN pu.destinatarioKm > 100 AND pu.destinatarioKm <= 200 THEN (pu.destinatarioKm * v.peso) * 0.15 " +
            "  WHEN pu.destinatarioKm > 200 AND pu.destinatarioKm <= 500 THEN (pu.destinatarioKm * v.peso) * 0.1 " +
            "  ELSE (pu.destinatarioKm * v.peso) * 0.075 " +
            "END AS valor_liquido " +
            "FROM entregador_frete ef " +
            "JOIN ef.frete f " +
            "JOIN f.pedidoUsuario pu " +
            "JOIN pu.usuario u " +
            "JOIN ef.entregador e " +
            "JOIN e.tipoVeiculo v " +
            "WHERE e.id = :pedido AND f.statusEntrega != 'concluido'")
    List<Map<String, Object>> findAllEntregadorFrete(@Param("pedido") Long pedido);

    Optional<Entregador> findByCpf(String cpf);

    Optional<Entregador> findByNome(String nome);

    void deleteByCpf(String cpf);

}
