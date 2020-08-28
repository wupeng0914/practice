package com.marvel.tank;

import java.awt.*;

/**
 * @Description Bullet
 * @Author wupeng
 * @Motto Stay Hungry, Stay Foolish !
 * @Date 2020/8/28 4:54 下午
 **/
public class Bullet {
    private int WIDTH = 0;
    private int HEIGHT = 0;
    private int x, y;
    private static final int SPEED=8;
    private Direction dir;
    private boolean living;

    public Bullet(int x, int y, Direction dir) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.living = true;
    }

    public void paint(Graphics g){
        switch (dir){
            case UP:
                g.drawImage(ResourceMgr.bulletU, x, y, null);
                WIDTH = ResourceMgr.bulletU.getWidth();
                HEIGHT = ResourceMgr.bulletU.getHeight();
                break;
            case DOWN:
                g.drawImage(ResourceMgr.bulletD, x, y, null);
                WIDTH = ResourceMgr.bulletD.getWidth();
                HEIGHT = ResourceMgr.bulletD.getHeight();
                break;
            case LEFT:
                g.drawImage(ResourceMgr.bulletL, x, y, null);
                WIDTH = ResourceMgr.bulletL.getWidth();
                HEIGHT = ResourceMgr.bulletL.getHeight();
                break;
            case RIGHT:
                g.drawImage(ResourceMgr.bulletR, x, y, null);
                WIDTH = ResourceMgr.bulletR.getWidth();
                HEIGHT = ResourceMgr.bulletR.getHeight();
                break;
        }
        move();
    }

    /**
     * 子弹移动
     */
    public void move(){
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
        //TODO:碰撞检测、边界检测
        boundsCheck();
    }

    /**
     * 判断子弹是否存活
     * @return
     */
    public boolean isLiving(){
        return living;
    }

    /**
     * 子弹消亡
     */
    public void dieAway(){
        this.living = false;
    }

    /**
     * 坦克移动边界检查
     */
    private void boundsCheck(){
        if (this.x < 0 || this.y < 0 ||
            this.x > TankFrame.GAME_WIDTH ||
            this.y > TankFrame.GAME_HEIGHT)
            dieAway();
    }

}
