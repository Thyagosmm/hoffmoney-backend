package br.com.hoffmoney_backend.api.receita;

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

import br.com.hoffmoney_backend.modelo.receita.Receita;
import br.com.hoffmoney_backend.modelo.receita.ReceitaService;


import io.swagger.v3.oas.annotations.Operation;
// import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/receita")
@CrossOrigin
public class ReceitaController {

    @Autowired
    private ReceitaService receitaService;

    @Operation(summary = "Serviço responsável por salvar uma receita no sistema.")
    @PostMapping
    public ResponseEntity<Receita> save(@RequestBody ReceitaRequest request) {

        Receita receita = receitaService.save(request.build());
        return new ResponseEntity<Receita>(receita, HttpStatus.CREATED);
    }

    @Operation(summary = "Serviço responsável por listar todas as receitas do cliente no sistema.")
    @GetMapping
    public List<Receita> listarTodos() {
        return receitaService.listarTodos();
    }

    @Operation(summary = "Serviço responsável por listar uma receita do cliente no sistema através do ID.")
    @GetMapping("/{id}")
    public Receita obterPorID(@PathVariable Long id) {
        return receitaService.obterPorID(id);
    }

    @Operation(summary = "Serviço responsável por atualizar os dados de uma receita do cliente no sistema através do ID.")
    @PutMapping("/{id}")
    public ResponseEntity<Receita> update(@PathVariable("id") Long id, @RequestBody ReceitaRequest request) {

        receitaService.update(id, request.build());
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Serviço responsável por excluir uma receita do cliente no sistema através do ID.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        receitaService.delete(id);
        return ResponseEntity.ok().build();
    }


    
}
