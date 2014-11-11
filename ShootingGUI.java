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
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Set;
import java.util.logging.Level;
import javax.imageio.ImageIO;

/**
 *
 * @author rayson
 */
public class ShootingGUI extends JFrame {

    private ShootingPanel main;
    private JLabel statusBar;
    private Graphics draw;
    private int mousePX, mousePY, mouseDX, mouseDY;
    private double initAA, initPwr;
    private boolean pullArrow = false, player1Turn = true, fireArrow = false;

    private int arrowX, arrowY;
  
    public ShootingGUI() {
        super("Shooting Star By Rayson & Eng Yao");
        main = new ShootingPanel();
        add(main, BorderLayout.CENTER);
        
        statusBar = new JLabel("Default");
        add(statusBar, BorderLayout.SOUTH);
    }

    public class ShootingPanel extends JPanel implements ActionListener {

        private Timer timer;
        private final int DELAY = 60;
        private double angle;
        private double x_diff;
        private double y_diff;
        private double distance;
        private double power;
        private final double MAX_SPEED = 40.0;
        private CrazyArrow currArrow;
        private ArrayList<CrazyArrow> arrowGrave;
        private Player player1, player2;

        Image background = Toolkit.getDefaultToolkit().createImage("214015.jpg");
        
        public ShootingPanel() {

            //this.setBackground(Color.white);

            
            MouseHandler mouse = new MouseHandler();
            this.addMouseListener(mouse);
            this.addMouseMotionListener(mouse);

            this.setDoubleBuffered(true);

            player1 = new Player(200, 550);
            player2 = new Player(1000, 550);

            arrowGrave = new ArrayList();

            timer = new Timer(DELAY, this);
            timer.start();
        }

        public void paintComponent(Graphics g) {
            g.drawImage(background, 0, 0, null);
            drawGround(g);
            DrawPlayer(g, 200, 550);
            DrawPlayer(g, 1000, 550);

            p1ArmArrow(g, 200, 486);
            p2ArmArrow(g, 1000, 486);

            //System.out.println("Player 1 arrow size "+ player1.getArrows().size());
            ArrayList<CrazyArrow> temp = new ArrayList();
            CrazyArrow tempArr = new CrazyArrow();

            temp = player1.getArrows();

            Graphics2D g2d = (Graphics2D) g;
            g2d.setStroke(new BasicStroke(1));

            for (int i = 0; i < temp.size(); i++) {
                tempArr = temp.get(i);
                g.drawLine(tempArr.getTailX(), tempArr.getTailY(), tempArr.getHeadX(), tempArr.getHeadY());
            }
            //System.out.println("Player 2 arrow size "+ player2.getArrows().size());
            temp = player2.getArrows();

            for (int i = 0; i < temp.size(); i++) {
                tempArr = temp.get(i);
                g.drawLine(tempArr.getTailX(), tempArr.getTailY(), tempArr.getHeadX(), tempArr.getHeadY());
            }

            if (arrowGrave.size() > 0) {
                for (int i = 0; i < arrowGrave.size(); i++) {
                    tempArr = arrowGrave.get(i);
                    g.drawLine(tempArr.getTailX(), tempArr.getTailY(), tempArr.getHeadX(), tempArr.getHeadY());
                }
            }
            
            g.setColor(Color.BLUE);
            g2d.setStroke(new BasicStroke(5));
            
            if(player1.getHealth() < 0){
                g2d.drawString("Game Over, Player 2 Win!", 1200/2-50, 600/2);
            }
            if(player2.getHealth() < 0){
                g2d.drawString("Game Over, Player 1 Win!", 1200/2-50, 600/2);
            }
        }

        public void drawGround(Graphics g) {
            g.setColor(Color.black);
            g.drawLine(0, 550, 1200, 550);
        }

        public void DrawPlayer(Graphics g, int x, int y) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setStroke(new BasicStroke(4));

            g.fillOval(x - 13, y - 90, 26, 26); //head
            g.drawLine(x, y - 64, x, y - 14); //body

            g.drawLine(x, y - 14, x - 17, y); //left leg
            g.drawLine(x, y - 14, x + 17, y); //right leg
        }

        public void p1ArmArrow(Graphics g, double x, double y) {
            g.setColor(Color.black);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setStroke(new BasicStroke(4));
            x_diff = x - 200;
            y_diff = 486 - y;
            distance = Math.sqrt(x_diff * x_diff + y_diff * y_diff);

            double ooh = y_diff / distance;
            angle = Math.asin(ooh);

            if (x_diff < 0) {
                angle = Math.PI - angle;
            } else if (x_diff > 0 && y_diff < 0) {
                angle = angle + Math.PI * 2;
            }
            //System.out.println(angle);
            if (x_diff == 0 && y_diff == 0) {

            } else {
                g.drawLine(200, 486, (int) (200 + (20 * Math.cos(angle))), (int) (486 - 20 * Math.sin(angle)));
                g2d.setStroke(new BasicStroke(2));
                g.drawLine((int) (200 + (20 * Math.cos(angle))), (int) (486 - 20 * Math.sin(angle)), (int) (200 + (50 * Math.cos(angle))), (int) (486 - 50 * Math.sin(angle)));
                g2d.draw(new QuadCurve2D.Float((float) (200 + 36.0555 * Math.cos((angle + 0.98279323))),
                        (float) (486 - 36.0555 * Math.sin((angle + 0.98279323))),
                        (float) (200 + (55 * Math.cos(angle))),
                        (float) (486 - 55 * Math.sin(angle)),
                        (float) (200 + 36.0555 * Math.cos((angle - 0.98279323))),
                        (float) (486 - 36.0555 * Math.sin((angle - 0.98279323)))));
                g2d.setStroke(new BasicStroke(1));
                g.drawLine((int) (200 + 36.0555 * Math.cos((angle + 0.98279323))),
                        (int) (486 - 36.0555 * Math.sin((angle + 0.98279323))),
                        (int) (200 + 36.0555 * Math.cos((angle - 0.98279323))),
                        (int) (486 - 36.0555 * Math.sin((angle - 0.98279323))));
            }
        }

        public void p2ArmArrow(Graphics g, double x, double y) {
            g.setColor(Color.black);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setStroke(new BasicStroke(4));

            x_diff = x - 1000;
            y_diff = 486 - y;
            distance = Math.sqrt(x_diff * x_diff + y_diff * y_diff);
            double ooh = y_diff / distance;
            angle = Math.asin(ooh);

            if (x_diff < 0) {
                angle = Math.PI - angle;
            } else if (x_diff > 0 && y_diff < 0) {
                angle = angle + Math.PI * 2;
            }

            if (x_diff == 0 && y_diff == 0) {

            } else {
                g.drawLine(1000, 486, (int) (1000 + (20 * Math.cos(angle))), (int) (486 - 20 * Math.sin(angle)));
                g2d.setStroke(new BasicStroke(2));
                g.drawLine((int) (1000 + (20 * Math.cos(angle))),
                        (int) (486 - 20 * Math.sin(angle)),
                        (int) (1000 + (50 * Math.cos(angle))),
                        (int) (486 - 50 * Math.sin(angle)));
                g2d.draw(new QuadCurve2D.Float((float) (1000 + 36.0555 * Math.cos((angle + 0.98279323))),
                        (float) (486 - 36.0555 * Math.sin((angle + 0.98279323))),
                        (float) (1000 + (55 * Math.cos(angle))),
                        (float) (486 - 55 * Math.sin(angle)),
                        (float) (1000 + 36.0555 * Math.cos((angle - 0.98279323))),
                        (float) (486 - 36.0555 * Math.sin((angle - 0.98279323)))));
                g2d.setStroke(new BasicStroke(1));
                g.drawLine((int) (1000 + 36.0555 * Math.cos((angle + 0.98279323))),
                        (int) (486 - 36.0555 * Math.sin((angle + 0.98279323))),
                        (int) (1000 + 36.0555 * Math.cos((angle - 0.98279323))),
                        (int) (486 - 36.0555 * Math.sin((angle - 0.98279323))));
            }
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (pullArrow) {
                Thread t = new Thread() {
                    public void run() {
                        // forever:
                        while (pullArrow) {
                            // rapaint it
                            SwingUtilities.invokeLater(new Runnable() {
                                public void run() {
                                    repaint();
                                }
                            });

                            // sleep for while
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException ie) {
                            }
                        }
                    }
                };
                t.start();

//                System.out.println(mouseDX + " " + mouseDY + " " + mousePX + " " + mousePY);
            }
            if (fireArrow) {
                Thread shoot = new Thread() {
                    public void run() {
                        // forever:
                        while (fireArrow) {
                            // rapaint it
                            SwingUtilities.invokeLater(new Runnable() {
                                public void run() {
                                    repaint();
                                }
                            });

                            // sleep for while
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException ie) {
                            }
                        }
                    }
                };
                shoot.start();
            }
        }

        @Override
        public void paint(Graphics g) {
            super.paint(g);
            
            if (pullArrow) {
                currArrow = new CrazyArrow();
                if (player1Turn) {
                    p2ArmArrow(g, mouseDX, mouseDY);

                    power = (Math.sqrt(x_diff * x_diff + y_diff * y_diff)) / 300 * 100;

                    double speed = power * 2 / 10.0;
                    speed = speed >= MAX_SPEED ? MAX_SPEED : speed;

                    currArrow.setSpeed(speed);
                    currArrow.setAngle(angle);
                    currArrow.setHeadX((int) (1000 + (50 * Math.cos(angle))));
                    currArrow.setHeadY((int) (486 - 50 * Math.sin(angle)));
                    currArrow.setTailX((int) (1000 + (20 * Math.cos(angle))));
                    currArrow.setTailY((int) (486 - 20 * Math.sin(angle)));
                } else {
                    p1ArmArrow(g, mouseDX, mouseDY);

                    power = (Math.sqrt(x_diff * x_diff + y_diff * y_diff)) / 300 * 100;

                    double speed = power * 2 / 10.0;
                    
                    speed = speed >= MAX_SPEED ? MAX_SPEED : speed;

                    System.out.println(speed);

                    currArrow.setSpeed(speed);
                    currArrow.setAngle(angle);
                    currArrow.setHeadX((int) (200 + (50 * Math.cos(angle))));
                    currArrow.setHeadY((int) (486 - 50 * Math.sin(angle)));
                    currArrow.setTailX((int) (200 + (20 * Math.cos(angle))));
                    currArrow.setTailY((int) (486 - 20 * Math.sin(angle)));
                }
            }
            if (fireArrow) {
                int diffx = (int) (currArrow.getSpeed() * Math.cos(currArrow.getAngle()));
                int diffy = (int) (currArrow.getSpeed() * Math.sin(currArrow.getAngle()));

                int headx = currArrow.getHeadX();
                headx += diffx;
                currArrow.setHeadX(headx);

                int heady = currArrow.getHeadY();
                heady -= diffy;
                currArrow.setHeadY(heady);

                int tailx = currArrow.getTailX();
                tailx += diffx;
                currArrow.setTailX(tailx);

                int taily = currArrow.getTailY();
                taily -= diffy;
                currArrow.setTailY(taily);

                statusBar.setText(String.format("Arrow move to %d %d", mousePX, mousePY));
                g.drawLine(tailx, taily, headx, heady);

                update();
                
                if (player1Turn) {
                    if (player1.isHit(currArrow)) {
                        currArrow.setSpeed(0);
                        statusBar.setText(String.format("Player 1 is hit and damage!! (Heatlh left " + player1.getHealth() + ")"));
                        fireArrow = false;
                    }
                    if (isHitGround(currArrow)) {
                        arrowGrave.add(currArrow);
                        currArrow.setSpeed(0);
                    }
                    if (isFlyOut(currArrow)) {
                        currArrow.setSpeed(0);
                    }
                } else {
                    if (player2.isHit(currArrow)) {
                        currArrow.setSpeed(0);
                        statusBar.setText(String.format("Player 2 is hit and damage!! (Heatlh left " + player2.getHealth() + ")"));

                        fireArrow = false;
                    }
                    if (isHitGround(currArrow)) {
                        arrowGrave.add(currArrow);
                        currArrow.setSpeed(0);
                    }
                    if (isFlyOut(currArrow)) {
                        currArrow.setSpeed(0);
                    }
                }
            }
        }

        public void bloodBurst(CrazyArrow arr){
            Random r = new Random();
            
        }
        
        public void update() {
            double horv = currArrow.getSpeed() * Math.cos(currArrow.getAngle());
            double verv = currArrow.getSpeed() * Math.sin(currArrow.getAngle());

            verv -= 9.8 * DELAY / 1000;
            System.out.println(verv);

            currArrow.setSpeed(Math.sqrt(horv * horv + verv * verv));

            if (player1Turn) {
                if (verv >= 0 && horv >= 0) {
                    double ooh = verv / currArrow.getSpeed();
                    currArrow.setAngle(Math.asin(ooh));
                } else if (verv < 0 && horv >= 0) {
                    double ooh = verv / currArrow.getSpeed();
                    currArrow.setAngle(Math.asin(ooh) + Math.PI * 2);
                } else {
                    double ooh = verv / currArrow.getSpeed();
                    currArrow.setAngle(Math.PI - Math.asin(ooh));
                }

            } else {
                if (verv >= 0 && horv >= 0) {
                    double ooh = verv / currArrow.getSpeed();
                    currArrow.setAngle(Math.asin(ooh));
                } else if (verv < 0 && horv >= 0) {
                    double ooh = verv / currArrow.getSpeed();
                    currArrow.setAngle(Math.asin(ooh) + Math.PI * 2);
                } else {
                    double ooh = verv / currArrow.getSpeed();
                    currArrow.setAngle(Math.PI - Math.asin(ooh));
                }
            }

            int midx = (currArrow.getHeadX() + currArrow.getTailX()) / 2;
            int midy = (currArrow.getHeadY() + currArrow.getTailY()) / 2;

            currArrow.setHeadX((int) (midx + 15 * Math.cos(currArrow.getAngle())));
            currArrow.setHeadY((int) (midy - 15 * Math.sin(currArrow.getAngle())));
            currArrow.setTailX((int) (midx - 15 * Math.cos(currArrow.getAngle())));
            currArrow.setTailY((int) (midy + 15 * Math.sin(currArrow.getAngle())));
        }

        public boolean isHitGround(CrazyArrow arr) {
            if (arr.getHeadY() > 550) {
                return true;
            }
            return false;
        }

        public boolean isFlyOut(CrazyArrow arr) {
            if (arr.getTailX() < 0 || arr.getTailX() > 1200) {
                return true;
            }
            return false;
        }
    }

    public class MouseHandler implements MouseListener, MouseMotionListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            statusBar.setText(String.format("Click at %d %d", e.getX(), e.getY()));
        }

        @Override
        public void mousePressed(MouseEvent e) {
            if (player1Turn) {
                mousePX = 200;
                mousePY = 486;
                mouseDX = mousePX;
                mouseDY = mousePY;
                player1Turn = false;
            } else {
                mousePX = 1000;
                mousePY = 486;
                mouseDX = mousePX;
                mouseDY = mousePY;
                player1Turn = true;
            }
            pullArrow = true;
            fireArrow = false;
            statusBar.setText(String.format("Pressed at %d %d", e.getX(), e.getY()));
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            pullArrow = false;
            fireArrow = true;
            statusBar.setText(String.format("Mouse Released at %d %d", e.getX(), e.getY()));
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            statusBar.setText(String.format("Mouse Enter at %d %d", e.getX(), e.getY()));
            main.setBackground(Color.LIGHT_GRAY);
        }

        @Override
        public void mouseExited(MouseEvent e) {
            statusBar.setText(String.format("Mouse exited at %d %d", e.getX(), e.getY()));
            main.setBackground(Color.white);
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            mouseDX = e.getX();
            mouseDY = e.getY();
            statusBar.setText(String.format("Mouse Drag at %d %d", e.getX(), e.getY()));
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            statusBar.setText(String.format("Mouse Moved at %d %d", e.getX(), e.getY()));
        }
    }
}
