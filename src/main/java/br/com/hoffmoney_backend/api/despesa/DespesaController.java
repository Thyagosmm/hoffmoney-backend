package br.com.hoffmoney_backend.api.despesa;

import br.com.hoffmoney_backend.modelo.despesa.Despesa;
import br.com.hoffmoney_backend.modelo.despesa.DespesaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/despesas")
@CrossOrigin(origins = "*")
public class DespesaController {

    @Autowired
    private DespesaService despesaService;

    @GetMapping
    public List<Despesa> listarTodasDespesas() {
        return despesaService.listarTodasDespesas();
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Despesa>> listarDespesasPorUsuarioId(@PathVariable Long usuarioId) {
        List<Despesa> despesas = despesaService.listarDespesasPorUsuarioId(usuarioId);
        return ResponseEntity.ok(despesas);
    }

    @GetMapping("/usuario/{usuarioId}/{id}")
    public ResponseEntity<Despesa> consultarDespesaPorId(@PathVariable Long usuarioId, @PathVariable Long id) {
        Optional<Despesa> despesa = despesaService.consultarDespesaPorIdEUsuarioId(id, usuarioId);
        if (despesa.isPresent()) {
            return ResponseEntity.ok(despesa.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping
    public ResponseEntity<Despesa> criarDespesa(@RequestBody Despesa despesa) {
        despesaService.salvarDespesa(despesa);
        return ResponseEntity.status(HttpStatus.CREATED).body(despesa);
    }

    @PutMapping("/usuario/{usuarioId}/{id}")
    public ResponseEntity<Void> atualizarDespesa(@PathVariable Long usuarioId, @PathVariable Long id, @RequestBody Despesa novosDados) {
        despesaService.atualizarDespesa(id, usuarioId, novosDados);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/usuario/{usuarioId}/{id}")
    public ResponseEntity<Void> deletarDespesa(@PathVariable Long usuarioId, @PathVariable Long id) {
        despesaService.deletarDespesa(id, usuarioId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/{id}/paga")
    public ResponseEntity<Void> atualizarPaga(@PathVariable Long id, @RequestBody Boolean novaSituacaoPaga) {
        despesaService.atualizarPaga(id, novaSituacaoPaga);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    
    @PostMapping("/filtrar")
    public List<Despesa> filtrar(
        @RequestParam(value = "dataDeCobranca", required = false) LocalDate dataDeCobranca,
        @RequestParam(value = "valor", required = false) Double valor,
        @RequestParam(value = "categoria", required = false) String categoria,
        @RequestParam(value = "nome", required = false) String nome,
        @RequestParam(value = "usuarioId", required = true) Long usuarioId) {

    return despesaService.filtrar(dataDeCobranca, valor, categoria, nome, usuarioId);
}
}
