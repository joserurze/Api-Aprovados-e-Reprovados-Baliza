package agendamentosdetran.agendamentos.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TotalGeralDto {

    private String resultado;
    private Long totalGeral;

    public TotalGeralDto() {
        this.resultado=resultado;
        this.totalGeral=totalGeral;
    }
}
