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
    //private static final float PPM = 100; // Pixeles por metro

    private SpriteBatch batch;
    private boolean isJumping = false;
    private float jumpForce = 5f; // Ajusta este valor seg�n sea necesario
    private Body body;
    private Texture texture; // Textura adicional para el jugador
    private boolean canJump = true;
    private float maxJumpTime = 0.3f; // Tiempo m�ximo en el aire
    private float jumpTimeCounter = 0;

    private float width = 100; // Ancho del jugador
    private float height = 100; // Altura del jugador

    public Player(World world, float x, float y, float radius) {
        texture = new Texture("messi.png"); // Textura del jugador

        // Escala de la textura en funci�n de PPM
        width = radius * 2; // Ancho del jugador en metros
        height = radius * 2; // Alto del jugador en metros

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
        /*if (Math.abs(body.getLinearVelocity().y) < 0.01f) {
            canJump = true;
        } else {
            canJump = false;
        }*/
    	canJump = Math.abs(body.getLinearVelocity().y) < 0.01f;
    }

    public void render(SpriteBatch batch) {
   
        batch.draw(texture,
                (body.getPosition().x - width /2 ), // Convertir a p�xeles
                (body.getPosition().y - height /2),
                width,
                height);

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
            // Pateo con CONTROL DERECHO
            if (Gdx.input.isKeyJustPressed(Input.Keys.CONTROL_RIGHT)) {
                kick(ball);
            }
        }

        // Salir del juego con Z
        if (Gdx.input.isKeyPressed(Input.Keys.Z)) {
            System.exit(0);
        }
    }

    private void kick(Ball ball) {
        float distance = body.getPosition().dst(ball.getBody().getPosition());
        float maxKickDistance = 1f; // Distancia m�xima para patear

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
        texture.dispose();
    }
}
