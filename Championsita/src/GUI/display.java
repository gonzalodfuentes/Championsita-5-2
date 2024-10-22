package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class display extends JFrame {
    private int x; // X CUADRADO 1
    private int y; // Y CUADRADO 1
    
    private final int DIAMETER = 50; // DIÁMETROS
    
    private int x2; // X CUADRADO 2
    private int y2; // Y CUADRADO 2

    private boolean wPressed, aPressed, sPressed, dPressed;
    private boolean upPressed, leftPressed, downPressed, rightPressed;

    private final int BOUNDARY_SIZE = 500; // Tamaño del cuadrado delimitador
    private final int BOUNDARY_X;
    private final int BOUNDARY_Y;

    public display() {
        setTitle("LA CHAMPIONSITA");
        setExtendedState(JFrame.MAXIMIZED_BOTH); // PANTALLA COMPLETA
        setUndecorated(true); // SIN BORDES
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        
        // Centrar los cuadrados
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        BOUNDARY_X = (screenSize.width - BOUNDARY_SIZE) / 2; // X del cuadrado delimitador
        BOUNDARY_Y = (screenSize.height - BOUNDARY_SIZE) - 50; // Y del cuadrado delimitador
        
        x = BOUNDARY_X + (BOUNDARY_SIZE - DIAMETER) / 2 - 200; // Centra el primer cuadrado
        y = BOUNDARY_Y + (BOUNDARY_SIZE - DIAMETER) - 50;
        
        x2 = BOUNDARY_X + (BOUNDARY_SIZE - DIAMETER) / 2 + 200; // Centra el segundo cuadrado
        y2 = BOUNDARY_Y + (BOUNDARY_SIZE - DIAMETER) - 50;

        // KeyListener PARA MANEJAR LAS TECLAS
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_W: wPressed = true; break;
                    case KeyEvent.VK_A: aPressed = true; break;
                    case KeyEvent.VK_S: sPressed = true; break;
                    case KeyEvent.VK_D: dPressed = true; break;
                    case KeyEvent.VK_UP: upPressed = true; break;
                    case KeyEvent.VK_LEFT: leftPressed = true; break;
                    case KeyEvent.VK_DOWN: downPressed = true; break;
                    case KeyEvent.VK_RIGHT: rightPressed = true; break;
                    case KeyEvent.VK_Z: System.exit(0); break;
                }
                move(); // Mueve los cuadrados
                repaint(); // Actualiza la pantalla
            }
            
            @Override
            public void keyReleased(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_W: wPressed = false; break;
                    case KeyEvent.VK_A: aPressed = false; break;
                    case KeyEvent.VK_S: sPressed = false; break;
                    case KeyEvent.VK_D: dPressed = false; break;
                    case KeyEvent.VK_UP: upPressed = false; break;
                    case KeyEvent.VK_LEFT: leftPressed = false; break;
                    case KeyEvent.VK_DOWN: downPressed = false; break;
                    case KeyEvent.VK_RIGHT: rightPressed = false; break;
                }
                repaint(); // Actualiza la pantalla
            }
        });

        setVisible(true);
    }
    
    private void move() {
        // MOVER EL PRIMER CUADRADO
        if (wPressed) {
            if (y > BOUNDARY_Y) {
                y = Math.max(y - 20, BOUNDARY_Y); // Limita hacia arriba
            }
        }
        if (aPressed) {
            if (x > BOUNDARY_X) {
                x = Math.max(x - 20, BOUNDARY_X); // Limita hacia la izquierda
            }
        }
        if (sPressed) {
            if (y < BOUNDARY_Y + BOUNDARY_SIZE - DIAMETER) {
                y = Math.min(y + 20, BOUNDARY_Y + BOUNDARY_SIZE - DIAMETER); // Limita hacia abajo
            }
        }
        if (dPressed) {
            if (x < BOUNDARY_X + BOUNDARY_SIZE - DIAMETER) {
                x = Math.min(x + 20, BOUNDARY_X + BOUNDARY_SIZE - DIAMETER); // Limita hacia la derecha
            }
        }

        // MOVER EL SEGUNDO CUADRADO
        if (upPressed) {
            if (y2 > BOUNDARY_Y) {
                y2 = Math.max(y2 - 20, BOUNDARY_Y); // Limita hacia arriba
            }
        }
        if (leftPressed) {
            if (x2 > BOUNDARY_X) {
                x2 = Math.max(x2 - 20, BOUNDARY_X); // Limita hacia la izquierda
            }
        }
        if (downPressed) {
            if (y2 < BOUNDARY_Y + BOUNDARY_SIZE - DIAMETER) {
                y2 = Math.min(y2 + 20, BOUNDARY_Y + BOUNDARY_SIZE - DIAMETER); // Limita hacia abajo
            }
        }
        if (rightPressed) {
            if (x2 < BOUNDARY_X + BOUNDARY_SIZE - DIAMETER) {
                x2 = Math.min(x2 + 20, BOUNDARY_X + BOUNDARY_SIZE - DIAMETER); // Limita hacia la derecha
            }
        }
    }

    @Override
    public void paint(Graphics g) {
        // Dibuja en un buffer
        Image buffer = createImage(getWidth(), getHeight());
        Graphics gBuffer = buffer.getGraphics();
        
        // Limpia el buffer
        gBuffer.setColor(getBackground());
        gBuffer.fillRect(0, 0, getWidth(), getHeight());

        // Dibuja el cuadrado delimitador
        gBuffer.setColor(Color.RED); // Color del cuadrado delimitador
        gBuffer.drawRect(BOUNDARY_X, BOUNDARY_Y, BOUNDARY_SIZE, BOUNDARY_SIZE); // Dibuja el cuadrado delimitador
        
        // Dibuja los cuadrados en el buffer
        gBuffer.setColor(Color.BLUE); // COLOR CUADRADO 1
        gBuffer.fillRect(x, y, DIAMETER, DIAMETER); // DIBUJAR CUADRADO 1
        
        gBuffer.setColor(Color.GREEN); // COLOR CUADRADO 2
        gBuffer.fillRect(x2, y2, DIAMETER, DIAMETER); // DIBUJAR CUADRADO 2
        
        // Dibuja el buffer en la ventana
        g.drawImage(buffer, 0, 0, this);
        
        gBuffer.dispose(); // Libera recursos
    }
}
