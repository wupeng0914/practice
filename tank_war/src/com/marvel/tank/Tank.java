package com.marvel.tank;

import lombok.Data;

import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * @Description Tank
 * @Author wupeng
 * @Motto Stay Hungry, Stay Foolish !
 * @Date 2020/8/26 3:01 下午
 **/
@Data
public class Tank {
    private static int WIDTH =  ResourceMgr.goodTankU.getWidth();
    private static int HEIGHT = ResourceMgr.goodTankU.getHeight();

    private static final int SPEED = 5; //坦克速度

    private Direction dir; //移动方向
    private int x, y;
    private boolean moving;
    private boolean living;
    private TankFrame tf;

    public Tank(int x, int y, Direction dir, TankFrame tf) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.tf = tf;
    }

    /**
     * 画出坦克
     * @param g
     */
    public void paint(Graphics g){
        switch (dir){
            case UP:
                g.drawImage(ResourceMgr.goodTankU, x, y, null);
                break;
            case RIGHT:
                g.drawImage(ResourceMgr.goodTankR, x, y, null);
                break;
            case DOWN:
                g.drawImage(ResourceMgr.goodTankD, x, y, null);
                break;
            case LEFT:
                g.drawImage(ResourceMgr.goodTankL, x, y, null);
                break;
        }
        move();
    }

    /**
     * 发射子弹
     */
    public void fire(){
        int bx = 0, by = 0;
        switch(dir){
            case UP:
                bx = x + ResourceMgr.goodTankU.getWidth()/2 - ResourceMgr.bulletU.getWidth()/2;
                by = y;
                break;
            case RIGHT:
                bx = x + ResourceMgr.goodTankR.getWidth();
                by = y + ResourceMgr.goodTankR.getHeight()/2 - ResourceMgr.bulletR.getHeight()/2;
                break;
            case DOWN:
                bx = x + ResourceMgr.goodTankR.getWidth()/2 - ResourceMgr.bulletR.getWidth()/2;
                by = y + ResourceMgr.goodTankR.getHeight();
                break;
            case LEFT:
                bx = x;
                by = y + ResourceMgr.goodTankR.getHeight()/2 - ResourceMgr.bulletL.getHeight()/2;
                break;
            default:
                break;
        }
        tf.addBullet(new Bullet(bx, by, dir));
    }

    /**
     * 移动坦克
     */
    private void move(){
        if (!moving) return;
        switch (dir){
            case UP:
                y -= SPEED;
                break;
            case RIGHT:
                x += SPEED;
                break;
            case DOWN:
                y += SPEED;
                break;
            case LEFT:
                x -= SPEED;
                break;
            default:
                break;
        }
        boundsCheck();
    }

    /**
     * 坦克移动边界检查
     */
    private void boundsCheck(){
        if (this.x < 2) x = 2;
        if (this.y < 28) y = 28;
        if (this.x > TankFrame.GAME_WIDTH-Tank.WIDTH-2) x = TankFrame.GAME_WIDTH - Tank.WIDTH - 2;
        if (this.y > TankFrame.GAME_HEIGHT-Tank.HEIGHT-2) y = TankFrame.GAME_HEIGHT - Tank.HEIGHT - 2;
    }

}
