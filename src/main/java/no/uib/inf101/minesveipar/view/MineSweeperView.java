package no.uib.inf101.minesveipar.view;

import javax.swing.JPanel;

import no.uib.inf101.grid.GridCell;
import no.uib.inf101.minesveipar.model.GameState;
import no.uib.inf101.minesveipar.model.MineCell;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class MineSweeperView extends JPanel {

    ViewableMineSveiparModel model;
    ColorTheme colorTheme;
    MineCell minecell;

    public MineSweeperView(ViewableMineSveiparModel model) {
        this.colorTheme = new DefaultColorTheme();
        this.minecell = new MineCell(0, true);
        this.model = model;
        this.setBackground(colorTheme.getBackgroundColor());
        this.setFocusable(true);
        this.setPreferredSize(new Dimension(500, 700));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        drawGame(g2);
    }

    private void drawGame(Graphics2D g2) {
        double margin = 2;
        double headerHeight = this.getHeight() * 1 / 8;
        double x = margin;
        double y = margin + headerHeight;
        double width = this.getWidth() - 2 * margin;
        double height = this.getHeight() - (2 * margin) - headerHeight;

        // Tegn rammen for spillbrettet
        g2.setColor(colorTheme.getFrameColor());
        Rectangle2D boardRect = new Rectangle2D.Double(x, y, width, height);
        g2.fill(boardRect);
        g2.draw(boardRect);

        // Konverterer for å få størrelsen på hver celle
        CellPositionToPixelConverter converter = new CellPositionToPixelConverter(boardRect, model.getDimension(),
                margin);

        // Tegn alle cellene på spillbrettet
        drawCells(g2, model.getTilesonBoard(), converter, colorTheme);

        // Tegn smilefjes, teller og instruksjoner
        drawSmiley(g2);
        drawCounter(g2);
        drawInstructions(g2);

        // Håndter forskjellige spilltilstander
        GameState gameState = model.getGameState();
        if (gameState == GameState.GAME_OVER || gameState == GameState.GAME_WON) {
            // Fyll hele spillbrettet med passende farge for spilltilstanden
            Color fillColor;
            if (gameState == GameState.GAME_OVER) {
                fillColor = colorTheme.getGameOverColor();
            } else { // GameState.GAME_WON
                fillColor = colorTheme.getGameWonColor();
            }
            g2.setColor(fillColor);
            g2.fill(boardRect);

            // Tegn meldingen (enten "Game Over" eller "You Won!") sentrert i spillbrettet
            String message = (gameState == GameState.GAME_OVER) ? "Game Over" : "You Won!";
            g2.setColor(colorTheme.getGameOverTextColor());
            g2.setFont(new Font("Monospaced", Font.BOLD, 40));
            Inf101Graphics.drawCenteredString(g2, message, boardRect);
        }
    }

    private void drawCells(Graphics2D g2,
            Iterable<GridCell<MineCell>> cells,
            CellPositionToPixelConverter converter,
            ColorTheme colorTheme) {
        for (GridCell<MineCell> cell : cells) {
            Rectangle2D rectangle = converter.getBoundsForCell(cell.pos());
            g2.setColor(colorTheme.getBackgroundColor());
            g2.fill(rectangle);
            g2.draw(rectangle);

            if (cell.value().getValue() == 10) {
                g2.setColor(colorTheme.getUncoveredCellColor());
                g2.fill(rectangle);
                double x = rectangle.getCenterX();
                double y = rectangle.getCenterY();
                BufferedImage image = Inf101Graphics.loadImageFromResources("/src/main/resources/Minesweeperflag.png");
                Inf101Graphics.drawCenteredImage(g2, image, x, y, 0.15);
            }
            if (cell.value().getHidden() == false) {
                g2.setColor(colorTheme.getUncoveredCellColor());
                g2.fill(rectangle);
                if (cell.value().getValue() != 0) {
                    if (cell.value().getValue() == -1) {
                        g2.setColor(colorTheme.getUncoveredCellColor());
                        g2.fill(rectangle);
                        double x = rectangle.getCenterX();
                        double y = rectangle.getCenterY();
                        BufferedImage image = Inf101Graphics.loadImageFromResources("/Bomb.png");
                        Inf101Graphics.drawCenteredImage(g2, image, x, y, 0.060);
                    }
                    g2.setColor(colorTheme.getCellColor(new MineCell(cell.value().getValue(), false)));
                    g2.setFont(new Font("Arial", Font.BOLD, 18));
                    String string = String.valueOf(cell.value().getValue());
                    Inf101Graphics.drawCenteredString(g2, string, rectangle);
                }
            }

        }
    }

    private void drawSmiley(Graphics2D g2d) {
        double margin = 2;
        double x = 200;
        double y = margin * 2;
        double width = 55;
        double height = 55;
        Rectangle2D smileyRectangle = new Rectangle2D.Double(x, y, width, height);
        g2d.setColor(colorTheme.getCellColor(minecell));
        g2d.fill(smileyRectangle);
        g2d.draw(smileyRectangle);

        if (model.getGameState() == GameState.ACTIVE_GAME) {
            BufferedImage image = Inf101Graphics.loadImageFromResources("/Smiley.png");
            Inf101Graphics.drawImage(g2d, image, x, y, 0.10);
        }

        if (model.getGameState() == GameState.GAME_OVER) {
            BufferedImage image = Inf101Graphics.loadImageFromResources("/Sad.png");
            Inf101Graphics.drawImage(g2d, image, x, y, 0.10);
        }

        if (model.getGameState() == GameState.GAME_WON) {
            BufferedImage image = Inf101Graphics.loadImageFromResources("/win.png");
            Inf101Graphics.drawImage(g2d, image, x, y, 0.8);
        }
    }

    /**
     * 
     * @return the rectangle of the grid
     */
    public Rectangle2D getBox() {
        double margin = 2;
        double x = margin;
        double y = margin + this.getHeight() * 1 / 8;
        double width = this.getWidth();
        double height = this.getHeight() - (2 * margin) - this.getHeight() * 1 / 8;
        Rectangle2D rectangle = new Rectangle2D.Double(x, y, width, height);
        return rectangle;
    }

    private void drawCounter(Graphics2D g2) {
        double margin = 2;
        double x = 560;
        double y = margin * 2;
        double width = 100;
        double height = 55;
        Rectangle2D rectangle = new Rectangle2D.Double(x, y, width, height);
        g2.setColor(colorTheme.getUncoveredCellColor());
        g2.fill(rectangle);
        g2.draw(rectangle);
        for (int i = 0; i < this.model.mineCounter(); i++) {
            int count = this.model.mineCounter();
            g2.setColor(colorTheme.getGameOverTextColor());
            g2.setFont(new Font("Arial", Font.BOLD, 40));
            String string = String.valueOf(count);
            Inf101Graphics.drawCenteredString(g2, string, rectangle);
        }
    }

    private void drawInstructions(Graphics2D g2) {

        double margin = 2;
        double x = 4;
        double y = margin * 2;
        double width = 250;
        double height = 55;
        Rectangle2D rectangle = new Rectangle2D.Double(x, y, width, height);
        g2.setColor(Color.lightGray);
        g2.fill(rectangle);
        g2.draw(rectangle);

        String string = "Left-click: uncover a cell";
        String string2 = "Right-click: flag a cell";
        String string3 = "Press space: start over";

        g2.setColor(colorTheme.getGameOverTextColor());
        g2.setFont(new Font("Arial", Font.BOLD, 14));
        int x1 = 8;
        int y1 = 20;
        int y2 = 35;
        int y3 = 50;

        g2.setColor(Color.BLACK);
        g2.drawString(string, x1, y1);
        g2.drawString(string2, x1, y2);
        g2.drawString(string3, x1, y3);
    }

}
