package Utility;

public interface Config {
    public static final float FRAMERATE = 30f;

    public static final double CURRENCY_OFFSET = .5;
    public static final double SPEED_OFFSET = .4;
    public static final double HEALTH_OFFSET = .5;
    public static final double SHIELD_OFFSET = 1;
    public static final double INVENTORY_OFFSET = .2;

    public static final int BASE_ENGINE_SPEED = 20;
    public static final int BASE_HULL_HEALTH = 120;
    public static final int BASE_HULL_INVENTORY = 16;
    public static final int BASE_SHIELD_VALUE = 50;

    public static final double ACCELERATE_RATE = 0.2;
    public static final double FRICTION_RATE = .1;

    //WEAPONS
    public static final int BASE_WEAPON_SPEED = 3;
    public static final int BASE_WEAPON_COOLDOWN = 20;
    public static final int BASE_WEAPON_DAMAGE = 10;

    //PROJECTILES
    public static final double PROJECTILE_SPEED_OFFSET = .5;
    public static final double PROJECTILE_DAMAGE_OFFSET = 5;
    public static final float BASE_PROJECTILE_RANGE = 300f;

    //SPECIALS
    public static final int BASE_MAX_FUEL = 100;
    public static final int BASE_FUEL_COST = 20;
    public static final int BASE_SPECIAL_USE = 20;
    public static final double MAX_FUEL_OFFSET = .5;
    public static final double FUEL_COST_OFFSET = .5;
    public static final double SPECIAL_USE_OFFSET = .5;

    public static final int ENERGY_WEAPON_CD = 20;
    public static final int SHIELD_CD = 12;

    public static final float AI_ACCURACY = .25f;

    //BATTLEZONE
    public static final int BATTLEZONE_SIZE = 500;
}
