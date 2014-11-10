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
import java.awt.Graphics2D;
import java.awt.geom.QuadCurve2D;

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
        private double angle;
        private double x_diff;
        private double y_diff;
        private double distance;
        private double power;
        private final double MAX_POWER = 300;
        private CrazyArrow currArrow;
        
        
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
            DrawPlayer1(g , 200, 550);
            DrawPlayer2(g , 1000, 550);

            p1ArmArrow(g, 200, 486);
            p2ArmArrow(g, 1000, 486);
        }
    
        public void drawGround(Graphics g){
            g.setColor( Color.black );
            g.drawLine( 0, 550, 1200 , 550 );
        }
        
        public void DrawPlayer1(Graphics g, int x, int y) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setStroke(new BasicStroke(4));
            
            g.fillOval(x - 13, y - 90, 26, 26); //head
            g.drawLine(x, y - 64, x, y - 14); //body

            g.drawLine(x, y - 14, x - 17, y); //left leg
            g.drawLine(x, y - 14, x + 17, y); //right leg
        }
        
        public void DrawPlayer2(Graphics g, int x, int y) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setStroke(new BasicStroke(4));
            g.fillOval(x - 13, y - 90, 26, 26); //head
            g.drawLine(x, y - 64, x, y - 14); //body
            g.drawLine(x, y - 14, x - 17, y); //left leg
            g.drawLine(x, y - 14, x + 17, y); //right leg
        }
        
        public void p1ArmArrow (Graphics g, double x, double y){
            g.setColor(Color.black);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setStroke(new BasicStroke(4));
            
            x_diff = x-200;
            y_diff = y-486;
            distance = Math.sqrt(x_diff*x_diff+y_diff*y_diff);
            double ooh = y_diff/distance;
            angle = Math.asin(ooh);
            
            if(x_diff<0){
                angle = Math.PI-angle;
            }
            
            if(x_diff == 0 && y_diff == 0){
                g.drawLine((int)x, (int)y, (int)x + 20, (int)y);
                g2d.setStroke(new BasicStroke(2));
                g.drawLine((int)x+21, (int)y, (int)x + 50, (int)y);
                g2d.draw(new QuadCurve2D.Float((float)x + 20,(float)y - 30, (float)x + 55, (float)y, (float)x + 20, (float)y +30));
                g2d.setStroke(new BasicStroke(1));
                g.drawLine((int)x+20, (int)y-30, (int)x+20, (int)y+30);
            }else {
                g.drawLine(200, 486, (int)(200+20/distance*x_diff), (int)(486+20/distance*y_diff));
                g2d.setStroke(new BasicStroke(2));
                g.drawLine((int)(200+20/distance*x_diff), (int)(486+20/distance*y_diff), (int)(200+50/distance*x_diff), (int)(486+50/distance*y_diff));
                g2d.draw(new QuadCurve2D.Float((float)(200+36.0555*Math.cos((angle+0.98279323))),(float)(486+36.0555*Math.sin((angle+0.98279323))), (float)(200+55/distance*x_diff), (float)(486+55/distance*y_diff), (float)(200+36.0555*Math.cos((angle-0.98279323))),(float)(486+36.0555*Math.sin((angle-0.98279323)))));
                g2d.setStroke(new BasicStroke(1));
                g.drawLine((int)(200+36.0555*Math.cos((angle+0.98279323))), (int)(486+36.0555*Math.sin((angle+0.98279323))), (int)(200+36.0555*Math.cos((angle-0.98279323))), (int)(486+36.0555*Math.sin((angle-0.98279323))));
            }
        }
        
        public void p2ArmArrow (Graphics g, double x, double y){
            g.setColor(Color.black);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setStroke(new BasicStroke(4));
            
            x_diff = x-1000;
            y_diff = y-486;
            distance = Math.sqrt(x_diff*x_diff+y_diff*y_diff);
            double ooh = y_diff/distance;
            angle = Math.asin(ooh);
            
            if(x_diff<0){
                angle = Math.PI-angle;
            }
            
            if(x_diff == 0 && y_diff == 0){
                g.drawLine((int)x, (int)y, (int)x - 20, (int)y);
                g2d.setStroke(new BasicStroke(2));
                g.drawLine((int)x-21, (int)y, (int)x - 50, (int)y);
                g2d.draw(new QuadCurve2D.Float((float)x - 20,(float)y - 30, (float)x - 55, (float)y, (float)x - 20, (float)y +30));
                g2d.setStroke(new BasicStroke(1));
                g.drawLine((int)x-20, (int)y-30, (int)x-20, (int)y+30);
            }else {
                g.drawLine(1000, 486, (int)(1000+20/distance*x_diff), (int)(486+20/distance*y_diff));
                g2d.setStroke(new BasicStroke(2));
                g.drawLine((int)(1000+20/distance*x_diff), (int)(486+20/distance*y_diff), (int)(1000+50/distance*x_diff), (int)(486+50/distance*y_diff));
                g2d.draw(new QuadCurve2D.Float((float)(1000+36.0555*Math.cos((angle+0.98279323))),(float)(486+36.0555*Math.sin((angle+0.98279323))), (float)(1000+55/distance*x_diff), (float)(486+55/distance*y_diff), (float)(1000+36.0555*Math.cos((angle-0.98279323))),(float)(486+36.0555*Math.sin((angle-0.98279323)))));
                g2d.setStroke(new BasicStroke(1));
                g.drawLine((int)(1000+36.0555*Math.cos((angle+0.98279323))), (int)(486+36.0555*Math.sin((angle+0.98279323))), (int)(1000+36.0555*Math.cos((angle-0.98279323))), (int)(486+36.0555*Math.sin((angle-0.98279323))));
            }
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
                
//                System.out.println(mouseDX + " " + mouseDY + " " + mousePX + " " + mousePY);
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
//                g.drawLine(mousePX, mousePY, mouseDX, mouseDY);
                currArrow = new CrazyArrow();
                if(player1Turn){
                    p2ArmArrow(g, mouseDX, mouseDY);

                    power = (Math.sqrt( x_diff*x_diff + y_diff*y_diff ))/300*100;
                    power = power >= 100.00 ? 100: power;
                    System.out.println("power =" + power);
                    
                    currArrow.setAngle(angle);
                }
                else{
                    p1ArmArrow(g, mouseDX, mouseDY);
                    
                    power = (Math.sqrt( x_diff*x_diff + y_diff*y_diff ))/300*100;
                    power = power >= 100.00 ? 100: power;
                    System.out.println("power =" + power);
                    
                    currArrow.setAngle(angle);
                }
            }
            if(fireArrow){
                //System.out.println(currArrow.getAngle());
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
                mousePX = 200;
                mousePY = 486;
                mouseDX = mousePX;
                mouseDY = mousePY;
                player1Turn = false;
            }
            else{
                mousePX = 1000;
                mousePY = 486;
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