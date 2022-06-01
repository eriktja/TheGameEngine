package hiof.rammeverk.TheGameEngine;

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
    private final int height = 100;
    private final int width = 40;
    private int health;

    private final Random r = new Random();

    /**
     * Constructor for the class
     * <p>
     *     If no {@code GameObject} with the {@code Id.PLAYER} exist in {@code ApplicationHandler.objects} the {@code player} variable will be {@code null}.<br>
     *     This class will follow the {@code Id.PLAYER GameObject}.
     * </p>
     * @param id {@code Id}
     * @param app {@code ApplicationHandler}
     * @see Id
     * @see ApplicationHandler
     */
    private EnemyCharacter(Id id, ApplicationHandler app) {
        super(id, app);

        for (int i = 0; i < this.app.objects.size(); i ++){
            if(this.app.objects.get(i).getId() == Id.PLAYER) {
                this.player = this.app.objects.get(i);
                break;
            }

        }

        setXPos(r.nextInt(app.getWindowWidth()));
        setYPos(r.nextInt(app.getWindowHeight()));
        health = 100;
    }

    /**
     * Constructor that returns a copy of existing EnemyCharacter
     * @param enemyCharacter {@code EnemyCharacter}
     */
    private EnemyCharacter(EnemyCharacter enemyCharacter) {
        super(enemyCharacter.id, enemyCharacter.app);
        setMovementSpeed(enemyCharacter.getMovementSpeed());
        setHeight(enemyCharacter.getHeight());
        setWidth(enemyCharacter.getWidth());
        setColor(enemyCharacter.getColor());

        for (int i = 0; i < this.app.objects.size(); i ++){
            if(this.app.objects.get(i).getId() == Id.PLAYER) {
                this.player = this.app.objects.get(i);
                break;
            }
        }

        setXPos(r.nextInt(app.getWindowWidth()));
        setYPos(r.nextInt(app.getWindowHeight()));
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
     * This implementation of the {@code tick}-method will make the {@code GameObject} follow <br>
     * the {@code GameObject} stored in the {@code GameObject player}-instance variable.
     * <p>
     *     It also implements {@code collision}.
     * </p>
     */
    @Override
    public void tick() {
        movementPattern1(player);

        collision();
        if(health <= 0){
            this.id = Id.DEAD;
            HeadUpDisplay.setHealth(HeadUpDisplay.getHealth()+2);
            HeadUpDisplay.setCounter(HeadUpDisplay.getCounter()+1);
            Bullet bul1 = new Bullet(Id.BULLET, app);
            Bullet bul2 = new Bullet(Id.BULLET, app);
            Bullet bul3 = new Bullet(Id.BULLET, app);
            Bullet bul4 = new Bullet(Id.BULLET, app);
            bul1.setVelX(10);
            bul1.setVelY(10);
            bul1.setXPos(this.xPos);
            bul1.setYPos(this.yPos);
            bul2.setVelX(-10);
            bul2.setVelY(-10);
            bul2.setXPos(this.xPos);
            bul2.setYPos(this.yPos);
            bul3.setVelX(-10);
            bul3.setVelY(10);
            bul3.setXPos(this.xPos);
            bul3.setYPos(this.yPos);
            bul4.setVelX(10);
            bul4.setVelY(-10);
            bul4.setXPos(this.xPos);
            bul4.setYPos(this.yPos);
            app.addObject(bul1);
            app.addObject(bul2);
            app.addObject(bul3);
            app.addObject(bul4);
        }
    }

    /**
     * This implementation of {@code collision} detect collision with {@code Id.BULLET}.
     * <br> Uses {@code hitBox} and {@code Rectangle}.
     * @see Rectangle
     * @see Id
     */
    private void collision(){
        for (int i = 0; i < app.objects.size(); i++) {
            if(app.objects.get(i).getId() == Id.BULLET && this.hitBox().intersects(app.objects.get(i).hitBox())){
                // Collision
                health -= 10;
                // Remove bullet from game.
                app.objects.get(i).setId(Id.DEAD);
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
        g.fillRect((int) xPos, (int) yPos, width, height);
        g.setColor(Color.GREEN);
        g.fillRect((int) xPos,(int) yPos + (height/3), width, height/3);
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
        return new Rectangle((int) xPos, (int) yPos, width, height);
    }
}
