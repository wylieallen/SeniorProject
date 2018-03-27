package Model.Ship;

import Model.Items.Inventory;
import Model.Pilot.Pilot;
import Model.Ship.ShipParts.*;
import Model.physics.collidable.BoundingBoxCollidable;
import Utility.Geom3D.Dimension3D;
import Utility.Geom3D.Orientation3D;
import Utility.Geom3D.Point3D;
import Utility.SystemTimer;
import Utility.Geom3D.Vector3D;

import static Utility.Config.*;

public class Ship extends BoundingBoxCollidable{

    final private Pilot myPilot;
    final private ShipStats shipStats;
    final private ShipHull hullSlot;

    //Rendering info

    private boolean
            yawingLeft = false, yawingRight = false,
            pitchingUp = false, pitchingDown = false,
            rollingLeft = false, rollingRight = false;

    private boolean accelerating = false, decelerating = false;

    Vector3D facingDirection;
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

    public Ship(Pilot owner, ShipHull myShip){
        super(new Point3D(), new Dimension3D(.2f, .2f, 1), new Orientation3D());
        this.hullSlot = myShip;
        shipStats = new ShipStats(hullSlot.getmaxHealth());
        inventory = new Inventory(hullSlot.getInventorySize());
        myPilot = owner;
        shieldActivated = false;
        shieldCooldown = new SystemTimer();
    }

    public Ship(Pilot owner, ShipHull myShip, Point3D origin, Orientation3D orientation, float base, float height){
        super(origin, new Dimension3D(base, base, height), orientation);
        this.hullSlot = myShip;
        shipStats = new ShipStats(hullSlot.getmaxHealth());
        inventory = new Inventory(hullSlot.getInventorySize());
        myPilot = owner;
        shieldActivated = false;
        shieldCooldown = new SystemTimer();
    }

    public void equipWeapon1(ShipWeapon weapon){
        weaponSlot1 = weapon;
        updateMaxStats();
    }

    public void equipWeapon2(ShipWeapon weapon){
        weaponSlot2 = weapon;
        updateMaxStats();
    }

    public void equipEngine(ShipEngine engine){
        engineSlot = engine;
        updateMaxStats();
    }

    public void equipShield(ShipShield shield){
        shieldSlot = shield;
        updateMaxStats();
    }

    public void equipSpecial(ShipSpecial special){
        specialSlot = special;
        updateMaxStats();
    }

    //TODO add unequip methods

    public void updateMaxStats(){
        if (engineSlot != null) shipStats.setMaxSpeed(engineSlot.getMaxSpeed());
        if (specialSlot != null) shipStats.setMaxFuel(specialSlot.getmaxFuel());
        if (shieldSlot != null) shipStats.setMaxShield(shieldSlot.getmaxShield());
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

    public void useWeapon1(){
        if (!shieldActivated && weaponSlot1 != null) {
            weaponSlot1.fireWeapon(myPilot);
        }
        else{
            System.out.println("Shield is active, CANNOT fire");
        }
    }

    public void useWeapon2(){
        if (!shieldActivated && weaponSlot2 != null) {
            weaponSlot2.fireWeapon(myPilot);
        }
        else{
            System.out.println("Shield is active, CANNOT fire");
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
        }
        else if (shieldActivated){
            shieldCooldown.reset();
            shieldActivated = false;
            shipStats.modifyCurrentShield(-shipStats.getCurrentShield());
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
        shipStats.modifyCurrentSpeed(ACCELERATE_RATE);
    }

    public void decelerate(){
        shipStats.modifyCurrentSpeed(-ACCELERATE_RATE);
    }

    public void applyFriction(){
        if (shipStats.getCurrentSpeed() > 0){
            shipStats.modifyCurrentSpeed(-FRICTION_RATE);
        }
        else if (shipStats.getCurrentSpeed() < 0) {
            shipStats.modifyCurrentSpeed(FRICTION_RATE);
        }
    }


    public boolean shieldActivated(){
        return shieldActivated;
    }

    public void takeDamage(int amount){
        shipStats.modifyCurrentHealth(amount);
        if (!isAlive())
        {
            myPilot.pilotDied();
        }
    }

    @Override
    public void update(){

        super.update();

        boolean directionUpdate = false;
        if(rollingLeft ^ rollingRight)
        {
            adjustRoll(rollingRight ? rollSpeed : -rollSpeed);
            directionUpdate = true;
        }
        if(pitchingDown ^ pitchingUp)
        {
            adjustPitch(pitchingUp ? -pitchSpeed : pitchSpeed);
            directionUpdate = true;
        }
        if(yawingLeft ^ yawingRight)
        {
            adjustYaw(yawingRight ? yawSpeed : -yawSpeed);
            directionUpdate = true;
        }

        if (directionUpdate){
            float yawRads = super.getOrientation().getYaw()/180.0f * 3.1415926535f;
            float pitchRads = -super.getOrientation().getPitch()/180.0f * 3.1415926535f;

            float i = (float) ((Math.cos(pitchRads) * Math.sin(yawRads)));
            float j = (float) Math.sin(pitchRads);
            float k = (float) (Math.cos(pitchRads) * Math.cos(yawRads));
            facingDirection = new Vector3D(i,j,k);
            facingDirection.makeUnitVector();
        }

        if(accelerating)
        {
            accelerate();
        }
        if(decelerating)
        {
            decelerate();
        }

        // todo: remove this logic when we're ready to go back to locationtuples



        this.moveForward((float) shipStats.getCurrentSpeed());

        //System.out.println("Speed: " + shipStats.getCurrentSpeed() + " Curloc: " + super.getOrigin().getX() + "," + super.getOrigin().getY() + "," + super.getOrigin().getZ());
    }

    //Rendering Methods
    public void setYawingLeft(boolean yawingLeft) { this.yawingLeft = yawingLeft; }
    public void setYawingRight(boolean yawingRight) { this.yawingRight = yawingRight; }
    public void setPitchingUp(boolean pitchingUp) { this.pitchingUp = pitchingUp; }
    public void setPitchingDown(boolean pitchingDown) { this.pitchingDown = pitchingDown; }

    public void setAccelerating(boolean b) { this.accelerating = b; }
    public void setDecelerating(boolean b) { this.decelerating = b; }

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
}
