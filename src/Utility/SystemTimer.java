package Utility;

public class SystemTimer {

    long startTime;
    long currentTime;

    public SystemTimer(){
        startTime = System.nanoTime();
    }

    public void reset(){
        startTime = System.nanoTime();
    }

    public long getElapsedTime(){
        return (System.nanoTime() - startTime)/1000000000;
    }

}
