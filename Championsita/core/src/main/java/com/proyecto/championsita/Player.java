package com.proyecto.championsita;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.math.Vector2;

public class Player {
    private static final float PPM = 100; // Pixeles por metro

    private boolean isJumping = false;
    private float jumpForce = 5f; // Ajusta este valor según sea necesario
    private Body body;
    private Texture texture;
    private boolean canJump = true;
    private float maxJumpTime = 0.3f; // Tiempo máximo en el aire
    private float jumpTimeCounter = 0;

    private Texture playerTexture;

    public Player(World world, float x, float y, float radius) {
        playerTexture = new Texture("messi.png");

        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(x, y);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.fixedRotation = true;

        body = world.createBody(bodyDef);

        CircleShape shape = new CircleShape();
        shape.setRadius(radius);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1.0f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0.0f;

        body.createFixture(fixtureDef);
        shape.dispose();
    }

    public void update() {
        if (Math.abs(body.getLinearVelocity().y) < 0.01f) {
            canJump = true;
        } else {
            canJump = false;
        }
    }

    public void render(SpriteBatch batch) {
        batch.draw(playerTexture,
                (body.getPosition().x * PPM) - (playerTexture.getWidth() / 2 / PPM),
                (body.getPosition().y * PPM) - (playerTexture.getHeight() / 2 / PPM),
                playerTexture.getWidth() / PPM,
                playerTexture.getHeight() / PPM);
    }

    public void handleInput(Ball ball, boolean isPlayerA) {
        float speed = 2f;

        if (isPlayerA) { // Controles para el Jugador A
            if (Gdx.input.isKeyPressed(Input.Keys.A)) {
                body.setLinearVelocity(new Vector2(-speed, body.getLinearVelocity().y));
            }
            if (Gdx.input.isKeyPressed(Input.Keys.D)) {
                body.setLinearVelocity(new Vector2(speed, body.getLinearVelocity().y));
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.W) && canJump) {
                jump();
            }
            // Pateo con ESPACIO
            if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
                kick(ball);
            }
        } else { // Controles para el Jugador B
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                body.setLinearVelocity(new Vector2(-speed, body.getLinearVelocity().y));
            }
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                body.setLinearVelocity(new Vector2(speed, body.getLinearVelocity().y));
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.UP) && canJump) {
                jump();
            }
            // Pateo con SHIFT DERECHO
            if (Gdx.input.isKeyJustPressed(Input.Keys.CONTROL_RIGHT)) {
                kick(ball);
            }
        }
    }

    private void kick(Ball ball) {
        float distance = body.getPosition().dst(ball.getBody().getPosition());
        float maxKickDistance = 1f; // Distancia máxima para patear

        if (distance <= maxKickDistance) {
            Vector2 direction = ball.getBody().getPosition().cpy().sub(body.getPosition()).nor();
            float kickForce = 1.1f; // Fuerza del pateo
            ball.getBody().applyLinearImpulse(direction.scl(kickForce), ball.getBody().getWorldCenter(), true);
        }
    }


    private void jump() {
        isJumping = true;
        jumpTimeCounter = 0;
        body.setLinearVelocity(new Vector2(body.getLinearVelocity().x, jumpForce));
    }

    public Body getBody() {
        return body;
    }

    public void dispose() {
        playerTexture.dispose();
    }
}
