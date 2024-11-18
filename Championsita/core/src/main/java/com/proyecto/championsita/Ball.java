package com.proyecto.championsita;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

public class Ball {
    private static final float PPM = 100;

    private Body body;
    private Texture texture;
    private float radius;
    
    public Ball(World world, float x, float y, float radius, boolean isDynamic) {
        this.radius = radius;

        // Cargar la textura de la pelota (aseg�rate de que la ruta sea correcta)
        texture = new Texture("ball.png"); // Ajusta la ruta seg�n tu proyecto

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
        fixtureDef.restitution = 0.4f; // Rebote de la pelota
        fixtureDef.friction = 1f;

        // Crear el fixture y liberar la forma
        body.createFixture(fixtureDef);
        shape.dispose();
        
        if (texture.getWidth() != texture.getHeight()) {
            System.err.println("La textura de la pelota no es cuadrada. Podr�a causar distorsiones.");
        }
    }

    public Body getBody() {
        return body;
    }

    // M�todo para renderizar la pelota con la imagen ajustada a la circunferencia
    public void render(SpriteBatch batch) {
        // Calcular el di�metro en p�xeles seg�n el radio y el PPM
        float diameter = radius * 2 * 100; // Tama�o f�sico de la pelota en p�xeles

        // Dibujar la textura escalada y centrada en la posici�n del cuerpo
        batch.draw(texture,
                (body.getPosition().x * 100) - diameter / 2, // Posici�n X centrada
                (body.getPosition().y * 100) - diameter / 2, // Posici�n Y centrada
                diameter, // Escalar ancho al di�metro
                diameter); // Escalar alto al di�metro
        
        batch.draw(texture, body.getPosition().x - radius, body.getPosition().y - radius, radius * 2, radius * 2);
    }

    // Liberar los recursos de la textura
    public void dispose() {
        texture.dispose();
    }
}
