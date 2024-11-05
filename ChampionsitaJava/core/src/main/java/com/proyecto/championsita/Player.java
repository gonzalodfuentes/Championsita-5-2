package com.proyecto.championsita;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Player {
    public float posX, posY;
    public Vector2 velocity = new Vector2();
    public final float size = 50f;
    private final float speed = 110f;
    private final float gravity = -800f;
    private final float jumpImpulse = 300f;
    private final float kickPowerX = 120f;
    private final float kickPowerY = 120f;
    private final float kickPowerMultiplier = 1.5f;
    public boolean canJump = true;
    private final int leftKey, rightKey, jumpKey;
    final int kickKey;
    private Texture texture;
    private boolean facingRight = true;

    public Player(float startX, float startY, int leftKey, int rightKey, int jumpKey, int kickKey, String texturePath) {
        posX = startX;
        posY = startY;
        this.leftKey = leftKey;
        this.rightKey = rightKey;
        this.jumpKey = jumpKey;
        this.kickKey = kickKey;
        texture = new Texture(Gdx.files.internal(texturePath));
    }

    public void handleInput() {
        if (Gdx.input.isKeyPressed(leftKey) && posX > Gdx.graphics.getWidth() / 2 - 400) {
            velocity.x = -speed;
            facingRight = false;
        } else if (Gdx.input.isKeyPressed(rightKey) && posX + size < Gdx.graphics.getWidth() / 2 + 400) {
            velocity.x = speed;
            facingRight = true;
        } else {
            velocity.x = 0;
        }

        if (Gdx.input.isKeyPressed(jumpKey) && canJump) {
            velocity.y = jumpImpulse;
            canJump = false;
        }
    }

    public void update(float deltaTime) {
        // Aplicar gravedad
        velocity.y += gravity * deltaTime;
        posY += velocity.y * deltaTime;

        // Evitar que el jugador atraviese el suelo
        if (posY <= 20) {
            posY = 20;
            canJump = true;
            velocity.y = 0;
        }

        // Movimiento horizontal
        posX += velocity.x * deltaTime;

        // Mantener al jugador dentro de los límites del campo
        if (posX < Gdx.graphics.getWidth() / 2 - 400) {
            posX = Gdx.graphics.getWidth() / 2 - 400;
        } else if (posX + size > Gdx.graphics.getWidth() / 2 + 400) {
            posX = Gdx.graphics.getWidth() / 2 + 400 - size;
        }
    }

    public void render(SpriteBatch batch) {
        if (facingRight) {
            batch.draw(texture, posX, posY, size, size);
        } else {
            batch.draw(texture, posX + size, posY, -size, size);
        }
    }

    public void handleKick(Ball ball, boolean kickReady) {
        if (Gdx.input.isKeyPressed(kickKey) && kickReady) {
            kickBall(ball);
        }
    }

    public void kickBall(Ball ball) {
        ball.velocity.x = (facingRight ? kickPowerX : -kickPowerX) * kickPowerMultiplier * 2f;
        ball.velocity.y = kickPowerY * kickPowerMultiplier * 1.5f;
    }

    public void dispose() {
        if (texture != null) {
            texture.dispose();
        }
    }
}