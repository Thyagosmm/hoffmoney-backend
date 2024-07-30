package br.com.hoffmoney_backend.api.despesa;

import br.com.hoffmoney_backend.modelo.despesa.DespesaService.Periodo;

import br.com.hoffmoney_backend.modelo.despesa.Despesa;
import br.com.hoffmoney_backend.modelo.despesa.DespesaRepository;
import br.com.hoffmoney_backend.modelo.despesa.DespesaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/despesas")
@CrossOrigin(origins = "http://localhost:3000")
public class DespesaController {

    @Autowired
    private DespesaService despesaService;

    @Autowired
    private DespesaRepository despesaRepository;

    @GetMapping
    public List<Despesa> listarTodasDespesas() {
        return despesaService.listarTodasDespesas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Despesa> consultarDespesaPorId(@PathVariable Long id) {
        Optional<Despesa> despesa = despesaService.consultarDespesaPorId(id);
        if (despesa.isPresent()) {
            return ResponseEntity.ok(despesa.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping
    public ResponseEntity<Despesa> criarDespesa(@RequestBody Despesa despesa) {
        Despesa novaDespesa = despesaRepository.save(despesa);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaDespesa);
    }

    @PostMapping("/repetir")
    public ResponseEntity<Void> cadastrarDespesasRepetidas(
            @RequestBody Despesa despesaOriginal) {
        // Converter a String para o enum Periodo
        Periodo periodoEnum = Periodo.valueOf(despesaOriginal.getPeriodo().toLowerCase());

        // Chamar o método com os parâmetros corretos
        despesaService.cadastrarDespesaRepetida(despesaOriginal, despesaOriginal.getVezes(), periodoEnum);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/atualizar")
    public ResponseEntity<Void> atualizarDespesasRecorrentes(@RequestBody Despesa despesaOriginal,
            @RequestBody Despesa novosDados) {
        despesaService.atualizarDespesasRecorrentes(despesaOriginal, novosDados);
        System.out.println("Despesa original: " + despesaOriginal + ", Novos dados: " + novosDados);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/deletar")
    public ResponseEntity<Void> deletarDespesasRecorrentes(@RequestBody Despesa despesaOriginal) {
        despesaService.deletarDespesasRecorrentes(despesaOriginal);
        System.out.println("Despesa deletada: " + despesaOriginal);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}