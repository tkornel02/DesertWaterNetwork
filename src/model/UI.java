package model;

import java.util.Scanner;

/**
 * A játék során a felhasználóval való kommunikációért felelős osztály.
 */
public class UI {
    static Scanner scanner;

    public static void invalidAction(String string) {
        System.out.println("Invalid Action"+ string);
    }

    public static void move(Field src, Field dst) {
        System.out.println(Game.getCurrentPlayer().getName() +" moved to " + dst + " from " + src);
        System.out.println("Number of players on the destination field: " + dst.getPlayers().size());
    }

    public static void moveToOccupiedPipe(Field field) {
        invalidAction("moveToOccupiedPipe");
        System.out.println("Cannot move, " + field + " is occupied by " + field.getPlayers().get(0).getName());
    }

    public static void moveFromStickyPipe(PipeField pipeField) {
        invalidAction("moveFromStickyPipe");
        System.out.println(Game.getCurrentPlayer().getName() + " is stuck on " + pipeField + " for " + pipeField.getStepsToBeClean() + " more steps.");
    }

    public static void incorrectParams(String string) {
        System.out.println("The given parameters are incorrect, correct form: " + string);
    }

    public static void makePipeSticky(PipeField pipeField) {
        System.out.println(Game.getCurrentPlayer().getName() + " made " + pipeField + " sticky.");
    }

    public static void makePipeSlippery(PipeField pipeField) {
        System.out.println(Game.getCurrentPlayer().getName() + " made " + pipeField + " slippery.");
    }

    public static void changePumpSettings(PumpField pump) {
        System.out.println(pump + " : Source from: " + pump.getSource() + " and Destination to: " + pump.getDestination());
    }

    public static void blockedCheck(PipeField input, PipeField output, boolean un) {
        System.out.println("model.Water " + (un ? "" : "un") + "blocked at " + input + " and " + output);
    }

    public static void blockedWaterPath(Field f) {
        System.out.println("model.Water blocked at " + f);
    }

    public static void fixGoodPipe(PipeField pipeField) {
        invalidAction("fixGoodPipe");
        System.out.println(pipeField + " is not broken.");
    }

    public static void invalidMoves(Player player, Field field) {
        invalidAction(" ");
        System.out.println(player.getName() + " cannot move to the currentField: " + field);
    }

    public static void generatePipe(CisternField cisternField, PipeField pipeField) {
        System.out.println(cisternField + " generated " + pipeField + ".");
    }

    public static void setupGame() {
        System.out.println("model.Game g started, default map set up.");
    }

    public static void currentPlayer(Player player) {
        System.out.println("The current player is: " + player.getName());
    }

    public static void nextPlayer(Player player) {
        System.out.println("The next player is: " + player.getName());
    }

    public static void mechanicWon() {
        System.out.println("Team model.Mechanic reached target scored and won.");
    }

    public static void saboteurWon() {
        System.out.println("Team model.Saboteur reached target scored and won.");
    }

    public static void placePipe(Player player, PipeField pf) {
        System.out.println(player.getName() + " placed " + pf + " with source " + pf.getSource() + ".");
    }
    public static void placePump(Player player, PumpField pf) {
        System.out.println(player.getName() + " placed " + pf + " with source " + pf.getSource() + ".");
    }



    public static void startWater(SourceField sourceField, Water w){
        System.out.println(sourceField + " started flowing " + w +".");
    }

    public static void puncturePuncturedPipe(Player player, PipeField pipeField){
        System.out.println("Invalid Action " + player.getName() + " tried puncturing " + pipeField + ", but it was already punctured.");
    }

    public static void moveToSlipperyPipe(Field pipe, Field field){
        System.out.println(Game.getCurrentPlayer().getName() + " moved to a slippery " + pipe +", sliding randomly to " + field + ".");
    }

    public static void pipeCleaned(PipeField pipeField){
        System.out.println(pipeField + " effect expired, it is now clean");
    }
    public static void couldNotPlacePipe(){
        System.out.println("Could not place down a pipe");
    }

    public static void enterCommand(){
        System.out.println("Please enter a command and parameters (if any):");
    }

    public static void commandNotRecognizable(){
        System.out.println("Command not recognizable");
    }

    public static String failedToLoad(){
        return "Failed to load commands: ";
    }

    public static String failedToSave(){
        return "Failed to save commands: ";
    }

    public static void addSaboteurPoint(Field f){
        System.out.println("model.Water flows into desert at " + f +", saboteurs earn 1 point. Saboteurs points: " + Game.getSPoints());
    }

    public static void addMechanicPoint(Field f){
        System.out.println("model.Water reached " + f +", mechanic team scored 1 point. model.Mechanic points: " + Game.getMPoints());
    }

    public static void brokenPump(Field f){
        System.out.println("Pump " + f +"broke down");
    }

    public static void fixPipe(PipeField pipeField){
        System.out.println(pipeField + " fixed");
    }

    public static void puncturePipe(PipeField pipeField){
        System.out.println(pipeField + " punctured");
    }

    public static void fixPump(PumpField pumpField){
        System.out.println(pumpField + " fixed");
    }

    public static void inventoryFull(){
        invalidAction("inventoryFull");
        System.out.println("Inventory is full");
    }

    public static void pickUp(Field field){
        System.out.println("Successfuly picked up : "+ field);
    }

    public static void canNotPickUp(){
        System.out.println("Cannot Pickup");
    }

    public static void inventoryEmpty(){
        invalidAction(" InventoryEmpty");
    }
    public static void insertPump(PipeField old, PipeField p, PumpField pump){
        System.out.println(pump + " inserted between " + old + " and " + p);
    }
    public static void printMap(int i, Field f){
        System.out.println( "[#" + i + ":" + f);
        System.out.println( "Sticky: " + f.isSticky());
        System.out.println( "Slippery: " + f.isSlippery());
        System.out.println( "Broken: " + f.isBroken());
        System.out.println( "Punctured: " + f.isPunctured());
        System.out.println( "Occupied: " + f.isOccupied());
        System.out.println( "model.Water: " + f.currentWater);
        System.out.println( "Source: " + f.getSource());
        System.out.println( "Destination: " + f.getDestination());
        System.out.print( "Neighbours: ");
        for (Field fields : f.getNeighbours()){
            System.out.print(fields+" ");
        }
        System.out.print( "\nPlayers: ");
        for (Player p : f.getPlayers()){
            System.out.print(p.getName()+" ");
        }
        System.out.print("]\n\n");
    }
    public static void separator(){
        System.out.println("\n--------------------------------------------------\n");
    }

    public static void playerPos(String name, int i){
        System.out.println(name + "'s position: #" + i);
    }

    public static void gameState(){
        System.out.println("model.Mechanic points: " + Game.getMPoints());
        System.out.println("model.Saboteur points: " + Game.getSPoints());
        System.out.println("TargetScore: " + Game.getTargetScore());
    }

    public static void incorrectPlayer(){
        System.out.println("Invalid action for " + Game.getCurrentPlayer().getName());
    }

    public static void breakPump(Field f){
        System.out.println(f + " broke down");
    }

    public static void pickedUpPump(CisternField c){
        System.out.println("Pump picked up from " + c);
    }
}
