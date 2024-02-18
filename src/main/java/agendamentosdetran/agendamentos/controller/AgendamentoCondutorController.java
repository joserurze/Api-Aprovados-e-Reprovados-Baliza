package agendamentosdetran.agendamentos.controller;
import agendamentosdetran.agendamentos.entity.AprovadosReprovadosDto;
import agendamentosdetran.agendamentos.entity.ResultadosDTO;
import agendamentosdetran.agendamentos.repository.AgendamentoCondutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping
    @RequestMapping("/aprovereprov/{dataInicial}/{dataFinal}")
    public List<AprovadosReprovadosDto> listaAprovReprov(@PathVariable("dataInicial") String dataInicial,
                                                         @PathVariable("dataFinal") String dataFinal) {

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy"); // Substitua pelo formato real das suas datas

            Date dataInicial1 = dateFormat.parse(dataInicial);
            Date dataFinal1 = dateFormat.parse(dataFinal);

            List<Object[]> listaprovreprov = repository.findapprovedFailed(dataInicial1, dataFinal1);

            List<AprovadosReprovadosDto> aprovadosReprovadosDtos = new ArrayList<>();

            for (Object[] obj : listaprovreprov) {

                AprovadosReprovadosDto aprovadosReprovadosDto = new AprovadosReprovadosDto();
                aprovadosReprovadosDto.setResultado(obj[0].toString());
                aprovadosReprovadosDto.setContagem(Long.parseLong(obj[1].toString()));
                BigDecimal bigDecimalValor = new BigDecimal(obj[2].toString());
                BigDecimal valorArredondado = bigDecimalValor.setScale(2, RoundingMode.HALF_UP);
                aprovadosReprovadosDto.setPorcentagem(valorArredondado);
                aprovadosReprovadosDtos.add(aprovadosReprovadosDto);
            }
            return aprovadosReprovadosDtos;

        } catch (ParseException e) {
            // Trate a exceção de formato de data inválido
            e.printStackTrace(); // Ou faça outro tratamento, como lançar uma exceção personalizada

        }
            return null;
    }
}
