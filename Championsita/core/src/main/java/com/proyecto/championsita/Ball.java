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

        // Cargar la textura de la pelota (asegúrate de que la ruta sea correcta)
        texture = new Texture("ball.png"); // Ajusta la ruta según tu proyecto

        // Definir el tipo de cuerpo (dinámico o estático)
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
        
        if (texture.getWidth() != texture.getHeight()) {
            System.err.println("La textura de la pelota no es cuadrada. Podría causar distorsiones.");
        }
    }

    public Body getBody() {
        return body;
    }

    // Método para renderizar la pelota con la imagen ajustada a la circunferencia
    public void render(SpriteBatch batch) {
        // Calcular el diámetro en píxeles según el radio y el PPM
        float diameter = radius * 2 * PPM; // Tamaño físico de la pelota en píxeles

        // Dibujar la textura escalada y centrada en la posición del cuerpo
        batch.draw(texture,
                (body.getPosition().x * PPM) - diameter / 2, // Posición X centrada
                (body.getPosition().y * PPM) - diameter / 2, // Posición Y centrada
                diameter, // Escalar ancho al diámetro
                diameter); // Escalar alto al diámetro
        
        batch.draw(texture, body.getPosition().x - radius, body.getPosition().y - radius, radius * 2, radius * 2);
    }

    // Liberar los recursos de la textura
    public void dispose() {
        texture.dispose();
    }
}
