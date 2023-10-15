package Test;

import Model.DAO.TGDAO;
import Model.DTO.TGDTO;

public class TGTest {
    public static void main(String[] args) {
        TGDAO tgdao = new TGDAO();

        System.out.println(tgdao.getTgPorIdAluno(12L));
        tgdao.addTg(new TGDTO("Relatório Técnico", null, null, "Oracle", 19L));
        System.out.println(tgdao.getAllTgs());

    }
}
