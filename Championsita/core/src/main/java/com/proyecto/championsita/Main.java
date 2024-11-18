package com.proyecto.championsita;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

public class Main extends ApplicationAdapter {
    private Menu menu; // Pantalla del menú
    private boolean inMenu = true; // Comenzamos en el menú

    // Elementos del partido
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private World world;
    private Box2DDebugRenderer debugRenderer;
    private Ball ball;
    private Player playerA;
    private Player playerB;
    private Texture backgroundTexture;

    @Override
    public void create() {
        menu = new Menu();
        menu.create(); // Crear el menú
    }

    @Override
    public void render() {
        if (inMenu) {
            menu.render();

            // Cambiar a la pantalla del partido
            if (menu.shouldStartMatch()) {
                inMenu = false;
                menu.dispose(); // Liberar recursos del menú
                initializeMatch(); // Inicializar el partido
            }
        } else {
            renderMatch(); // Renderizar el partido
        }
    }

    private void initializeMatch() {
        // Configurar la cámara
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 16, 9); // 16 unidades de ancho por 9 de alto

        // Configurar el mundo con gravedad
        world = new World(new Vector2(0, -9.8f), true);
        debugRenderer = new Box2DDebugRenderer();
        batch = new SpriteBatch();

        // Cargar la textura del fondo
        backgroundTexture = new Texture("stadium.png"); // Asegúrate de tener esta textura

        // Crear las líneas del campo (invisibles)
        createFieldLines();

        // Crear la pelota
        createBall();

        // Crear los jugadores
        createPlayers();
    }

    private void renderMatch() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Dibujar el fondo
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(backgroundTexture, 0, 0, camera.viewportWidth, camera.viewportHeight);
        batch.end();

        // Actualizar el mundo
        world.step(1 / 60f, 6, 2); // Avanza el mundo Box2D
        camera.position.set(8, 4.5f, 0); // Centra la cámara
        camera.update();

        // Actualizar jugadores
        playerA.update();
        playerB.update();

        // Manejar entrada
        playerA.handleInput(ball, true); // Controles del Jugador A
        playerB.handleInput(ball, false); // Controles del Jugador B

        // Dibujar jugadores y pelota manualmente
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        ball.render(batch);  // Renderiza la pelota
        playerA.render(batch);  // Renderiza el jugador A
        playerB.render(batch);  // Renderiza el jugador B
        batch.end();
    }

    // Método para crear la pelota
    private void createBall() {
        float ballRadius = 15f / 100; // 15 píxeles de diámetro, ajustado con PPM
        ball = new Ball(world, 8, 5, ballRadius, true);
    }

    // Método para crear los jugadores
    private void createPlayers() {
        playerA = new Player(world, 6, 1, 0.5f); // Jugador A con radio de 0.5 metros
        playerB = new Player(world, 10, 1, 0.5f); // Jugador B con radio de 0.5 metros
    }

    // Método para crear las líneas verdes del campo (suelo, paredes y techo)
    private void createFieldLines() {
        createStaticBody(8, 0.5f, 16, 0.5f); // Suelo
        createStaticBody(0, 4.5f, 0.5f, 9);  // Pared izquierda
        createStaticBody(16, 4.5f, 0.5f, 9); // Pared derecha
        createStaticBody(8, 9.5f, 16, 0.5f); // Techo
    }

    private void createStaticBody(float x, float y, float width, float height) {
        var bodyDef = new com.badlogic.gdx.physics.box2d.BodyDef();
        bodyDef.position.set(x, y);
        bodyDef.type = com.badlogic.gdx.physics.box2d.BodyDef.BodyType.StaticBody;

        var body = world.createBody(bodyDef);

        var shape = new com.badlogic.gdx.physics.box2d.PolygonShape();
        shape.setAsBox(width / 2, height / 2);

        var fixtureDef = new com.badlogic.gdx.physics.box2d.FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.friction = 0.5f;

        body.createFixture(fixtureDef);
        shape.dispose();
    }

    @Override
    public void dispose() {
        if (menu != null) menu.dispose();
        if (world != null) world.dispose();
        if (debugRenderer != null) debugRenderer.dispose();
        if (batch != null) batch.dispose();
        if (playerA != null) playerA.dispose();
        if (playerB != null) playerB.dispose();
        if (backgroundTexture != null) backgroundTexture.dispose();
    }
}
