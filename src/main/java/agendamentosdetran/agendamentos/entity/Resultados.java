package agendamentosdetran.agendamentos.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Table(name = "agendamento_condutor")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Resultados {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "renach")
    private String renach;

    @Column(name = "cpf")
    private String cpf;

    @Column(name = "nome")
    private String nome;

    @Column(name = "categoria")
    private String categoria;

    @Column(name = "av_id")
    private Long agendamentoVeiculoId;

    @Column(name = "data_agendamento")
    private Date dataAgendamento;

    @Column(name = "created")
    private Date created;

    private Integer status;

    @Column(name = "resultado_id")
    private Integer resultadoId;

    @Column(name = "data_cancelamento")
    private Date dataCancelamento;

    @Column(name = "usuario_cancelamento")
    private Integer usuarioCancelamento;

    @Column(name = "excedente")
    private String excedente;

    @Column(name = "usuario_cadastro")
    private String usuarioCadastrado;

    @Column(name = "banca_id")
    private Long bancaExaminadoraId;

    @Column(name = "bancahorario_id")
    private Integer bancaHorarioId;

    @Column(name = "horario_descricao")
    private String horarioDescricao;

    @Column(name = "municipio_id")
    private Integer municipioId;

    @Column(name = "cfc_cod")
    private Integer cfc;

    @Column(name = "usuario_perfil")
    private Integer usuarioPerfil;

    @Column(name = "deficiente")
    private Integer deficiente;

    @Column(name = "data_nascimento")
    private Date dataNascimento;

    @Column(name = "sexo")
    private String sexo;

    @Column(name = "dt_sync_confirmar_agendamento")
    private Date dataSyn;

}
