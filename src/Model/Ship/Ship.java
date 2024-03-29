package Model.Ship;

import Model.Items.Inventory;
import Model.Pilot.Pilot;
import Model.Ship.ShipParts.*;
import Model.Ship.ShipParts.Projectile.Projectile;
import Utility.Geom3D.Orientation3D;
import Utility.Geom3D.Point3D;
import Utility.SystemTimer;
import Utility.Geom3D.Vector3D;
import guiframework.gui2d.ImageFactory;

import java.awt.image.BufferedImage;
import java.util.Collection;
import java.util.HashSet;

import static Utility.Config.*;

public class Ship{

    final private Pilot myPilot;
    final private ShipStats shipStats;
    final private ShipHull hullSlot;

    //Rendering info

    private boolean
            yawingLeft = false, yawingRight = false,
            pitchingUp = false, pitchingDown = false,
            rollingLeft = false, rollingRight = false;

    private boolean accelerating = false, decelerating = false, breaking = false;

    private Vector3D facingDirection;
    private float yawSpeed = 3.0f, pitchSpeed = 3.0f , rollSpeed = 3.0f;


    //
    private ShipWeapon weaponSlot1;
    private ShipWeapon weaponSlot2;
    private ShipEngine engineSlot;
    private ShipShield shieldSlot;
    private ShipSpecial specialSlot;
    private Inventory inventory;

    private boolean shieldActivated;
    private SystemTimer shieldCooldown;
    private boolean firing1 = false;
    private boolean firing2 = false;

    public Ship(Pilot owner, ShipHull myShip){
        //super(new Point3D(), new Dimension3D(.2f, .2f, 1), new Orientation3D());
        this.hullSlot = myShip;
        shipStats = new ShipStats(hullSlot.getmaxHealth());
        inventory = new Inventory(hullSlot.getInventorySize());
        myPilot = owner;
        shieldActivated = false;
        shieldCooldown = new SystemTimer();
        this.facingDirection = new Vector3D(0, 0, -1);
    }

    public Ship(Pilot owner, ShipHull myShip, Point3D origin, Orientation3D orientation, float base, float height){
        //super(origin, new Dimension3D(base, base, height), orientation);
        this.hullSlot = myShip;
        shipStats = new ShipStats(hullSlot.getmaxHealth());
        inventory = new Inventory(hullSlot.getInventorySize());
        myPilot = owner;
        owner.setActiveShip(this);
        shieldActivated = false;
        shieldCooldown = new SystemTimer();
        this.facingDirection = new Vector3D(0, 0, -1);
    }

    public Pilot getMyPilot() {
        return myPilot;
    }


    public void equipWeapon1(ShipWeapon weapon){
        inventory.removeItem(weapon);
        if (weaponSlot1 != null){
            inventory.addItem(weaponSlot1);
        }
        weaponSlot1 = weapon;
        updateMaxStats();
    }

    public float getWeaponSlot1ProjectileSpeed() {
        return weaponSlot1.getProjectileSpeed();
    }

    public void equipWeapon2(ShipWeapon weapon){
        inventory.removeItem(weapon);
        if (weaponSlot2 != null){
            inventory.addItem(weaponSlot2);
        }
        weaponSlot2 = weapon;
        updateMaxStats();
    }

    public float getWeaponSlot2ProjectileSpeed() {
        return weaponSlot2.getProjectileSpeed();
    }

    public void equipEngine(ShipEngine engine){
        inventory.removeItem(engine);
        if (engineSlot != null){
            inventory.addItem(engineSlot);
        }
        engineSlot = engine;
        updateMaxStats();
    }

    public void equipShield(ShipShield shield){
        inventory.removeItem(shield);
        if (shieldSlot != null){
            inventory.addItem(shieldSlot);
        }
        shieldSlot = shield;
        updateMaxStats();
    }

    public void equipSpecial(ShipSpecial special){
        inventory.removeItem(special);
        if (specialSlot != null){
            inventory.addItem(specialSlot);
        }
        specialSlot = special;
        updateMaxStats();
    }


    //Ship part GETTERS
    public ShipEngine getEngineSlot() {
        return engineSlot;
    }

    public ShipHull getHullSlot() {
        return hullSlot;
    }

    public ShipShield getShieldSlot() {
        return shieldSlot;
    }

    public ShipSpecial getSpecialSlot() {
        return specialSlot;
    }

    public ShipWeapon getWeaponSlot1() {
        return weaponSlot1;
    }

    public ShipWeapon getWeaponSlot2() {
        return weaponSlot2;
    }


    public void updateMaxStats(){
        if (engineSlot != null) shipStats.setMaxSpeed(engineSlot.getMaxSpeed());
        if (specialSlot != null) shipStats.setMaxFuel(specialSlot.getmaxFuel());
        if (shieldSlot != null) shipStats.setMaxShield(shieldSlot.getmaxShield());
    }

    public void resetStats(){
        shipStats.reset();
        yawingLeft = false;
        yawingRight = false;
        pitchingUp = false;
        pitchingDown = false;
        rollingLeft = false;
        rollingRight = false;
        accelerating = false;
        decelerating = false;
        breaking = false;
        firing1 = false;
        firing2 = false;
        facingDirection = new Vector3D(0, 0, -1);
    }

    public Inventory getInventory(){
        return inventory;
    }

    public ShipStats getShipStats() {
        return shipStats;
    }

    public boolean isAlive(){
        return shipStats.isAlive();
    }

    public Collection<Projectile> useWeapon1(){
        if (shieldActivated){
            toggleShieldActivated();
        }
        if (weaponSlot1 != null) {
            return weaponSlot1.fireWeapon(myPilot);
        }
        else{
            return new HashSet<>();
        }
    }

    public Collection<Projectile> useWeapon2(){
        if (shieldActivated){
            toggleShieldActivated();
        }
        if (weaponSlot2 != null) {
            return weaponSlot2.fireWeapon(myPilot);
        }
        else{
            return new HashSet<>();
        }
    }

    public void activateSpecial(){
        if (specialSlot != null){
            specialSlot.activate(myPilot);
        }
    }

    public void deactivateSpecial(){
        if (specialSlot != null){
            specialSlot.deactivate(myPilot);
        }
    }

    public void toggleShieldActivated(){
        if (!shieldActivated && shieldCooldown.getElapsedTime() >= SHIELD_CD){
            shieldActivated = true;
            shipStats.modifyCurrentShield(shipStats.getMaxShield());
            System.out.println("Shield " + shieldActivated);
        }
        else if (shieldActivated){
            shieldCooldown.reset();
            shieldActivated = false;
            shipStats.modifyCurrentShield(-shipStats.getCurrentShield());
            System.out.println("Shield " + shieldActivated);
        }
    }

    public void setFacingDirection(Vector3D facingDirection) {
        facingDirection.makeUnitVector();
        this.facingDirection = facingDirection;
    }

    public double getDetectRange() {
        return shipStats.getDetectRange();
    }

    public void setDetectRange(double detectRange) {
        shipStats.setDetectRange(detectRange);
    }

    public Vector3D getFacingDirection() {
        return facingDirection;
    }

    public void accelerate(){
        shipStats.modifyCurrentSpeed(ACCELERATE_RATE + ACCELERATE_RATE*myPilot.getPilotStats().flyingScaling());
    }

    public void decelerate(){
        shipStats.modifyCurrentSpeed(-ACCELERATE_RATE - ACCELERATE_RATE*myPilot.getPilotStats().flyingScaling());
    }

    public void brake() { applyFriction(); }

    public void applyFriction(){
        if (shipStats.getCurrentSpeed() > 0 && shipStats.getCurrentSpeed() != 0){
            shipStats.modifyCurrentSpeed(-FRICTION_RATE);
            if (shipStats.getCurrentSpeed() < 0){
                shipStats.modifyCurrentSpeed(-shipStats.getCurrentSpeed());
            }
        }
        else if (shipStats.getCurrentSpeed() < 0 && shipStats.getCurrentSpeed() != 0) {
            shipStats.modifyCurrentSpeed(FRICTION_RATE);
            if (shipStats.getCurrentSpeed() > 0){
                shipStats.modifyCurrentSpeed(-shipStats.getCurrentSpeed());
            }
        }

    }

    public boolean shieldActivated(){
        return shieldActivated;
    }

    public void takeDamage(int amount){
        shipStats.modifyCurrentHealth(-amount);
        if (!isAlive())
        {
            myPilot.pilotDied();
        }
    }

    public boolean expired()
    {
        return !isAlive();
    }
    public void update()
    {
        weaponSlot1.update();
        weaponSlot2.update();
    }

    //Rendering Methods
    public void setYawingLeft(boolean yawingLeft) { this.yawingLeft = yawingLeft; }
    public void setYawingRight(boolean yawingRight) { this.yawingRight = yawingRight; }
    public void setPitchingUp(boolean pitchingUp) { this.pitchingUp = pitchingUp; }
    public void setPitchingDown(boolean pitchingDown) { this.pitchingDown = pitchingDown; }

    public void setAccelerating(boolean b) { this.accelerating = b; }
    public void setDecelerating(boolean b) { this.decelerating = b; }

    public void setBreaking(boolean breaking) { this.breaking = breaking; }

    public void setYawSpeed(float yawSpeed)
    {
        this.yawSpeed = yawSpeed;
    }

    public void setPitchSpeed(float pitchSpeed)
    {
        this.pitchSpeed = pitchSpeed;
    }

    public void ceaseRotation()
    {
        this.yawSpeed = this.pitchSpeed = this.rollSpeed = 0;
    }

    public boolean getRollingRight() { return rollingRight; }
    public boolean getRollingLeft() { return rollingLeft; }
    public boolean getPitchingUp() { return pitchingUp; }
    public boolean getPitchingDown() { return pitchingDown; }
    public boolean getYawingLeft() { return yawingLeft; }
    public boolean getYawingRight() { return yawingRight; }

    public float getRollSpeed() { return rollSpeed; }
    public float getPitchSpeed() { return pitchSpeed; }
    public float getYawSpeed() { return yawSpeed; }

    public boolean isAccelerating()
    {
        return accelerating;
    }

    public boolean isDecelerating() { return decelerating; }

    public boolean isBraking() { return breaking; }

    public float getSpeed()
    {
        return (float) shipStats.getCurrentSpeed();
    }

    public void setFiring1(boolean firing1) { this.firing1 = firing1; }

    public void setFiring2(boolean firing2) { this.firing2 = firing2; }

    public boolean isFiring1() {
        return firing1;
    }

    public boolean isFiring2() {
        return firing2;
    }

    //Image getters
//    public BufferedImage getShipImageWhite(){
//        return ImageFactory.getShipImageWhite();
//    }
    public BufferedImage getShipImageBlack(){
        return getHullSlot().getImage();
    }
}
