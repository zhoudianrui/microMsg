package io.vicp.frlib.dp.strategy;

/**
 * Created by zhoudr on 2017/3/7.
 */
public class KnifeBehavior implements WeaponBehavior {
    @Override
    public void useWeapon() {
        System.out.println("use knife to fight...");
    }
}
