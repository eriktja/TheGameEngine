package hiof.rammeverk;

import hiof.rammeverk.TheGameEngine.Window.GameBuilder;
import hiof.rammeverk.TheGameEngine.Behavior.GameLoopExample;
import hiof.rammeverk.TheGameEngine.*;
import hiof.rammeverk.TheGameEngine.GameObjects.*;
import hiof.rammeverk.TheGameEngine.Behavior.ObjectCreator;


public class Main {
    public static void main(String[] args) {
        App app = new App(1000, 800, ObjectCreator.create());

        GameLoopExample loop = new GameLoopExample(app);
        GameBuilder game = GameBuilder.createGame(app, loop);
        game.setKeyAdapter(new KeyInput(app, game));

        PlayerCharacter player = PlayerCharacter.create(Id.PLAYER, app);
        EnemyCharacter enemyCharacter = EnemyCharacter.create(Id.SMART_ENEMY, app);
        app.getCreator().addPrototype(enemyCharacter);
        app.getCreator().addPrototype(player);
        app.getCreator().addPrototype(Bullet.create(Id.BULLET, app));

        game.start();
    }
}