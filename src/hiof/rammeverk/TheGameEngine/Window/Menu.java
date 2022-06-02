package hiof.rammeverk.TheGameEngine.Window;

import hiof.rammeverk.TheGameEngine.ApplicationHandler;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Display <em>Buttons</em> on {@code Window}.
 * <p>
 *     {@code Menu} draws Buttons to {@code Window}.<br>
 *     {@code Menu} implements {@code MouseAdapter} to respond to <em>mouse-input.</em>
 * </p>
 * <p>
 *     This class can not be inherited.
 *     <br>You may change button text and size.
 * </p>
 * @see MouseAdapter
 * @see Window
 *
 */
public final class Menu extends MouseAdapter {
    private final Window window;
    private final ApplicationHandler app;
    private final HeadUpDisplay hud;
    private final int centerX;
    private final int centerY;
    private final int bottomY;
    private final int topY;
    private int buttonWidth = 300;
    private int buttonHeight = 100;
    private String topText = "Menu";
    private String startButtonText = "Play";
    private String settingButtonText = "Help";
    private String backButtonText = "Back";
    private String quitButtonText = "Quit";
    private String settingsInstruction1 = "Use WASD to move";
    private String settingInstruction2 = "Use SPACE to shoot";
    private String settingInstruction3 = "Try to avoid the zombies";
    private String pausedMessage = "GAME PAUSED";
    private String loseMessage = "You died";
    private String loseAdvice = "Git gud";
    private String winMessage = "You survived!";
    private String winAdvice = "Think you can do it again???";
    private Color startButtonColor = Color.BLUE;
    private Color settingButtonColor = Color.MAGENTA;
    private Color backButtonColor = Color.GREEN;
    private Color quitButtonColor = Color.GREEN;

    /**
     * Instantiate a new instance of {@code Menu}.
     * <p>
     *     Constructor is called by {@code Window} and should not be used by itself.
     *     <br>
     *     Uses the sizes from {@code ApplicationHandler} to determine position of buttons and {@code mousePressed}.
     * </p>
     * @param window {@code Window}
     * @param app {@code ApplicationHandler}
     * @param hud {@code HeadUpDisplay}
     * @see MouseAdapter
     * @see Window
     * @see ApplicationHandler
     * @see HeadUpDisplay
     */
    Menu(Window window, ApplicationHandler app, HeadUpDisplay hud) {
        this.window = window;
        this.app = app;
        this.hud = hud;
        this.centerX = app.getWindowWidth() / 2 - (buttonWidth / 2);
        this.centerY = app.getWindowHeight() / 2 - (buttonHeight / 2);
        this.bottomY = app.getWindowHeight() - (app.getWindowHeight() / 4) - 50;
        this.topY = app.getWindowHeight() / 4 - 50;
    }

    /**
     * Receives {@code MouseEvents} and changes {@code GameState}.
     * <p>
     *     {@code MouseEvents} are detected. This is an event-class inherited from {@code MouseAdapter}.
     * </p>
     * <p>
     *     {@code GameState} in {@code Window} is changed depending on which button was clicked.
     * </p>
     * @param e {@code MouseEvent} to be processed
     * @see MouseEvent
     * @see MouseAdapter
     * @see Window
     */
    public void mousePressed(MouseEvent e){
        int mX = e.getX();
        int mY = e.getY();

        if(window.gameState == GameState.MENU){
            // Play button
            if(mouseOver(mX, mY, centerX, topY, buttonWidth, buttonHeight)){
                app.clearAll();
                hud.reset();
                window.gameState = GameState.GAME;
            }
            // Help button
            if(mouseOver(mX, mY, centerX, centerY, buttonWidth, buttonHeight)){
                window.gameState = GameState.HELP;
            }
            // Quit button
            if(mouseOver(mX, mY, centerX, bottomY, buttonWidth, buttonHeight)){
                System.exit(0);
            }
        }

        if(window.gameState ==  GameState.HELP){
            // Back button
            if(mouseOver(mX, mY, centerX, bottomY, buttonWidth, buttonHeight)){
                window.gameState = GameState.MENU;
            }
        }
        if(window.gameState ==  GameState.LOSE){
            // Back button
            if(mouseOver(mX, mY, centerX, bottomY, buttonWidth, buttonHeight)){
                window.gameState = GameState.MENU;
            }
        }
        if(window.gameState ==  GameState.WIN){
            // Back button
            if(mouseOver(mX, mY, centerX, bottomY, buttonWidth, buttonHeight)){
                window.gameState = GameState.MENU;
            }
        }
    }

    public void mouseReleased(MouseEvent e){

    }

    /**
     * Check the position of the mouse.
     * <p>
     *     Used by {@code mousePressed}
     * </p>
     * @param mX Mouse X-Position
     * @param mY Mouse Y-Position
     * @param x Button start x-position
     * @param y Button start y-position
     * @param width Button width
     * @param height Button height
     * @return true, if mouse is over button. Otherwise, false.
     */
    private boolean mouseOver(int mX, int mY, int x, int y, int width, int height){
        if(mX > x && mX < x + width)
            return mY > y && mY < y + height;
        return false;
    }

    /**
     * Draw the {@code Menu}.
     * @param g {@code Graphics}
     * @see Graphics
     */
    public void render(Graphics g) {
        Font fnt1 = new Font("arial", Font.BOLD, 50);
        Font fnt2 = new Font("arial", Font.BOLD, 30);
        if (window.gameState == GameState.MENU) {
            g.setFont(fnt1);
            g.setColor(Color.black);
            g.drawString(topText, centerX + 90, topY - 30);

            // PLay button
            g.setColor(startButtonColor);
            g.fillRect(centerX, topY, buttonWidth, buttonHeight);
            g.setFont(fnt1);
            g.setColor(Color.white);
            g.drawString(startButtonText, centerX + 90, topY + 65);

            // Help button
            g.setColor(settingButtonColor);
            g.fillRect(centerX, centerY, buttonWidth, buttonHeight);
            g.setColor(Color.white);
            g.drawString(settingButtonText, centerX + 90, centerY + 65);

            // Quit
            g.setColor(quitButtonColor);
            g.fillRect(centerX, bottomY, buttonWidth, buttonHeight);
            g.setColor(Color.white);
            g.drawString(quitButtonText, centerX + 90, bottomY + 65);

        } else if (window.gameState == GameState.HELP) {
            g.setFont(fnt1);
            g.setColor(Color.black);
            g.drawString(settingButtonText, centerX + 90, topY - 30);

            g.setFont(fnt2);
            g.setColor(Color.black);
            g.drawString(settingsInstruction1, centerX, centerY);
            g.drawString(settingInstruction2, centerX, centerY + 50);
            g.drawString(settingInstruction3, centerX, centerY + 100);

            // Back button
            g.setColor(backButtonColor);
            g.fillRect(centerX, bottomY, buttonWidth, buttonHeight);
            g.setFont(fnt1);
            g.setColor(Color.white);
            g.drawString(backButtonText, centerX + 90, bottomY + 65);
        } else if (window.gameState == GameState.LOSE) {
            g.setFont(fnt1);
            g.setColor(Color.black);
            g.drawString(loseMessage, centerX, topY);

            g.setFont(fnt2);
            g.setColor(Color.black);
            g.drawString(loseAdvice, centerX, centerY);
            g.drawString("Score: " + hud.getScore(), centerX, centerY + 30);
            g.drawString("Level: " + hud.getLevel(), centerX, centerY + 60);
            g.drawString("Kill count: " + hud.getCounterToString(), centerX, centerY + 90);

            // Back button
            g.setColor(backButtonColor);
            g.fillRect(centerX, bottomY, buttonWidth, buttonHeight);
            g.setFont(fnt1);
            g.setColor(Color.white);
            g.drawString(topText, centerX + 90, bottomY + 65);
        }else if (window.gameState == GameState.WIN) {
            g.setFont(fnt1);
            g.setColor(Color.black);
            g.drawString(winMessage, centerX, topY);

            g.setFont(fnt2);
            g.setColor(Color.black);
            g.drawString(winAdvice, centerX, centerY);
            g.drawString("Score: " + hud.getScore(), centerX, centerY + 30);
            g.drawString("Level: " + hud.getLevel(), centerX, centerY + 60);
            g.drawString("Kill count: " + hud.getCounterToString(), centerX, centerY + 90);

            // Back button
            g.setColor(backButtonColor);
            g.fillRect(centerX, bottomY, buttonWidth, buttonHeight);
            g.setFont(fnt1);
            g.setColor(Color.white);
            g.drawString(topText, centerX + 90, bottomY + 65);
        } else if (window.gameState == GameState.GAME) {
            if (Window.paused) {
                g.setFont(fnt1);
                g.setColor(Color.RED);
                g.drawString(pausedMessage, centerX + 90, topY - 30);
            }
        }
    }

    /**
     * Behavior of {@code Menu} for each iteration ({@code tick}) of the game.
     * @see Window
     */
    public void tick(){

    }

    public void setTopText(String topText) {
        this.topText = topText;
    }

    public void setStartButtonText(String startButtonText) {
        this.startButtonText = startButtonText;
    }

    public void setSettingButtonText(String settingButtonText) {
        this.settingButtonText = settingButtonText;
    }

    public void setBackButtonText(String backButtonText) {
        this.backButtonText = backButtonText;
    }

    public void setQuitButtonText(String quitButtonText) {
        this.quitButtonText = quitButtonText;
    }
    
    public void setSettingsInstruction1(String settingsInstruction1) {
        this.settingsInstruction1 = settingsInstruction1;
    }

    public void setSettingInstruction2(String settingInstruction2) {
        this.settingInstruction2 = settingInstruction2;
    }

    public void setSettingInstruction3(String settingInstruction3) {
        this.settingInstruction3 = settingInstruction3;
    }

    public void setPausedMessage(String pausedMessage) {
        this.pausedMessage = pausedMessage;
    }

    public void setLoseMessage(String loseMessage) {
        this.loseMessage = loseMessage;
    }

    public void setLoseAdvice(String loseAdvice) {
        this.loseAdvice = loseAdvice;
    }

    public void setButtonWidth(int buttonWidth) {
        this.buttonWidth = buttonWidth;
    }

    public void setButtonHeight(int buttonHeight) {
        this.buttonHeight = buttonHeight;
    }

    public void setWinMessage(String winMessage) {
        this.winMessage = winMessage;
    }

    public void setWinAdvice(String winAdvice) {
        this.winAdvice = winAdvice;
    }

    public void setStartButtonColor(Color startButtonColor) {
        this.startButtonColor = startButtonColor;
    }

    public void setSettingButtonColor(Color settingButtonColor) {
        this.settingButtonColor = settingButtonColor;
    }

    public void setBackButtonColor(Color backButtonColor) {
        this.backButtonColor = backButtonColor;
    }

    public void setQuitButtonColor(Color quitButtonColor) {
        this.quitButtonColor = quitButtonColor;
    }
}
