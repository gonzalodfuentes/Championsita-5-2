package com.proyecto.championsita;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

public class Ball {
    private static final float PPM = 100; // Pixeles por metro

    private Body body;
    private Texture texture;

    public Ball(World world, float x, float y, float radius, boolean isDynamic) {
        // Cargar la textura de la pelota
        texture = new Texture("ball.png");

        // Definir el tipo de cuerpo (dinámico o estático)
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(x, y);
        bodyDef.type = isDynamic ? BodyDef.BodyType.DynamicBody : BodyDef.BodyType.StaticBody;

        // Crear el cuerpo en el mundo
        body = world.createBody(bodyDef);

        // Crear la forma circular de la pelota
        CircleShape shape = new CircleShape();
        shape.setRadius(radius);

        // Definir las propiedades del fixture
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1.0f;
        fixtureDef.restitution = 0.6f; // Rebote de la pelota

        // Crear el fixture y liberar la forma
        body.createFixture(fixtureDef);
        shape.dispose();
    }

    public Body getBody() {
        return body;
    }
    
    // Método para renderizar la pelota
    public void render(SpriteBatch batch) {
        batch.draw(texture,
                   (body.getPosition().x * PPM) - (texture.getWidth() / 2 / PPM),
                   (body.getPosition().y * PPM) - (texture.getHeight() / 2 / PPM),
                   texture.getWidth() / PPM,
                   texture.getHeight() / PPM);
    }

    // Liberar recursos
    public void dispose() {
        texture.dispose();
    }
}