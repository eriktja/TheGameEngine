package hiof.rammeverk.TheGameEngine;

import java.awt.Rectangle;
import java.awt.Color;
import java.awt.Graphics;

/**
 * Example of a {@code GameObject}-subclass.
 * <p>
 *     This class has a movement horizontally and will be deleted when reaching the edge of the screen.
 * </p>
 * @see GameObject
 * @see Id
 * @see Rectangle
 */
public class Bullet extends GameObject {

    private static Bullet prototype;
    private GameObject shooter;
    private final int height = 6;
    private final int width = 6;

    /** Returns a new instance of bullet
     *
     * @param id The ID of the instance. Used to determine action in regard to other objects
     * @param app Instance of {@code ApplicationHandler}.
     */
    public Bullet(Id id, ApplicationHandler app) {
        super(id, app);
        velX = 15;
        velY = 0;
    }

    public Bullet(Bullet bullet){
        super(bullet.id, bullet.app);

        setVelX(bullet.getVelX());
        setHeight(bullet.getHeight());
        setWidth(bullet.getWidth());

        for(int i = 0; i < app.objects.size(); i++){
            if(app.objects.get(i).getId() == Id.PLAYER)
                shooter = app.objects.get(i);
        }
        if (shooter.movingLeft) {
            setXPos(shooter.getXPos());
            setYPos(shooter.getYPos() + (shooter.getHeight() / 2.0f));
            velX *= -1;
        }else {
            setXPos(shooter.getXPos() + shooter.getWidth());
            setYPos(shooter.getYPos() + (shooter.getHeight() / 2.0f));
        }
    }

    public static Bullet create(Id id, ApplicationHandler app){
        if(prototype == null)
            prototype = new Bullet(id, app);
        return prototype;
    }

    @Override
    public void tick() {
        xPos += velX;
        yPos += velY;

        if(yPos <= 0 || yPos >= app.getWindowHeight() - 32) this.id = Id.DEAD;
        if(xPos <= 0 || xPos >= app.getWindowWidth() - 32)  this.id = Id.DEAD;
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.red);
        g.fillRect((int) xPos, (int) yPos, width, height);
    }

    @Override
    public Rectangle hitBox() {
        return new Rectangle((int) xPos, (int) yPos, width, height);
    }

    /**
     * Create a new instance of the class which is a copy of the prototype.
     * @return Bullet copy of prototype.
     * @see Cloneable
     */
    @Override
    public Bullet cloneObject(){
        return new Bullet(this);
    }

}
