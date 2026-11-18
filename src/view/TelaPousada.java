package view;

import exception.QuartoOcupadoException;
import model.ReservaHospede;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class TelaPousada extends JFrame implements ActionListener {

    // Estrutura de persistência 100% em memória RAM
    private final List<ReservaHospede> listaReservas = new ArrayList<>();
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    // Componentes de entrada
    private JTextField txtCodigo;
    private JTextField txtHospede;
    private JTextField txtQuarto;
    private JTextField txtCheckIn;
    private JTextField txtCheckOut;
    private JTextField txtValorDiaria;
    private JTextField txtConsumoExtra;

    // Botões de ação
    private JButton btnReservar;
    private JButton btnCheckIn;
    private JButton btnLancarConsumo;
    private JButton btnEncerrar;
    private JButton btnPesquisar;
    private JButton btnOrdenarListar;
    private JButton btnSair;

    // Área de saída / relatórios
    private JTextArea txtAreaRelatorio;

    public TelaPousada() {
        super("Gestão de Quartos e Reservas - Pousada");
        inicializarComponentes();
        configurarJanela();
    }

    private void inicializarComponentes() {
        setLayout(new BorderLayout(10, 10));

        // Painel Superior: Formulário de Dados
        JPanel painelFormulario = new JPanel(new GridLayout(4, 4, 8, 8));
        painelFormulario.setBorder(BorderFactory.createTitledBorder("Dados da Reserva e Estadia"));

        painelFormulario.add(new JLabel("Código da Reserva:"));
        txtCodigo = new JTextField();
        painelFormulario.add(txtCodigo);

        painelFormulario.add(new JLabel("Número do Quarto:"));
        txtQuarto = new JTextField();
        painelFormulario.add(txtQuarto);

        painelFormulario.add(new JLabel("Nome do Hóspede:"));
        txtHospede = new JTextField();
        painelFormulario.add(txtHospede);

        painelFormulario.add(new JLabel("Valor da Diária (R$):"));
        txtValorDiaria = new JTextField();
        painelFormulario.add(txtValorDiaria);

        painelFormulario.add(new JLabel("Data Check-in (dd/MM/aaaa):"));
        txtCheckIn = new JTextField();
        painelFormulario.add(txtCheckIn);

        painelFormulario.add(new JLabel("Data Check-out (dd/MM/aaaa):"));
        txtCheckOut = new JTextField();
        painelFormulario.add(txtCheckOut);

        painelFormulario.add(new JLabel("Consumo Extra (R$):"));
        txtConsumoExtra = new JTextField();
        painelFormulario.add(txtConsumoExtra);

        // Preenchendo célula vazia do GridLayout
        painelFormulario.add(new JLabel(""));
        painelFormulario.add(new JLabel(""));

        add(painelFormulario, BorderLayout.NORTH);

        // Painel Central: Área de Texto com Scroll para Extratos e Listagens
        txtAreaRelatorio = new JTextArea(15, 60);
        txtAreaRelatorio.setEditable(false);
        txtAreaRelatorio.setFont(new Font("Monospaced", Font.PLAIN, 13));
        JScrollPane scrollPane = new JScrollPane(txtAreaRelatorio);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Extratos e Listagem de Reservas em Memória"));
        add(scrollPane, BorderLayout.CENTER);

        // Painel Inferior: Botões de Operação
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 8));

        btnReservar = new JButton("1. Cadastrar Reserva");
        btnCheckIn = new JButton("2. Realizar Check-in");
        btnLancarConsumo = new JButton("3. Lançar Consumo");
        btnEncerrar = new JButton("4. Encerrar / Extrato");
        btnPesquisar = new JButton("Pesquisar Cód.");
        btnOrdenarListar = new JButton("Listar Ordenado");
        btnSair = new JButton("Sair");

        // Registrando ActionListeners
        btnReservar.addActionListener(this);
        btnCheckIn.addActionListener(this);
        btnLancarConsumo.addActionListener(this);
        btnEncerrar.addActionListener(this);
        btnPesquisar.addActionListener(this);
        btnOrdenarListar.addActionListener(this);
        btnSair.addActionListener(this);

        painelBotoes.add(btnReservar);
        painelBotoes.add(btnCheckIn);
        painelBotoes.add(btnLancarConsumo);
        painelBotoes.add(btnEncerrar);
        painelBotoes.add(btnPesquisar);
        painelBotoes.add(btnOrdenarListar);
        painelBotoes.add(btnSair);

        add(painelBotoes, BorderLayout.SOUTH);
    }

    private void configurarJanela() {
        setSize(860, 540);
        setLocationRelativeTo(null); // Centraliza na tela
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        // Tratamento de fechamento com WindowListener
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                confirmarSaida();
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object origem = e.getSource();

        if (origem == btnReservar) {
            cadastrarReserva();
        } else if (origem == btnCheckIn) {
            realizarCheckIn();
        } else if (origem == btnLancarConsumo) {
            lancarConsumoExtra();
        } else if (origem == btnEncerrar) {
            encerrarEstadia();
        } else if (origem == btnPesquisar) {
            pesquisarReserva();
        } else if (origem == btnOrdenarListar) {
            listarOrdenado();
        } else if (origem == btnSair) {
            confirmarSaida();
        }
    }

    private void cadastrarReserva() {
        try {
            int codigo = Integer.parseInt(txtCodigo.getText().trim());
            int quarto = Integer.parseInt(txtQuarto.getText().trim());
            String hospede = txtHospede.getText().trim();
            double valorDiaria = Double.parseDouble(txtValorDiaria.getText().trim().replace(",", "."));
            LocalDate checkIn = LocalDate.parse(txtCheckIn.getText().trim(), formatter);
            LocalDate checkOut = LocalDate.parse(txtCheckOut.getText().trim(), formatter);

            if (hospede.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Informe o nome do hóspede.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (checkOut.isBefore(checkIn)) {
                JOptionPane.showMessageDialog(this, "A data de Check-out não pode ser anterior ao Check-in.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Verifica se já existe reserva com mesmo código
            if (buscarPorCodigo(codigo) != null) {
                JOptionPane.showMessageDialog(this, "Já existe uma reserva com o código " + codigo + ".", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Regra de negócio: valida disponibilidade do quarto (lança QuartoOcupadoException)
            validarDisponibilidadeQuarto(quarto);

            ReservaHospede novaReserva = new ReservaHospede(codigo, hospede, quarto, checkIn, checkOut, valorDiaria);
            listaReservas.add(novaReserva);

            JOptionPane.showMessageDialog(this, "Reserva cadastrada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            limparCampos();
            listarOrdenado();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Erro de formato numérico: verifique Código, Quarto e Valor da Diária.", "Erro de Entrada", JOptionPane.ERROR_MESSAGE);
        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(this, "Formato de data inválido! Utilize o padrão dd/MM/aaaa (ex: 20/11/2026).", "Erro de Data", JOptionPane.ERROR_MESSAGE);
        } catch (QuartoOcupadoException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Quarto Ocupado", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void validarDisponibilidadeQuarto(int numeroQuarto) throws QuartoOcupadoException {
        for (ReservaHospede r : listaReservas) {
            if (r.getNumeroQuarto() == numeroQuarto && !r.getStatus().equals("ENCERRADA")) {
                throw new QuartoOcupadoException("O Quarto nº " + numeroQuarto + " já está ocupado ou reservado pelo hóspede " + r.getNomeHospede() + ".");
            }
        }
    }

    private void realizarCheckIn() {
        try {
            int codigo = Integer.parseInt(txtCodigo.getText().trim());
            ReservaHospede reserva = buscarPorCodigo(codigo);

            if (reserva == null) {
                JOptionPane.showMessageDialog(this, "Reserva não encontrada para o código informado.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (reserva.getStatus().equals("ENCERRADA")) {
                JOptionPane.showMessageDialog(this, "Esta reserva já foi encerrada.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            reserva.setStatus("CHECK-IN REALIZADO");
            JOptionPane.showMessageDialog(this, "Check-in confirmado para " + reserva.getNomeHospede() + " no Quarto " + reserva.getNumeroQuarto() + "!", "Check-in", JOptionPane.INFORMATION_MESSAGE);
            listarOrdenado();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Informe um Código de Reserva numérico válido para realizar o Check-in.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void lancarConsumoExtra() {
        try {
            int codigo = Integer.parseInt(txtCodigo.getText().trim());
            double valorConsumo = Double.parseDouble(txtConsumoExtra.getText().trim().replace(",", "."));

            if (valorConsumo <= 0) {
                JOptionPane.showMessageDialog(this, "Informe um valor de consumo maior que zero.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            ReservaHospede reserva = buscarPorCodigo(codigo);
            if (reserva == null) {
                JOptionPane.showMessageDialog(this, "Reserva não encontrada.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (reserva.getStatus().equals("ENCERRADA")) {
                JOptionPane.showMessageDialog(this, "Não é possível lançar consumo em uma reserva já encerrada.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            reserva.adicionarConsumo(valorConsumo);
            txtConsumoExtra.setText("");
            JOptionPane.showMessageDialog(this, String.format("Consumo de R$ %.2f adicionado à reserva %d!", valorConsumo, codigo), "Consumo Lançado", JOptionPane.INFORMATION_MESSAGE);
            txtAreaRelatorio.setText(reserva.gerarExtratoDetalhado());

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Informe valores numéricos válidos para o Código da Reserva e Consumo Extra.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void encerrarEstadia() {
        try {
            int codigo = Integer.parseInt(txtCodigo.getText().trim());
            ReservaHospede reserva = buscarPorCodigo(codigo);

            if (reserva == null) {
                JOptionPane.showMessageDialog(this, "Reserva não encontrada.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (reserva.getStatus().equals("ENCERRADA")) {
                JOptionPane.showMessageDialog(this, "Esta estadia já está encerrada.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            reserva.setStatus("ENCERRADA");
            String extrato = reserva.gerarExtratoDetalhado();
            txtAreaRelatorio.setText(extrato);

            JOptionPane.showMessageDialog(this,
                    String.format("Estadia encerrada e quarto %d liberado!\nTotal Faturado: R$ %.2f",
                            reserva.getNumeroQuarto(), reserva.emitirDemonstrativoConsumo()),
                    "Check-out Concluído", JOptionPane.INFORMATION_MESSAGE);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Informe o Código da Reserva para encerrar a estadia.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void pesquisarReserva() {
        try {
            int codigo = Integer.parseInt(txtCodigo.getText().trim());
            ReservaHospede reserva = buscarPorCodigo(codigo);

            if (reserva == null) {
                JOptionPane.showMessageDialog(this, "Nenhuma reserva encontrada com o código " + codigo + ".", "Pesquisa", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            txtQuarto.setText(String.valueOf(reserva.getNumeroQuarto()));
            txtHospede.setText(reserva.getNomeHospede());
            txtValorDiaria.setText(String.valueOf(reserva.getValorDiaria()));
            txtCheckIn.setText(reserva.getDataCheckIn().format(formatter));
            txtCheckOut.setText(reserva.getDataCheckOut().format(formatter));

            txtAreaRelatorio.setText(reserva.gerarExtratoDetalhado());

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Digite um Código numérico válido para pesquisar.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void listarOrdenado() {
        if (listaReservas.isEmpty()) {
            txtAreaRelatorio.setText("Nenhuma reserva cadastrada na memória no momento.");
            return;
        }

        // Ordenação utilizando Comparable<ReservaHospede>
        Collections.sort(listaReservas);

        StringBuilder sb = new StringBuilder();
        sb.append("=== LISTA DE RESERVAS ORDENADA POR DATA DE CHECK-IN ===\n\n");
        for (ReservaHospede r : listaReservas) {
            sb.append(r.toString()).append("\n");
        }
        txtAreaRelatorio.setText(sb.toString());
    }

    private ReservaHospede buscarPorCodigo(int codigo) {
        for (ReservaHospede r : listaReservas) {
            if (r.getCodigoReserva() == codigo) {
                return r;
            }
        }
        return null;
    }

    private void limparCampos() {
        txtCodigo.setText("");
        txtQuarto.setText("");
        txtHospede.setText("");
        txtValorDiaria.setText("");
        txtCheckIn.setText("");
        txtCheckOut.setText("");
        txtConsumoExtra.setText("");
        txtCodigo.requestFocus();
    }

    private void confirmarSaida() {
        int opcao = JOptionPane.showConfirmDialog(this,
                "Deseja realmente encerrar o sistema? Os dados em memória serão perdidos.",
                "Confirmar Saída", JOptionPane.YES_NO_OPTION);
        if (opcao == JOptionPane.YES_OPTION) {
            dispose();
            System.exit(0);
        }
    }
}