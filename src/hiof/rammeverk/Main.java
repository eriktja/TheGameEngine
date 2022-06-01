package hiof.rammeverk;

import hiof.rammeverk.TheGameEngine.Window.GameBuilder;
import hiof.rammeverk.TheGameEngine.Behavior.GameLoopExample;
import hiof.rammeverk.TheGameEngine.*;
import hiof.rammeverk.TheGameEngine.GameObjects.*;
import hiof.rammeverk.TheGameEngine.Behavior.ObjectCreator;

import java.awt.*;

public class Main {
    public static void main(String[] args) {
        App app = new App(1900, 1000, ObjectCreator.create());

        GameLoopExample loop = new GameLoopExample(app);
        GameBuilder game = GameBuilder.createGame(app, loop);

        game.setKeyAdapter(new KeyInput(app, game.getWindow()));
        PlayerCharacter player = PlayerCharacter.create(Id.PLAYER, app);
        EnemyCharacter enemyCharacter = EnemyCharacter.create(Id.ENEMY_1, app);
        enemyCharacter.setColor(Color.PINK);
        app.getCreator().addPrototype(enemyCharacter);

        app.getCreator().addPrototype(player);
        app.getCreator().addPrototype(Bullet.create(Id.BULLET, app));
        app.getCreator().addPrototype(Bullet.create(Id.BULLET_2, app));
        app.getCreator().addPrototype(EnemyCharacter.create(Id.SMART_ENEMY, app));
        EnemyCharacter enmy = EnemyCharacter.create(Id.BASIC_ENEMY, app);
        //app.getCreator().addPrototype(new GameObject(Id.BASIC_ENEMY, app));
        //lol
        enmy.setColor(Color.BLACK);
        app.getCreator().addPrototype(enmy);

        game.start();
    }
}