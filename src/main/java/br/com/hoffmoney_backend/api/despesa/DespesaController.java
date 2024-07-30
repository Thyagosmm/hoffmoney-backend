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

@RestController
@RequestMapping("/api/despesas")
public class DespesaController {

    @Autowired
    private DespesaService despesaService;

    @Autowired
    private DespesaRepository despesaRepository;

   
    @GetMapping
    public ResponseEntity<List<Despesa>> listarDespesas() {
        List<Despesa> despesas = despesaService.listarTodasDespesas();
        System.out.println("Despesas no controlador: " + despesas); // Log para verificar o retorno do serviço

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(despesas);
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