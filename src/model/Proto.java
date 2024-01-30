package model;

import java.io.*;
import java.nio.channels.Pipe;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;
import java.util.function.Consumer;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 * Prototípus szakasz tesztelő osztálya.
 */
public class Proto {
    /**
     * A parancsokat tartalmazó HashMap.
     */
    static HashMap<String, Consumer<String>> commands;
    /**
     * A parancsokat tartalmazó StringBuilder, mely a kimentés során használatos
     */
    public static StringBuilder commandHistory = new StringBuilder("");

    public Proto() {
        commands = new HashMap<String, Consumer<String>>();
        loadCommands();
    }
    public HashMap<String, Consumer<String>> getCommands()
    {
        return commands;
    }
    /**
     * A bemeneti nyelv feltöltése
     */
    public static void loadCommands() {
        /*
        commands.put("create_mechanic", (params) ->{ //create_mechanic <name> <index of field>
            String[] parameters = params.split(" ");
            if (parameters.length == 2) {
                System.out.println(Game.getFields().get(Integer.parseInt(parameters[1])));
                Game.getPlayers().add((new Mechanic(parameters[0], Game.getFields().get(Integer.parseInt(parameters[1])))));
            }
            else{
                UI.incorrectParams("create_mechanic <name> <index of field>");
            }
        });
        commands.put("create_saboteur", (params) ->{ //create_saboteur <name> <index of field>
            String[] parameters = params.split(" ");
            if (parameters.length == 2) {
                Game.getPlayers().add((new Saboteur(parameters[0], Game.getFields().get(Integer.parseInt(parameters[1])))));
            }
            else{
                UI.incorrectParams("create_saboteur <name> <index of field>");
            }
        });


        commands.put("create_cistern", (params) ->{ //create_cistern <index of source>
            String[] parameters = params.split(" ");
            if (parameters.length == 1) {
                CisternField c = new CisternField();
                c.AddNeighbour(Game.getFields().get(Integer.parseInt(parameters[0]))); //szomszedsag beallitasa
                Game.getFields().get(Integer.parseInt(parameters[0])).setDestination(c);
                Game.getFields().add(c);
                UI.printMap(Game.getFields().size(),c);
            }
            else{
                UI.incorrectParams("create_cistern <index of source>");
            }
        });

        commands.put("create_source", (params) ->{//create_source <destination of source>
            String[] parameters = params.split(" ");
            if (parameters.length == 1) {
                SourceField s = new SourceField();
                s.AddNeighbour(Game.getFields().get(Integer.parseInt(parameters[0]))); //szomszedsag beallitasa
                Game.getFields().get(Integer.parseInt(parameters[0])).setSource(s);
                Game.getFields().add(s);
                UI.printMap(Game.getFields().size(),s);
            }
            else{
                UI.incorrectParams("create_source <destination of source>");
            }
        });

        commands.put("create_pipe", (params) ->{
            String[] parameters = params.split(" ");//create_pipe <index of source> <index of destination>
            if (parameters.length == 2) {
                PipeField pipe = new PipeField();
                pipe.AddNeighbour(Game.getFields().get(Integer.parseInt(parameters[0])));
                pipe.AddNeighbour(Game.getFields().get(Integer.parseInt(parameters[1])));
                pipe.setSource(Game.getFields().get(Integer.parseInt(parameters[0])));
                pipe.setDestination(Game.getFields().get(Integer.parseInt(parameters[1])));
                Game.getFields().add(pipe);
                UI.printMap(Game.getFields().size(),pipe);
            }
            else if (parameters.length == 1){
                PipeField pipe = new PipeField();
                pipe.AddNeighbour(Game.getFields().get(Integer.parseInt(parameters[0])));
                pipe.setSource(Game.getFields().get(Integer.parseInt(parameters[0])));
                Game.getFields().add(pipe);
                UI.printMap(Game.getFields().size(),pipe);
            }
            else{
                UI.incorrectParams("create_pipe <index of source> <index of destination>");
            }
        });

        commands.put("create_pump", (params) ->{
            String[] parameters = params.split(" ");//create_pump <index of source> <index of destination>
            if (parameters.length == 2) {
                PumpField pump = new PumpField();
                pump.AddNeighbour(Game.getFields().get(Integer.parseInt(parameters[0])));
                pump.AddNeighbour(Game.getFields().get(Integer.parseInt(parameters[1])));
                pump.setSource(Game.getFields().get(Integer.parseInt(parameters[0])));
                pump.setDestination(Game.getFields().get(Integer.parseInt(parameters[1])));
                Game.getFields().add(pump);
                UI.printMap(Game.getFields().size(),pump);
            }
            else if (parameters.length == 1){
                PumpField pump = new PumpField();
                pump.AddNeighbour(Game.getFields().get(Integer.parseInt(parameters[0])));
                pump.setSource(Game.getFields().get(Integer.parseInt(parameters[0])));
                Game.getFields().add(pump);
                UI.printMap(Game.getFields().size(),pump);
            }
            else{
                UI.incorrectParams("create_pump <index of source> <index of destination>");
            }});
         */

        /*
        commands.put("execute_breakpump", (params) ->{//execute_breakpump
             for(int i = 0; i < 10; i++){
                 Game.nextRound();
             }
        });
         */

        commands.put("MoveTo", (params) ->{ //execute_moveto <index of neighbour>
                String[] parameters = params.split(" ");
                System.out.println(Arrays.toString(parameters));
                if (parameters.length == 1) {
                    Game.getCurrentPlayer().moveTo(Game.getCurrentPlayer().getField().getNeighbourByIdx(Integer.parseInt(parameters[0])));
                }
                else {
                    UI.incorrectParams("MoveTo <index of neighbour>");
                }
            }
        );


        commands.put("Fix", (params) ->{ //mechanic_fix <field>
                Game.getCurrentPlayer().fix();
        });

        commands.put("InstallPipe", (params) ->{   //mechanic_installpipe
                Game.getCurrentPlayer().installPipe();
        });
        commands.put("InstallPump", (params) ->{   //mechanic_installpump
                Game.getCurrentPlayer().installPump();
        });

        commands.put("PickUpPipe", (params) ->{ //addpipe
            String[] parameters = params.split(" "); //TODO: valahogy cast nelkul? vagy baj egyaltalan a cast?
            Game.getCurrentPlayer().pickUpPipe((PipeField) Game.getCurrentPlayer().getField().getNeighbourByIdx(Integer.parseInt(parameters[0])));
        });

//        commands.put("PickUpPump", (params) ->{ //addpump
//                Mechanic m = (Mechanic) Game.getCurrentPlayer();
//                m.setPumpInventory(1);
//        });


        commands.put("MakePipeSticky", (params) ->{ //mechanic_makepipesticky
            Game.getCurrentPlayer().makePipeSticky();

        });
        commands.put("MakePipeSlippery", (params) ->{ //saboteur_makepipeslippery
            Game.getCurrentPlayer().makePipeSlippery();
        });

        /*
        commands.put("cisternfield_producepipe", (params) ->{ //cisternfield producepipe <index>
            String[] parameters = params.split(" ");
            if (parameters.length == 1) {
                PipeField nPipe = new PipeField();
                Game.getFields().get(Integer.parseInt(parameters[0])).placePipe(nPipe);
            }
            else{
                UI.incorrectParams("cisternfield_producepipe <index>");
            }
        });
         */

        commands.put("PickUpPump", (params) -> {//cisternfield_pickuppump
                Game.getCurrentPlayer().pickUpPump();
        });

        /*
        commands.put("sourcefield_flowwater", (params) ->{// sourcefield_flowwater
            Game.nextRound();
        });
         */

        commands.put("PuncturePipe", (params) ->{//pipefield_puncturepipe
            Game.getCurrentPlayer().puncturePipe();
        });

        commands.put("AdjustPump", (params) ->{//pumpfield_adjust <source> <destination>
            String[] parameters = params.split(" ");
            if (parameters.length == 2) {
                Game.getCurrentPlayer().changePumpSettings((PipeField) Game.getCurrentPlayer().getField().getNeighbourByIdx(Integer.parseInt(parameters[0])),(PipeField) Game.getCurrentPlayer().getField().getNeighbourByIdx(Integer.parseInt(parameters[1])));
            }
            else{
                UI.incorrectParams("pumpfield_adjust <source> <destination>");
            }
        });

        commands.put("InsertPump", (params)->{
            Game.getCurrentPlayer().insertPump();
        });
        /*
        commands.put("game_startgame", (params) ->{
            Game.startGame();
        });
        commands.put("game_targetscorecheck", (params) ->{
            Game.targetScoreCheck();
        });
        commands.put("game_addmechanicpoints", (params) ->{
            Game.addMechanicPoints();
        });
        commands.put("game_addsaboteurpoints", (params) ->{
            Game.addSaboteurPoints();
        });*/


        commands.put("Pass", (params) ->{
            Game.nextPlayer();
        });
        commands.put("Skip round", (params) ->{
            Game.nextRound();
        });/*
        commands.put("game_endgame", (params) ->{
            Game.endGame();
        });
        commands.put("print_neighbours", (params) -> { //print_neighbours
            String[] parameters = params.split(" ");
            System.out.println("Neighbours of player: " + Game.getCurrentPlayer());
            if (parameters.length == 1) {
                int i = 0;
                for (Field field : Game.getCurrentPlayer().getField().getNeighbours()) {
                    System.out.println(i++ + ": "+field);
                }
            }
            else{
                UI.incorrectParams("print_neighbours");
            }
        });
        commands.put("currentplayer", (params) -> {
            UI.currentPlayer(Game.getCurrentPlayer());
        });

        commands.put("playerpos", (params) -> { //playerpos <player name>
            String[] parameters = params.split(" ");
            if (parameters.length == 1){
                for(int i = 0; i < Game.getFields().size(); i++){
                    for (Player p : Game.getFields().get(i).getPlayers()){
                        if (p.getName().equals(parameters[0])){
                            UI.playerPos(parameters[0],i);
                        }
                    }
                }
            }
            else {

                UI.incorrectParams("playerpos <player name>");}
        });
         */

        commands.put("Save", (params) -> saveCommands());

        commands.put("Load", (params) -> loadCommandsFromFile());

        /**
         * Tesztesetek parancsai
         */

        /*
        commands.put("MoveToEmptyPipe", (params) -> {
            Game.startGame();
            Game.getCurrentPlayer().moveTo(Game.getFields().get(1));
        });
        commands.put("MoveToPump", (params) -> {
            Game.startGame();
            Game.setCurrentPlayer(Game.getPlayers().get(2));
            Game.getCurrentPlayer().moveTo(Game.getFields().get(4));
        });
        commands.put("MoveToOccupiedPipe", (params) -> {
            Game.startGame();
            Game.getCurrentPlayer().moveTo(Game.getFields().get(3));
        });

        commands.put("MoveToCistern", (params) -> {
            Game.startGame();
            Game.setCurrentPlayer(Game.getPlayers().get(3));
            Game.getCurrentPlayer().moveTo(Game.getFields().get(10));
        });

        commands.put("MoveToStickyPipe", (params) -> {
            Game.startGame();
            PipeField p = (PipeField) Game.getFields().get(1);
            p.makePipeSticky();
            Game.getCurrentPlayer().moveTo(Game.getFields().get(1));
            Game.setCurrentPlayer(Game.getPlayers().get(0));
            Game.getCurrentPlayer().moveTo(Game.getFields().get(0));
        });

        commands.put("StuckPlayerTryMove", (params) -> {
            Game.startGame();
            Game.setCurrentPlayer(Game.getPlayers().get(2));
            PipeField p = (PipeField) Game.getFields().get(3);
            p.makePipeSticky();
            Game.getCurrentPlayer().moveTo(Game.getFields().get(4));
        });
        commands.put("FlowWater", (params) -> {
            for (int i =0; i <5; i++){
                Game.nextRound();
            }
        });

        commands.put("PlayerMoveToSlipperyPipe", (params) -> {
            Game.startGame();
            Game.setCurrentPlayer(Game.getPlayers().get(1));
            PipeField p = (PipeField) Game.getFields().get(5);
            p.makePipeSlippery();
            Game.getCurrentPlayer().moveTo(Game.getFields().get(5));
        });

        commands.put("PipeSlipperyExpire", (params) -> {
            Game.startGame();
            Game.setCurrentPlayer(Game.getPlayers().get(3));
            Saboteur s = (Saboteur) Game.getCurrentPlayer();
            s.makePipeSlippery();
            Game.nextRound();
            Game.nextRound();
        });

        commands.put("PipeStickyExpire", (params) -> {
            Game.startGame();
            Game.setCurrentPlayer(Game.getPlayers().get(3));
            Saboteur s = (Saboteur) Game.getCurrentPlayer();
            s.makePipeSticky();
            Game.nextRound();
            Game.nextRound();
        });
         */

        /*
        commands.put("AdjustPump", (params) -> {
            Game.startGame();
            Mechanic m = (Mechanic) Game.getCurrentPlayer();
            PipeField newPipe = new PipeField();
            m.setPipeInventory(newPipe);
            m.installPipe();
            m.changePumpSettings((PipeField) m.getField().getSource(), newPipe);
        });

        commands.put("MakePipeSticky", (params) -> {
            Game.startGame();
            Game.setCurrentPlayer(Game.getPlayers().get(1));
            Saboteur s = (Saboteur) Game.getCurrentPlayer();
            s.moveTo(Game.getFields().get(5));
            s.makePipeSticky();
        });

        commands.put("MakePipeSlippery", (params) -> {
            Game.startGame();
            Game.setCurrentPlayer(Game.getPlayers().get(1));
            Saboteur s = (Saboteur) Game.getCurrentPlayer();
            s.moveTo(Game.getFields().get(5));
            s.makePipeSlippery();
        });

        commands.put("FixPuncturedPipe", (params) -> {
            Game.startGame();
            Game.getFields().get(3).puncturePipe();
            Game.setCurrentPlayer(Game.getPlayers().get(2));
            Mechanic m = (Mechanic) Game.getCurrentPlayer();
            m.fix();
        });
         */
        /*
        commands.put("FixGoodPipe", (params) -> {
            Game.startGame();
            Game.setCurrentPlayer(Game.getPlayers().get(0));
            Mechanic m = (Mechanic) Game.getCurrentPlayer();
            m.moveTo(Game.getFields().get(1));
            m.fix();
        });
        */

        /*
        commands.put("FixPump", (params) -> {
            Game.startGame();
            Game.setCurrentPlayer(Game.getPlayers().get(0));
            Mechanic m = (Mechanic) Game.getCurrentPlayer();
            m.setField(Game.getFields().get(2));
            Game.getFields().get(2).breakPump();
            m.fix();
        });

        commands.put("PickUpPipe", (params) -> {
            Game.startGame();
            Mechanic m = (Mechanic) Game.getCurrentPlayer();
            m.pickUpPipe((PipeField) Game.getFields().get(1));
        });

        commands.put("PickUpPump", (params) -> {
            Game.startGame();
            Mechanic m = (Mechanic) Game.getCurrentPlayer();
            m.setField(Game.getFields().get(10));
            CisternField c = (CisternField) Game.getFields().get(10);
            c.setPumpInventory(1);
            m.pickUpPump();
        });
         */
        /*
        commands.put("PlacePipeWithEmptyInventory", (params) -> {
            Game.startGame();
            Mechanic m = (Mechanic) Game.getCurrentPlayer();
            m.setPumpInventory(0);
            m.installPipe();
        });

        commands.put("InvalidAction", (params) -> {
            Game.startGame();
            Game.setCurrentPlayer(Game.getPlayers().get(1));
            Saboteur s = (Saboteur) Game.getCurrentPlayer();
            s.setField(Game.getFields().get(10));
            Field field = s.getField();
            s.puncturePipe();
            UI.invalidAction("The field"+field +"cannot be punctured");
        });

        commands.put("InstallPumpNoPumpInventory", (params) -> {
            Game.startGame();
            Mechanic m = (Mechanic) Game.getCurrentPlayer();
            m.setPumpInventory(0);
            PipeField p1 = (PipeField) Game.getFields().get(1);
            m.moveTo(p1);
            m.installPump();
        });

        commands.put("CisternPumpInventoryEmpty", (params) -> {
            Game.startGame();
            CisternField c = (CisternField) Game.getFields().get(10);
            c.setPumpInventory(0);
            Mechanic m = (Mechanic) Game.getCurrentPlayer();
            m.setField(Game.getFields().get(10));
            m.pickUpPump();
        });

        commands.put("CantPickUpPipe", (params) -> {
            Game.startGame();
            Mechanic m = (Mechanic) Game.getCurrentPlayer();
            PipeField pf = new PipeField();
            m.setPipeInventory(pf);
            m.moveTo(Game.getFields().get(1));
            m.pickUpPipe((PipeField) Game.getFields().get(1));
        });

        commands.put("SetupGame", (params) -> {
            Game.startGame();
            UI.setupGame();
            for(int i = 0; i < Game.getFields().size(); i++){
                UI.printMap(i,Game.getFields().get(i));
            }
        });

        commands.put("NextPlayer", (params) -> {
            Game.startGame();
            Game.nextPlayer();
        });

        commands.put("NextRound", (params) -> {
            Game.startGame();
            Game.nextRound();
        });

        commands.put("MechanicWon", (params) -> {
            Game.startGame();
            Game.setTargetScore(1);
            Game.addMechanicPoints();
            if(Game.targetScoreCheck()){
                Game.endGame();
            }
        });

        commands.put("SaboteurWon", (params) -> {
            Game.startGame();
            Game.setTargetScore(1);
            Game.addSaboteurPoints();
            if(Game.targetScoreCheck()){
                Game.endGame();
            }
        });
         */

        /*
        commands.put("PlacePipe", (params) -> {
            Game.startGame();
            Mechanic m = (Mechanic) Game.getCurrentPlayer();
            PipeField pf = new PipeField();
            m.setPipeInventory(pf);
            m.installPipe();
        });

        commands.put("PlacePump", (params) -> {
            Game.startGame();
            Mechanic m = (Mechanic) Game.getCurrentPlayer();
            PipeField pf = new PipeField();
            m.setPipeInventory(pf);
            m.installPipe();
            Game.nextRound();
            m.moveTo(pf);
            Game.nextRound();
            m.setPumpInventory(1);
            m.installPump();
        });
         */
        /*
        commands.put("PipeGenerate", (params) -> {
            Game.startGame();
            for(int i = 0; i<10; i++){
                Game.nextRound();
            }
        });

        commands.put("StartWater", (params) -> {
            Game.startGame();
            Game.nextRound();
        });

        commands.put("BlockWaterPath", (params) -> {
            Game.startGame();
            Game.getFields().get(2).setBlocked(true);
            Game.nextRound();
            Game.nextRound();
        });

        commands.put("PlayerPuncturePuncturedPipe", (params) -> {
            Game.startGame();
            Game.setCurrentPlayer(Game.getPlayers().get(3));
            Saboteur s = (Saboteur) Game.getCurrentPlayer();
            PipeField pf = (PipeField) Game.getFields().get(9);
            pf.setPunctured(true);
            s.puncturePipe();
        });

        commands.put("RecentlyFixedPipePuncture", (params) -> {
            Game.startGame();
            Game.getFields().get(1).puncturePipe();
            Mechanic m = (Mechanic) Game.getCurrentPlayer();
            m.moveTo(Game.getFields().get(1));
            m.fix();
            Game.nextRound();
            Game.getCurrentPlayer().moveTo(Game.getFields().get(1));
            Game.getCurrentPlayer().puncturePipe();
        });

        commands.put("MechanicAddPoint", (params) -> {
            Game.startGame();
            Water w = new Water();
            //model.Game.getFieldsHashMap().get("mid10").setCurrentWater(w);
            for(int i = 0; i < 5; i++) {
                Game.nextRound();
            }
        });

        commands.put("SaboteurAddPointPuncture", (params) -> {
            Game.startGame();
            Game.getFields().get(1).puncturePipe();
            Game.nextRound();
        });

        commands.put("SaboteurAddPointPipe", (params) -> {
            Game.startGame();
            Game.getFields().get(1).setDestination(null);
            Game.nextRound();
        });

        commands.put("InsertPumpBetweenPipe", (params) -> { 
            Game.startGame();
            Game.setCurrentPlayer(Game.getPlayers().get(2));
            Mechanic m = (Mechanic) Game.getCurrentPlayer();
            m.setPumpInventory(1);
            m.insertPump();
        });
         */
    }

    /**
     * A felhasználó "Save" bemenetét követően lementi az addigi paracsokat, és tetszőleges helyre elmenthető, választható formátumba
     */
    public static void saveCommands() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showSaveDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.write(commandHistory.toString());
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, UI.failedToSave() + e.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * A felhasználó "Load" bemenetét követően betölti a parancsokat egy txt fájlból
     */
    public static void loadCommandsFromFile() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(" ", 2);
                    String command = parts[0];
                    String params = (parts.length > 1) ? parts[1] : "";
                    if (commands.containsKey(command)) {
                        System.out.println(command+ "\n");
                        commands.get(command).accept(params);
                    } else {
                       UI.commandNotRecognizable();
                    }
                    UI.separator();
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, UI.failedToLoad() + e.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void proto(String[] args) {
        commands = new HashMap<>();

        commandHistory = new StringBuilder();
        UI.scanner = new Scanner(System.in);
        boolean exit = false;
        Game.startGame();
        loadCommands();

        while (!exit) {
            UI.enterCommand();
            String input = UI.scanner.nextLine();
            if (!"Save".equals(input))
                commandHistory.append(input).append("\n");
            String[] parts = input.split(" ", 2);

            String command = parts[0];
            String params = (parts.length > 1) ? parts[1] : "";

            if ("exit".equals(command)) {
                exit = true;
            } else if (commands.containsKey(command)) {
                commands.get(command).accept(params);
                commandHistory.append(command);
            } else {
                UI.commandNotRecognizable();
            }
            UI.separator();
        }
        UI.scanner.close();
    }
}

