package graphics;
import model.*;
import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.IntStream;

/**
 * Interakciót indító gombok panelje
 */
public class ButtonPanel extends JPanel {
    /**
     * Gomb amellyel lehet mozogni
     * */
    private JButton moveButton;
    /**
     * Gomb amellyel lehet interactolni
     * */
    private JButton interactButton;

    /**
     * Inicializáló függvény
     */
    public ButtonPanel() {
        setLayout(new GridLayout(1, 2));
        Proto proto = new Proto();
        StringBuilder commandHistory = new StringBuilder();

        moveButton = new JButton("Move");
        interactButton = new JButton("Interact");
        moveButton.setPreferredSize(new Dimension(200,100));
        moveButton.setFont(new Font("Arial", Font.BOLD,20));
        interactButton.setFont(new Font("Arial", Font.BOLD,20));
       // Proto.loadCommands();
        HashMap<String, Consumer<String>> commands = proto.getCommands();


        /**
         * Interact gomb lenyomásakor választható commandok felsorolása
         **/
        interactButton.addActionListener(e -> {
            String[] options = commands.keySet().toArray(new String[0]);
            Arrays.sort(options);
            String selection = (String) JOptionPane.showInputDialog(
                    null,
                    "Choose an action:",
                    "Interact options",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[0]
            );
            LinkedHashSet<Field> neighbourSet = Game.getCurrentPlayer().getField().getNeighbours();
            ArrayList<Field> neighbourList = new ArrayList<>(neighbourSet);
            String[] neighbourIndexes = IntStream.range(0, neighbourList.size())
                    .mapToObj(Integer::toString)
                    .toArray(String[]::new);
            System.out.println("Interact action selected: " + selection);
            if (selection != null) {
                Consumer<String> commandToDo = commands.get(selection);
                if(!selection.equals("Save")) Proto.commandHistory.append(selection);
                if (commandToDo != null) {
                    if(selection.equals("AdjustPump")){
                        String n1 = (String) JOptionPane.showInputDialog(
                                null,
                                "Choose an action:",
                                "Neighbours",
                                JOptionPane.QUESTION_MESSAGE,
                                null,
                                neighbourIndexes,
                                neighbourIndexes[0]);
                        String n2 = (String) JOptionPane.showInputDialog(
                                null,
                                "Choose an action:",
                                "Neighbours",
                                JOptionPane.QUESTION_MESSAGE,
                                null,
                                neighbourIndexes,
                                neighbourIndexes[0]);
                        if(!Objects.equals(n1, n2)){
                            commandToDo.accept(n1+" "+ n2);}
                        else {
                            System.out.println("You chose the same neighbours");
                        }
                    }
                    else if(selection.equals("PickUpPipe")){
                        String toPickUp = (String) JOptionPane.showInputDialog(
                                null,
                                "Choose an action:",
                                "Neighbours",
                                JOptionPane.QUESTION_MESSAGE,
                                null,
                                neighbourIndexes,
                                neighbourIndexes[0]

                        );
                        commandToDo.accept(toPickUp);
                    }
                    else{
                        commandToDo.accept(null);
                    }
                }
            }
            ((GameGui)this.getParent().getParent()).mapPanel.repaint();
        });


        /**
         * Move gomb lenyomásakor választható indexek jelennek meg, hogy hova léphet tovább a játékos.
         **/
        moveButton.addActionListener(e -> {
            LinkedHashSet<Field> neighbourSet = Game.getCurrentPlayer().getField().getNeighbours();
            ArrayList<Field> neighbourList = new ArrayList<>(neighbourSet);
            String[] neighbourIndexes = IntStream.range(0, neighbourList.size())
                    .mapToObj(Integer::toString)
                    .toArray(String[]::new);
            String selection = (String) JOptionPane.showInputDialog(
                    null,
                    "Choose an action:",
                    "Neighbours",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    neighbourIndexes,
                    neighbourIndexes[0]
            );
            System.out.println("Move action selected: " + selection);
            if (selection != null) {
                Consumer<String> moveCommand = commands.get("MoveTo");
                Proto.commandHistory.append("MoveTo ").append(selection).append("\n");
                if (moveCommand != null) {
                    int selectedIndex = Integer.parseInt(selection);
                    System.out.println(selectedIndex);
                    moveCommand.accept(Integer.toString(selectedIndex));
                }
            }
            ((GameGui)this.getParent().getParent()).mapPanel.repaint();
        });

        add(moveButton);
        add(interactButton);
    }
}
