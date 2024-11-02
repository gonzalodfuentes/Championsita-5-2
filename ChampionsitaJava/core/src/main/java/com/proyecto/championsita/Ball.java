package com.proyecto.championsita;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Ball {
    private Texture image;
    private float posX, posY;
    private float velocityX, velocityY;
    private final float gravity = -9.81f; // Ajusta la gravedad para que caiga más rápido
    private boolean isFalling = true;

    public Ball() {
        image = new Texture("pelota.png"); // Asegúrate de tener esta textura
        posX = Gdx.graphics.getWidth() / 2 - image.getWidth() * 0.25f; // Centrado en el inicio
        posY = Gdx.graphics.getHeight() - image.getHeight() * 0.25f; // Comienza en la parte superior
    }

    public void kick(float force, float direction) {
        // Cambia la velocidad de la pelota al patearla
        velocityX = direction * force; // Cambia la dirección de la patada
        velocityY = 200f; // Cambia la velocidad vertical para que no sea tan rápida
    }

    public void update(float deltaTime) {
        if (isFalling) {
            velocityY += gravity; // Aplica la gravedad
        }

        posX += velocityX * deltaTime;
        posY += velocityY * deltaTime;

        // Evitar que la pelota atraviese el suelo
        if (posY < 10) {
            posY = 10; // Ajustar la posición en el suelo
            isFalling = false; // Deja de caer
            velocityY = 0; // Detener la velocidad vertical
        }
    }

    public void render(SpriteBatch batch) {
        float scale = 0.25f; // Cambiar el tamaño de la pelota
        batch.draw(image, posX, posY, image.getWidth() * scale, image.getHeight() * scale);
    }

    public void dispose() {
        image.dispose();
    }

    public float getX() {
        return posX;
    }

    public float getY() {
        return posY;
    }

    public float getWidth() {
        return image.getWidth() * 0.25f; // Ajustar el tamaño
    }

    public float getHeight() {
        return image.getHeight() * 0.25f; // Ajustar el tamaño
    }
}
