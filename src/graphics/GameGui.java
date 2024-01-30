package graphics;

import model.*;
import javax.swing.*;
import java.awt.*;
import java.util.Map;

/**
 * A különböző paneleket összefogó főpanel
 */
public class GameGui extends JPanel{

    /**
     * Játékteret kirajzoló panel
     */
    public MapPanel mapPanel = new MapPanel();

    /**
     * Interakcóért felelős gombokat tartalmazó panel
     */
    public JPanel buttonPanel = new ButtonPanel();

    /**
     * Eredményjelzőt tartalmazó panel
     */
    public JPanel scorePanel = new ScorePanel();

    /**
     * Játékosok információit tartalmazó panel
     */
    public JPanel playerPanel = new PlayerPanel();

    /**
     * Layoutot állít be, és hozzáadja a különböző paneleket
     */
    public GameGui(){
        this.setVisible(true);
        this.setLayout(new BorderLayout());
        Game.SetMapPanel(mapPanel);
        Game.SetScorePanel((ScorePanel) scorePanel);

        this.add(scorePanel, BorderLayout.NORTH);
        this.add(mapPanel, BorderLayout.CENTER);


        JPanel southPanel = new JPanel(new GridLayout(1,2));
        southPanel.add(playerPanel);
        southPanel.add(buttonPanel);
        southPanel.setVisible(true);
        this.add(southPanel, BorderLayout.SOUTH);
    }
}
