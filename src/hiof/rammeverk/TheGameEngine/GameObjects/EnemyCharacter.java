package hiof.rammeverk.TheGameEngine.GameObjects;

import hiof.rammeverk.TheGameEngine.ApplicationHandler;
import hiof.rammeverk.TheGameEngine.Behavior.GameLoop;
import hiof.rammeverk.TheGameEngine.Window.HeadUpDisplay;
import hiof.rammeverk.TheGameEngine.Helpers.Limiter;

import java.awt.Rectangle;
import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

/**
 * Example of a {@code GameObject}-subclass.
 * <p>
 *     This class has a movement pattern that will follow {@code GameObject} with the {@code Id.PLAYER}.<br>
 *     The {@code tick()}-method applies this movement.
 * </p>
 * <p>
 *     This class has collision detection for {@code GameObjects} with {@code Id.BULLET}.<br>
 *     The {@code collision}-method applies this detection.<br>
 *     {@code collision} uses a {@code GameObjects} {@code hitBox()}-method.
 * </p>
 * @see GameObject
 * @see Bullet
 * @see Id
 * @see Rectangle
 * @see Limiter
 * @see Cloneable
 */
public class EnemyCharacter extends GameObject {
    private GameObject player;
    private int health;
    private final int height = 100;
    private final int width = 40;
    private final Random r = new Random();

    /**
     * Constructor for the class
     * @param id {@code Id}
     * @param app {@code ApplicationHandler}
     * @see Id
     * @see ApplicationHandler
     */
    private EnemyCharacter(Id id, ApplicationHandler app) {
        super(id, app);
        // Spawns at a random position.
        setXPos(r.nextInt(app.getWindowWidth()));
        setYPos(r.nextInt(app.getWindowHeight()));
        health = 100;
    }

    /**
     * Constructor that returns a copy of existing EnemyCharacter
     *<p>
     *    If no {@code GameObject} with the {@code Id.PLAYER} exist in {@code ApplicationHandler.objects} the {@code player} variable will be {@code null}.
     *    This will cause a NullPointerException.<br>
     *    This class will follow the {@code Id.PLAYER GameObject}.
     *</p>
     * @param enemyCharacter {@code EnemyCharacter}
     */
    private EnemyCharacter(EnemyCharacter enemyCharacter) {
        super(enemyCharacter.getId(), enemyCharacter.getApp());
        setMovementSpeed(enemyCharacter.getMovementSpeed());
        setHeight(enemyCharacter.getHeight());
        setWidth(enemyCharacter.getWidth());
        setColor(enemyCharacter.getColor());

        for (int i = 0; i < getApp().objects.size(); i ++){
            if(getApp().objects.get(i).getId() == Id.PLAYER) {
                this.player = getApp().objects.get(i);
                break;
            }
        }

        setXPos(r.nextInt(getApp().getWindowWidth()));
        setYPos(r.nextInt(getApp().getWindowHeight()));
        health = 100;
    }

    /**
     * Method calls the private constructor.
     * <p>
     *     This method calls the private constructor to instantiate a new instance of {@code EnemyCharacter}.
     * </p>
     * <p>
     *     This is meant to be used once per new type of enemy added to the game. <br>
     *     Use {@code cloneObject()} to add it to the {@code GameLoop}.
     * </p>
     * @param id {@code Id}
     * @param app {@code ApplicationHandler}
     * @return {@code EnemyCharacter} - {@code prototype} class-parameter
     * @see Id
     * @see ApplicationHandler
     * @see Cloneable
     * @see GameLoop
     */
    public static EnemyCharacter create(Id id, ApplicationHandler app) {
        return new EnemyCharacter(id, app);
    }

    /**
     * Create a new instance of the class which is a copy of {@code this} class-instance.
     * @return EnemyCharacter {@code this} class-instance.
     * @see Cloneable
     */
    @Override
    public EnemyCharacter cloneObject() {
        return new EnemyCharacter(this);
    }

    /**
     * This implementation of the {@code tick}-method will make this {@code GameObject} follow <br>
     * the {@code GameObject} stored in the {@code player}-instance variable.
     */
    @Override
    public void tick() {
        movementPattern1(player);

        collision();
        if(health <= 0){
            setId(Id.DEAD);
            HeadUpDisplay.setHealth(HeadUpDisplay.getHealth()+2);
            HeadUpDisplay.setCounter(HeadUpDisplay.getCounter()+1);
            Bullet bul1 = new Bullet(Id.BULLET, getApp());
            Bullet bul2 = new Bullet(Id.BULLET, getApp());
            Bullet bul3 = new Bullet(Id.BULLET, getApp());
            Bullet bul4 = new Bullet(Id.BULLET, getApp());
            bul1.setVelX(10);
            bul1.setVelY(10);
            bul1.setXPos(getXPos());
            bul1.setYPos(getYPos());
            bul2.setVelX(-10);
            bul2.setVelY(-10);
            bul2.setXPos(getXPos());
            bul2.setYPos(getYPos());
            bul3.setVelX(-10);
            bul3.setVelY(10);
            bul3.setXPos(getXPos());
            bul3.setYPos(getYPos());
            bul4.setVelX(10);
            bul4.setVelY(-10);
            bul4.setXPos(getXPos());
            bul4.setYPos(getYPos());
            getApp().addObject(bul1);
            getApp().addObject(bul2);
            getApp().addObject(bul3);
            getApp().addObject(bul4);
        }
    }

    /**
     * This implementation of {@code collision} detects collision with {@code Id.BULLET}.
     * <br> Uses {@code hitBox} and {@code Rectangle}.
     * @see Rectangle
     * @see Id
     */
    private void collision(){
        for (int i = 0; i < getApp().objects.size(); i++) {
            if(getApp().objects.get(i).getId() == Id.BULLET && this.hitBox().intersects(getApp().objects.get(i).hitBox())){
                // Collision
                health -= 10;
                // Remove bullet from game.
                getApp().objects.get(i).setId(Id.DEAD);
            }
        }
    }

    /**
     * How to draw the {@code GameObject} to the window.
     * @param g {@code Graphics}
     * @see Graphics
     */
    @Override
    public void render(Graphics g) {
        g.setColor(getColor());
        g.fillRect((int) getXPos(), (int) getYPos(), width, height);
        g.setColor(Color.GREEN);
        g.fillRect((int) getXPos(),(int) getYPos() + (height/3), width, height/3);
    }

    /**
     * Determine {@code hitBox} of the {@code GameObject}.
     * <p>
     *     A {@code hitBox} is the area the of the {@code GameObject} that is checked for {@code collision}.
     *     <br> I.e this can be to deal damage or receive damage.
     * </p>
     * @return Rectangle {@code java.awt.Rectangle}
     * @see Rectangle
     */
    @Override
    public Rectangle hitBox() {
        return new Rectangle((int) getXPos(), (int) getYPos(), width, height);
    }
}
