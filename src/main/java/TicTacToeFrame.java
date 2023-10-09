import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TicTacToeFrame extends JFrame {
    private JButton[][] buttons;
    private JButton quitButton;
    private char currentPlayer;
    private boolean gameOver;
    private int moves;

    public TicTacToeFrame() {
        setTitle("Tic Tac Toe");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        buttons = new JButton[3][3];
        currentPlayer = 'X';
        gameOver = false;
        moves = 0;

        JPanel boardPanel = new JPanel(new GridLayout(3, 3));
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                buttons[row][col] = new JButton("");
                buttons[row][col].setFont(new Font("Arial", Font.PLAIN, 48));
                buttons[row][col].setFocusPainted(false);

                int finalRow = row;
                int finalCol = col;

                buttons[row][col].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (!gameOver) {
                            if (buttons[finalRow][finalCol].getText().isEmpty()) {
                                buttons[finalRow][finalCol].setText(Character.toString(currentPlayer));
                                moves++;
                                if (checkWin(finalRow, finalCol)) {
                                    showWinDialog(currentPlayer);
                                    gameOver = true;
                                    promptPlayAgain();
                                } else if (moves == 9) {
                                    showNotFullBoardTieDialog();
                                    gameOver = true;
                                    promptPlayAgain();
                                } else {
                                    togglePlayer();
                                }
                            } else {
                                // Display an error message for an invalid move
                                JOptionPane.showMessageDialog(TicTacToeFrame.this, "Invalid move. Cell is already chosen.", "Invalid Move", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    }
                });

                boardPanel.add(buttons[row][col]);
            }
        }

        quitButton = new JButton("Quit");
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to quit?", "Quit", JOptionPane.YES_NO_OPTION);
                if (choice == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });

        add(boardPanel, BorderLayout.CENTER);
        add(quitButton, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void togglePlayer() {
        currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
    }

    private boolean checkWin(int row, int col) {
        // Implement your win-checking logic here
        if (checkRow(row) || checkColumn(col) || checkDiagonals(row, col)) {
            return true;
        }
        return false;
    }

    private boolean checkRow(int row) {
        char symbol = currentPlayer;
        return buttons[row][0].getText().equals(Character.toString(symbol)) &&
                buttons[row][1].getText().equals(Character.toString(symbol)) &&
                buttons[row][2].getText().equals(Character.toString(symbol));
    }

    private boolean checkColumn(int col) {
        char symbol = currentPlayer;
        return buttons[0][col].getText().equals(Character.toString(symbol)) &&
                buttons[1][col].getText().equals(Character.toString(symbol)) &&
                buttons[2][col].getText().equals(Character.toString(symbol));
    }

    private boolean checkDiagonals(int row, int col) {
        char symbol = currentPlayer;
        if ((row == col) || (row + col == 2)) {
            return checkMainDiagonal(symbol) || checkAntiDiagonal(symbol);
        }
        return false;
    }

    private boolean checkMainDiagonal(char symbol) {
        return buttons[0][0].getText().equals(Character.toString(symbol)) &&
                buttons[1][1].getText().equals(Character.toString(symbol)) &&
                buttons[2][2].getText().equals(Character.toString(symbol));
    }

    private boolean checkAntiDiagonal(char symbol) {
        return buttons[0][2].getText().equals(Character.toString(symbol)) &&
                buttons[1][1].getText().equals(Character.toString(symbol)) &&
                buttons[2][0].getText().equals(Character.toString(symbol));
    }

    private void showWinDialog(char player) {
        JOptionPane.showMessageDialog(this, "Player " + player + " wins!", "Winner", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showNotFullBoardTieDialog() {
        JOptionPane.showMessageDialog(this, "It's a tie! The board is not full.", "Tie Game", JOptionPane.INFORMATION_MESSAGE);
    }

    private void promptPlayAgain() {
        int playAgain = JOptionPane.showConfirmDialog(this, "Do you want to play another game?", "Play Again", JOptionPane.YES_NO_OPTION);
        if (playAgain == JOptionPane.YES_OPTION) {
            resetGame();
        } else {
            System.exit(0);
        }
    }

    private void resetGame() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                buttons[row][col].setText("");
            }
        }
        currentPlayer = 'X';
        gameOver = false;
        moves = 0;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TicTacToeFrame frame = new TicTacToeFrame();
        });
    }
}
