package io.vicp.frlib.dp.strategy;

/**
 * Created by zhoudr on 2017/3/7.
 */
public class Queen extends Character {

    public Queen() {
        System.out.println("I'm Queen");
        weaponBehavior = new KnifeBehavior();
    }

    @Override
    public void fight() {
        weaponBehavior.useWeapon();
    }

}
