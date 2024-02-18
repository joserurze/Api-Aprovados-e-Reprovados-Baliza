package agendamentosdetran.agendamentos.repository;

import agendamentosdetran.agendamentos.entity.Resultados;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;


public interface AgendamentoCondutorRepository extends JpaRepository<Resultados, Long> {

        @Query(nativeQuery = true, value = "select data_agendamento,sum(contagem) as contagem,categoria from (\n" +
                "select month(data_agendamento) as data_agendamento,count(*) as contagem,categoria From agendamento_condutor where \n" +
                " data_agendamento between :dataInicial1 and :dataFinal1 \n" +
                " and status=1 and \n" +
                " resultado_id in (1,2) \n" +
                " and municipio_id in (1219,1220) and sexo is not null and data_nascimento is not null \n" +
                " group by categoria,data_agendamento) as consulta group by categoria,data_agendamento")
        List<Object[]> findByquantityResults(String dataInicial1, String dataFinal1);


        @Query(nativeQuery = true, value = "SELECT\n" +
                " resultado,\n" +
                " COUNT(*) AS contagem,\n" +
                " (COUNT(*) * 100.0 / SUM(COUNT(*)) OVER ()) AS percentual \n" +
                " FROM\n" +
                " (\n" +
                " SELECT \n" +
                " CASE\n" +
                " WHEN resultado_id = 1 THEN 'Aprovado' \n" +
                " ELSE 'Reprovado'\n" +
                " END AS resultado\n" +
                " FROM\n" +
                " agendamento_condutor\n" +
                " WHERE\n" +
                " data_agendamento BETWEEN :dataInicial AND :dataFinal\n" +
                " AND status = 1\n" +
                " AND resultado_id IN (1, 2)\n" +
                " AND municipio_id IN (1219, 1220)\n" +
                " AND sexo IS NOT NULL\n" +
                " AND data_nascimento IS NOT NULL\n" +
                " ) AS consulta\n" +
                " GROUP BY\n" +
                " resultado;")
        List<Object[]> findapprovedFailed(Date dataInicial, Date dataFinal);

}
