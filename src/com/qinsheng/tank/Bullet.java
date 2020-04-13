package com.qinsheng.tank;

import org.w3c.dom.css.Rect;

import java.awt.*;

/**
 * Created by qinsheng on 2020/4/12.
 */
public class Bullet {

    private static final int SPEED = 10;

    public static int WIDTH = ResourceManager.bulletD.getWidth();
    public static int HEIGHT = ResourceManager.bulletD.getHeight();

    private int x, y;
    private Dir dir;
    private Group group = Group.BAD;

    TankFrame tankFrame = null;

    private boolean living = true;

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Bullet(int x, int y, Dir dir, Group group, TankFrame tankFrame) {
        this.x = x;
        this.y = y;

        this.dir = dir;
        this.group = group;
        this.tankFrame = tankFrame;
    }


    public void paint(Graphics graphics) {
        if(!living) {
            tankFrame.bulletList.remove(this);
        }

        switch (dir) {
            case LEFT:
                graphics.drawImage(ResourceManager.bulletL, x, y, null);
                break;
            case RIGHT:
                graphics.drawImage(ResourceManager.bulletR, x, y, null);
                break;
            case DOWN:
                graphics.drawImage(ResourceManager.bulletD, x, y, null);
                break;
            case UP:
                graphics.drawImage(ResourceManager.bulletU, x, y, null);
                break;
        }

        move();
    }

    private void move(){
        switch (dir) {
            case UP:
                y -= SPEED;
                break;
            case DOWN:
                y += SPEED;
                break;
            case LEFT:
                x -= SPEED;
                break;
            case RIGHT:
                x += SPEED;
                break;
        }

        if(x < 0 || y < 0 || x > TankFrame.GAME_WIDTH || y > TankFrame.GAME_HEIGHT) living = false;
    }

    private void die() {
        this.living = false;
    }

    public void collideWith(Tank tank) {
        if(this.group == tank.getGroup()) return;

        //TODO: 用一个rect来记录子弹的位置
        Rectangle rect1 = new Rectangle(this.x, this.y, WIDTH, HEIGHT);
        Rectangle rect2 = new Rectangle(tank.getX(), tank.getY(), Tank.WIDTH, Tank.HEIGHT);
        if(rect1.intersects(rect2)) {
            tank.die();
            this.die();
        }
    }
}
