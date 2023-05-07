package com.company;

import java.util.ArrayList;
import java.util.Scanner;

public abstract class character implements atributs{
    //Attributes
    private long strength;           // Attack strength
    private long intelligence;       // Magic attack strength
    private long dexterity;          // Attack speed
    private long defense;            // Armor
    private long vitality;           // Health regeneration speed
    private long wisdom;             // Mana regeneration speed
    private long health;
    private long mana;
    private long level = 0;
    private long xp = 0;
    //Parameters
    private String race;            // Human, Orc, Elf
    private String name;
    private String classification;
    //inventory
    private ArrayList<String> inventory = new ArrayList<>();
    //
    private boolean trainingCompleted = false;

    public character(long strength, long intelligence, long dexterity, long defense, long vitality, long wisdom, long health, long mana, String race, String name, String classification) {
        this.strength = strength;
        this.intelligence = intelligence;
        this.dexterity = dexterity;
        this.defense = defense;
        this.vitality = vitality;
        this.wisdom = wisdom;
        this.health = health;
        this.mana = mana;
        this.race = race;
        this.name = name;
        this.classification = classification;
    }

    public void itemUpgrade(){
        // TODO: 6/19/2021 item upgrade mechanics and save/load inventory methods(with xml files)
        /*
         * Если 10 одинаковых мечей(или волшебных палочек если маг) в инвентаре, то можно превратить их в улучшенную версию этого меча(палочки если маг)
         * 10 x wooden sword -> 1 bronze sword; 10 x bronze sword -> 1 iron sword; 10 iron sword -> 1 gold sword; 10 x gold sword -> 1 legendary sword
         * У всех мечей разные характеристики:
         * wooden sword: +1 strength
         * bronze sword: +2 strength
         * iron sword: strength + 2 + character level + 1
         * gold sword: strength + 3 + character level + 3
         * legendary sword: strength + 5 + character level + defense level + 5
         * По умолчанию сначала "wooden sword" у всех персонажей 1 уровня
         */
        int countWS = 0;
        int countBS = 0;
        int countIS = 0;
        int countGS = 0;
        for (String item : inventory){
            switch (item){
                case "wooden sword":
                    countWS++;
                    break;
                case "bronze sword":
                    countBS++;
                    break;
                case "iron sword":
                    countIS++;
                    break;
                case "gold sword":
                    countGS++;
                    break;
            }
        }
        if (countWS >= 10){
            for (int i = 0; i < 10; i++){
                inventory.remove("wooden sword");
            }
            inventory.add("bronze sword");
        }
        if (countBS >= 10){
            for (int i = 0; i < 10; i++){
                inventory.remove("bronze sword");
            }
            inventory.add("iron sword");
        }
        if (countIS >= 10){
            for (int i = 0; i < 10; i++){
                inventory.remove("iron sword");
            }
            inventory.add("gold sword");
        }
        if (countGS >= 10){
            for (int i = 0; i < 10; i++){
                inventory.remove("gold sword");
            }
            inventory.add("legendary sword");
            System.out.println("Congrats! You just made legendary sword!");
        }
    }

    public void fight(character enemy){
        long maxHealth = this.getHealth();
        while (this.getHealth() != 0 | enemy.getHealth() != 0){
            //TODO fight mechanics with abilities and skills
            if (this.getRace().equals("Mage")){
                enemy.setHealth(enemy.getHealth() + enemy.getDefense() - this.getIntelligence());
            } else {
                enemy.setHealth(enemy.getHealth() + enemy.getDefense() - this.getStrength());
            }
            if (enemy.getHealth() <= 0 ){
                System.out.println("You won the fight!");
                // TODO: 6/19/2021 xp earning
                System.out.println("You earned" + (50*enemy.getLevel())/2 + "xp");
            }
        }
        this.setHealth(maxHealth);
    }


    public void training(){
//        if (trainingCompleted){
//            System.out.println("You can train only once per level!");
//            return;
//        }
        Scanner scanner = new Scanner(System.in);
        //train only once every level, not every day to become imbalance
        System.out.println("What do you want to train? Remember, you can only train once every level!");
        System.out.println("a:  Strength     b:  defense    c: dexterity    d: intelligence(only if you are mage)");
        String trainingType = scanner.nextLine();
        while (!trainingType.equals("a") & !trainingType.equals("b") & !trainingType.equals("c") & !trainingType.equals("d")){
            System.out.println("Invalid input! Try again:");
            System.out.println("What do you want to train? Remember, you can only train once every level!");
            System.out.println("a:  Strength     b:  defense    c: dexterity    d: intelligence");
            trainingType = scanner.nextLine();
        }
        switch (trainingType){
            case "a":
                //if ypu want to train strength(if you are not mage)
                if (!this.getRace().equals("Mage")) {
                    this.setStrength(this.getStrength() + 1);
                    this.setXp(this.getXp() + 1);
                } else {
                    System.out.println("You cannot train strength as mage!");
                    training();
                }
            case "b":
                //if tou want to train defense
                this.setDefense(this.getDefense() + 1);
                this.setXp(this.getXp() + 1);
            case "c":
                //if you want to train dexterity
                this.setDexterity(this.getDexterity() + 1);
                this.setXp(this.getXp() + 1);
            case "d":
                //if you want to train intelligence
                if (this.getRace().equals("Mage")) {
                    this.setIntelligence(this.getIntelligence() + 1);
                    this.setXp(this.getXp() + 5);
                } else {
                    System.out.println("You can only train intelligence as mage");
                    training();
                }
        }
        trainingCompleted = true;
    }

    public void lvlUp(int xpToNext){
        this.setLevel(this.getLevel() + 1);
        this.setXp(this.getXp() - xpToNext);
        this.setHealth(this.getHealth() + 5);
        this.setStrength(this.getStrength() + 1);
        this.setDefense(this.getDefense() + 1);
        trainingCompleted = false;
        System.out.println("Level up! Now you have +1 Strength, +5 health and +1 defense");
    }

    public ArrayList<String> getInventory() {
        return inventory;
    }

    public void setInventory(ArrayList<String> inventory) {
        this.inventory = inventory;
    }

    public void setLevel(long level) {
        this.level = level;
    }

    public void setXp(long xp) {
        this.xp = xp;
    }

    public void setStrength(long strength) {
        this.strength = strength;
    }

    public void setIntelligence(long intelligence) {
        this.intelligence = intelligence;
    }

    public void setDexterity(long dexterity) {
        this.dexterity = dexterity;
    }

    public void setDefense(long defense) {
        this.defense = defense;
    }

    public void setVitality(long vitality) {
        this.vitality = vitality;
    }

    public void setWisdom(long wisdom) {
        this.wisdom = wisdom;
    }

    public void setHealth(long health) {
        this.health = health;
    }

    public void setMana(long mana) {
        this.mana = mana;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public long getStrength() {
        return strength;
    }

    public long getIntelligence() {
        return intelligence;
    }

    public long getDexterity() {
        return dexterity;
    }

    public long getDefense() {
        return defense;
    }

    public long getVitality() {
        return vitality;
    }

    public long getWisdom() {
        return wisdom;
    }

    public long getHealth() {
        return health;
    }

    public long getMana() {
        return mana;
    }

    public long getLevel() {
        return level;
    }

    public long getXp() {
        return xp;
    }

    public String getRace() {
        return race;
    }

    public String getName() {
        return name;
    }

    public String getClassification() {
        return classification;
    }
}
