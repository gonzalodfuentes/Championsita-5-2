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
    private float jumpTimeCounter = 0; // Contador de tiempo en el aire
    private float kickCharge = 0f;
    private float maxKickCharge = 1f;
    private boolean isKicking = false;
    private float strongKickForce = 10f; // Fuerza para el pateo fuerte
    private float lobKickForce = 5f; // Fuerza para el pateo pinchado    
    private Texture playerTexture;

    public Player(World world, float x, float y, float radius) {
        playerTexture = new Texture("messi.png");
        // Definir el tipo de cuerpo
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(x, y);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.fixedRotation = true; // Evitar que el cuerpo gire

        // Crear el cuerpo en el mundo
        body = world.createBody(bodyDef);

        // Crear la forma circular del jugador
        CircleShape shape = new CircleShape();
        shape.setRadius(radius);

        // Definir las propiedades del fixture
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1.0f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0.0f;

        // Crear el fixture y liberar la forma
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
    
    // Método para renderizar el jugador
    public void render(SpriteBatch batch) {
        batch.draw(playerTexture,
                   (body.getPosition().x * PPM) - (playerTexture.getWidth() / 2 / PPM),
                   (body.getPosition().y * PPM) - (playerTexture.getHeight() / 2 / PPM),
                   playerTexture.getWidth() / PPM,
                   playerTexture.getHeight() / PPM);
    }

    public void handleInput(Ball ball) {
        float speed = 2f;
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            body.setLinearVelocity(new Vector2(-speed, body.getLinearVelocity().y));
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            body.setLinearVelocity(new Vector2(speed, body.getLinearVelocity().y));
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP) && canJump) {
            isJumping = true;
            jumpTimeCounter = 0;
            body.setLinearVelocity(new Vector2(body.getLinearVelocity().x, jumpForce));
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP) && isJumping) {
            if (jumpTimeCounter < maxJumpTime) {
                body.setLinearVelocity(new Vector2(body.getLinearVelocity().x, jumpForce));
                jumpTimeCounter += Gdx.graphics.getDeltaTime();
            } else {
                isJumping = false;
            }
        }
        if (!Gdx.input.isKeyPressed(Input.Keys.UP)) {
            isJumping = false;
        }

        // Disparo fuerte
        if (Gdx.input.isKeyJustPressed(Input.Keys.Z)) {
            kick(ball, strongKickForce, false);
        }

        // Disparo pinchado
        if (Gdx.input.isKeyJustPressed(Input.Keys.X)) {
            kick(ball, lobKickForce, true);
        }
    }

    private void kick(Ball ball, float force, boolean lob) {
        float distance = body.getPosition().dst(ball.getBody().getPosition());
        float maxKickDistance = 1f; // Ajusta este valor según sea necesario

        // Solo permitir patear si la pelota está a la derecha
        if (distance <= maxKickDistance && ball.getBody().getPosition().x > body.getPosition().x) {
            Vector2 direction = ball.getBody().getPosition().cpy().sub(body.getPosition()).nor();

            if (lob) {
                direction.y += 1f; // Incrementa para un efecto pinchado
            }

            direction.nor();

            ball.getBody().applyLinearImpulse(direction.scl(force), ball.getBody().getWorldCenter(), true);
        }
    }
    

    // Liberar recursos
    public void dispose() {
        texture.dispose();
    }
}