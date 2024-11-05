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
    private final float fieldWidth = 400;
    private final float initialPlayerY = 20;

    @Override
    public void create() {
        batch = new SpriteBatch();
        // Jugador 1 (izquierda): usa la tecla 'P' para patear
        player1 = new Player(
            Gdx.graphics.getWidth() / 2 - 200, 
            initialPlayerY, 
            Input.Keys.A, 
            Input.Keys.D, 
            Input.Keys.W, 
            Input.Keys.P, // tecla de pateo para el jugador 1
            "messi.png"
        );

        // Jugador 2 (derecha): usa la tecla 'Control Derecho' para patear
        player2 = new Player(
            Gdx.graphics.getWidth() / 2 + 200, 
            initialPlayerY, 
            Input.Keys.LEFT, 
            Input.Keys.RIGHT, 
            Input.Keys.UP, 
            Input.Keys.CONTROL_RIGHT, // tecla de pateo para el jugador 2
            "messi.png"
        );

        ball = new Ball(Gdx.graphics.getWidth() / 2, 10, "pelota.png");
    }


    @Override
    public void render() {
        if (Gdx.input.isKeyPressed(Input.Keys.Z)) {
            Gdx.app.exit();
        }

        ScreenUtils.clear(0.40f, 0.40f, 0.40f, 1f);

        player1.update(Gdx.graphics.getDeltaTime());
        player2.update(Gdx.graphics.getDeltaTime());
        ball.update(Gdx.graphics.getDeltaTime());

        Colision.checkCollision(player1, player2, ball);

        batch.begin();
        player1.render(batch);
        player2.render(batch);
        ball.render(batch);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        player1.dispose();
        player2.dispose();
        ball.dispose();
    }
}
