package Model.Pilot;

public class PilotStats {

    private int level;
    private int experience;
    private int flying;
    private int combat;
    private int charisma;

    public PilotStats(int level){
        this.level = level;
        this.experience = 0;
        this.flying = 1;
        this.combat = 1;
        this.charisma = 1;
    }


    public void levelUp(){
        level++;
    }

    // Getters and Setters

    public int getLevel(){
        return level;
    }

    public int getExperience(){ return experience; }

    public int getFlying(){
        return flying;
    }

    public int getCombat(){
        return combat;
    }

    public int getCharisma() {
        return charisma;
    }

    public void modifyExperience(int amount){
        experience += amount;
    }

    public void modifyFlying(int amount){
        flying += amount;
    }

    public void modifyCombat(int amount){
        combat += amount;
    }

    public void modifyCharisma(int amount){
        charisma += amount;
    }


}
