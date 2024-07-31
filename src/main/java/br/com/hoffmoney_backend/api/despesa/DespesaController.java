package br.com.hoffmoney_backend.api.despesa;

import br.com.hoffmoney_backend.modelo.despesa.Despesa;
import br.com.hoffmoney_backend.modelo.despesa.DespesaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/despesas")
public class DespesaController {

    @Autowired
    private DespesaService despesaService;

    @GetMapping
    public ResponseEntity<List<Despesa>> listarDespesas() {
        List<Despesa> despesas = despesaService.listarDespesas();
        if (despesas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        System.out.println("Despesa: " + despesas);

        return ResponseEntity.ok(despesas);
    }

    @PostMapping
    public ResponseEntity<Void> criarDespesa(@RequestBody DespesaRequest despesaRequest) {
        despesaService.criarDespesa(despesaRequest);
        System.out.println("Despesa criada: " + despesaRequest);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/repetir")
    public ResponseEntity<Void> repetirDespesa(@RequestBody Despesa despesa, @RequestParam int quantidade,
            @RequestParam DespesaService.Periodo periodo) {
        despesaService.cadastrarDespesaRepetida(despesa, quantidade, periodo);
        System.out.println("Despesa repetida: " + despesa + ", Quantidade: " + quantidade + ", Periodo: " + periodo);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/atualizar")
    public ResponseEntity<Void> atualizarDespesasRecorrentes(@RequestBody Despesa despesaOriginal,
            @RequestBody Despesa novosDados) {
        despesaService.atualizarDespesasRecorrentes(despesaOriginal, novosDados);
        System.out.println("Despesa original: " + despesaOriginal + ", Novos dados: " + novosDados);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/deletar")
    public ResponseEntity<Void> deletarDespesasRecorrentes(@RequestBody Despesa despesaOriginal) {
        despesaService.deletarDespesasRecorrentes(despesaOriginal);
        System.out.println("Despesa deletada: " + despesaOriginal);
        return ResponseEntity.ok().build();
    }
}
