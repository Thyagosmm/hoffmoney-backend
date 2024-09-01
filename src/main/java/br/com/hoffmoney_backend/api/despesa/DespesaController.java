package br.com.hoffmoney_backend.api.despesa;

import br.com.hoffmoney_backend.modelo.categoriadespesa.CategoriaDespesa;
import br.com.hoffmoney_backend.modelo.categoriadespesa.CategoriaDespesaService;
import br.com.hoffmoney_backend.modelo.despesa.Despesa;
import br.com.hoffmoney_backend.modelo.despesa.DespesaService;
import br.com.hoffmoney_backend.modelo.usuario.Usuario;
import br.com.hoffmoney_backend.modelo.usuario.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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

    @Autowired
    private UsuarioService usuarioService;

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
    public ResponseEntity<String> criarDespesa(@RequestBody Despesa despesa) {
        try {
            // Verifica se o usuário está presente e válido
            Usuario usuario = usuarioService.findById(despesa.getUsuario().getId());
            if (usuario == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuário não encontrado.");
            }

            // Verifica se o ID da categoria despesa foi fornecido
            Long categoriaId = despesa.getCategoriaDespesa().getId();
            if (categoriaId == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Categoria não fornecida.");
            }

            // Busca a categoria despesa pelo ID
            CategoriaDespesa categoria = categoriaDespesaService.findById(categoriaId);
            if (categoria == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Categoria não encontrada.");
            }

            // Define o usuário e a categoria na despesa
            despesa.setUsuario(usuario);
            despesa.setCategoriaDespesa(categoria);

            // Salva a nova despesa
            despesaService.salvarDespesa(despesa);
            return ResponseEntity.status(HttpStatus.CREATED).body("Despesa criada com sucesso!");

        } catch (Exception e) {
            e.printStackTrace(); // Consider using a logger here
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao criar despesa.");
        }
    }

    @PutMapping("/{usuarioId}/{id}")
    public ResponseEntity<String> atualizarDespesa(@PathVariable Long usuarioId, @PathVariable Long id,
            @RequestBody Despesa despesa) {
        try {
            despesaService.atualizarDespesa(id, usuarioId, despesa);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Despesa atualizada com sucesso!");
        } catch (Exception e) {
            e.printStackTrace(); // Consider using a logger here
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao atualizar despesa.");
        }
    }

    @DeleteMapping("/{usuarioId}/{id}")
    public ResponseEntity<String> deletarDespesa(@PathVariable Long usuarioId, @PathVariable Long id) {
        try {
            despesaService.deletarDespesa(id, usuarioId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Despesa deletada com sucesso!");
        } catch (Exception e) {
            e.printStackTrace(); // Consider using a logger here
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao deletar despesa.");
        }
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
    public ResponseEntity<List<Despesa>> filtrar(
            @RequestParam(value = "dataDeCobranca", required = false) LocalDate dataDeCobranca,
            @RequestParam(value = "valor", required = false) Double valor,
            @RequestParam(value = "categoria", required = false) Long categoria,
            @RequestParam(value = "nome", required = false) String nome,
            @RequestParam(value = "usuarioId") Long usuarioId) {

        List<Despesa> despesas = despesaService.filtrar(dataDeCobranca, valor, categoria, nome, usuarioId);
        return ResponseEntity.ok(despesas);
    }
}
