import javax.swing.*;

public class TicTacToeButton extends JButton {
    private char state;

    public TicTacToeButton() {
        this.state = ' ';
    }

    public char getState() {
        return state;
    }

    public void setState(char newState) {
        state = newState;
        setText(Character.toString(newState));
        setEnabled(false);
    }
}

