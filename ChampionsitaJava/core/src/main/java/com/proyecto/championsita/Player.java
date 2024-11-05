package com.proyecto.championsita;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

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
    private final int leftKey, rightKey, jumpKey, kickKey;
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

    public void update(float deltaTime) {
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

        velocity.y += gravity * deltaTime;
        posY += velocity.y * deltaTime;

        if (posY <= 20) {
            posY = 20;
            canJump = true;
            velocity.y = 0;
        }

        posX += velocity.x * deltaTime;
    }

    public void render(SpriteBatch batch) {
        if (facingRight) {
            batch.draw(texture, posX, posY, size, size);
        } else {
            batch.draw(texture, posX + size, posY, -size, size);
        }
    }

    public void kickBall(Ball ball) {
        ball.velocity.x = (facingRight ? kickPowerX : -kickPowerX) * kickPowerMultiplier;
        ball.velocity.y = kickPowerY * kickPowerMultiplier;
    }
    
    public int getKickKey() {
        return kickKey;
    }


    public void dispose() {
        texture.dispose();
    }
}
