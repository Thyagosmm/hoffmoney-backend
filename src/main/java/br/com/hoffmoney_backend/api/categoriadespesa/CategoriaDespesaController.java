package br.com.hoffmoney_backend.api.categoriadespesa;

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
import br.com.hoffmoney_backend.modelo.categoriadespesa.CategoriaDespesa;
import br.com.hoffmoney_backend.modelo.categoriadespesa.CategoriaDespesaService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/categoriadespesa")
@CrossOrigin
public class CategoriaDespesaController {

    @Autowired
    private CategoriaDespesaService categoriaDespesaService;

    @Operation(summary = "Serviço responsável por salvar uma categoria de despesa no sistema.")

    @PostMapping
    public ResponseEntity<CategoriaDespesa> save(@RequestBody @Valid CategoriaDespesaRequest request) {

        CategoriaDespesa categoriaDespesaNova = request.build();
        CategoriaDespesa categoriaDespesa = categoriaDespesaService.save(categoriaDespesaNova);
        return new ResponseEntity<CategoriaDespesa>(categoriaDespesa, HttpStatus.CREATED);
    }

    @Operation(summary = "Serviço responsável por listar todas as categorias de despesas do sistema.")

    @GetMapping
    public ResponseEntity<List<CategoriaDespesa>> listarTodas() {
        List<CategoriaDespesa> categorias = categoriaDespesaService.listarTodos();
        return ResponseEntity.ok(categorias);
    }

    @Operation(summary = "Serviço responsável por listar uma categoria de despesa especifica do sistema.")

    @GetMapping("/{id}")
    public CategoriaDespesa obterPorID(@PathVariable Long id) {
        return categoriaDespesaService.obterPorID(id);
    }

    @Operation(summary = "Serviço responsável por alterar dados de uma categoria de despesa especifica do sistema.")

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaDespesa> update(@PathVariable("id") Long id,
            @RequestBody CategoriaDespesaRequest request) {

        categoriaDespesaService.update(id, request.build());
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Serviço responsável por excluir uma categoria de despesa especifica do sistema.")

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        categoriaDespesaService.delete(id);
        return ResponseEntity.ok().build();
    }

}