package hiof.rammeverk.TheGameEngine.GameObjects;

import hiof.rammeverk.TheGameEngine.ApplicationHandler;

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

    /** Returns a new instance of bullet with fixed velX speed.
     *
     * @param id The ID of the instance. Used to determine action in regard to other objects
     * @param app {@code ApplicationHandler}.
     */
    public Bullet(Id id, ApplicationHandler app) {
        super(id, app);
        setWidth(6);
        setHeight(6);
        setVelX(15);
        setVelY(0);
    }

    /**
     * Direction of bullet depends on movement of shooter.
     * @param bullet {@code Bullet}.
     * @see GameObject
     */
    public Bullet(Bullet bullet){
        super(bullet.getId(), bullet.getApp());

        setVelX(bullet.getVelX());
        setHeight(bullet.getHeight());
        setWidth(bullet.getWidth());

        for(int i = 0; i < getApp().objects.size(); i++){
            if(getApp().objects.get(i).getId() == Id.PLAYER)
                shooter = getApp().objects.get(i);
        }
        if (shooter.isMovingLeft()) {
            setXPos(shooter.getXPos());
            setYPos(shooter.getYPos() + (shooter.getHeight() / 2.0f));
            setVelX(getVelX() * -1);
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
        setXPos(getXPos()+getVelX());
        setYPos(getYPos()+getVelY());

        if(getYPos() <= 0 || getYPos() >= getApp().getWindowHeight() - 32) setId(Id.DEAD);
        if(getXPos() <= 0 || getXPos() >= getApp().getWindowWidth() - 32)  setId(Id.DEAD);
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.red);
        g.fillRect((int) getXPos(), (int) getYPos(), getWidth(), getHeight());
    }

    @Override
    public Rectangle hitBox() {
        return new Rectangle((int) getXPos(), (int) getYPos(), getWidth(), getHeight());
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
