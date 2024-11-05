package com.proyecto.championsita;

public class CollisionManager {

    private Player player1, player2;
    private Ball ball;

    public CollisionManager(Player player1, Player player2, Ball ball) {
        this.player1 = player1;
        this.player2 = player2;
        this.ball = ball;
    }

    public void checkCollisions() {
        checkPlayerBallCollision(player1);
        checkPlayerBallCollision(player2);
        checkPlayerCollision();
        stopBallBetweenPlayers();
    }

    private void checkPlayerBallCollision(Player player) {
        float playerLeft = player.posX;
        float playerRight = player.posX + player.size;
        float playerBottom = player.posY;
        float playerTop = player.posY + player.size;

        if (ball.posY <= playerTop && ball.posY + ball.size >= playerBottom &&
                ball.posX + ball.size > playerLeft && ball.posX < playerRight) {
            if (ball.posX + ball.size / 2 < playerLeft + player.size / 2) {
                ball.posX = playerLeft - ball.size;
            } else {
                ball.posX = playerRight;
            }
            ball.velocity.x = player.velocity.x * 0.5f;
        }

        if (ball.posX + ball.size > playerLeft && ball.posX < playerRight &&
                playerBottom < ball.posY + ball.size && playerTop > ball.posY) {
            if (player.velocity.y < 0 && player.posY > ball.posY) {
                player.posY = ball.posY + ball.size;
                player.velocity.y = 0;
                player.canJump = true;
            }
        }
    }

    private void checkPlayerCollision() {
        float player1Right = player1.posX + player1.size;
        float player2Left = player2.posX;

        if (player1Right > player2Left && player1.posX < player2.posX + player2.size) {
            if (player1.posX < player2.posX) {
                player1.posX = player2Left - player1.size;
            } else {
                player1.posX = player2.posX + player2.size;
            }
            // Ajustar las velocidades para simular un efecto de empuje
            float temp = player1.velocity.x;
            player1.velocity.x = player2.velocity.x;
            player2.velocity.x = temp;
        }
    }

    private void stopBallBetweenPlayers() {
        if (ball.posX + ball.size > player1.posX && ball.posX < player2.posX + player2.size &&
                ball.posY <= player1.posY + player1.size && ball.posY + ball.size >= player1.posY) {
            ball.velocity.x = 0;
            ball.velocity.y = 0;
        }
    }
}