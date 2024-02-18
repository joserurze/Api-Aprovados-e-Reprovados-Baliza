package agendamentosdetran.agendamentos.entity;

import lombok.Data;


@Data
public class ResultadosDTO {

    private String dataAgendamento;
    private String categoria;
    private Long contagem;

    public ResultadosDTO(String dataAgendamento, String categoria, Long contagem) {
        this.dataAgendamento = dataAgendamento;
        this.categoria = categoria;
        this.contagem=contagem;
    }
    public ResultadosDTO() {
    }

}
