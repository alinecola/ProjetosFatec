package exception;

public class QuartoOcupadoException extends Exception {

    public QuartoOcupadoException() {
        super("O quarto selecionado já se encontra ocupado no momento.");
    }

    public QuartoOcupadoException(String message) {
        super(message);
    }
}