package com.marvel.tank;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @Description TankFrame
 * @Author wupeng
 * @Motto Stay Hungry, Stay Foolish !
 * @Date 2020/8/26 2:31 下午
 **/
public class TankFrame extends Frame {

    public static final TankFrame INSTANCE = new TankFrame();

    static final int GAME_WIDTH = 1080, GAME_HEIGHT = 720;

    private Tank player;

    private List<Bullet> bullets = new ArrayList<>();

    Random random = new Random();
    private TankFrame(){
        setSize(GAME_WIDTH, GAME_HEIGHT);
        setResizable(false);
        setTitle("Tank War");

        player = new Tank(random.nextInt(GAME_WIDTH), random.nextInt(GAME_HEIGHT), Direction.UP, this);

        this.addWindowListener(new IWindowListener());
        this.addKeyListener(new IKeyListener());
    }

    @Override
    public void paint(Graphics g) {
        Color color = g.getColor();
        g.setColor(Color.WHITE);
        g.drawString("当前子弹数量：" + bullets.size(), 10, 40);
        g.setColor(color);
        //画出主战坦克
        player.paint(g);
        //子弹
        for (int i = 0; i < bullets.size(); i++) {
            if (bullets.get(i).isLiving()){
                bullets.get(i).paint(g);
            } else {
                bullets.remove(bullets.get(i));
            }
        }
    }

    /**
     * 重写update方法，解决屏幕闪烁问题
     */
    Image offScreenImage = null;
    @Override
    public void update(Graphics g) {
        if (offScreenImage == null) {
            offScreenImage = this.createImage(GAME_WIDTH, GAME_HEIGHT);
        }
        Graphics gOffScreen = offScreenImage.getGraphics();
        Color c = gOffScreen.getColor();
        gOffScreen.setColor(Color.BLACK);
        gOffScreen.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
        gOffScreen.setColor(c);
        paint(gOffScreen);
        g.drawImage(offScreenImage, 0, 0, null);
    }

    /**
     * 发射一颗子弹
     */
    public void addBullet(Bullet bullet) {
        bullets.add(bullet);
    }

    /**
     * 自定义窗口关闭事件监听
     */
    class IWindowListener extends WindowAdapter{
        @Override
        public void windowClosing(WindowEvent e) {
            System.out.println("Bye Bye !");
            System.exit(0);
        }
    }

    /**
     * 自定义键盘按键事件监听
     */
    class IKeyListener extends KeyAdapter{
        boolean TU=false;
        boolean TR=false;
        boolean TD=false;
        boolean TL=false;
        @Override
        public void keyPressed(KeyEvent key) {
            int keyCode = key.getKeyCode();
            switch(keyCode){
                case KeyEvent.VK_UP :
                    TU = true;
                    break;
                case KeyEvent.VK_RIGHT :
                    TR = true;
                    break;
                case KeyEvent.VK_DOWN :
                    TD = true;
                    break;
                case KeyEvent.VK_LEFT :
                    TL = true;
                    break;
                default:
                    break;
            }
            player.setMoving(true);
            changeTankDir();
        }
        @Override
        public void keyReleased(KeyEvent key) {
            int keyCode = key.getKeyCode();
            switch(keyCode){
                case KeyEvent.VK_UP :
                    TU = false;
                    break;
                case KeyEvent.VK_RIGHT :
                    TR = false;
                    break;
                case KeyEvent.VK_DOWN :
                    TD = false;
                    break;
                case KeyEvent.VK_LEFT :
                    TL = false;
                    break;
                case KeyEvent.VK_CONTROL :
                    player.fire();
                    break;
                default:
                    break;
            }
            changeTankDir();
        }

        /**
         * 改变坦克移动反向
         */
        private void changeTankDir(){
            if (!TU && !TR && !TD && !TL){
                player.setMoving(false);
            } else {
                if (TU) player.setDir(Direction.UP);
                if (TR) player.setDir(Direction.RIGHT);
                if (TD) player.setDir(Direction.DOWN);
                if (TL) player.setDir(Direction.LEFT);
            }
        }
    }
}
