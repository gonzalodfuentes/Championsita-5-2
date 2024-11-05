package com.proyecto.championsita;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Ball {
    public float posX, posY;
    public Vector2 velocity = new Vector2(); // Hacer pública la velocidad para que se pueda acceder desde el jugador
    public float size = 30f;
    private final float gravity = -800f; // Aumentar la gravedad para un efecto más realista
    private Texture texture;

    public Ball(float startX, float startY, String texturePath) {
        posX = startX;
        posY = startY;
        velocity.set(200f, 0); // Velocidad inicial horizontal
        texture = new Texture(Gdx.files.internal(texturePath));
    }
    
    public void update(float deltaTime) {
        // Aplicar gravedad
        velocity.y += gravity * deltaTime;
        posY += velocity.y * deltaTime;

        // Evitar que la pelota caiga por debajo de un cierto nivel
        if (posY <= 10) {
            posY = 10;
            velocity.y = -velocity.y * 0.8f; // Rebote con pérdida de energía
        }


        // Mantener la pelota dentro de los límites del campo
        if (posX < 0) {
            posX = 0;
            velocity.x = -velocity.x * 0.9f; // Rebote lateral con pérdida
        } else if (posX + size > Gdx.graphics.getWidth()) {
            posX = Gdx.graphics.getWidth() - size;
            velocity.x = -velocity.x * 0.9f; // Rebote lateral con pérdida
        }

        posX += velocity.x * deltaTime; // Movimiento horizontal
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, posX, posY, size, size);
    }

    public void dispose() {
        if (texture != null) {
            texture.dispose();
        }
    }
}
