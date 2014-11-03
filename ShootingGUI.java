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
import java.awt.geom.Line2D;
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
        private  int HEIGHT = 600, GROUND_HEIGHT = 50;
        private Player p1,p2;
        
        public ShootingPanel(){
            
            this.setBackground(Color.white);
            
            MouseHandler mouse = new MouseHandler();
            this.addMouseListener(mouse);
            this.addMouseMotionListener(mouse);
            
            p1 = new Player();
            p2 = new Player();
            initPlayer();
            
            this.setDoubleBuffered(true);
            
            timer = new Timer(DELAY , this);
            timer.start();
        }
        
        public void initPlayer(){
            // init P1 info
			
            p1.MAN_POSITION=200;
            p1.ARM_START_X=p1.MAN_POSITION+p1.HEAD_SIZE/2;
            p1.ARM_START_Y=this.HEIGHT-this.GROUND_HEIGHT-p1.MAN_HEIGHT+p1.HEAD_SIZE+p1.BODY_LENGTH/2;
            p1.bow_x=p1.ARM_START_X+p1.ARM_LENG;
            p1.bow_y=p1.ARM_START_Y;
            p1.bow_enda_x=p1.bow_x;
            p1.bow_enda_y=p1.bow_y;
            p1.bow_endb_x=p1.bow_x;
            p1.bow_endb_y=p1.bow_y;
            p1.life_block_messag_x=40;
            p1.life_block_messag_y=35;
            p1.life_block_x=30;
            p1.life_block_y=20;
            p1.life_block_length=20;
            p1.life_block_width=200;
            p1.life_block_fill_width=200;

            // init p2 info
            p2.MAN_POSITION=1000;
            p2.ARM_START_X=p2.MAN_POSITION+p2.HEAD_SIZE/2;
            p2.ARM_START_Y=this.HEIGHT-this.GROUND_HEIGHT-p2.MAN_HEIGHT+p2.HEAD_SIZE+p2.BODY_LENGTH/2;
            p2.bow_x=p2.ARM_START_X-p2.ARM_LENG;
            p2.bow_y=p2.ARM_START_Y;
            p2.bow_enda_x=p2.bow_x;
            p2.bow_enda_y=p2.bow_y;
            p2.bow_endb_x=p2.bow_x;
            p2.bow_endb_y=p2.bow_y;
            p2.life_block_messag_x=980;
            p2.life_block_messag_y=35;
            p2.life_block_x=970;
            p2.life_block_y=20;
            p2.life_block_length=20;
            p2.life_block_width=200;
            p2.life_block_fill_width=200;
        }
        
        public void paintComponent(Graphics g){
            drawGround(g);
            DrawPlayer(g);
            
            p1Arrow(g, 200, 550);
            p2Arrow(g, 1000, 550);
        }
    
        public void drawGround(Graphics g){
            g.setColor( Color.black );
            g.drawLine( 0, 550, 1200 , 550 );
        }
        
        public void DrawPlayer(Graphics g) {
            g.setColor(Color.BLACK);
            Graphics2D g2d=(Graphics2D)g;

            //AffineTransform old=g2d.getTransform();

            /*** player 1 ***/

            // head
            g.fillOval(p1.MAN_POSITION, this.HEIGHT-this.GROUND_HEIGHT-p1.MAN_HEIGHT, p1.HEAD_SIZE, p1.HEAD_SIZE);

            // body
            g2d.setStroke(new BasicStroke(5));
            g2d.draw(new Line2D.Float(p1.MAN_POSITION+p1.HEAD_SIZE/2, this.HEIGHT-this.GROUND_HEIGHT-p1.MAN_HEIGHT+p1.HEAD_SIZE, p1.MAN_POSITION+p1.HEAD_SIZE/2, this.HEIGHT-this.GROUND_HEIGHT-p1.MAN_HEIGHT+p1.HEAD_SIZE+p1.BODY_LENGTH));

            // leg
            g2d.draw(new Line2D.Float(p1.MAN_POSITION+p1.HEAD_SIZE/2, this.HEIGHT-this.GROUND_HEIGHT-p1.MAN_HEIGHT+p1.HEAD_SIZE+p1.BODY_LENGTH, p1.MAN_POSITION+p1.HEAD_SIZE/2-20, this.HEIGHT-this.GROUND_HEIGHT));
            g2d.draw(new Line2D.Float(p1.MAN_POSITION+p1.HEAD_SIZE/2, this.HEIGHT-this.GROUND_HEIGHT-p1.MAN_HEIGHT+p1.HEAD_SIZE+p1.BODY_LENGTH, p1.MAN_POSITION+p1.HEAD_SIZE/2+20, this.HEIGHT-this.GROUND_HEIGHT));

            // arm
            g2d.draw(new Line2D.Float(p1.ARM_START_X, p1.ARM_START_Y, p1.bow_x, p1.bow_y));

            // bow
            g2d.draw(new QuadCurve2D.Float(p1.bow_enda_x, p1.bow_enda_y, p1.bow_x, p1.bow_y, p1.bow_endb_x, p1.bow_endb_y));

            /*** player 2 ***/

            // head
            g.fillOval(p2.MAN_POSITION, this.HEIGHT-this.GROUND_HEIGHT-p2.MAN_HEIGHT, p2.HEAD_SIZE, p2.HEAD_SIZE);

            // body
            g2d.setStroke(new BasicStroke(5));
            g2d.draw(new Line2D.Float(p2.MAN_POSITION+p2.HEAD_SIZE/2, this.HEIGHT-this.GROUND_HEIGHT-p2.MAN_HEIGHT+p2.HEAD_SIZE, p2.MAN_POSITION+p2.HEAD_SIZE/2, this.HEIGHT-this.GROUND_HEIGHT-p2.MAN_HEIGHT+p2.HEAD_SIZE+p2.BODY_LENGTH));

            // leg
            g2d.draw(new Line2D.Float(p2.MAN_POSITION+p2.HEAD_SIZE/2, this.HEIGHT-this.GROUND_HEIGHT-p2.MAN_HEIGHT+p2.HEAD_SIZE+p2.BODY_LENGTH, p2.MAN_POSITION+p2.HEAD_SIZE/2-20, this.HEIGHT-this.GROUND_HEIGHT));
            g2d.draw(new Line2D.Float(p2.MAN_POSITION+p2.HEAD_SIZE/2, this.HEIGHT-this.GROUND_HEIGHT-p2.MAN_HEIGHT+p2.HEAD_SIZE+p2.BODY_LENGTH, p2.MAN_POSITION+p2.HEAD_SIZE/2+20, this.HEIGHT-this.GROUND_HEIGHT));

            // arm
            g2d.draw(new Line2D.Float(p2.ARM_START_X, p2.ARM_START_Y, p2.bow_x, p2.bow_y));

            // bow
            g2d.draw(new QuadCurve2D.Float(p2.bow_enda_x, p2.bow_enda_y, p2.bow_x, p2.bow_y, p2.bow_endb_x, p2.bow_endb_y));

            g2d.setStroke(new BasicStroke(2));
        }
        
//        public void DrawPlayer2(Graphics g, int x, int y) {
//            Graphics2D g2d = (Graphics2D) g;
//            g2d.setStroke(new BasicStroke(4));
//            g.fillOval(x - 13, y - 90, 26, 26); //head
//            g.drawLine(x, y - 64, x, y - 14); //body
//            g.drawLine(x, y - 64, x - 20, y - 64); //left arm
//            g.drawLine(x, y - 64, x + 17, y - 50); //right arm
//            g.drawLine(x, y - 14, x - 17, y); //left leg
//            g.drawLine(x, y - 14, x + 17, y); //right leg
//        }
        
        public void p1Arrow(Graphics g, int x, int y) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setStroke(new BasicStroke(2));
            g.drawLine(x + 20, y - 64, x + 50, y - 64);
            g.drawLine(x + 50, y - 64, x + 45, y - 54);
            g.drawLine(x + 50, y - 64, x + 45, y - 74);
            g.drawLine(x + 20, y - 90, x + 20, y - 30);
            g2d.draw(new QuadCurve2D.Float(x + 20, y - 90, x + 55, y - 60, x + 20, y - 30));
        }

        public void p2Arrow(Graphics g, int x, int y) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setStroke(new BasicStroke(2));
            g.drawLine(x - 20, y - 64, x - 50, y - 64);
            g.drawLine(x - 50, y - 64, x - 45, y - 54);
            g.drawLine(x - 50, y - 64, x - 45, y - 74);
            g.drawLine(x - 20, y - 90, x - 20, y - 30);
            g2d.draw(new QuadCurve2D.Float(x - 20, y - 90, x - 55, y - 60, x - 20, y - 30));
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