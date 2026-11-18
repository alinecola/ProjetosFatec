package interfaces;

public interface Faturavel {

    double emitirDemonstrativoConsumo();

    default String gerarCabecalhoFatura() {
        return "=========================================\n" +
                "     EXTRATO DE CONSUMO - POUSADA        \n" +
                "=========================================\n";
    }
}