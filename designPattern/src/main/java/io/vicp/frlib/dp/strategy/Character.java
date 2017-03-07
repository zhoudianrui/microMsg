package io.vicp.frlib.dp.strategy;

/**
 * 角色
 * Created by zhoudr on 2017/3/7.
 */
public abstract class Character {

    protected WeaponBehavior weaponBehavior = null;

    /**
     * 描述每个角色如何战斗
     */
    public abstract void fight();

    /**
     * 更换不同的武器行为
     * @param weaponBehavior
     */
    public void setWeaponBehavior(WeaponBehavior weaponBehavior) {
        this.weaponBehavior = weaponBehavior;
    }

    public WeaponBehavior getWeaponBehavior() {
        return weaponBehavior;
    }

}
