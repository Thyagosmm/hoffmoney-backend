package br.com.hoffmoney_backend.api.receita;

import br.com.hoffmoney_backend.modelo.categoriareceita.CategoriaReceita;
import br.com.hoffmoney_backend.modelo.categoriareceita.CategoriaReceitaService;
import br.com.hoffmoney_backend.modelo.receita.Receita;
import br.com.hoffmoney_backend.modelo.receita.ReceitaService;
import jakarta.validation.Valid;

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

    @Autowired
    private CategoriaReceitaService categoriaReceitaService;

    @GetMapping
    public ResponseEntity<List<Receita>> listarTodasReceitas() {
        List<Receita> receitas = receitaService.listarTodasReceitas();
        return ResponseEntity.ok(receitas);
    }

    @GetMapping("/{usuarioId}")
    public ResponseEntity<List<Receita>> listarReceitasPorUsuarioId(@PathVariable Long usuarioId) {
        List<Receita> receitas = receitaService.listarReceitasPorUsuarioId(usuarioId);
        return ResponseEntity.ok(receitas);
    }

    @GetMapping("/{usuarioId}/{id}")
    public ResponseEntity<Receita> consultarReceitaPorId(@PathVariable Long usuarioId, @PathVariable Long id) {
        Optional<Receita> receita = receitaService.consultarReceitaPorIdEUsuarioId(id, usuarioId);
        return receita.map(ResponseEntity::ok)
                .orElseThrow();
    }

    @PostMapping
    public ResponseEntity<Receita> criarReceita(@RequestBody @Valid ReceitaRequest request) {
        CategoriaReceita categoriaReceita = categoriaReceitaService.obterPorID(request.getIdCategoriaReceita());
        Receita receitaNova = request.build(categoriaReceita);
        Receita receita = receitaService.criarReceita(receitaNova);
        return new ResponseEntity<>(receita, HttpStatus.CREATED);
    }

    @PutMapping("/{usuarioId}/{id}")
    public ResponseEntity<Receita> atualizarReceita(@PathVariable("id") Long id, @RequestBody ReceitaRequest request) {
        CategoriaReceita categoriaReceita = categoriaReceitaService.obterPorID(request.getIdCategoriaReceita());
        Receita receita = request.build(categoriaReceita);
        receitaService.atualizarReceita(id, receita);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{usuarioId}/{id}")
    public ResponseEntity<Void> deletarReceita(@PathVariable Long usuarioId, @PathVariable Long id) {
        receitaService.deletarReceita(id, usuarioId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/paga")
    public ResponseEntity<String> atualizarPaga(@PathVariable Long id, @RequestBody Boolean novaSituacaoPaga) {
        try {
            receitaService.atualizarPaga(id, novaSituacaoPaga);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Situação de pagamento atualizada com sucesso!");
        } catch (Exception e) {
            e.printStackTrace(); // Consider using a logger here
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao atualizar situação de pagamento.");
        }
    }

    @PostMapping("/filtrar")
    public List<Receita> filtrarReceitas(@RequestBody FiltroReceitaDTO filtros) {
        return receitaService.filtrarReceitas(
                filtros.getNome(),
                filtros.getValor(),
                filtros.getDataDeCobranca(),
                filtros.getIdCategoriaReceita());
    }
}