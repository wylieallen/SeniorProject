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
        if (keys[KeyEvent.VK_W] == true)
        {
            player.increaseShipSpeed();
        }
        else if (keys[KeyEvent.VK_S] == true){
            player.decreaseShipSpeed();
        }
        else {
            player.applyFriction();
        }

        if (keys[KeyEvent.VK_SPACE] == true){
            player.fireWeapon1();
        }

        if (keys[KeyEvent.VK_E] == true){
            player.toggleShield();
        }
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
