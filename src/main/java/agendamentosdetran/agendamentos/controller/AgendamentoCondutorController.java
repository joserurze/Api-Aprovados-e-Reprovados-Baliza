package agendamentosdetran.agendamentos.controller;
import agendamentosdetran.agendamentos.entity.AprovadosReprovadosDto;
import agendamentosdetran.agendamentos.entity.ResultadosDTO;
import agendamentosdetran.agendamentos.entity.TotalGeralDto;
import agendamentosdetran.agendamentos.repository.AgendamentoCondutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("agendamentos")
public class AgendamentoCondutorController {

    @Autowired
    private AgendamentoCondutorRepository repository;

    @GetMapping
    @RequestMapping("/quantresultados/{dataInicial}/{dataFinal}")
    public List<ResultadosDTO> ListaAgendamento(@PathVariable("dataInicial") String dataInicial, @PathVariable("dataFinal") String dataFinal) {

        List<Object[]> listAgendamento = repository.findByquantityResults(dataInicial, dataFinal);
        List<ResultadosDTO> resultadosDTOS = new ArrayList<>();

        for (Object[] obj : listAgendamento) {
            ResultadosDTO resultadosDTO = new ResultadosDTO();
            resultadosDTO.setDataAgendamento((obj[0]).toString());
            resultadosDTO.setContagem(Long.parseLong(obj[1].toString()));
            resultadosDTO.setCategoria(obj[2].toString());
            resultadosDTOS.add(resultadosDTO);
        }
        return resultadosDTOS;


    }

    @CrossOrigin(origins = "http://127.0.0.1:5500/")
    @GetMapping
    @RequestMapping("/aprovereprov/{dataInicial}/{dataFinal}")
    public List<AprovadosReprovadosDto> listaAprovReprov(@PathVariable("dataInicial") String dataInicial,
                                                         @PathVariable("dataFinal") String dataFinal) {

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            Date dataInicial1 = dateFormat.parse(dataInicial);
            Date dataFinal1 = dateFormat.parse(dataFinal);

            List<Object[]> listaprovreprov = repository.findapprovedFailed(dataInicial1, dataFinal1);

            // Variáveis para armazenar a contagem de aprovados, reprovados e total geral
            long aprovados = 0;
            long reprovados = 0;
            long totalGeral = aprovados + reprovados;

            List<AprovadosReprovadosDto> aprovadosReprovadosDtos = new ArrayList<>();

            for (Object[] obj : listaprovreprov) {
                AprovadosReprovadosDto aprovadosReprovadosDto = new AprovadosReprovadosDto();
                aprovadosReprovadosDto.setResultado(obj[0].toString());
                aprovadosReprovadosDto.setContagem(Long.parseLong(obj[1].toString()));
                BigDecimal bigDecimalValor = new BigDecimal(obj[2].toString());
                BigDecimal valorArredondado = bigDecimalValor.setScale(2, RoundingMode.HALF_UP);
                aprovadosReprovadosDto.setPorcentagem(valorArredondado);
                aprovadosReprovadosDto.setTotalGeral(Long.parseLong(obj[3].toString()));

                // Atualizar as variáveis de contagem
                if (aprovadosReprovadosDto.getResultado().equals("Aprovado")) {
                    aprovados += aprovadosReprovadosDto.getContagem();
                } else if (aprovadosReprovadosDto.getResultado().equals("Reprovado")) {
                    reprovados += aprovadosReprovadosDto.getContagem();
                }
                totalGeral = aprovadosReprovadosDto.getTotalGeral();

                aprovadosReprovadosDtos.add(aprovadosReprovadosDto);

            }

            // Criar objeto para representar o total geral
            AprovadosReprovadosDto totalGeralDto = new AprovadosReprovadosDto();

            totalGeralDto.setResultado("Total Geral");
            totalGeralDto.setTotalGeral(totalGeral);

            // Adicionar o objeto do total geral à lista final
            aprovadosReprovadosDtos.add(totalGeralDto);

            return aprovadosReprovadosDtos;

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

}



