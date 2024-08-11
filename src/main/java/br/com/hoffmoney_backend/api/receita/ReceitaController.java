package br.com.hoffmoney_backend.api.receita;

import br.com.hoffmoney_backend.modelo.receita.Receita;
import br.com.hoffmoney_backend.modelo.receita.ReceitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/receitas")
@CrossOrigin(origins = "*")
public class ReceitaController {

    @Autowired
    private ReceitaService receitaService;

    @GetMapping
    public List<Receita> listarTodasReceitas() {
        return receitaService.listarTodasReceitas();
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Receita>> listarReceitasPorUsuarioId(@PathVariable Long usuarioId) {
        List<Receita> receitas = receitaService.listarReceitasPorUsuarioId(usuarioId);
        return ResponseEntity.ok(receitas);
    }

    @GetMapping("/usuario/{usuarioId}/{id}")
    public ResponseEntity<Receita> consultarReceitaPorId(@PathVariable Long usuarioId, @PathVariable Long id) {
        Optional<Receita> receita = receitaService.consultarReceitaPorIdEUsuarioId(id, usuarioId);
        if (receita.isPresent()) {
            return ResponseEntity.ok(receita.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping
    public ResponseEntity<Receita> criarReceita(@RequestBody Receita receita) {
        receitaService.salvarReceita(receita);
        return ResponseEntity.status(HttpStatus.CREATED).body(receita);
    }

    @PutMapping("/usuario/{usuarioId}/{id}")
    public ResponseEntity<Void> atualizarReceita(@PathVariable Long usuarioId, @PathVariable Long id, @RequestBody Receita novosDados) {
        receitaService.atualizarReceita(id, usuarioId, novosDados);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/usuario/{usuarioId}/{id}")
    public ResponseEntity<Void> deletarReceita(@PathVariable Long usuarioId, @PathVariable Long id) {
        receitaService.deletarReceita(id, usuarioId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
