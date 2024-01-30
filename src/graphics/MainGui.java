package graphics;
import javax.swing.*;

/**
 * Fő framet megvalósító osztály
 */
public class MainGui extends JFrame {

    /**
     * Komponenseket összefogó panel panel
     */
    private GameGui gamegui;

    /**
     * Konstruktor, hozzáadja főpanelt
     */
    public MainGui() {
        super("MissingPiece");

        gamegui = new GameGui();
        this.add(gamegui);

        setSize(1600, 1000);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        setEnabled(true);
        setResizable(false);
    }


}
