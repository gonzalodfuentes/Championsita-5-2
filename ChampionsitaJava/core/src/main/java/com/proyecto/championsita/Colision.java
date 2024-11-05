package com.proyecto.championsita;

import com.badlogic.gdx.Gdx;

public class Colision {
    public static void checkCollision(Player player1, Player player2, Ball ball) {
        checkPlayerBallCollision(player1, ball);
        checkPlayerBallCollision(player2, ball);
        checkPlayerPlayerCollision(player1, player2);
        checkBallBetweenPlayers(player1, player2, ball);
    }

    private static void checkPlayerBallCollision(Player player, Ball ball) {
        float playerLeft = player.posX;
        float playerRight = player.posX + player.size;
        float playerBottom = player.posY;
        float playerTop = player.posY + player.size;

        // Verificar colisión entre el jugador y la pelota
        if (ball.posY <= playerTop && ball.posY + ball.size >= playerBottom && 
            ball.posX + ball.size > playerLeft && ball.posX < playerRight) {

            // Ajustar la posición de la pelota según el lado de colisión
            if (ball.posX + ball.size / 2 < playerLeft + player.size / 2) {
                ball.posX = playerLeft - ball.size;
            } else {
                ball.posX = playerRight;
            }

            // Asignar la velocidad de la pelota con la del jugador
            ball.velocity.x = player.velocity.x * 0.5f;
        }

        // Verificar si el jugador patea la pelota
        if (Gdx.input.isKeyPressed(player.getKickKey())) {
            player.kickBall(ball);
        }
    }


    private static void checkPlayerPlayerCollision(Player player1, Player player2) {
        float player1Left = player1.posX;
        float player1Right = player1.posX + player1.size;
        float player1Bottom = player1.posY;
        float player1Top = player1.posY + player1.size;

        float player2Left = player2.posX;
        float player2Right = player2.posX + player2.size;
        float player2Bottom = player2.posY;
        float player2Top = player2.posY + player2.size;

        if (player1Right > player2Left && player1Left < player2Right &&
            player1Top > player2Bottom && player1Bottom < player2Top) {

            if (player1.posX < player2.posX) {
                player1.posX = player2Left - player1.size;
            } else {
                player1.posX = player2Right;
            }
        }
    }

    private static void checkBallBetweenPlayers(Player player1, Player player2, Ball ball) {
        float player1Right = player1.posX + player1.size;
        float player2Left = player2.posX;

        // Verifica si la pelota está entre los dos jugadores horizontalmente y en la misma altura
        if (ball.posX + ball.size > player1Right && ball.posX < player2Left &&
            ball.posY <= player1.posY + player1.size && ball.posY + ball.size >= player1.posY) {
            ball.velocity.x = 0;
            ball.velocity.y = 0;
        }
    }
}
