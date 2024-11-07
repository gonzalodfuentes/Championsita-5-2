package com.proyecto.championsita;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Main extends ApplicationAdapter {
    private static final float PPM = 100; // Pixeles por metro

    private OrthographicCamera camera;
    private SpriteBatch batch;
    private World world;
    private Box2DDebugRenderer debugRenderer;
    private Ball ball;
    private Player player;


    @Override
    public void create() {
        // Configurar la cámara
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 16, 9); // 32 unidades de ancho por 18 de alto

        // Configurar el mundo con gravedad
        world = new World(new Vector2(0, -9.8f), true);
        debugRenderer = new Box2DDebugRenderer();
        batch = new SpriteBatch();

        // Crear la pelota
        ball = new Ball(world, 8, 5, 0.5f, true);
        
        // Crear el jugador
        player = new Player(world, 8, 5, 0.5f);

        // Crear el suelo y las paredes
        createStaticBody(8, 0.5f, 16, 0.5f); // Suelo
        createStaticBody(0, 4.5f, 0.5f, 9);  // Pared izquierda
        createStaticBody(16, 4.5f, 0.5f, 9); // Pared derecha
    }

    // Método para crear cuerpos estáticos (suelo y paredes)
    private void createStaticBody(float x, float y, float width, float height) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(x, y);
        bodyDef.type = BodyDef.BodyType.StaticBody;

        Body body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2, height / 2); // Tamaño del rectángulo

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.friction = 0.5f;

        body.createFixture(fixtureDef);
        shape.dispose();
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        debugRenderer.render(world, camera.combined);
        world.step(1/60f, 6, 2);
        camera.position.set(8, 4.5f, 0); // Centra la cámara en (8, 4.5)
        camera.update();
        player.update();
        player.handleInput(ball);

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        ball.render(batch);
        player.render(batch);
        batch.end();
    }

    @Override
    public void dispose() {
        // Liberar recursos
        world.dispose();
        debugRenderer.dispose();
        batch.dispose();
        player.dispose();
    }
}