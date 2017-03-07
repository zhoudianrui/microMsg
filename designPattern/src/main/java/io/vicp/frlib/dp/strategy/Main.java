package io.vicp.frlib.dp.strategy;

/**
 * Created by zhoudr on 2017/3/7.
 */
public class Main {
    public static void main(String[] args) {
        King king = new King();
        king.fight();
        Queen queen = new Queen();
        queen.fight();
        System.out.println("king want to change weapon to knife");
        king.setWeaponBehavior(queen.getWeaponBehavior());
        king.fight();
    }
}
