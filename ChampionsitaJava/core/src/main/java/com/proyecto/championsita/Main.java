package com.proyecto.championsita;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    private Player player1, player2;
    private Ball ball;
    private CollisionManager collisionManager;
    private boolean player1KickReady = true;
    private boolean player2KickReady = true;

    @Override
    public void create() {
        batch = new SpriteBatch();
        player1 = new Player(Gdx.graphics.getWidth() / 2 - 200, 20, Input.Keys.A, Input.Keys.D, Input.Keys.W, Input.Keys.P, "messi.png");
        player2 = new Player(Gdx.graphics.getWidth() / 2 + 200, 20, Input.Keys.LEFT, Input.Keys.RIGHT, Input.Keys.UP, Input.Keys.CONTROL_RIGHT, "messi.png");
        ball = new Ball(Gdx.graphics.getWidth() / 2, 10, "pelota.png");
        collisionManager = new CollisionManager(player1, player2, ball);
    }

    @Override
    public void render() {
        if (Gdx.input.isKeyPressed(Input.Keys.Z)) {
            Gdx.app.exit();
        }

        ScreenUtils.clear(0.40f, 0.40f, 0.40f, 1f);

        player1.handleInput();
        player2.handleInput();

        player1.update(Gdx.graphics.getDeltaTime());
        player2.update(Gdx.graphics.getDeltaTime());
        ball.update(Gdx.graphics.getDeltaTime());

        collisionManager.checkCollisions();

        handleKicking();

        batch.begin();
        player1.render(batch);
        player2.render(batch);
        ball.render(batch);
        batch.end();
    }

    private void handleKicking() {
        player1.handleKick(ball, player1KickReady);
        player2.handleKick(ball, player2KickReady);

        if (!Gdx.input.isKeyPressed(player1.kickKey)) player1KickReady = true;
        if (!Gdx.input.isKeyPressed(player2.kickKey)) player2KickReady = true;
    }

    @Override
    public void dispose() {
        batch.dispose();
        player1.dispose();
        player2.dispose();
        ball.dispose();
    }
}