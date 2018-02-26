package Controller;

import Model.Pilot.Player;
import Utility.Vector3D;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class ShipInputHandler implements KeyListener {

    private boolean[] keys;

    public ShipInputHandler(){
        keys = new boolean[65536];
        System.out.println("HIHIHIHIHIHI");
    }


    //TODO Get VECTOR direction from camera/view
    public void processInput(Player player){
        if (keys[KeyEvent.VK_W] == true)
        {
            Vector3D newVector = new Vector3D(0,1,0);
            player.accelerate(newVector);
        }
        else if (keys[KeyEvent.VK_S] == true){
            Vector3D newVector = new Vector3D(0,1,0);
            player.decelerate(newVector);
        }
        else {
            Vector3D newVector = new Vector3D(0,1,0);
            player.applyFriction(newVector);
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
