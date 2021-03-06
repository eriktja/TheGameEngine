package hiof.rammeverk.TheGameEngine.Window;

import hiof.rammeverk.TheGameEngine.ApplicationHandler;
import hiof.rammeverk.TheGameEngine.Behavior.GameLoop;
import hiof.rammeverk.TheGameEngine.GameObjects.KeyInput;

import java.awt.*;
import java.awt.event.KeyAdapter;

/**
 * Class responsible for creating a new {@code Window} and set {@code GameLoop} to be used.
 * <p>
 *     Uses the <em>Singleton pattern</em>.
 * </p>
 * <p>
 *     Use this class to handle {@code Window} and {@code Menu}.
 * </p>
 */
public class GameBuilder {
    private static GameBuilder gameBuilder;
    private Window window;
    private GameLoop gameLoop;

    /**
     * Constructor for the class
     * <p>
     *     Creates a {@code Window} and sets {@code GameLoop} for  {@code Window}.
     * </p>
     * @param app {@code ApplicationHandler}
     * @param gameLoop {@code GameLoop}
     * @see GameLoop
     * @see ApplicationHandler
     * @see Window
     */
    private GameBuilder(ApplicationHandler app, GameLoop gameLoop) {
        this.window = Window.create(app, new HeadUpDisplay());
        this.gameLoop = gameLoop;
        this.window.setGameLoop(gameLoop);
    }

    /**
     * If you build your own {@code HeadUpDisplay}, use this constructor.
     * @param app - {@code ApplicationHandler}
     * @param gameLoop {@code GameLoop}
     * @param hud {@code HeadUpDisplay}
     */
    private GameBuilder(ApplicationHandler app, GameLoop gameLoop, HeadUpDisplay hud) {
        this.window = Window.create(app, hud);
        this.gameLoop = gameLoop;
        this.window.setGameLoop(gameLoop);
    }

    /**
     * Create-method calls the private Constructor.
     * <p>
     *     This class implements a <em>singleton pattern</em>.<br>
     *     This method returns the static gameBuilder class-parameter.
     * </p>
     * @param app {@code ApplicationHandler}
     * @param gameLoop {@code GameLoop}
     * @return {@code GameBuilder} - Static gameBuilder class-parameter.
     * @see ApplicationHandler
     * @see GameLoop
     */
    public static GameBuilder createGame(ApplicationHandler app, GameLoop gameLoop){
        if(gameBuilder == null){
            gameBuilder = new GameBuilder(app, gameLoop);
        }
        return gameBuilder;
    }
    /**
     * Create-method calls the private Constructor.
     * <p>
     *     This class implements a <em>singleton pattern</em>.<br>
     *     This method returns the static gameBuilder class-parameter.
     * </p>
     * @param app {@code ApplicationHandler}
     * @param gameLoop {@code GameLoop}
     * @param hud {@code HeadUpDisplay}
     * @return {@code GameBuilder} - Static gameBuilder class-parameter.
     * @see ApplicationHandler
     * @see GameLoop
     * @see HeadUpDisplay
     */
    public static GameBuilder createGame(ApplicationHandler app, GameLoop gameLoop, HeadUpDisplay hud){
        if(gameBuilder == null){
            gameBuilder = new GameBuilder(app, gameLoop, hud);
        }
        return gameBuilder;
    }

    /**
     * Call the {@code start}-method in {@code Window}<br>
     * This will start the game.<br>
     * @see Window
     */
    public void start(){
        window.start();
    }

    /**
     * Return the instance of window.
     * @return {@code Window}
     * @see Window
     */
    public Window getWindow() {
        return window;
    }

    public void setWindow(Window window) {
        this.window = window;
    }

    /**
     * Set {@code KeyAdapter} to be used in the game. <br>
     * This method calls the {@code java.awt.Canvas.addKeyListener()}-method.<br>
     * {@code Window} inherits from {@code Canvas}.
     * @param keyAdapter {@code KeyAdapter}
     * @see Window
     * @see java.awt.Canvas
     * @see KeyInput
     * @see KeyAdapter
     */
    public void setKeyAdapter(KeyAdapter keyAdapter){
        window.addKeyListener(keyAdapter);
    }

    /**
     * See the {@code GameState} of {@code Window}
     * @return {@code GameState}
     */
    public GameState gameState(){
        return window.gameState;
    }

    /**
     * Check if window is paused.
     * @return true if game is paused
     */
    public boolean isPaused(){
        return Window.paused;
    }

    /**
     * Change state of paused in window.
     * @param paused {@code boolean}
     */
    public void setPaused(boolean paused){
        Window.paused = paused;
    }

    /**
     * Use to access Menu
     * @return {@code Menu}
     */
    public Menu getMenu() {
        return window.getMenu();
    }

    /**
     * Change background color of the window
     * @param backGround {@code Color}
     */
    public void setBackGround(Color backGround) {
        window.setBackGround(backGround);
    }

    /**
     * Change title displayed in swing.Frame.
     * @param title {@code String} displayed in window frame.
     */
    public void setTitle(String title) {
        window.setTitle(title);
    }
    public void setWinCondition(int winCondition) {
        window.getHud().setWinCondition(winCondition);
    }
}
