package com.proyecto.championsita;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Ball {
    private Texture texture;
    private float posX, posY;
    private float velocityX, velocityY;
    private final float speed = 150f; // Velocidad de la pelota

    public Ball(float startX, float startY) {
        texture = new Texture("pelota.png"); // Asegúrate de tener una imagen de la pelota
        posX = startX;
        posY = startY;
    }

    public void update(float deltaTime) {
        posX += velocityX * deltaTime;
        posY += velocityY * deltaTime;

        // Aquí podrías agregar lógica para que la pelota rebote al tocar los bordes de la pantalla
        if (posX < 0 || posX + texture.getWidth() > com.badlogic.gdx.Gdx.graphics.getWidth()) {
            velocityX = -velocityX; // Cambia la dirección si toca los bordes
        }
        if (posY < 0 || posY + texture.getHeight() > com.badlogic.gdx.Gdx.graphics.getHeight()) {
            velocityY = -velocityY; // Cambia la dirección si toca los bordes
        }
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, posX, posY);
    }

    public void kick(float kickForce) {
        velocityX += kickForce; // Cambia la velocidad de la pelota al patear
    }

    public void dispose() {
        texture.dispose();
    }
}
