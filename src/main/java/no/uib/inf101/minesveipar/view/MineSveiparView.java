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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import javax.swing.Timer;

/**
 * The MineSveiparView class represents the graphical view of the MineSveipar
 * game.
 * It extends the JPanel class and is responsible for rendering the game board,
 * cells, timer, and other visual elements.
 * 
 * The view receives updates from the model and uses the provided color theme to
 * draw the game components on the graphics context.
 * It also handles user input events such as mouse clicks and keyboard input.
 * 
 * The MineSveiparView class provides methods for painting the game components,
 * drawing cells, drawing the timer, and drawing the face image.
 * It also includes a method to retrieve the rectangle of the grid.
 * 
 * The view uses the Inf101Graphics class to load images and draw centered text
 * on the graphics context.
 */
public class MineSveiparView extends JPanel {

    ViewableMineSveiparModel model;
    ColorTheme colorTheme;
    MineCell minecell;
    Timer gameTimer;
    int elapsedTime = 0;

    public MineSveiparView(ViewableMineSveiparModel model) {
        setupTimer();
        this.colorTheme = new DefaultColorTheme();
        this.minecell = new MineCell(0, true);
        this.model = model;
        this.setBackground(colorTheme.getBackgroundColor());
        this.setFocusable(true);
        this.setPreferredSize(new Dimension(550, 650));
        startTimer();

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        drawGame(g2);
        drawTime(g2);

        if (model.getGameState() == GameState.WELCOME_SCREEN) {
            Color overlay = new Color(45, 45, 45);
            Color text = new Color(196, 201, 204);
            g2.setColor(overlay);
            g2.fillRect(0, 0, this.getWidth(), this.getHeight());
            g2.setColor(text);
            g2.setFont(new Font("Monospaced", Font.BOLD, 35));

            String welcomeMessage = "Welcome to MineSveipar!";
            int xWelcome = (this.getWidth() - g2.getFontMetrics().stringWidth(welcomeMessage)) / 2;
            int yWelcome = (this.getHeight() - g2.getFontMetrics().getHeight()) / 2;
            g2.drawString(welcomeMessage, xWelcome, yWelcome);

            String startMessage = "Press enter to start";
            g2.setFont(new Font("Monospaced", Font.BOLD, 20));
            int xStart = (this.getWidth() - g2.getFontMetrics().stringWidth(startMessage)) / 2;
            int yStart = yWelcome + g2.getFontMetrics().getHeight() + 25;
            g2.drawString(startMessage, xStart, yStart);

            g2.setFont(new Font("Monospaced", Font.PLAIN, 16));
            String instruction1 = "Left-click to uncover a cell";
            String instruction2 = "Right-click to flag a cell";
            String instruction3 = "Enter to restart";
            int yInstructions = yStart + 45;

            int xInstruction1 = (this.getWidth() - g2.getFontMetrics().stringWidth(instruction1)) / 2;
            g2.drawString(instruction1, xInstruction1, yInstructions);

            int xInstruction2 = (this.getWidth() - g2.getFontMetrics().stringWidth(instruction2)) / 2;
            g2.drawString(instruction2, xInstruction2, yInstructions + 20);
            int xInstruction3 = (this.getWidth() - g2.getFontMetrics().stringWidth(instruction3)) / 2;
            g2.drawString(instruction3, xInstruction3, yInstructions + 40);

        }
    }

    /**
     * Draws the game board and its components on the graphics context.
     *
     * @param g2 the graphics context to draw on
     */
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

        drawCells(g2, model.getTilesOnBoard(), converter, colorTheme);

        drawFace(g2);
        drawCounter(g2);

        GameState gameState = model.getGameState();
        if (gameState == GameState.GAME_OVER || gameState == GameState.GAME_WON) {
            stopTimer();
            Color fillColor;
            if (gameState == GameState.GAME_OVER) {
                fillColor = colorTheme.getGameOverColor();
            } else {
                fillColor = colorTheme.getGameWonColor();
            }
            g2.setColor(fillColor);
            g2.fill(boardRect);

            String message = (gameState == GameState.GAME_OVER) ? "Game Over:( " : "You Won!";
            g2.setColor(colorTheme.getGameOverTextColor());
            g2.setFont(new Font("Monospaced", Font.BOLD, 40));
            Inf101Graphics.drawCenteredString(g2, message, boardRect);
        }
    }

    /**
     * Draws the current time on the graphics context.
     *
     * @param g2 the graphics context to draw on
     */
    private void drawTime(Graphics2D g2) {
        String time = getFormattedTime();
        g2.setFont(new Font("Monospaced", Font.BOLD, 25));
        g2.setColor(colorTheme.getFrameColor());
        int x = 40;
        int y = 40;
        g2.drawString("Time: " + time, x, y);
    }

    /**
     * Draws the cells on the graphics context using the provided parameters.
     *
     * @param g2         The graphics context to draw on.
     * @param cells      The iterable collection of grid cells to draw.
     * @param converter  The converter to convert cell positions to pixel
     *                   coordinates.
     * @param colorTheme The color theme to use for drawing.
     */
    private void drawCells(Graphics2D g2,
            Iterable<GridCell<MineCell>> cells,
            CellPositionToPixelConverter converter,
            ColorTheme colorTheme) {
        BufferedImage texture = Inf101Graphics.loadImageFromResources("/minesweeperTexture.png");

        for (GridCell<MineCell> cell : cells) {
            Rectangle2D rectangle = converter.getBoundsForCell(cell.pos());

            g2.drawImage(texture.getSubimage(16, 32, 16, 16), (int) rectangle.getX(), (int) rectangle.getY(),
                    (int) rectangle.getWidth(), (int) rectangle.getHeight(), null);

            if (((ControllableMineSveiparModel) model).isFlagged(cell.pos())) {
                g2.drawImage(texture.getSubimage(31, 31, 16, 16), (int) rectangle.getX(), (int) rectangle.getY(),
                        (int) rectangle.getWidth(), (int) rectangle.getHeight(), null);

            } else if (cell.value().getHidden() == false) {
                int value = cell.value().getValue();
                if (value == 0) {
                    g2.drawImage(texture.getSubimage(0, 0, 16, 16), (int) rectangle.getX(), (int) rectangle.getY(),
                            (int) rectangle.getWidth(), (int) rectangle.getHeight(), null);
                } else if (value == -1) {
                    g2.drawImage(texture.getSubimage(1, 49, 14, 14), (int) rectangle.getX(), (int) rectangle.getY(),
                            (int) rectangle.getWidth(), (int) rectangle.getHeight(), null);

                } else {
                    if (value == 1) {
                        g2.drawImage(texture.getSubimage(17, 0, 14, 14), (int) rectangle.getX(),
                                (int) rectangle.getY(), (int) rectangle.getWidth(), (int) rectangle.getHeight(), null);
                    } else if (value == 2) {
                        g2.drawImage(texture.getSubimage(33, 0, 14, 14), (int) rectangle.getX(),
                                (int) rectangle.getY(), (int) rectangle.getWidth(), (int) rectangle.getHeight(), null);
                    } else if (value == 3) {
                        g2.drawImage(texture.getSubimage(49, 0, 14, 14), (int) rectangle.getX(),
                                (int) rectangle.getY(), (int) rectangle.getWidth(), (int) rectangle.getHeight(), null);
                    } else if (value == 4) {
                        g2.drawImage(texture.getSubimage(0, 16, 14, 14), (int) rectangle.getX(),
                                (int) rectangle.getY(), (int) rectangle.getWidth(), (int) rectangle.getHeight(), null);
                    } else if (value == 5) {
                        g2.drawImage(texture.getSubimage(17, 17, 14, 14), (int) rectangle.getX(),
                                (int) rectangle.getY(), (int) rectangle.getWidth(), (int) rectangle.getHeight(), null);
                    } else if (value == 6) {
                        g2.drawImage(texture.getSubimage(33, 16, 14, 14), (int) rectangle.getX(),
                                (int) rectangle.getY(), (int) rectangle.getWidth(), (int) rectangle.getHeight(), null);
                    } else if (value == 7) {
                        g2.drawImage(texture.getSubimage(49, 16, 14, 14), (int) rectangle.getX(),
                                (int) rectangle.getY(), (int) rectangle.getWidth(), (int) rectangle.getHeight(), null);
                    } else if (value == 8) {
                        g2.drawImage(texture.getSubimage(0, 32, 14, 14), (int) rectangle.getX(),
                                (int) rectangle.getY(), (int) rectangle.getWidth(), (int) rectangle.getHeight(), null);

                    }
                }
            }
        }
    }

    /**
     * Draws the face image on the game view based on the current game state.
     * The face image is either a smiley or a sad face.
     *
     * @param g2d the Graphics2D object used for drawing
     */
    private void drawFace(Graphics2D g2d) {
        double x;
        double y;
        String imagePath;

        if (model.getGameState() == GameState.ACTIVE_GAME) {
            x = 190;
            y = -10;
            imagePath = "/Smiley.png";

        } else if (model.getGameState() == GameState.WELCOME_SCREEN) {
            x = 190;
            y = -10;
            imagePath = "/Smiley.png";

        } else {
            x = 200;
            y = -2.3;
            imagePath = "/Sad.png";
        }

        BufferedImage image = Inf101Graphics.loadImageFromResources(imagePath);
        Inf101Graphics.drawImage(g2d, image, x, y, (imagePath.equals("/Smiley.png") ? 0.14 : 0.23));

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

    /**
     * Draws the mine counter on the graphics context.
     *
     * @param g2 the graphics context to draw on
     */
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

        int count = this.model.mineCounter();
        g2.setColor(colorTheme.getFrameColor());
        g2.setFont(new Font("Monospaced", Font.BOLD, 40));
        String string = String.valueOf(count);
        Inf101Graphics.drawCenteredString(g2, string, rectangle);

        BufferedImage fullTexture = Inf101Graphics.loadImageFromResources("/minesweeperTexture.png");
        BufferedImage flagIcon = fullTexture.getSubimage(34, 34, 13, 13);

        int imageWidth = 30;
        int imageHeight = 30;
        double imageX = x - imageWidth + 20;
        double imageY = y + (height - imageHeight) / 2;
        g2.drawImage(flagIcon, (int) imageX, (int) imageY, imageWidth, imageHeight, null);
    }

    /**
     * Sets up the game timer.
     */
    private void setupTimer() {
        gameTimer = new Timer(1000, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                elapsedTime++;
                repaint();
            }
        });
    }

    /**
     * Starts the timer for the game.
     */
    public void startTimer() {
        elapsedTime = 0;
        gameTimer.start();
    }

    /**
     * Stops the game timer.
     */
    public void stopTimer() {
        gameTimer.stop();
    }

    /**
     * Returns the formatted time as a string in the format "MM:SS".
     *
     * @return the formatted time as a string
     */
    public String getFormattedTime() {
        int minutes = elapsedTime / 60;
        int seconds = elapsedTime % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

}
