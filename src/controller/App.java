package controller;

import view.TelaPousada;
import javax.swing.SwingUtilities;
public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TelaPousada tela = new TelaPousada();
            tela.setVisible(true);
        });
    }
}