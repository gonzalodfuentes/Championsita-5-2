package com.proyecto.championsita;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    private Player player;
    private Ball ball; // Definimos el campo para la pelota

    @Override
    public void create() {
        batch = new SpriteBatch();
        ball = new Ball(); // Inicializa la pelota en una posición
        player = new Player(ball); // Pasa la pelota al jugador
    }

    @Override
    public void render() {
        // Limpiar pantalla
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        
        // Actualizar el jugador y la pelota
        player.update(Gdx.graphics.getDeltaTime());
        ball.update(Gdx.graphics.getDeltaTime());
        
        // Renderizar
        batch.begin();
        player.render(batch);
        ball.render(batch);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        player.dispose();
        ball.dispose(); // Libera la memoria de la pelota
    }
}
