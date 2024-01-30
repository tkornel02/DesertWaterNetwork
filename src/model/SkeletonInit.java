package model;

import java.util.ArrayList;
import java.util.List;

import java.util.Scanner;

/**
 * Tesztelést végző Skeleton osztály
 */
public class SkeletonInit {
/*
    //model.Mechanic Repair Pipe
    public void RepairPipeTest() {
        System.out.println("----(1) Repair Pipe Test");

        model.Mechanic m = (model.Mechanic) model.Game.getPlayers().get(2);
        m.setField(model.Game.getFields().get(1).get(3));
        model.PipeField p = (model.PipeField) m.getField();
        p.setPunctured(false);
        m.fix();
        p.setPunctured(true);
        m.fix();
    }
    //model.Mechanic Repair Pump
    public void RepairPumpTest() {
        System.out.println("----(2) Repair Pump Test");
        model.Mechanic m = (model.Mechanic) model.Game.getPlayers().get(0);
        model.PumpField p = (model.PumpField) m.getField();
        p.setBroken(false);
        m.fix();
        p.setBroken(true);
        m.fix();
    }
    //model.Mechanic Pick Up Pump
    public void PickUpPumpTest() {
        System.out.println("----(3) Pick Up Pump Test");
        model.Mechanic m = (model.Mechanic) model.Game.getPlayers().get(2);
        m.setField(model.Game.getFields().get(1).get(10));
        model.CisternField cistern = (model.CisternField) model.Game.getFields().get(1).get(10);
        cistern.setPumpInventory(3);
       // model.Game.setTargetScore(6);
        m.pickUpPump();
    }
    //model.Mechanic Pick Up Pipe
    public void PickUpPipeTest() {
        System.out.println("----(4) Pick Up Pipe Test");
        model.Mechanic m = (model.Mechanic) model.Game.getPlayers().get(2);
        m.setField(model.Game.getFields().get(1).get(10));
        model.CisternField cistern = (model.CisternField) model.Game.getFields().get(1).get(10);
        model.PipeField pf = cistern.getPipes().get(0);
        System.out.println(m + " number of pipes: " +m.getPipeInventory());
        m.pickUpPipe(pf);
    }
    //model.Mechanic Place Pump
    public void MechanicPlacePumpTest() {
        System.out.println("----(5) model.Mechanic Place Pump Test");
        model.Mechanic m = (model.Mechanic) model.Game.getPlayers().get(2);
        m.setPumpInventory(1);
        m.setField(model.Game.getFields().get(1).get(5));
        EmptyField ef = (EmptyField) model.Game.getFields().get(0).get(5);
        m.installPump(ef, null, null);
    }
    //model.Mechanic Place Pipe
    public void MechanicPlacesPipe() {
        System.out.println("----(6) model.Mechanic Place Pipe Test");
        model.Mechanic m = (model.Mechanic) model.Game.getPlayers().get(2);
        m.setPipeInventory(1);
        m.setField(model.Game.getFields().get(1).get(6));
        EmptyField ef = (EmptyField) model.Game.getFields().get(0).get(6);
        m.installPipe(ef, null, null);
    }
    //model.Mechanic Pick Up Pump
    public void SaboteurBreakPipe() {
        System.out.println("----(7) model.Saboteur Break Pipe Test");
        model.Saboteur s = (model.Saboteur) model.Game.getPlayers().get(1);
        s.setField(model.Game.getFields().get(1).get(1));
        s.getField();
        s.puncturePipe();
        s.puncturePipe();
    }
    public void PlayerMoveOnPipe() {
        System.out.println("----(8) model.Player Move On Pipe Test");
        model.Mechanic m = (model.Mechanic) model.Game.getPlayers().get(2);
        model.PumpField initPos = (model.PumpField) model.Game.getFields().get(1).get(2);
        ArrayList<model.Player> initPosPlayers = new ArrayList<>();

        m.setField(initPos);
        initPosPlayers.add(m);
        initPos.setPlayers(initPosPlayers);
        System.out.println("model.Mechanic starting position: " + initPos);
        System.out.println("Number of players in starting position: " + initPosPlayers.size());

        model.PipeField toThisPipe = (model.PipeField) model.Game.getFields().get(1).get(3);
        toThisPipe.setPlayers(new ArrayList<model.Player>());
        System.out.println("Number of players of destination before the movement: " + toThisPipe.getPlayers().size());
        m.moveTo(toThisPipe); System.out.println("\nmodel.Mechanic moved\n");
        System.out.println("Position of model.Mechanic after position: " + m.getField());
        System.out.println("Number of players in starting position after movement: " + initPosPlayers.size());
        System.out.println("Number of players in destination after movement: " + toThisPipe.getPlayers().size());
    }
    public void PlayerMoveOnPump() {
        System.out.println("----(9) model.Player Move On Pump test");
        model.Saboteur s = (model.Saboteur) model.Game.getPlayers().get(1);
        model.PipeField initPos = (model.PipeField) model.Game.getFields().get(1).get(1);
        ArrayList<model.Player> initPosPlayers = new ArrayList<>();

        s.setField(initPos); initPosPlayers.add(s); initPos.setPlayers(initPosPlayers);
        System.out.println("model.Saboteur starting position: " + initPos);
        System.out.println("Number of players in starting position: " + initPosPlayers.size());

        model.PumpField toThisPump = (model.PumpField) model.Game.getFields().get(1).get(2);
        toThisPump.setPlayers(new ArrayList<model.Player>());
        System.out.println("Number of players of destination before the movement: " + toThisPump.getPlayers().size());

        s.moveTo(toThisPump);
        System.out.println("\nmodel.Saboteur moved.\n");

        System.out.println("Position of model.Saboteur after position: " + s.getField());
        System.out.println("Number of players in starting position after movement: " + initPosPlayers.size());
        System.out.println("Number of players in destination after movement: " + toThisPump.getPlayers().size());
    }
    public void PlayerAdjustPump() {
        System.out.println("----(10) model.Player Adjust Pump Test");
        model.Mechanic m =(model.Mechanic) model.Game.getPlayers().get(2);
        model.PumpField initPos = (model.PumpField) model.Game.getFields().get(1).get(2);
        m.setField(initPos);
        ArrayList<model.Player> initPosPlayers = new ArrayList<>();
        initPosPlayers.add(m);
        initPos.setPlayers(initPosPlayers);
        System.out.println("Pump starting source: " + initPos.getSource());
        System.out.println("Pump starting destination: " + initPos.getDestination());
        model.PipeField newDestination = new model.PipeField();
        model.Game.getFields().get(2).set(2, newDestination);

        m.changePumpSettings((model.PipeField) initPos.getSource(), newDestination);
    }
    public void PlayerDoesInvalidAction() {
        System.out.println("----(11) model.Player Does Invalid Action Test");
        model.Saboteur s = (model.Saboteur) model.Game.getPlayers().get(1);
        s.setField(model.Game.getFields().get(1).get(10));
        System.out.println("model.Field of the model.Player: " + s.getField());
        s.puncturePipe();
    }
    public void PlayerProgressRound() {
        System.out.println("----(12) model.Player Progress Round Test");
        model.Game.setCurrentPlayer(model.Game.getPlayers().get(3));
        model.Player before = model.Game.getCurrentPlayer();
        System.out.println("Current model.Player: " + before);
        model.Saboteur s = (model.Saboteur) model.Game.getCurrentPlayer();
        s.puncturePipe();
        model.Player after = model.Game.getCurrentPlayer();
        System.out.println("Current player: " + after);

    }
    public void EndGame() {
        System.out.println("(----13) End model.Game Test");
        model.Game.setMPoints(2);
        model.Game.setTargetScore(3);
        model.CisternField cistern = (model.CisternField) model.Game.getFields().get(1).get(10);
        cistern.flowWater();
        model.Game.nextRound();
    }
    public void StartWater() {
        System.out.println("----(14) Start model.Water Test");
        model.PipeField pf = (model.PipeField) model.Game.getFields().get(1).get(1);
        model.SourceField sf = (model.SourceField) model.Game.getFields().get(1).get(0);
        System.out.println("model.Water in the pipe next to the model.SourceField: " + pf.getCurrentWater());
        sf.flowWater();
        System.out.println("model.Water in the pipe next to the model.SourceField: " + pf.getCurrentWater() + pf.getClass().toString());
    }
    public void GeneratePipe() {
        System.out.println("----(15) Generate Pipe test");
        model.CisternField cistern = (model.CisternField) model.Game.getFields().get(1).get(10);
        cistern.producePipe();
    }
    public void GeneratePump() {
        System.out.println("----(16) Generate Pump Test");
        model.CisternField cistern = (model.CisternField) model.Game.getFields().get(1).get(10);
        System.out.println("Pump inventory: " + cistern.getPumpInventory());
        cistern.setPumpInventory(10);
        System.out.println("Pump inventory: " + cistern.getPumpInventory());
    }
    public void GenerateMap() {
        System.out.println("----(17) Generate Map Test");
        model.Game.setFields(null);
        System.out.println("The map of the game: " + model.Game.getFields());
        model.Game.startGame();
        System.out.println("The map of the game: " + model.Game.getFields());

    }
    public void BreakPump() {
        System.out.println("----(18) Break Pump test.");
        model.PumpField pump = (model.PumpField) model.Game.getFields().get(1).get(2);
        for (int i = 0; i < 3; i++) {
            pump.step();
        }
    }

    /**
     * Menürendszert megvalósító függvény, az egyes teszteseteket innen lehet futtatni
     */

    /*
    public static void Skeleton() {
        model.SkeletonInit skeleton = new model.SkeletonInit();
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;
        model.Game.startGame();


        while(!exit) {
            System.out.print("\nPlease choose an option\n");
            System.out.println("(1) Repair Pipe Test");
            System.out.println("(2) Repair Pump Test");
            System.out.println("(3) Pick Up Pump test");
            System.out.println("(4) Pick Up Pipe Test");
            System.out.println("(5) model.Mechanic Place Pump Test");
            System.out.println("(6) model.Mechanic Place Pipe Test");
            System.out.println("(7) model.Saboteur Break Pipe Test");
            System.out.println("(8) model.Player Move On Pipe Test");
            System.out.println("(9) model.Player Move On Pump test");
            System.out.println("(10) model.Player Adjust Pump Test");
            System.out.println("(11) model.Player Does Invalid Action Test");
            System.out.println("(12) model.Player Progress Round Test");
            System.out.println("(13) End model.Game Test");
            System.out.println("(14) Start model.Water Test");
            System.out.println("(15) Generate Pipe Test");
            System.out.println("(16) Generate Pump Test");
            System.out.println("(17) Generate Map Test");
            System.out.println("(18) Break Pump test");

            System.out.println("(0) Exit Test Program");

            int input = scanner.nextInt();

            switch (input) {
                case 1 : skeleton.RepairPipeTest(); break;
                case 2 : skeleton.RepairPumpTest(); break;
                case 3 : skeleton.PickUpPumpTest(); break;
                case 4 : skeleton.PickUpPipeTest(); break;
                case 5 : skeleton.MechanicPlacePumpTest(); break;
                case 6 : skeleton.MechanicPlacesPipe(); break;
                case 7 : skeleton.SaboteurBreakPipe(); break;
                case 8 : skeleton.PlayerMoveOnPipe(); break;
                case 9 : skeleton.PlayerMoveOnPump(); break;
                case 10 : skeleton.PlayerAdjustPump(); break;
                case 11 : skeleton.PlayerDoesInvalidAction(); break;
                case 12 : skeleton.PlayerProgressRound(); break;
                case 13 : skeleton.EndGame(); break;
                case 14 : skeleton.StartWater(); break;
                case 15 : skeleton.GeneratePipe(); break;
                case 16 : skeleton.GeneratePump(); break;
                case 17 : skeleton.GenerateMap(); break;
                case 18 : skeleton.BreakPump(); break;
                case 0 : {
                    System.out.println("Exit program.");
                    exit = true;
                }
            }
        }
        scanner.close();
    }
     */
}


