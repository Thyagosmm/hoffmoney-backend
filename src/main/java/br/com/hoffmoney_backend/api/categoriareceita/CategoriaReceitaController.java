package br.com.hoffmoney_backend.api.categoriareceita;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.hoffmoney_backend.modelo.categoriareceita.CategoriaReceita;
import br.com.hoffmoney_backend.modelo.categoriareceita.CategoriaReceitaService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/categoriareceita")
@CrossOrigin
public class CategoriaReceitaController {

    @Autowired
    private CategoriaReceitaService categoriaReceitaService;

    @Operation(summary = "Serviço responsável por salvar uma categoria de receita no sistema.")
    @PostMapping
    public ResponseEntity<CategoriaReceita> save(@RequestBody @Valid CategoriaReceitaRequest request) {
        CategoriaReceita categoriaReceitaNova = request.build();
        CategoriaReceita categoriaReceita = categoriaReceitaService.save(categoriaReceitaNova);
        return new ResponseEntity<>(categoriaReceita, HttpStatus.CREATED);
    }

    @Operation(summary = "Serviço responsável por listar todas as categorias de receitas do sistema.")
    @GetMapping
    public ResponseEntity<List<CategoriaReceita>> listarTodos() {
        List<CategoriaReceita> categorias = categoriaReceitaService.listarTodos();
        return ResponseEntity.ok(categorias);
    }

    @Operation(summary = "Serviço responsável por listar uma categoria de receita específica do sistema.")
    @GetMapping("/{id}")
    public ResponseEntity<CategoriaReceita> obterPorID(@PathVariable Long id) {
        CategoriaReceita categoriaReceita = categoriaReceitaService.obterPorID(id);
        return ResponseEntity.ok(categoriaReceita);
    }

    @Operation(summary = "Serviço responsável por alterar dados de uma categoria de receita específica do sistema.")
    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable("id") Long id, @RequestBody CategoriaReceitaRequest request) {
        categoriaReceitaService.update(id, request.build());
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Serviço responsável por excluir uma categoria de receita específica do sistema.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        categoriaReceitaService.delete(id);
        return ResponseEntity.ok().build();
    }
}
