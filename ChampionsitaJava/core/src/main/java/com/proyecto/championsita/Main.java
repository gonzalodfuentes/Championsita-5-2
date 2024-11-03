package com.proyecto.championsita;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;

public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    private Player player1, player2;
    private Ball ball;
    private final float fieldWidth = 400;
    private final float initialPlayerY = 20;

    @Override
    public void create() {
        batch = new SpriteBatch();
        player1 = new Player(Gdx.graphics.getWidth() / 2 - 200, initialPlayerY, Input.Keys.A, Input.Keys.D, Input.Keys.W, Input.Keys.P, "messi.png");
        player2 = new Player(Gdx.graphics.getWidth() / 2 + 200, initialPlayerY, Input.Keys.LEFT, Input.Keys.RIGHT, Input.Keys.UP, Input.Keys.CONTROL_RIGHT, "messi.png");
        ball = new Ball(Gdx.graphics.getWidth() / 2, 10, "pelota.png");
    }

    @Override
    public void render() {
        if (Gdx.input.isKeyPressed(Input.Keys.Z)) {
            Gdx.app.exit();
        }

        ScreenUtils.clear(0.40f, 0.40f, 0.40f, 1f);

        player1.update(Gdx.graphics.getDeltaTime());
        player2.update(Gdx.graphics.getDeltaTime());
        ball.update(Gdx.graphics.getDeltaTime());

        checkCollision(player1);
        checkCollision(player2);

        batch.begin();
        player1.render(batch);
        player2.render(batch);
        ball.render(batch);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        player1.dispose();
        player2.dispose();
        ball.dispose();
    }

    private void checkCollision(Player player) {
        float playerLeft = player.posX;
        float playerRight = player.posX + player.size;
        float playerBottom = player.posY;
        float playerTop = player.posY + player.size;

        // Colisi�n horizontal entre el jugador y la pelota
        if (ball.posY <= playerTop && ball.posY + ball.size >= playerBottom && 
            ball.posX + ball.size > playerLeft && ball.posX < playerRight) {

            // Ajustar la posici�n horizontal de la pelota seg�n el lado de colisi�n
            if (ball.posX + ball.size / 2 < playerLeft + player.size / 2) {
                ball.posX = playerLeft - ball.size; // Lado izquierdo
            } else {
                ball.posX = playerRight; // Lado derecho
            }

            // La pelota hereda un poco de la velocidad horizontal del jugador mientras est� en el piso
            ball.velocity.x = player.velocity.x * 0.5f;
        }

        // Colisi�n vertical: Evitar que el jugador atraviese la pelota cuando salta
        if (ball.posX + ball.size > playerLeft && ball.posX < playerRight &&
            playerBottom < ball.posY + ball.size && playerTop > ball.posY) {

            // Ajuste en el eje vertical para que el jugador "se suba" a la pelota
            if (player.velocity.y < 0 && player.posY > ball.posY) { 
                player.posY = ball.posY + ball.size;
                player.velocity.y = 0;
                player.canJump = true;
            }
        }

        // Colisi�n entre jugadores
        if (player == player1) {
            // Verificar colisi�n con el jugador 2
            float player2Left = player2.posX;
            float player2Right = player2.posX + player2.size;
            float player2Bottom = player2.posY;
            float player2Top = player2.posY + player2.size;

            if (playerRight > player2Left && playerLeft < player2Right &&
                playerTop > player2Bottom && playerBottom < player2Top) {
                // Ajustar posiciones de los jugadores para evitar superposici�n
                if (player.posX < player2.posX) {
                    player.posX = player2Left - player.size; // Mover player1 a la izquierda
                } else {
                    player.posX = player2Right; // Mover player1 a la derecha
                }
            }
        }

        // Si la pelota est� entre los dos jugadores, detenerla
        if (ball.posX + ball.size > player1.posX && ball.posX < player2.posX + player2.size &&
            ball.posY <= player1.posY + player1.size && ball.posY + ball.size >= player1.posY) {
            ball.velocity.x = 0; // Detener la pelota
            ball.velocity.y = 0; // Asegurarse de que no se mueva verticalmente
        }

        // Si la tecla de patear est� presionada, la pelota se dispara
        if (Gdx.input.isKeyPressed(player.kickKey)) {
            player.kickBall(ball);
        }
    }





    // Clase Player
    public class Player {
        public float posX, posY;
        private Vector2 velocity = new Vector2();
        public final float size = 50f;
        private final float speed = 110f;
        private final float gravity = -800f;
        private final float jumpImpulse = 300f;
        private final float kickPowerX = 120f; // Velocidad horizontal del disparo
        private final float kickPowerY = 120f; // Velocidad vertical del disparo
        private final float kickPowerMultiplier = 1.5f; // Ajuste de la velocidad al patear
        private boolean canJump = true;
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
            if (Gdx.input.isKeyPressed(leftKey) && posX > Gdx.graphics.getWidth() / 2 - fieldWidth) {
                velocity.x = -speed;
                facingRight = false;
            } else if (Gdx.input.isKeyPressed(rightKey) && posX + size < Gdx.graphics.getWidth() / 2 + fieldWidth) {
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

            if (posY <= initialPlayerY) {
                posY = initialPlayerY;
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

        public void dispose() {
            texture.dispose();
        }
    }

    // Clase Ball
    public class Ball {
        public float posX, posY;
        private Vector2 velocity = new Vector2();
        private final float size = 30f;
        private final float gravity = -10f;
        private Texture texture;

        public Ball(float startX, float startY, String texturePath) {
            posX = startX;
            posY = startY;
            texture = new Texture(Gdx.files.internal(texturePath));
        }

        public void update(float deltaTime) {
            velocity.y += gravity * deltaTime;
            posX += velocity.x * deltaTime;
            posY += velocity.y * deltaTime;

            // Aplicar fricci�n solo cuando la pelota est� en el suelo
            if (posY <= 10) {
                posY = 10;
                velocity.y = 0;
                velocity.x *= 0.9f;  // Reducir la velocidad horizontal para simular fricci�n
            }

            // Colisiones con los l�mites del campo
            if (posX < Gdx.graphics.getWidth() / 2 - fieldWidth) {
                posX = Gdx.graphics.getWidth() / 2 - fieldWidth;
                velocity.x = 0; // Detener en lugar de rebotar
            } else if (posX + size > Gdx.graphics.getWidth() / 2 + fieldWidth) {
                posX = Gdx.graphics.getWidth() / 2 + fieldWidth - size;
                velocity.x = 0;
            }
        }


        public void render(SpriteBatch batch) {
            batch.draw(texture, posX, posY, size, size);
        }

        public void dispose() {
            texture.dispose();
        }
    }
}
