package graphics;
import model.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Line2D;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;


/**
 * A pálya kirajzolásáért felelős osztály
 */
public class MapPanel extends JPanel implements ActionListener {
    /**
     *  A fieldek és a koordinátáik párban
     * */
    HashMap<Field, Point> fieldPositions = new HashMap<>();
    /**
     *  Vég nélküli csövek kirajzolását segítő tároló
     * */
    HashMap<Field, Point> endlessPipes = new HashMap<>();
/**
 *  Színek amiket használunk
 * */
    private final Color blackColor = Color.BLACK;
    private final Color blueColor = Color.BLUE;
    private final Color backgroundColor = new Color(233, 189, 246);

    /**
     * A pálya szélessége és magassága
     */
    int mapWidth = 1600;
    int mapHeight = 800;

    /**
     * Konstruktor
     */
    public MapPanel() {
        Game.startGame();
        setBackground(backgroundColor);
    }
    /**
     *  Játék vége esetén a végképernyő kirajzolása
     * */
    public void endGameScreen(Graphics g) {
        Color backgroundColor = Color.BLACK;

        String message = "Ha idáig eljutottál, szólj a csapatunknak, és meghívunk egy túró rudira!";
        if(Game.getMPoints() > Game.getSPoints()) { message+="\nTeam Mechanic won!"; }
        else { message += "\nTeam Saboteur won!"; }
        Font font = new Font("Arial", Font.BOLD, 40);
        FontMetrics fontMetrics = g.getFontMetrics(font);

        int screenWidth = getWidth();
        int screenHeight = getHeight();

        g.setColor(backgroundColor);
        g.fillRect(0, 0, screenWidth, screenHeight);

        g.setColor(Color.WHITE);
        g.setFont(font);

        int Width = fontMetrics.stringWidth(message);
        int Height = fontMetrics.getHeight();
        int x = (screenWidth - Width) / 2;
        int y = (screenHeight - Height) / 2;

        g.drawString(message, x, y);
    }
/**
 * Pálya kirajzolása, valamint a koordináták összepárosítása, melyek minden játék elején randomok
 * */
    public void DrawMap(Graphics g) {
        int pixelPerObject = (mapWidth) / 12;
        int i = 0;

        if(Game.targetScoreCheck()) {
            endGameScreen(g);
        }

        for (Field f : Game.getFields()) {
            i++;
            int x;
            int y = (int) (Math.random() * (mapHeight - 200)) + 100;
            if (fieldPositions.get(f) == null) {
                if (i < 12) {
                    x = i * pixelPerObject;
                } else {
                    x = (int) (Math.random() * (mapWidth - 200)) + 100;
                }
                fieldPositions.put(f, new Point(x, y));
            }
        }

        for (Field f : Game.getFields()) {
            f.DrawMe(g);
            if (!f.getPlayers().isEmpty()) {
                for (Player p : f.getPlayers()) {
                    p.DrawMe(g);
                }
            }
        }
    }

    /**
     * A szerelő játékosok kirajzolása
     */
    public void DrawMechanic(Mechanic m, Graphics g) {
        Field mf = m.getField();
        Point p = fieldPositions.get(mf);

        g.setColor(blackColor);

        int rectWidth = 20;
        int rectHeight = 40;
        int rCircle = 20;

        int rectX = p.x - rectWidth / 2 + 15;
        int rectY = p.y - rectHeight - rCircle - 5;

        g.fillRect(rectX - 2, rectY - 2, rectWidth + 4, rectHeight + 4);
        g.fillOval(p.x - rCircle / 2 + 13, rectY - rCircle - 4, rCircle + 4, rCircle + 4);

        g.setColor(blueColor);
        g.fillRect(rectX, rectY, rectWidth, rectHeight);

        g.setColor(blueColor);
        g.fillOval(p.x - rCircle / 2 + 15, rectY - rCircle - 2, rCircle, rCircle);

        g.setFont(new Font("Arial", Font.BOLD, 16));
        String mechanicName = m.getName();
        int nameX = p.x - g.getFontMetrics().stringWidth(mechanicName) / 2 + 15;
        int nameY = rectY - 30;
        g.drawString(mechanicName, nameX, nameY);
    }

    /**
     * A szabotőr játékosok kirajzolása
     */
    public void DrawSaboteur(Saboteur s, Graphics g) {
        Field sf = s.getField();
        Point p = fieldPositions.get(sf);


        g.setColor(blackColor);

        int rectWidth = 20;
        int rectHeight = 40;
        int circleDiameter = 20;

        int rectX = p.x - rectWidth / 2 + 15;
        int rectY = p.y - rectHeight - circleDiameter - 5;

        g.fillRect(rectX - 2, rectY - 2, rectWidth + 4, rectHeight + 4);
        g.fillOval(p.x - circleDiameter / 2 + 13, rectY - circleDiameter - 4, circleDiameter + 4, circleDiameter + 4);

        g.setColor(Color.RED);
        g.fillRect(rectX, rectY, rectWidth, rectHeight);

        g.setColor(Color.RED);
        g.fillOval(p.x - circleDiameter / 2 + 15, rectY - circleDiameter - 2, circleDiameter, circleDiameter);

        g.setFont(new Font("Arial", Font.BOLD, 16));
        String saboteurName = s.getName();
        int nameX = p.x - g.getFontMetrics().stringWidth(saboteurName) / 2 + 15;
        int nameY = rectY - 30;
        g.drawString(saboteurName, nameX, nameY);
    }


    /**
     * A ciszterna mező kirajzolása
     */
    public void DrawCistern(CisternField cf, Graphics g) {
        Point p = fieldPositions.get(cf);
        int x = p.x;
        int y = p.y;
        int sideLength = 30;

        int[] xPoints = {x, x + sideLength, x + sideLength * 3 / 2, x + sideLength, x, x - sideLength / 2};
        int[] yPoints = {y, y, y + (int) (sideLength * 0.866), y + (int) (sideLength * 1.732), y + (int) (sideLength * 1.732), y + (int) (sideLength * 0.866)};

        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(4));
        g2.setColor(Color.BLACK);
        g2.drawPolygon(xPoints, yPoints, 6);

        g.setColor(Color.BLACK);
        g.fillPolygon(xPoints, yPoints, 6);

        g2.setStroke(new BasicStroke(1));
        g2.setColor(Color.BLACK);
        g2.drawPolygon(xPoints, yPoints, 6);
    }

    /**
     * A forrás mező kirajzolása
     */
    public void DrawSource(SourceField sf, Graphics g) {
        Point p = fieldPositions.get(sf);
        int x = p.x;
        int y = p.y;
        int sideLength = 30;

        int[] xPoints = {x, x + sideLength, x + sideLength * 3 / 2, x + sideLength, x, x - sideLength / 2};
        int[] yPoints = {y, y, y + (int) (sideLength * 0.866), y + (int) (sideLength * 1.732), y + (int) (sideLength * 1.732), y + (int) (sideLength * 0.866)};

        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(4));
        g2.setColor(Color.BLACK);
        g2.drawPolygon(xPoints, yPoints, 6);

        g.setColor(Color.CYAN); // Set fill color
        g.fillPolygon(xPoints, yPoints, 6);

        g2.setStroke(new BasicStroke(1));
        g2.setColor(Color.BLACK);
        g2.drawPolygon(xPoints, yPoints, 6);
    }


    /**
     * A pumpa mező kirajzolása
     * If-else-ekkel vizsgálva, hogy a különböző metódusok hívása után milyen színnel rajzolja ki
     */
    public void DrawPump(PumpField pf, Graphics g) {
        Point p = fieldPositions.get(pf);

        int x = p.x;
        int y = p.y;
        int sideLength = 30;

        int[] xPoints = {x, x + sideLength, x + sideLength * 3 / 2, x + sideLength, x, x - sideLength / 2};
        int[] yPoints = {y, y, y + (int) (sideLength * 0.866), y + (int) (sideLength * 1.732), y + (int) (sideLength * 1.732), y + (int) (sideLength * 0.866)
        };

        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(4));
        g2.setColor(Color.BLACK);
        g2.drawPolygon(xPoints, yPoints, 6);

        g.setColor(Color.ORANGE);
        if (pf.isBroken()) {
            g.setColor(Color.RED);
        }
        g.fillPolygon(xPoints, yPoints, 6);

        g2.setStroke(new BasicStroke(1));
        g2.setColor(Color.BLACK);
        g2.drawPolygon(xPoints, yPoints, 6);
        g2.setColor(Color.BLUE);
        if (pf.getCurrentWater() != null)
            g2.drawOval(x+10,y+10,5,5);
        if (pf.getStoredWater() != null)
            g2.drawOval(x+5,y+10,5,5);
    }


    /**
     * A cső mező kirajzolása
     * If-else-ekkel vizsgálva, hogy a különböző metódusok hívása után milyen színnel rajzolja ki, illetve csatlakozástól hogy függ a megjelenítés
     */
    public void DrawPipe(PipeField pf, Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        Color pipeColor;
        if (!pf.getInInventory()) {
            if (pf.isPunctured()) {
                pipeColor = Color.RED;
            } else if (pf.getCurrentWater() != null) {
                pipeColor = Color.BLUE;
            } else {
                pipeColor = Color.WHITE;
            }
            g.setColor(pipeColor);
            g2.setColor(pipeColor);
            Point dest = fieldPositions.get(pf.getDestination());
            Point src = fieldPositions.get(pf.getSource());

            int centerX;
            int centerY;
            g2.setStroke(new BasicStroke(4));

            if (src != null && dest != null) {//basic pipe
                g.drawLine(src.x + 50, src.y + 25, dest.x, dest.y + 25);
                centerX = (src.x+50 + dest.x) / 2;
                centerY = (src.y + dest.y+50) / 2;
                Point pn = new Point(centerX,centerY);
                fieldPositions.replace(pf,pn);
            }
            else if (src == null && dest != null) { // cisterna
                if(endlessPipes.get(pf) == null ){
                    int x = 0;
                    int y = 0;
                    while (x > -50 && y > -50 || x < 50 && y < 50 ){
                        x = (int)(Math.random()* 200) - 100;
                        y = (int)(Math.random()* 200) - 100;
                    }
                    int vegX = dest.x - x;
                    int vegY = dest.y - y;
                    g.drawLine(dest.x + 20, dest.y + 20, vegX, vegY);
                    endlessPipes.put(pf, new Point(vegX, vegY));
                    centerX = (dest.x + vegX)/2;
                    centerY = (dest.y + vegY)/2;
                }
                else{
                    g.drawLine(dest.x + 20, dest.y + 20, endlessPipes.get(pf).x, endlessPipes.get(pf).y);
                    centerX = (dest.x + endlessPipes.get(pf).x)/2;
                    centerY = (dest.y + endlessPipes.get(pf).y)/2;
                }
            }
            else if (src != null) { // placepipe
                if(endlessPipes.get(pf) == null){
                    int x = 0;
                    int y = 0;
                    while (x > -50 && y > -50 || x < 50 && y < 50 ){
                        x = (int)(Math.random()* 200) - 100;
                        y = (int)(Math.random()* 200) - 100;
                    }
                    int vegX = src.x - x;
                    int vegY = src.y - y;
                    g.drawLine(src.x + 20, src.y + 20, vegX, vegY);
                    endlessPipes.put(pf, new Point(vegX, vegY));
                    centerX = (src.x + vegX)/2;
                    centerY = (src.y + vegY)/2;
                }
                else{
                    g.drawLine(src.x + 20, src.y + 20, endlessPipes.get(pf).x, endlessPipes.get(pf).y);
                    centerX = (src.x + endlessPipes.get(pf).x)/2;
                    centerY = (src.y + endlessPipes.get(pf).y)/2;
                }
            }
            else {  //semmiben a pipe
                Point closest = fieldPositions.get(pf.getNeighbourByIdx(0));
                g.drawLine(closest.x, closest.y, closest.x + 60, closest.y + 60);
                centerX = closest.x + 15;
                centerY = closest.y + 15;
            }

            int radius = 20;

            if (pf.isSlippery()) {
                g.setColor(Color.MAGENTA);
                g.fillOval(centerX, centerY, radius * 2, radius * 2);
            }

            if (pf.isSticky()) {
                g.setColor(Color.GREEN);
                g.fillOval(centerX, centerY, radius * 2, radius * 2);
            }

            g.setFont(new Font("Arial", Font.ITALIC, 12));
            g.setColor(Color.BLACK);
            FontMetrics fontMetrics = g.getFontMetrics();
            String attributew = "Water:" + pf.getCurrentWater() + ",";
            String attributec = "CleanIn:" + pf.getStepsToBeClean() + "," ;
            String attributep = "PuncturedIn" + pf.getStepsToBePuncturable() + ",";
            int textX = (centerX - fontMetrics.stringWidth(attributew) / 2) + 15;
            int textY = centerY + 15;
            g.drawString(attributew, textX, textY);
            g.drawString(attributec, textX, textY+15);
            g.drawString(attributep, textX, textY+30);
        }
    }


    /**
     * Komponens újrarajzolása
     * @param g a Graphics objektum
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        DrawMap(g);

    }

    /**
     * Eseménykezelő
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
    }
}
