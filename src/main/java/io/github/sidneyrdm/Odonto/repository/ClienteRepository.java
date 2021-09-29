package io.github.sidneyrdm.Odonto.repository;

import io.github.sidneyrdm.Odonto.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
}
