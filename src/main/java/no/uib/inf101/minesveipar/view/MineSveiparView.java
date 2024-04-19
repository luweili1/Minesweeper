package no.uib.inf101.minesveipar.view;

import javax.swing.JPanel;

import no.uib.inf101.grid.GridCell;
import no.uib.inf101.minesveipar.controller.ControllableMineSveiparModel;
import no.uib.inf101.minesveipar.model.GameState;
import no.uib.inf101.minesveipar.model.MineCell;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class MineSveiparView extends JPanel {

    ViewableMineSveiparModel model;
    ColorTheme colorTheme;
    MineCell minecell;

    public MineSveiparView(ViewableMineSveiparModel model) {
        this.colorTheme = new DefaultColorTheme();
        this.minecell = new MineCell(0, true);
        this.model = model;
        this.setBackground(colorTheme.getBackgroundColor());
        this.setFocusable(true);
        this.setPreferredSize(new Dimension(550, 650));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        drawGame(g2);
    }

    private void drawGame(Graphics2D g2) {
        double margin = 4;
        double headerHeight = this.getHeight() * 0.1;
        double x = margin;
        double y = margin + headerHeight;
        double width = this.getWidth() - 2 * margin;
        double height = this.getHeight() - (2 * margin) - headerHeight;

        g2.setColor(colorTheme.getFrameColor());
        Rectangle2D boardRect = new Rectangle2D.Double(x, y, width, height);
        g2.fill(boardRect);
        g2.draw(boardRect);

        CellPositionToPixelConverter converter = new CellPositionToPixelConverter(boardRect, model.getDimension(),
                margin);

        drawCells(g2, model.getTilesonBoard(), converter, colorTheme);

        drawSmiley(g2);
        drawCounter(g2);
        drawInstructions(g2);

        GameState gameState = model.getGameState();
        if (gameState == GameState.GAME_OVER || gameState == GameState.GAME_WON) {
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
        BufferedImage texture = Inf101Graphics.loadImageFromResources("/minesweeperTexture.png");

        for (GridCell<MineCell> cell : cells) {
            Rectangle2D rectangle = converter.getBoundsForCell(cell.pos());

            // Cell ikon
            g2.drawImage(texture.getSubimage(16, 32, 16, 16), (int) rectangle.getX(), (int) rectangle.getY(),
                    (int) rectangle.getWidth(), (int) rectangle.getHeight(), null);

            if (((ControllableMineSveiparModel) model).isFlagged(cell.pos())) { // Flag
                g2.drawImage(texture.getSubimage(31, 31, 16, 16), (int) rectangle.getX(), (int) rectangle.getY(),
                        (int) rectangle.getWidth(), (int) rectangle.getHeight(), null);
            } else if (cell.value().getHidden() == false) {
                if (cell.value().getValue() != 0) {
                    if (cell.value().getValue() == -1) {
                        // mine
                        g2.drawImage(texture.getSubimage(1, 49, 14, 14), (int) rectangle.getX(),
                                (int) rectangle.getY(),
                                (int) rectangle.getWidth(), (int) rectangle.getHeight(), null);
                    } else { // empty cell
                        g2.drawImage(texture.getSubimage(0, 0, 16, 16), (int) rectangle.getX(), (int) rectangle.getY(),
                                (int) rectangle.getWidth(), (int) rectangle.getHeight(), null);

                    }
                }
            }
        }
    }

    private void drawSmiley(Graphics2D g2d) {
        double x = 190;
        double y = -10;

        g2d.setColor(colorTheme.getCellColor(minecell));

        if (model.getGameState() == GameState.ACTIVE_GAME) {
            BufferedImage image = Inf101Graphics.loadImageFromResources("/Smiley.png");
            Inf101Graphics.drawImage(g2d, image, x, y, 0.14);
        }

        if (model.getGameState() == GameState.GAME_OVER) {
            BufferedImage image = Inf101Graphics.loadImageFromResources("/Sad.png");
            Inf101Graphics.drawImage(g2d, image, x, y, 0.20);
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
        double x = 400;
        double y = margin * 2;
        double width = 100;
        double height = 55;
        Rectangle2D rectangle = new Rectangle2D.Double(x, y, width, height);
        g2.setColor(colorTheme.getBackgroundColor());
        g2.fill(rectangle);
        g2.draw(rectangle);
        for (int i = 0; i < this.model.mineCounter(); i++) {
            int count = this.model.mineCounter();
            g2.setColor(colorTheme.getFrameColor());
            g2.setFont(new Font("Arial", Font.BOLD, 40));
            String string = String.valueOf(count);
            Inf101Graphics.drawCenteredString(g2, string, rectangle);
        }
    }

    private void drawInstructions(Graphics2D g2) {

        double margin = 2;
        double x = 4;
        double y = margin * 2;
        double width = 200;
        double height = 55;
        Rectangle2D rectangle = new Rectangle2D.Double(x, y, width, height);
        g2.setColor(colorTheme.getBackgroundColor());
        g2.fill(rectangle);
        g2.draw(rectangle);

        String string = "Left-click: uncover a cell";
        String string2 = "Right-click: flag a cell";
        String string3 = "Press space: start over";

        g2.setColor(colorTheme.getFrameColor());
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