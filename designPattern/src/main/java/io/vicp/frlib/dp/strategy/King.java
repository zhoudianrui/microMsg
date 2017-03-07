package io.vicp.frlib.dp.strategy;

/**
 * Created by zhoudr on 2017/3/7.
 */
public class King extends Character {

    public King() {
        System.out.println("I'm King");
        this.weaponBehavior = new AxeBehavior();
    }

    @Override
    public void fight() {
        weaponBehavior.useWeapon();
    }
}
