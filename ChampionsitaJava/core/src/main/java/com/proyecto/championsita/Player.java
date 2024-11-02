package com.proyecto.championsita;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Player {
    private Texture image;
    private Texture botin; 
    private float posX, posY;
    private float velocityX, velocityY;
    private final float speed = 200f;
    private final float jumpStrength = 400f;
    private final float gravity = -15f;
    private boolean isJumping = false; //Si salta o no
    private boolean isKicking = false; //Si patea o no
    
    public Player() {
        image = new Texture("messi.png");
        botin = new Texture ("botin.png");
        posX = 140;
        posY = 10;
    }

    public void update(float deltaTime) {
        // Movimiento horizontal
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            velocityX = -speed;
        } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            velocityX = speed;
        } else {
            velocityX = 0;
        }

        // Salto
        if (Gdx.input.isKeyPressed(Input.Keys.W) && !isJumping) {
            velocityY = jumpStrength;
            isJumping = true;
        }
        
        //Patear
        
        if (Gdx.input.isKeyPressed(Input.Keys.P)) { // Con p pateas
            isKicking = true;
        } else {
            isKicking = false; // Detener el botín cuando no se está pateando
        }
        

        // Aplicar gravedad
        velocityY += gravity;

        // Actualizar posición
        posX += velocityX * deltaTime;
        posY += velocityY * deltaTime;

        // Evitar que el personaje atraviese el suelo
        if (posY < 10) {
            posY = 10;
            isJumping = false;
            velocityY = 0;
        }
        
        if (posX < 0) {
            posX = 0;
            velocityX = 0;
        }
        
        float width = image.getWidth() * 0.5f; // Ancho del jugador
        if (posX + width > Gdx.graphics.getWidth()) {
            posX = Gdx.graphics.getWidth() - width; // Ajusta la posición del jugador
        }
        
        
        
        Gdx.app.log("Player Position", "X: " + posX + ", Y: " + posY);
        
    }

    public void render(SpriteBatch batch) {
    	
    	float width = image.getWidth() * 0.5f;  // Cambia el tamaño al 50%  0.31147242
    	float height = image.getHeight() * 0.5f;
    	
        batch.draw(image, posX, posY, width, height);
        
        if (isKicking) {
            // Ajusta la posición del botín en relación con el jugador
        	float bootPosX = posX + width - (botin.getWidth() * 0.1f); // Alineado a la derecha del jugador
        	float bootPosY = posY + height * 0.05f;
            
            float bootScale = 0.1f;
            float bootWidth = botin.getWidth() * bootScale;
            float bootHeight = botin.getHeight() * bootScale;
            
           
            
            if (bootPosX < 0) {
                bootPosX = 0; // Límite izquierdo
            } else if (bootPosX + bootWidth > Gdx.graphics.getWidth()) {
                bootPosX = Gdx.graphics.getWidth() - bootWidth; // Límite derecho
            }

            if (bootPosY < 0) {
                bootPosY = 0; // Límite inferior
            } else if (bootPosY + bootHeight > Gdx.graphics.getHeight()) {
                bootPosY = Gdx.graphics.getHeight() - bootHeight; // Límite superior
            }

            batch.draw(botin, bootPosX, bootPosY, bootWidth, bootHeight);
            
            
            
        }
        
        
    }

    public void dispose() {
        image.dispose();
        botin.dispose(); 
    }
}
