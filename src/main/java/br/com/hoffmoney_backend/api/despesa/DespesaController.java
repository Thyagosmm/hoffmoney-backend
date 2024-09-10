package br.com.hoffmoney_backend.api.despesa;

import br.com.hoffmoney_backend.modelo.categoriadespesa.CategoriaDespesa;
import br.com.hoffmoney_backend.modelo.categoriadespesa.CategoriaDespesaService;
import br.com.hoffmoney_backend.modelo.despesa.Despesa;
import br.com.hoffmoney_backend.modelo.despesa.DespesaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/despesas")
@CrossOrigin(origins = "*")
public class DespesaController {

    @Autowired
    private DespesaService despesaService;

    @Autowired
    private CategoriaDespesaService categoriaDespesaService;

    @GetMapping
    public ResponseEntity<List<Despesa>> listarTodasDespesas() {
        List<Despesa> despesas = despesaService.listarTodasDespesas();
        return ResponseEntity.ok(despesas);
    }

    @GetMapping("/{usuarioId}")
    public ResponseEntity<List<Despesa>> listarDespesasPorUsuarioId(@PathVariable Long usuarioId) {
        List<Despesa> despesas = despesaService.listarDespesasPorUsuarioId(usuarioId);
        return ResponseEntity.ok(despesas);
    }

    @GetMapping("/{usuarioId}/{id}")
    public ResponseEntity<Despesa> consultarDespesaPorId(@PathVariable Long usuarioId, @PathVariable Long id) {
        Optional<Despesa> despesa = despesaService.consultarDespesaPorIdEUsuarioId(id, usuarioId);
        return despesa.map(ResponseEntity::ok)
                .orElseThrow();
    }

    @PostMapping
    public ResponseEntity<Despesa> criarDespesa(@RequestBody @Valid DespesaRequest request) {
        CategoriaDespesa categoriaDespesa = categoriaDespesaService.obterPorID(request.getIdCategoriaDespesa());
        Despesa despesaNova = request.build(categoriaDespesa);
        Despesa despesa = despesaService.criarDespesa(despesaNova);
        return new ResponseEntity<>(despesa, HttpStatus.CREATED);
    }

    @PutMapping("/{usuarioId}/{id}")
    public ResponseEntity<Despesa> atualizarDespesa(@PathVariable("id") Long id, @RequestBody DespesaRequest request) {
        CategoriaDespesa categoriaDespesa = categoriaDespesaService.obterPorID(request.getIdCategoriaDespesa());
        Despesa despesa = request.build(categoriaDespesa);
        despesaService.atualizarDespesa(id, despesa);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{usuarioId}/{id}")
    public ResponseEntity<Void> deletarDespesa(@PathVariable Long usuarioId, @PathVariable Long id) {
        despesaService.deletarDespesa(id, usuarioId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/paga")
    public ResponseEntity<String> atualizarPaga(@PathVariable Long id, @RequestBody Boolean novaSituacaoPaga) {
        try {
            despesaService.atualizarPaga(id, novaSituacaoPaga);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Situação de pagamento atualizada com sucesso!");
        } catch (Exception e) {
            e.printStackTrace(); // Consider using a logger here
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao atualizar situação de pagamento.");
        }
    }

    @PostMapping("/filtrar")
    public List<Despesa> filtrarDespesas(@RequestBody FiltroDespesaDTO filtros) {
        return despesaService.filtrarDespesas(
                filtros.getNome(),
                filtros.getValor(),
                filtros.getDataDeCobranca(),
                filtros.getIdCategoriaDespesa());
    }
}
