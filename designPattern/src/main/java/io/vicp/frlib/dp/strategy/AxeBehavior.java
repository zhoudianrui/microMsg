package io.vicp.frlib.dp.strategy;

/**
 * Created by zhoudr on 2017/3/7.
 */
public class AxeBehavior implements WeaponBehavior {
    @Override
    public void useWeapon() {
        System.out.println("use axe to fight. cut cut cut");
    }
}
