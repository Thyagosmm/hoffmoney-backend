package br.com.hoffmoney_backend.api.categoriaReceita;

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

import br.com.hoffmoney_backend.modelo.categoriaReceita.CategoriaReceitaService;
import br.com.hoffmoney_backend.modelo.categoriaReceita.CategoriaReceita;

@RestController
@RequestMapping("/api/categoriaReceita")
@CrossOrigin
public class CategoriaReceitaController {

    @Autowired
    private CategoriaReceitaService categoriaReceitaService;

    @PostMapping
    public ResponseEntity<CategoriaReceita> save(@RequestBody CategoriaReceitaRequest request) {

        CategoriaReceita categoria = categoriaReceitaService.save(request.build());
        return new ResponseEntity<CategoriaReceita>(categoria, HttpStatus.CREATED);
    }

    @GetMapping
    public List<CategoriaReceita> listarTodos() {
        return categoriaReceitaService.listarTodos();
    }

    @GetMapping("/{id}")
    public CategoriaReceita obterPorID(@PathVariable Long id) {
        return categoriaReceitaService.obterPorID(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaReceita> update(@PathVariable("id") Long id, @RequestBody CategoriaReceitaRequest request) {

        categoriaReceitaService.update(id, request.build());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
 
        categoriaReceitaService.delete(id);
        return ResponseEntity.ok().build();
    }
    
}
