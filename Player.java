/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shootingstar;

import java.util.ArrayList;

public class Player {
    private int topY, midY, botY, leftX, rightX;
    private int health;
    private ArrayList<CrazyArrow> arrowList;

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }
    
    // person info
    public Player(int x, int y){
        this.health = 100;
        
        this.topY = 460;
        this.midY = 486;
        this.botY = y;
        
        this.leftX = x - 13;
        this.rightX = x + 13;
        
        arrowList=new ArrayList<CrazyArrow>();
    }

    public boolean isHit(CrazyArrow arr){
        
        if(arr.getHeadX() <= this.rightX && arr.getHeadX() >= this.leftX){
            if(arr.getHeadY() <= this.botY && arr.getHeadY() >= this.midY){
                arrowList.add(arr);
                this.health -= 20;
                return true;
            }
            else if(arr.getHeadY() <= midY && arr.getHeadY() >= topY){
                arrowList.add(arr);
                this.health -= 50;
                return true;
            }
        }
        if(arr.getTailX() <= this.rightX && arr.getTailX() >= this.leftX){
            if(arr.getTailY() <= this.botY && arr.getTailY() >= this.midY){
                arrowList.add(arr);
                this.health -= 20;
                return true;
            }
            else if(arr.getTailY() <= midY && arr.getTailY() >= topY){
                arrowList.add(arr);
                this.health -= 50;
                return true;
            }
        }
        
        
        return false;
    }
    
    public boolean isHeadShot(CrazyArrow arr){
        if(arr.getHeadX() <= this.rightX && arr.getHeadX() >= this.leftX){
            if(arr.getHeadY() <= midY && arr.getHeadY() >= topY){
                return true;
            }
        }
        if(arr.getTailX() <= this.rightX && arr.getTailX() >= this.leftX){
            if(arr.getTailY() <= midY && arr.getTailY() >= topY){
                return true;
            }
        }
        return false;
    }
    
    public boolean isDead(){
        return this.health <= 0;
    }
    
    public ArrayList getArrows(){
        return arrowList;
    }
    
    @Override
    public String toString(){
        return topY + " " + midY + " " +botY + " " + leftX + " " + rightX;
    }
}
