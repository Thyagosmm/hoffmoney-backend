package br.com.hoffmoney_backend.modelo.despesa;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DespesaRepository extends JpaRepository<Despesa, Long> {
}