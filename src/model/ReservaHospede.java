package model;

import interfaces.Faturavel;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class ReservaHospede implements Comparable<ReservaHospede>, Faturavel {

    private int codigoReserva;
    private String nomeHospede;
    private int numeroQuarto;
    private LocalDate dataCheckIn;
    private LocalDate dataCheckOut;
    private double valorDiaria;
    private double consumoExtra;
    private String status;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public ReservaHospede() {
        this.consumoExtra = 0.0;
        this.status = "RESERVADA";
    }

    public ReservaHospede(int codigoReserva, String nomeHospede, int numeroQuarto,
                          LocalDate dataCheckIn, LocalDate dataCheckOut, double valorDiaria) {
        this.codigoReserva = codigoReserva;
        this.nomeHospede = nomeHospede;
        this.numeroQuarto = numeroQuarto;
        this.dataCheckIn = dataCheckIn;
        this.dataCheckOut = dataCheckOut;
        this.valorDiaria = valorDiaria;
        this.consumoExtra = 0.0;
        this.status = "RESERVADA";
    }

    // Getters e Setters
    public int getCodigoReserva() {
        return codigoReserva;
    }

    public void setCodigoReserva(int codigoReserva) {
        this.codigoReserva = codigoReserva;
    }

    public String getNomeHospede() {
        return nomeHospede;
    }

    public void setNomeHospede(String nomeHospede) {
        this.nomeHospede = nomeHospede;
    }

    public int getNumeroQuarto() {
        return numeroQuarto;
    }

    public void setNumeroQuarto(int numeroQuarto) {
        this.numeroQuarto = numeroQuarto;
    }

    public LocalDate getDataCheckIn() {
        return dataCheckIn;
    }

    public void setDataCheckIn(LocalDate dataCheckIn) {
        this.dataCheckIn = dataCheckIn;
    }

    public LocalDate getDataCheckOut() {
        return dataCheckOut;
    }

    public void setDataCheckOut(LocalDate dataCheckOut) {
        this.dataCheckOut = dataCheckOut;
    }

    public double getValorDiaria() {
        return valorDiaria;
    }

    public void setValorDiaria(double valorDiaria) {
        this.valorDiaria = valorDiaria;
    }

    public double getConsumoExtra() {
        return consumoExtra;
    }

    public void setConsumoExtra(double consumoExtra) {
        this.consumoExtra = consumoExtra;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Métodos de Negócio da Entidade
    public void adicionarConsumo(double valor) {
        if (valor > 0) {
            this.consumoExtra += valor;
        }
    }

    public long calcularQuantidadeDiarias() {
        if (dataCheckIn != null && dataCheckOut != null) {
            long dias = ChronoUnit.DAYS.between(dataCheckIn, dataCheckOut);
            return dias > 0 ? dias : 1; // Mínimo de 1 diária cobrada
        }
        return 1;
    }

    @Override
    public double emitirDemonstrativoConsumo() {
        long diarias = calcularQuantidadeDiarias();
        return (diarias * valorDiaria) + consumoExtra;
    }

    public String gerarExtratoDetalhado() {
        long diarias = calcularQuantidadeDiarias();
        double totalDiarias = diarias * valorDiaria;
        double totalGeral = emitirDemonstrativoConsumo();

        return gerarCabecalhoFatura() +
                String.format("Código da Reserva : %d\n", codigoReserva) +
                String.format("Hóspede           : %s\n", nomeHospede) +
                String.format("Quarto Nº         : %d\n", numeroQuarto) +
                String.format("Check-in          : %s\n", dataCheckIn.format(FORMATTER)) +
                String.format("Check-out         : %s\n", dataCheckOut.format(FORMATTER)) +
                String.format("Status            : %s\n", status) +
                "-----------------------------------------\n" +
                String.format("Diárias (%d x R$ %.2f) : R$ %.2f\n", diarias, valorDiaria, totalDiarias) +
                String.format("Consumo Extra         : R$ %.2f\n", consumoExtra) +
                "-----------------------------------------\n" +
                String.format("TOTAL A PAGAR         : R$ %.2f\n", totalGeral) +
                "=========================================\n";
    }

    @Override
    public int compareTo(ReservaHospede outra) {
        int comparacaoData = this.dataCheckIn.compareTo(outra.dataCheckIn);
        if (comparacaoData != 0) {
            return comparacaoData;
        }
        return this.nomeHospede.compareToIgnoreCase(outra.nomeHospede);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReservaHospede that = (ReservaHospede) o;
        return codigoReserva == that.codigoReserva;
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigoReserva);
    }

    @Override
    public String toString() {
        return String.format("[Cód: %d] Quarto %d | Hóspede: %s | Check-in: %s | Check-out: %s | Status: %s | Total Atual: R$ %.2f",
                codigoReserva,
                numeroQuarto,
                nomeHospede,
                dataCheckIn.format(FORMATTER),
                dataCheckOut.format(FORMATTER),
                status,
                emitirDemonstrativoConsumo());
    }
}