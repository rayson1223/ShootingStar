/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shootingstar;

import java.awt.*;
import java.applet.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.BevelBorder;

/**
 *
 * @author rayson
 */
public class ShootingGUI extends JFrame{
    private ShootingPanel main;
    private JLabel statusBar;
    private Graphics draw;
    private int mousePX  , mousePY, mouseDX , mouseDY;
    private double initAA , initPwr;
    private boolean pullArrow = false, player1Turn = true, fireArrow = false;
    
    private int arrowX, arrowY;
    
    public ShootingGUI(){
        super("Shooting Star By Rayson & Eng Yao");
        main =new ShootingPanel();
        add(main , BorderLayout.CENTER);
        
        statusBar = new JLabel("Default");
        add(statusBar, BorderLayout.SOUTH);
    }
    
    public class ShootingPanel extends JPanel implements ActionListener{

        private Timer timer;
        private final int DELAY = 60;
        
        public ShootingPanel(){
            
            this.setBackground(Color.white);
            
            MouseHandler mouse = new MouseHandler();
            this.addMouseListener(mouse);
            this.addMouseMotionListener(mouse);
            
            this.setDoubleBuffered(true);
            
            timer = new Timer(DELAY , this);
            timer.start();
        }
        
        public void paintComponent(Graphics g){
            drawGround(g);
            DrawPlayer(g , 200, 550);
            DrawPlayer(g , 1000, 550);
        }
    
        public void drawGround(Graphics g){
            g.setColor( Color.black );
            g.drawLine( 0, 550, 1200 , 550 );
        }
        
        public void DrawPlayer(Graphics g, int x, int y){
            g.fillOval(x-13, y-90, 26, 26);
            g.drawLine(x,y-64,x,y-14);
            g.drawLine(x,y-64,x-17,y-50);
            g.drawLine(x,y-64,x+17,y-50);
            g.drawLine(x,y-14,x-17,y);
            g.drawLine(x,y-14,x+17,y);
        }
        
        @Override
        public void actionPerformed(ActionEvent e) {
            if(pullArrow){
                Thread t = new Thread(){
                    public void run(){
                        // forever:
                        while( pullArrow ){
                            // rapaint it
                            SwingUtilities.invokeLater( new Runnable(){
                                public void run(){
                                    repaint();
                                }
                            });
                            
                            // sleep for while
                            try{
                                Thread.sleep( 1000 );
                            }catch( InterruptedException ie ){}
                        }
                    }
                };
                t.start();
                System.out.println(mouseDX + " " + mouseDY + " " + mousePX + " " + mousePY);
            } 
            if(fireArrow)
            {
                Thread shoot = new Thread(){
                    public void run(){
                        // forever:
                        while( fireArrow ){
                            // rapaint it
                            SwingUtilities.invokeLater( new Runnable(){
                                public void run(){
                                    repaint();
                                }
                            });
                            
                            // sleep for while
                            try{
                                Thread.sleep( 1000 );
                            }catch( InterruptedException ie ){}
                        }
                    }
                };
                shoot.start();
            }
        }
        
        @Override
        public void paint(Graphics g) {
            super.paint(g);
            if(pullArrow){
                g.drawLine(mousePX, mousePY, mouseDX, mouseDY);
            }
            if(fireArrow){
                mousePX += 10;
                mousePY -= 10; 
                statusBar.setText(String.format("Arrow move to %d %d", mousePX , mousePY));
                g.drawLine(mousePX, mousePY, mousePX+50, mousePY-50);
            }
        }

    }
    
    public class MouseHandler implements MouseListener, MouseMotionListener{

        @Override
        public void mouseClicked(MouseEvent e) {
            statusBar.setText(String.format("Click at %d %d", e.getX() , e.getY()));
        }

        @Override
        public void mousePressed(MouseEvent e) {
            if(player1Turn){
                mousePX = 217;
                mousePY = 500;
                mouseDX = mousePX;
                mouseDY = mousePY;
                player1Turn = false;
            }
            else{
                mousePX = 983;
                mousePY = 500;
                mouseDX = mousePX;
                mouseDY = mousePY;
                player1Turn = true;
            }    
            pullArrow = true;
            fireArrow = false;
            statusBar.setText(String.format("Pressed at %d %d", e.getX() , e.getY()));
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            pullArrow = false;
            fireArrow = true;
            statusBar.setText(String.format("Mouse Released at %d %d", e.getX() , e.getY()));
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            statusBar.setText(String.format("Mouse Enter at %d %d", e.getX() , e.getY()));
            main.setBackground(Color.LIGHT_GRAY);
        }

        @Override
        public void mouseExited(MouseEvent e) {
            statusBar.setText(String.format("Mouse exited at %d %d", e.getX() , e.getY()));
            main.setBackground(Color.white);
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            mouseDX = e.getX();
            mouseDY = e.getY();
            statusBar.setText(String.format("Mouse Drag at %d %d", e.getX() , e.getY()));
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            statusBar.setText(String.format("Mouse Moved at %d %d", e.getX() , e.getY()));
        }    
    }
}
