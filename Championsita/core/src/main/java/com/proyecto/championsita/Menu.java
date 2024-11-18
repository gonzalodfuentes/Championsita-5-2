package com.proyecto.championsita;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Menu extends ApplicationAdapter {
    private SpriteBatch batch;
    private Texture buttonTexture; // Imagen para el botón
    private Rectangle buttonBounds; // Área del botón
    private BitmapFont font; // Fuente para el texto

    private boolean startMatch = false; // Bandera para iniciar el partido

    @Override
    public void create() {
        batch = new SpriteBatch();
        buttonTexture = new Texture("juegazo.png"); // Asegúrate de que esta imagen exista
        font = new BitmapFont();

        // Definir el tamaño y posición del botón
        float buttonWidth = 300; // Ancho en píxeles
        float buttonHeight = 100; // Alto en píxeles
        float buttonX = (Gdx.graphics.getWidth() - buttonWidth) / 2; // Centrado horizontalmente
        float buttonY = (Gdx.graphics.getHeight() - buttonHeight) / 2; // Centrado verticalmente
        buttonBounds = new Rectangle(buttonX, buttonY, buttonWidth, buttonHeight);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        // Dibujar el botón
        batch.draw(buttonTexture, buttonBounds.x, buttonBounds.y, buttonBounds.width, buttonBounds.height);

        // Dibujar texto sobre el botón
        String buttonText = "Start Match";
        GlyphLayout layout = new GlyphLayout(font, buttonText);
        font.draw(batch, buttonText, 
                  buttonBounds.x + (buttonBounds.width - layout.width) / 2,
                  buttonBounds.y + (buttonBounds.height + layout.height) / 2);

        batch.end();

        // Detectar clic en el botón
        if (Gdx.input.justTouched()) {
            Vector2 touchPos = new Vector2(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY());
            if (buttonBounds.contains(touchPos)) {
                startMatch = true;
            }
        }
    }

    public boolean shouldStartMatch() {
        return startMatch;
    }

    @Override
    public void dispose() {
        batch.dispose();
        buttonTexture.dispose();
        font.dispose();
    }
}
