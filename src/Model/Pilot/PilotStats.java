package Model.Pilot;

public class PilotStats {

    private int level;
    private int experience;
    private int maxSkillPoints;
    private int currentSkillPoints;
    private final int SKILLCAP = 25;

    private int flying;
    private int combat;
    private int charisma;

    public PilotStats(int level){
        this.level = level;
        this.experience = 0;
        this.flying = 1;
        this.combat = 1;
        this.charisma = 1;
        this.maxSkillPoints = 1;
        this.currentSkillPoints = 1;
    }

    public PilotStats(int level, int experience, int maxSkillPoints, int currentSkillPoints, int flying, int combat, int charisma){
        this.level = level;
        this.experience = experience;
        this.flying = flying;
        this.combat = combat;
        this.charisma = charisma;
        this.maxSkillPoints = maxSkillPoints;
        this.currentSkillPoints = currentSkillPoints;
    }


    public void levelUp(){
            level++;
            maxSkillPoints++;
            currentSkillPoints++;
    }

    // Getters and Setters

    public int getLevel(){
        return level;
    }

    public int getCurrentSkillPoints() {
        return currentSkillPoints;
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
        if (experience >= 100){
            experience = experience-100;
            levelUp();
        }
    }

    public void modifySkillPoints(int amount) { currentSkillPoints += amount; }

    public Boolean levelFlying(){
        if (currentSkillPoints > 0 && flying < SKILLCAP){
            flying++;
            currentSkillPoints--;
            return true;
        }
        return false;
    }

    public Boolean levelCombat(){
        if (currentSkillPoints > 0 && combat < SKILLCAP){
            combat++;
            currentSkillPoints--;
            return true;
        }
        return false;
    }

    public Boolean levelCharisma(){
        if (currentSkillPoints > 0 && charisma < SKILLCAP){
            charisma++;
            currentSkillPoints--;
            return true;
        }
        return false;
    }

    public double flyingScaling(){
        return flying*.04;
    }

    public double charismaScaling(){
        return charisma*.04;
    }

    public double combatScaling(){
        return combat*.04;
    }




}
