package graphics;

import javax.swing.*;
import java.awt.*;
import model.*;

/**
 * Jpanel, amely a játékosok pontjait jeleníti meg
 */
public class ScorePanel extends JPanel {

    /**
     * A szabotőrök pontjait megjelenítő label
     */
    private JLabel saboteurPointsLabel;

    /**
     * A szerelők pontjait megjelenítő label
     */
    private JLabel mechanicPointsLabel;

    /**
     * A cél pontszámot megjelenítő label
     */
    private JLabel targetScoreLabel;

    /**
     * A játékosok pontjait megjelenítő panel
     */
    public ScorePanel() {
        setLayout(new GridLayout(1, 3));
        setPreferredSize(new Dimension(800, 50));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 4));


        saboteurPointsLabel = createStyledLabel("Saboteur: " + Game.getSPoints(), Color.RED);
        mechanicPointsLabel = createStyledLabel("Mechanic: " + Game.getMPoints(), Color.BLUE);
        targetScoreLabel = createStyledLabel("Target Score: " + Game.getTargetScore(), Color.BLACK);

        saboteurPointsLabel.setHorizontalAlignment(JLabel.CENTER);
        mechanicPointsLabel.setHorizontalAlignment(JLabel.CENTER);
        targetScoreLabel.setHorizontalAlignment(JLabel.CENTER);

        saboteurPointsLabel.setFont(new Font("Arial", Font.BOLD, 24));
        mechanicPointsLabel.setFont(new Font("Arial", Font.BOLD, 24));
        targetScoreLabel.setFont(new Font("Arial", Font.BOLD, 24));

        add(mechanicPointsLabel);
        add(targetScoreLabel);
        add(saboteurPointsLabel);
    }

    /**
     * Stílussal ellátott labelt hoz létre
     * @param text
     * @param color
     * @return
     */
    private JLabel createStyledLabel(String text, Color color) {
        JLabel label = new JLabel(text);
        label.setForeground(color);
        return label;
    }

    /**
     * Eredmények frissítése
     */
    public void updateScores() {
        saboteurPointsLabel.setText("Saboteur: " + Game.getSPoints());
        mechanicPointsLabel.setText("Mechanic: " + Game.getMPoints());
    }

    /**
     * Komponens kirajzolása
     * @param g
     */
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        repaint();
    }
}
