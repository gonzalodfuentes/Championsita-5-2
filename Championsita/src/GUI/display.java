package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;

public class display extends JFrame 
{
    private int x = 150; // X CUADRADO 1
    private int y = 150; // X CUADRADO 1
    
    private final int DIAMETER = 200; // DIAMETROS
    
    private int x2 = 250; // X CUADRADO 2
    private int y2 = 250; // X CUADRADO 2

    private boolean wPressed, aPressed, sPressed, dPressed;
    private boolean upPressed, leftPressed, downPressed, rightPressed;
    
    public display() 
    {
        setTitle("LA CHAMPIONSITA");
        setExtendedState(JFrame.MAXIMIZED_BOTH); // PANTALLA COMPLETA
        setUndecorated(true); // SIN BORDES
        setDefaultCloseOperation(EXIT_ON_CLOSE); // 
        setResizable(false); //
        
        // KeyListener PARA MANEJAR LAS TECLAS
        addKeyListener(new KeyAdapter() 
        {
            @Override
            public void keyPressed(KeyEvent e) 
            {
                switch (e.getKeyCode()) 
                {
                	case KeyEvent.VK_W:
                		wPressed = true;
                		break;
                		
                	case KeyEvent.VK_A:
                		aPressed = true;
                		break;
                		
                	case KeyEvent.VK_S:
                		sPressed = true;
                		break;
                		
                	case KeyEvent.VK_D:
                		dPressed = true;
                		break;
                		
                	case KeyEvent.VK_UP:
                		upPressed = true;
                		break;
                		
                	case KeyEvent.VK_LEFT:
                		leftPressed = true;
                		break;
                		
                	case KeyEvent.VK_DOWN:
                		downPressed = true;
                		break;
                		
                	case KeyEvent.VK_RIGHT:
                		rightPressed = true;
                		break;
                		
                	case KeyEvent.VK_Z: // Cerrar
                		System.exit(0);
                		break;
                }
                move(); // PARA VALIDAR LOS MOVIMIENTOS DESPUES
                repaint(); // PARA ACTUALIZAR LAS POSICIONES DE LOS CIRCULOS
            }
            
            @Override
            public void keyReleased(KeyEvent e) 
            {
                switch (e.getKeyCode()) 
                {
                	case KeyEvent.VK_W:
                		wPressed = false;
                		break;
                		
                	case KeyEvent.VK_A:
                		aPressed = false;
                		break;
                		
                	case KeyEvent.VK_S:
                		sPressed = false;
                		break;
                		
                	case KeyEvent.VK_D:
                		dPressed = false;
                		break;
                		
                	case KeyEvent.VK_UP:
                		upPressed = false;
                		break;
                		
                	case KeyEvent.VK_LEFT:
                		leftPressed = false;
                		break;
                		
                	case KeyEvent.VK_DOWN:
                		downPressed = false;
                		break;
                		
                	case KeyEvent.VK_RIGHT:
                		rightPressed = false;
                		break;
                		
                	case KeyEvent.VK_Z: // Cerrar
                		System.exit(0);
                		break;
                }
                repaint(); // PARA ACTUALIZAR LAS POSICIONES DE LOS CIRCULOS
            }
        });

        setVisible(true);
    }
    
    private void move() 
    {
        // MOVER EL PRIMER CUADRADO
        if (wPressed) y -= 20;
        if (aPressed) x -= 20;
        if (sPressed) y += 20;
        if (dPressed) x += 20;

        // MOVER EL SEGUNDO CUADRADO
        if (upPressed) y2 -= 20;
        if (leftPressed) x2 -= 20;
        if (downPressed) y2 += 20;
        if (rightPressed) x2 += 20;
    }

    @Override
    public void paint(Graphics g) 
    {
        super.paint(g);
        g.setColor(Color.BLUE); // COLOR CUADRADO 1
        g.fillRect(x, y, DIAMETER, DIAMETER); // DIBUJAR CUADRADO 1
        
        g.setColor(Color.GREEN); // COLOR CUADRADO 2
        g.fillRect(x2, y2, DIAMETER, DIAMETER); // DIBUJAR CUADRADO 2
    }
}
