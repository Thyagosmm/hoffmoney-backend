package br.com.hoffmoney_backend.modelo.despesa;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.hoffmoney_backend.api.despesa.DespesaRequest;
import br.com.hoffmoney_backend.modelo.usuario.Usuario;
import br.com.hoffmoney_backend.modelo.usuario.UsuarioRepository;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.Calendar;

@Service
public class DespesaService {

    @Autowired
    private DespesaRepository despesaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Despesa> listarDespesas() {
        return despesaRepository.findAll();
    }

    @Transactional
    public void criarDespesa(DespesaRequest despesaRequest) {
        Despesa despesa = new Despesa();

        Usuario usuario = usuarioRepository.findById(despesaRequest.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        despesa.setUsuario(usuario);
        despesa.setNome(despesaRequest.getNome());
        despesa.setValor(despesaRequest.getValor());
        despesa.setDataDeCobranca(despesaRequest.getDataDeCobranca());
        // Defina outros campos conforme necessário

        despesaRepository.save(despesa);
    }

    public void cadastrarDespesaRepetida(Despesa despesaOriginal, int quantidade, Periodo periodo) {
        List<Despesa> despesas = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(despesaOriginal.getDataDeCobranca());

        for (int i = 0; i < quantidade; i++) {
            Despesa novaDespesa = new Despesa();
            novaDespesa.setUsuario(despesaOriginal.getUsuario());
            novaDespesa.setNome(despesaOriginal.getNome());
            novaDespesa.setValor(despesaOriginal.getValor());
            novaDespesa.setDataDeCobranca(calendar.getTime());

            despesas.add(novaDespesa);

            // Incrementa a data com base no período
            switch (periodo) {
                case daily:
                    calendar.add(Calendar.DAY_OF_MONTH, 1);
                    break;
                case weekly:
                    calendar.add(Calendar.WEEK_OF_YEAR, 1);
                    break;
                case monthly:
                    calendar.add(Calendar.MONTH, 1);
                    break;
                case yearly:
                    calendar.add(Calendar.YEAR, 1);
                    break;
            }
        }

        despesaRepository.saveAll(despesas);
    }

    public enum Periodo {
        daily, weekly, monthly, yearly
    }

    // Método para atualizar despesas recorrentes
    @Transactional
    public void atualizarDespesasRecorrentes(Despesa despesaOriginal, Despesa novosDados) {
        List<Despesa> despesasRecorrentes = despesaRepository.findByUsuario(despesaOriginal.getUsuario());

        for (Despesa despesa : despesasRecorrentes) {
            despesa.setValor(novosDados.getValor());
            despesa.setNome(novosDados.getNome());
            despesa.setDataDeCobranca(novosDados.getDataDeCobranca());
            // Atualize outros campos conforme necessário
        }

        despesaRepository.saveAll(despesasRecorrentes);
    }

    // Método para deletar despesas recorrentes
    @Transactional
    public void deletarDespesasRecorrentes(Despesa despesaOriginal) {
        List<Despesa> despesasRecorrentes = despesaRepository.findByUsuario(despesaOriginal.getUsuario());
        despesaRepository.deleteAll(despesasRecorrentes);
    }
}