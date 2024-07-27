package br.com.hoffmoney_backend.api.despesa;
import br.com.hoffmoney_backend.modelo.despesa.Despesa;
import br.com.hoffmoney_backend.modelo.despesa.DespesaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/despesas")
public class DespesaController {

	@Autowired
	private DespesaService despesaService;

	@PostMapping("/repetir")
	public ResponseEntity<Void> repetirDespesa(@RequestBody Despesa despesa, @RequestParam int quantidade, @RequestParam DespesaService.Periodo periodo) {
		despesaService.cadastrarDespesaRepetida(despesa, quantidade, periodo);
		return ResponseEntity.ok().build();
	}
    
    @PutMapping("/atualizar")
    public ResponseEntity<Void> atualizarDespesasRecorrentes(@RequestBody Despesa despesaOriginal, @RequestBody Despesa novosDados) {
        despesaService.atualizarDespesasRecorrentes(despesaOriginal, novosDados);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/deletar")
    public ResponseEntity<Void> deletarDespesasRecorrentes(@RequestBody Despesa despesaOriginal) {
        despesaService.deletarDespesasRecorrentes(despesaOriginal);
        return ResponseEntity.ok().build();
    }
}
