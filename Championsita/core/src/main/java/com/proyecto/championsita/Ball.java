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
    private float radius;

    public Ball(World world, float x, float y, float radius, boolean isDynamic) {
        this.radius = radius;

        // Cargar la textura de la pelota (aseg�rate de que la imagen sea cuadrada)
        texture = new Texture("ball.png"); // Aseg�rate de que esta textura sea adecuada para la pelota

        // Definir el tipo de cuerpo (din�mico o est�tico)
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(x, y);
        bodyDef.type = isDynamic ? BodyDef.BodyType.DynamicBody : BodyDef.BodyType.StaticBody;

        // Crear el cuerpo en el mundo
        body = world.createBody(bodyDef);

        // Crear la forma circular de la pelota
        CircleShape shape = new CircleShape();
        shape.setRadius(radius); // Establecer el radio de la pelota

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

    // M�todo para renderizar la pelota con la imagen ajustada a la circunferencia
    public void render(SpriteBatch batch) {
        // El di�metro en p�xeles es el radio multiplicado por 2 y luego escalado por PPM
        float width = radius * 2 * PPM;  // El di�metro de la pelota en p�xeles
        float height = radius * 2 * PPM; // El di�metro de la pelota en p�xeles

        // Dibujar la textura de la pelota, ajustada al tama�o y centrada en la posici�n del cuerpo
        batch.draw(texture,
                   (body.getPosition().x * PPM) - width / 2, // X: centrado en la posici�n del cuerpo
                   (body.getPosition().y * PPM) - height / 2, // Y: centrado en la posici�n del cuerpo
                   width, // Ancho de la textura
                   height); // Alto de la textura
    }

    // Liberar los recursos de la textura
    public void dispose() {
        texture.dispose();
    }
}
