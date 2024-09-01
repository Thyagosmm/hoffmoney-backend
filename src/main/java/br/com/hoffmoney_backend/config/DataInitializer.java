package br.com.hoffmoney_backend.config;

import br.com.hoffmoney_backend.modelo.categoriadespesa.CategoriaDespesa;
import br.com.hoffmoney_backend.modelo.categoriadespesa.CategoriaDespesaRepository;
import br.com.hoffmoney_backend.modelo.categoriareceita.CategoriaReceita;
import br.com.hoffmoney_backend.modelo.categoriareceita.CategoriaReceitaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

@Configuration
public class DataInitializer {
    
    @Bean
    @Transactional
    public CommandLineRunner initializeData(CategoriaDespesaRepository categoriaDespesaRepository, CategoriaReceitaRepository categoriaReceitaRepository) {
        return args -> {
            // Verifica se o banco de dados já está populado
            if (categoriaDespesaRepository.count() == 0) {
                // Categorias de Despesa
                categoriaDespesaRepository.save(CategoriaDespesa.builder().descricaoCategoriaDespesa("Alimentação").build());
                categoriaDespesaRepository.save(CategoriaDespesa.builder().descricaoCategoriaDespesa("Transporte").build());
                categoriaDespesaRepository.save(CategoriaDespesa.builder().descricaoCategoriaDespesa("Moradia").build());
                categoriaDespesaRepository.save(CategoriaDespesa.builder().descricaoCategoriaDespesa("Saúde").build());
                categoriaDespesaRepository.save(CategoriaDespesa.builder().descricaoCategoriaDespesa("Educação").build());
                categoriaDespesaRepository.save(CategoriaDespesa.builder().descricaoCategoriaDespesa("Lazer").build());
                categoriaDespesaRepository.save(CategoriaDespesa.builder().descricaoCategoriaDespesa("Vestuário").build());
                categoriaDespesaRepository.save(CategoriaDespesa.builder().descricaoCategoriaDespesa("Contas e Serviços").build());
                categoriaDespesaRepository.save(CategoriaDespesa.builder().descricaoCategoriaDespesa("Dívidas e Empréstimos").build());
                categoriaDespesaRepository.save(CategoriaDespesa.builder().descricaoCategoriaDespesa("Outros").build());
            }

            if (categoriaReceitaRepository.count() == 0) {
                // Categorias de Receita
                categoriaReceitaRepository.save(CategoriaReceita.builder().descricaoCategoriaReceita("Salário").build());
                categoriaReceitaRepository.save(CategoriaReceita.builder().descricaoCategoriaReceita("Investimentos").build());
                categoriaReceitaRepository.save(CategoriaReceita.builder().descricaoCategoriaReceita("Freelance").build());
                categoriaReceitaRepository.save(CategoriaReceita.builder().descricaoCategoriaReceita("Aluguel de Imóveis").build());
                categoriaReceitaRepository.save(CategoriaReceita.builder().descricaoCategoriaReceita("Dividendos").build());
                categoriaReceitaRepository.save(CategoriaReceita.builder().descricaoCategoriaReceita("Juros de Poupança").build());
                categoriaReceitaRepository.save(CategoriaReceita.builder().descricaoCategoriaReceita("Vendas de Produtos").build());
                categoriaReceitaRepository.save(CategoriaReceita.builder().descricaoCategoriaReceita("Reembolsos").build());
                categoriaReceitaRepository.save(CategoriaReceita.builder().descricaoCategoriaReceita("Prêmios").build());
                categoriaReceitaRepository.save(CategoriaReceita.builder().descricaoCategoriaReceita("Outros").build());
            }
        };
    }
}