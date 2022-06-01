package hiof.rammeverk.TheGameEngine.Window;

import hiof.rammeverk.TheGameEngine.*;
import hiof.rammeverk.TheGameEngine.GameObjects.GameObject;
import hiof.rammeverk.TheGameEngine.GameObjects.KeyInput;
import hiof.rammeverk.TheGameEngine.Behavior.GameLoop;

import javax.swing.JFrame;
import java.awt.Graphics;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferStrategy;
import java.io.Serial;

/**
 * Creates a Window for the game to be rendered on.
 * <p>
 *     Uses {@code javax.swing.JFrame} & {@code java.awt} to create the window.
 *     The drawing is done by {@code java.awt}
 * </p>
 * <p>
 *     <strong>Do not instantiate this class</strong><br>
 *     {@code GameBuilder} will create the instance of this class.
 * </p>
 * @see java.awt
 * @see javax.swing.JFrame
 * @see GameBuilder
 */
public class Window extends Canvas implements Runnable{
    @Serial
    private static final long serialVersionUID = -8754111336500953605L;
    private static Window window;
    private final int width;
    private final int height;
    public static boolean paused = false;
    private String title = "The Game Engine";
    private Thread thread;
    private boolean running = false;
    private final ApplicationHandler app;
    private final HeadUpDisplay hud;
    private GameLoop gameLoop;
    private final Menu menu;
    private Color backGround = Color.LIGHT_GRAY;

    /**
     * Enum used to decide the state of the game. <br>
     * Game will respond differently to key/mouse input for different game states.
     * @see Menu
     * @see KeyInput
     */
    public enum GameState {
        MENU,
        GAME,
        HELP,
        LOSE,
        WIN
    }

    public GameState gameState = GameState.MENU;

    /**
     * Returns a new instance of window.
     * <p>
     *     This constructor is <strong>private</strong><br>
     *     This class is using factory methods and a singleton pattern.
     * </p>
     * @param app {@code ApplicationsHandler}
     * @see ApplicationHandler
     * @see Menu
     * @see HeadUpDisplay
     * @see JFrame
     */
    private Window(ApplicationHandler app, HeadUpDisplay hud) {
        this.app = app;
        this.width = app.getWindowWidth();
        this.height = app.getWindowHeight();
        this.hud = hud;
        menu = new Menu(this, app, hud);
        this.addMouseListener(menu);
        JFrame frame = new JFrame(title);
        frame.setPreferredSize(new Dimension(width, height));
        frame.setMaximumSize(new Dimension(width, height));
        frame.setMinimumSize(new Dimension(width, height));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.add(this);
        frame.setVisible(true);
    }

    /**
     * Create and return a new instance of window.
     * <p>
     *     If window is already instantiated, that instance will be returned. <br>
     *     Implements the <em>singleton pattern.</em>
     * </p>
     * @param app {@code ApplicationHandler}
     * @see ApplicationHandler
     * @return {@code Window}
     *
     */
    public static Window create(ApplicationHandler app, HeadUpDisplay hud){
        if(window == null){
            window = new Window(app,hud);
        }
        return window;
    }

    /**
     * Set {@code GameLoop}
     * @param gameLoop {@code GameLoop}
     * @see GameLoop
     */
    public void setGameLoop(GameLoop gameLoop) {
        this.gameLoop = gameLoop;
    }

    /**
     * Return {@code this.window}.
     * <p>
     *     The instance of {@code Window}. Null if {@code Window} is not instantiated.
     * </p>
     * @return {@code Window}
     */
    public static Window get(){
        return window;
    }

    /**
     * Start the thread for the game to run on.
     * <p>
     *     Create a new instance of the class {@code Thread} and start it.
     * </p>
     * @see java.lang.Thread
     */
    public synchronized void start(){
        thread = new Thread(this);
        try{
            thread.start();
            running = true;
        }catch (IllegalThreadStateException e){
            System.err.println(e.getMessage());
            stop();
        }
    }

    /**
     * Stop and join the {@code Thread} started with {@code start()}.
     * @see java.lang.Thread
     */
    public synchronized void stop(){
        try {
            thread.join();
            running = false;
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Implementation of {@code Runnable}-interface.<br>
     * Controls the timing of the game.
     * <p>
     *     run executes the game loop.<br>
     *     while {@code running} is <em>true</em>
     * </p>
     * @see Runnable
     */
    @Override
    public synchronized void run() {
        this.requestFocus();
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        while(running){
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1){
                tick();
                delta--;
            }
            if(running){
                render();
            }
        }
        stop();
    }

    /**
     * Call the {@code tick}-method of all game-elements
     * <p>
     *     The {@code GameLoop} decides which {@code GameObjects} are created.
     * </p>
     * @see ApplicationHandler
     * @see HeadUpDisplay
     * @see GameLoop
     * @see GameObject
     * @see Menu
     */
    private void tick(){
        if(gameState == GameState.GAME){
            if(!Window.paused){
                app.tick();
                hud.tick();
                gameLoop.tick();
                if(hud.loseGame()){
                    this.gameOver();
                }
                if(hud.winGame())
                    this.gameWon();
            }
        }else if(gameState == GameState.MENU || gameState == GameState.LOSE || gameState == GameState.WIN){
            app.tick();
            menu.tick();
        }
    }

    /**
     * Draw elements on game window. <br>
     * Call other classes' {@code render}-methods
     */
    private void render(){
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null){
            this.createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();
//        Graphics ground = bs.getDrawGraphics();

        g.setColor(backGround);
        g.fillRect(0,0,width, height);
//        ground.setColor(Color.PINK);
//        ground.fillRect(0,height-140,width, 120);

        app.render(g);
//        app.render(ground);

        if(gameState == GameState.GAME){
            hud.render(g);
            menu.render(g);
        }else if(gameState == GameState.MENU || gameState == GameState.HELP || gameState == GameState.LOSE || gameState == GameState.WIN){
            menu.render(g);
        }

        g.dispose();
        bs.show();
    }

    /**
     * Called when game ends
     *
     */
    private void gameOver(){
        gameLoop.reset();
        app.objects.clear();
        gameState = GameState.LOSE;
    }
    private void gameWon(){
        gameLoop.reset();
        app.objects.clear();
        gameState = GameState.WIN;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setBackGround(Color backGround) {
        this.backGround = backGround;
    }

    public void setTitle(String title){
        this.title = title;
    }
}

