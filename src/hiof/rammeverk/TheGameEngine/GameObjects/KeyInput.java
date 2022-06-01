package hiof.rammeverk.TheGameEngine.GameObjects;

import hiof.rammeverk.TheGameEngine.ApplicationHandler;
import hiof.rammeverk.TheGameEngine.Window.GameBuilder;
import hiof.rammeverk.TheGameEngine.Window.Window;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ConcurrentModificationException;

/**
 * Add movement to {@code GameObjects} with {@code Id.PLAYER}.
 * <p>
 *     To create your own key-input class, either extend this class or {@code KeyAdapter}.<br>
 *     Remember to use {@code Id} to determine which {@code GameObject} should listen to {@code KeyEvent}
 * </p>
 * @see java.awt.event.KeyAdapter
 * @see KeyEvent
 *
 */
public class KeyInput extends KeyAdapter {

    private final ApplicationHandler app;
    private final GameBuilder gameBuilder;
    private final Boolean[] keyDown = new Boolean[6];

    public KeyInput(ApplicationHandler app, GameBuilder gameBuilder) {
        this.app = app;
        this.gameBuilder = gameBuilder;

        keyDown[0]=false;
        keyDown[1]=false;
        keyDown[2]=false;
        keyDown[3]=false;
        keyDown[4]=false;
        keyDown[5]=false;

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        try {
            for (int i = 0; i < app.objects.size(); i++) {
                if (app.objects.get(i).getId() == Id.PLAYER) {
                    GameObject object = app.objects.get(i);
                    // Player key events
                    if (key == KeyEvent.VK_W) {
                        object.setVelY(object.getMovementSpeed() * -1);
                        keyDown[0] = true;
                    }
                    if (key == KeyEvent.VK_S) {
                        object.setVelY(object.getMovementSpeed());
                        keyDown[1] = true;
                    }
                    if (key == KeyEvent.VK_A) {
                        object.setVelX(object.getMovementSpeed() * -1);
                        keyDown[2] = true;
                    }
                    if (key == KeyEvent.VK_D) {
                        object.setVelX(object.getMovementSpeed());
                        keyDown[3] = true;
                    }
                    if (key == KeyEvent.VK_SPACE) {
                        keyDown[4] = true;
                        object.app.addObject(app.getCreator().cloneGameObject(Id.BULLET));
                    }
                }
            }
            if(gameBuilder.gameState() == Window.GameState.GAME)
                if(key == KeyEvent.VK_P){
                    keyDown[5] = true;
                    gameBuilder.setPaused(!gameBuilder.isPaused());
                }

            // Exit with esc
            if(key == KeyEvent.VK_ESCAPE) System.exit(0);
        }catch (ConcurrentModificationException ce){
            System.out.println("This is not an error");
        }
    }
    @Override
    public void keyReleased(KeyEvent e){
        int key = e.getKeyCode();
        try {
            for (int i = 0; i < app.objects.size(); i++) {
                if (app.objects.get(i).getId() == Id.PLAYER) {
                    GameObject object = app.objects.get(i);
                    // Player key events
                    if (key == KeyEvent.VK_W) keyDown[0]=false;
                    if (key == KeyEvent.VK_S) keyDown[1]=false;
                    if (key == KeyEvent.VK_A) keyDown[2]=false;
                    if (key == KeyEvent.VK_D) keyDown[3]=false;
                    if (key == KeyEvent.VK_SPACE) keyDown[4]=false;

                    if(!keyDown[0] && !keyDown[1]) object.setVelY(0);
                    if(!keyDown[2] && !keyDown[3]) object.setVelX(0);
                }
            }
            if (key == KeyEvent.VK_P) keyDown[5]=false;

        }catch (ConcurrentModificationException ce){
            System.out.println("Hello");
        }
    }
}
