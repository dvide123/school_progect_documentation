package com.company;

public interface atributs {
    void setStrength(long strength);
    void setIntelligence(long intelligence);
    void setDexterity(long dexterity);
    void setDefense(long defense);
    void setVitality(long vitality);
    void setWisdom(long wisdom);
    void setHealth(long health);
    void setMana(long mana);
    void setXp(long xp);
    void setLevel(long level);

    long getStrength();
    long getIntelligence();
    long getDexterity();
    long getDefense();
    long getVitality();
    long getWisdom();
    long getHealth();
    long getMana();
    long getXp();
    long getLevel();
    String getRace();
    String getName();
}