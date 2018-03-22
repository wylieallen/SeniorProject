package Controller;

import Model.Pilot.Player;
import Utility.Geom3D.Vector3D;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class ShipInputHandler implements KeyListener {

    private boolean[] keys;

    public ShipInputHandler(){
        keys = new boolean[65536];
    }


    //TODO Get VECTOR direction from camera/view
    public void processInput(Player player){
        if (keys[KeyEvent.VK_W])
        {
            player.increaseShipSpeed();
        }
        else if (keys[KeyEvent.VK_S]){
            player.decreaseShipSpeed();
        }
        else {
            player.applyFriction();
        }


        //Fire Weapon 1
        if (keys[KeyEvent.VK_SPACE]){
            player.fireWeapon1();
        }

        //Use Special
        if (keys[KeyEvent.VK_SHIFT]){
            player.activateSpecial();
        }
        else{
            player.deactivateSpecial();
        }

        //Toggle Shield
        if (keys[KeyEvent.VK_E]){
            player.toggleShield();
        }

        //Adjust pitch up
        if (keys[KeyEvent.VK_UP]){
            player.getActiveShip().setPitchingUp(true);
        }
        else{
            player.getActiveShip().setPitchingUp(false);
        }

        //Adjust pitch down
        if (keys[KeyEvent.VK_DOWN]){
            player.getActiveShip().setPitchingDown(true);
        }
        else{
            player.getActiveShip().setPitchingDown(false);
        }

        //Adjust Yaw Left
        if (keys[KeyEvent.VK_LEFT]){
            player.getActiveShip().setYawingLeft(true);
        }
        else{
            player.getActiveShip().setYawingLeft(false);
        }

        //Adjust Yaw Right
        if (keys[KeyEvent.VK_RIGHT]){
            player.getActiveShip().setYawingRight(true);
        }
        else{
            player.getActiveShip().setYawingRight(false);
        }

        player.getActiveShip().update();
    }

    @Override
    public void keyTyped(KeyEvent e) {
        return;
    }

    @Override
    public void keyPressed(KeyEvent e){
        keys[e.getKeyCode()] = true;

    }

    @Override
    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()] = false;
    }
}
