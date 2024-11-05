package com.proyecto.championsita;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.Gdx;

public class Ball {
    public float posX, posY;
    public Vector2 velocity = new Vector2();
    public final float size = 30f;
    private final float gravity = -10f;
    private Texture texture;

    public Ball(float startX, float startY, String texturePath) {
        posX = startX;
        posY = startY;
        texture = new Texture(Gdx.files.internal(texturePath));
    }

    public void update(float deltaTime) {
        velocity.y += gravity * deltaTime;
        posX += velocity.x * deltaTime;
        posY += velocity.y * deltaTime;

        // Aplicar fricción solo cuando la pelota esté en el suelo
        if (posY <= 10) {
            posY = 10;
            velocity.y = 0;
            velocity.x *= 0.9f;
        }

        // Colisiones con los límites del campo
        if (posX < Gdx.graphics.getWidth() / 2 - 400) {
            posX = Gdx.graphics.getWidth() / 2 - 400;
            velocity.x = 0;
        } else if (posX + size > Gdx.graphics.getWidth() / 2 + 400) {
            posX = Gdx.graphics.getWidth() / 2 + 400 - size;
            velocity.x = 0;
        }
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, posX, posY, size, size);
    }

    public void dispose() {
        texture.dispose();
    }
}
