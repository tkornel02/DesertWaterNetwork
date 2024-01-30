package graphics;

import model.Game;
import model.Player;

import javax.swing.*;
import java.awt.*;

/**
 * A játékosokhoz kapcsolódó információkat jeleníti meg
 */
public class PlayerPanel extends JPanel {

    /**
     * A játékos nevét kiíró label
     */
    private JLabel nameLabel;

    /**
     * A játékos paramétereit kiíró label
     */
    private JLabel parameterLabel;

    /**
     * Kirajzolja a játékosokhoz kapcsolódó információkat: Játékos neve és az Inventoryjának a tartalma
     */
    public PlayerPanel() {
        setLayout(new GridLayout(2, 1));
        setPreferredSize(new Dimension(200, 100));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 4));

        nameLabel = new JLabel("Player Name: " + Game.getCurrentPlayer().getName());
        parameterLabel = new JLabel("Player Parameter: ");
        nameLabel.setHorizontalAlignment(JLabel.CENTER);
        parameterLabel.setHorizontalAlignment(JLabel.CENTER);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 20));
        parameterLabel.setFont(new Font("Arial", Font.BOLD, 20));

        add(nameLabel);
        add(parameterLabel);
    }

    /**
     * Frissíti a játékosokról szóló információs mezőt
     */
    public void updatePlayerInfo() {
        Player currentPlayer = Game.getCurrentPlayer();
        nameLabel.setText("Player Name: " + currentPlayer.getName());
        parameterLabel.setText("Player Parameter: " + currentPlayer.getParameter()); // Replace "getParameter()" with the actual method to retrieve the player parameter
    }

    /**
     * Komponens kirajzolása
     * @param g the <code>Graphics</code> object to protect
     */
    @Override
    public void paintComponent(Graphics g){
        updatePlayerInfo();
        super.paintComponent(g);
        repaint();

    }
}
