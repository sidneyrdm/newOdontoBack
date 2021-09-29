package io.github.sidneyrdm.Odonto.repository;

import io.github.sidneyrdm.Odonto.model.Cliente;
import io.github.sidneyrdm.Odonto.model.Servico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface ServicoRepository extends JpaRepository<Servico, Integer> {

    @Query(" select s from Servico s join s.cliente c where upper(c.nome) like upper(:nome) and MONTH(s.dataCadastro)= :mes")
    List<Servico> findByNameAndMes(@Param("nome") String nome, @Param("mes") Integer mes);


}
