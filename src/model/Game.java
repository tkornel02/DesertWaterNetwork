package model;

import graphics.GameGui;
import graphics.MapPanel;
import graphics.ScorePanel;

import java.awt.*;
import java.nio.channels.Pipe;
import java.util.ArrayList;

/**
 * A játék megfelelő működéséért felelős osztály, amely a játék kezdetétől a végéig irányítja a
 * játékmenetet, felügyeli az aktuálisan lépő játékost, a csapatok eredményét, és ellenőrzi, hogy
 * beteljesül-e a játék befejezését előíró feltétel.
 */
public class Game {

        /**
         * A jelenlegi játékos.
         */
        private static Player currentPlayer;
        /**
         * A céleredmény.
         */
        private static int targetScore;
        /**
         * Szerelők pontjai.
         */
        private static int mPoints;
        /**
         * Szabotőrök pontjai.
         */
        private static int sPoints;

        /**
         * Játékosok listája.
         */
        private static ArrayList<Player> players;
        /**
         * A mezők listája.
         */
        private static ArrayList<Field> fields;
        /**
        * A játék grafikus felületének objektuma
        */
        public static MapPanel mapPanel;

        /**
        *A játék eredményjelzőjének objektuma
        */
        public static ScorePanel scorePanel;

    /**
     *
     * Grafikus játéktér objektumának beállítása
     */
    public static void SetMapPanel(MapPanel mp){
            mapPanel = mp;
        }

        /**
         * A játék eredményjelző objektumának lekérdezése
         */
        public static void SetScorePanel(ScorePanel sp) {scorePanel = sp;}
        private int currentRound = 0;
    /**
     * Konstruktor, amely beállítja a játék kezdeti állapotát.
     * @param currentPlayer
     * @param targetScore
     * @param players
     */
    public Game(Player currentPlayer, int targetScore, ArrayList<Player> players) {
        Game.currentPlayer = currentPlayer;
        Game.targetScore = targetScore;
        Game.players = players;
    }

    /**
     * Elindítja a játékot és egy előre meghatározott pályát inicializál.
     * 33 játékmezőt inicializál, felső emptyfield sor, középső nem-emptyfield mezők, alsó emptyfield sor.
     * Beállítja a szükséges kapcsolatokat a mezők között.
     */
    public static void startGame() {
        players = new ArrayList<>();
        fields = new ArrayList<>();

        for (int i = 0; i <= 10; i++) {
            if(i == 0){
                fields.add(0, new SourceField());
            }
            else if(i == 10){
                fields.add(10, new CisternField());
            } //páros indexek model.PumpField
            else if(i % 2 == 0){
                fields.add(i, new PumpField());
            } //páratlanok PipeFIeld
            else{
                fields.add(i, new PipeField());
            }
        }

        for (int i = 0; i <= 10; i++) {
            if (i == 0) {
                fields.get(i).setSource(null);
                fields.get(i).setDestination(fields.get(i+1));
                fields.get(i).AddNeighbour(fields.get(i+1));
            } else if (i == 10) {
                fields.get(i).setDestination(null);
                fields.get(i).setSource(fields.get(i-1));
                fields.get(i).AddNeighbour(fields.get(i-1));
            } else {
                fields.get(i).AddNeighbour(fields.get(i - 1));
                fields.get(i).setSource(fields.get(i - 1));
                fields.get(i).AddNeighbour(fields.get(i + 1));
                fields.get(i).setDestination(fields.get(i + 1));
            }
        }
        PipeField k1 = new PipeField();
        k1.setSource(fields.get(4));
        fields.get(4).AddNeighbour(k1);
        
        PumpField r1 = new PumpField();
        k1.AddNeighbour(r1);
        r1.setSource(k1);
        k1.setDestination(r1);

        PipeField k2 = new PipeField();
        k2.setSource(r1);
        k2.setDestination(fields.get(8));
        r1.AddNeighbour(k2);

        PipeField k3 = new PipeField();
        k3.setSource(fields.get(2));
        fields.get(2).AddNeighbour(k3);

        fields.add(k3);
        fields.add(k2);
        fields.add(k1);
        fields.add(r1);



        players.add(new Mechanic("m1  ", fields.get(10))); // Pumpán áll
        players.add(new Saboteur("   s1", fields.get(2))); // Pumpán áll
        players.add(new Mechanic("       m2", fields.get(8))); // Csövön áll
        players.add(new Saboteur("          s2", fields.get(3))); // Csövön áll

        //players.get(0).pickUpPump();

        setCurrentPlayer(players.get(0));
        setMPoints(0);
        setSPoints(0);
        setTargetScore(100);
        //System.out.println("<-model.Game.startGame()");
        }
        /**
         * Beállítja a jelenlegi játékost.
         *
         * @param currentPlayer
         */

        public static void setCurrentPlayer(Player currentPlayer) {
            Game.currentPlayer = currentPlayer;
        }

        /**
         * Lekéri a jelenlegi játékost.
         */
        public static Player getCurrentPlayer() {
            //System.out.println("Current player: " + currentPlayer.getName());
            return currentPlayer;
        }

        /**
         * Beállítja a céleredményt.
         *
         * @param targetScore
         */
        public static void setTargetScore(int targetScore) {
            //System.out.println("Target score: " + targetScore);
            Game.targetScore = targetScore;
        }

        /**
         * Lekéri a céleredményt.
         */
        public static int getTargetScore() {
            //System.out.println("Target score: " + targetScore);
            return targetScore;
        }

        /**
         * Beállítja a Szerelők pontjait.
         *
         * @param mPoints
         */
        public static void setMPoints(int mPoints) {
            Game.mPoints = mPoints;
            //System.out.println("model.Mechanic points: " + mPoints);
        }

        /**
         * Lekéri a Szerelők pontjait.
         *
         * @return
         */
        public static int getMPoints() {
            //System.out.println("model.Mechanic points: " + mPoints);
            return mPoints;
        }

        /**
         * Beállítja a Szabotőrök pontjait.
         *
         * @param sPoints
         */
        public static void setSPoints(int sPoints) {
            Game.sPoints = sPoints;
            //System.out.println("model.Saboteur points: " + sPoints);
        }

        /**
         * Lekéri a Szabotőrök pontjait.
         *
         * @return
         */
        public static int getSPoints() {
            //System.out.println("model.Saboteur points: " + sPoints);
            return sPoints;
        }

        /**
         * Ellenőrzi a céleredményt, ha valamelyik játékos elérte
         * a győzelemhez szükséges pontot, akkor true-val tér vissza, ha pedig még nem érte el
         * senki, akkor false-al.
         */
        public static boolean targetScoreCheck() {
            if (mPoints == targetScore) {
                //System.out.println("Mechanics win");
                UI.mechanicWon();
                return true;
            }
            if (sPoints == targetScore) {
                //System.out.println("Saboteurs win");
                UI.saboteurWon();
                return true;
            }
            UI.gameState();
            return false;
        }

        /**
         * Lekéri a játéktéren található mezők listáját.
         */
        public static ArrayList<Field> getFields() {
            return fields;
        }

        /**
         * Beállítja a játéktéren található mezők listáját.
         *
         * @param fields
         */
        public static void setFields(ArrayList<Field> fields) {
            Game.fields = fields;
        }


        /**
         * Hozzáad egy pontot a Szerelők pontjaihoz.
         */
        public static void addMechanicPoints() {
            //System.out.println("\t->addmechanicPoints()");
            setMPoints(getMPoints() + 1);
            //System.out.println("\t<->addmechanicPoints()");
            scorePanel.updateScores();
        }


        /**
         * Hozzáad egy pontot a Szabotőrök pontjaihoz.
         */
        public static void addSaboteurPoints() {
            setSPoints(getSPoints() + 1);
            scorePanel.updateScores();
        }
        /**
         * A játékosok egymás után.
         */
        public static void nextPlayer() {
            UI.currentPlayer(currentPlayer);
            if(players.indexOf(currentPlayer) == players.size()-1)
                nextRound();
            else{
                setCurrentPlayer(players.get(players.indexOf(currentPlayer) + 1));
                UI.nextPlayer(currentPlayer);
            }
            //gamegui.getMapPanel().DrawMap();
        }

        /**
         * Vizet mozgat, és az első játékost állítja be újra next playerként.
         */
        public static void nextRound() {
            //System.out.println("->nextRound()");
            //System.out.println("Did any of the teams win? Y/N");
            if (targetScoreCheck()) {
                //System.out.println("\tY");
                endGame();
            } else {
                //System.out.println("\tN");
                for (int i =  fields.size() - 1; i > -1; i--) {
                    fields.get(i).step();
                }
            }
            setCurrentPlayer(players.get(0));
            UI.currentPlayer(currentPlayer);
            //System.out.println("Next round.");
        }

        /**
         * Ha a TargetScoreCheck true-val tér vissza, akkor ez a függvény
         * hívódik meg és a játék véget ér.
         */
        public static void endGame() {
            System.out.println("model.Game ended.");
        }

        /**
         * Beállítja a játékosok listáját.
         *
         * @param players
         */
        public static void setPlayers(ArrayList<Player> players) {
            Game.players = players;
        }

        /**
         * Lekéri a játékosok listáját.
         *
         * @return
         */

        public static ArrayList<Player> getPlayers() {
            return players;
        }
}
