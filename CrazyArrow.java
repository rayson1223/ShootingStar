/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shootingstar;

/**
 *
 * @author rayson
 */
public class CrazyArrow extends Thread {
    private String name;
    private int headX, headY, bodyEX, bodyEY;
    private double speed;
    private double gravity;
    private double angle;
    
    public CrazyArrow(){
    }
    
    public CrazyArrow(String name){
        this.name = name;
    }

    public int getHeadX() {
        return headX;
    }

    public void setHeadX(int headX) {
        this.headX = headX;
    }

    public int getHeadY() {
        return headY;
    }

    public void setHeadY(int headY) {
        this.headY = headY;
    }

    public int getBodyEX() {
        return bodyEX;
    }

    public void setBodyEX(int bodyEX) {
        this.bodyEX = bodyEX;
    }

    public int getBodyEY() {
        return bodyEY;
    }

    public void setBodyEY(int bodyEY) {
        this.bodyEY = bodyEY;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getGravity() {
        return gravity;
    }

    public void setGravity(double gravity) {
        this.gravity = gravity;
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }
    
    @Override
   public void run() {
       
   }
}
