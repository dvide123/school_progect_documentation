package com.company;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

public class Main{

    //Path to your saves
    final static String SAVESPATH = "E:\\saves";

    public static void main(String[] args) throws Exception {
        Character character = null;
        Game game = new Game();
        System.out.println("Start a new game or load your character from a save:");
        System.out.println("a: load character      b: new character");
        //TODO Start game before new character
        Scanner scanner = new Scanner(System.in);
        String loadOrNewGame = scanner.nextLine();
        while(true){
            if (loadOrNewGame.equals("a")){
                if (!areSavesEmpty(SAVESPATH)){
                    character = game.loadCharacter(SAVESPATH, scanner);
                    character.setInventory(game.loadInventory(SAVESPATH, character));
                    break;
                }
            }
            if (loadOrNewGame.equals("b")){
                character = newCharacter(scanner);
                switch (character.getRace()){
                    case "Orc":
                        character.setStrength(character.getStrength() + 3);
                        character.setVitality(character.getVitality() + 2);
                        character.setDefense(character.getDefense() - 1);
                        break;
                    case "Elf":
                        character.setIntelligence(character.getIntelligence() + 1);
                        character.setWisdom(character.getWisdom() + 1);
                        character.setVitality(character.getVitality() + 1);
                        character.setDexterity(character.getDexterity() + 1);
                        break;
                    case "Human":
                        character.setDexterity(character.getDexterity() + 2);
                        break;
                }
                break;
            }
            System.out.println("Invalid input, try again!");
            System.out.println("Start a new game or load your character from a save:");
            System.out.println("a: load character      b: new game");
            loadOrNewGame = scanner.nextLine();
        }

        //TODO game logics and game AND rewrite code below normally
        world world = new world();
        boolean gameGoes = true;
        String save;
        String quitOrContinue;
        while (gameGoes){
            //TODO game

            //TODO game
            System.out.println("Do you want to save game?(every input except 'a' will automatically continue the game without saving it)");
            System.out.println("a: yes     b: no");
            save = scanner.nextLine();
            if (save.equals("a")){
                game.saveGame(character, world, SAVESPATH);
            }
            System.out.println("Do you want to continue or quit the game?(every input except 'a', will quit the game)");
            System.out.println("a: continue     b: quit");
            quitOrContinue = scanner.nextLine();
            if (!quitOrContinue.equals("a")){
                gameGoes = false;
            }
            world.setDay(world.getDay() + 1);
        }
    }

    public static boolean areSavesEmpty(String path){
        String[] pathNames;
        File f = new File(path);
        FilenameFilter filter = (f1, name) -> name.endsWith(".json");

        pathNames = f.list(filter);
        ArrayList<String> name = new ArrayList<>();

        for (String pathname : pathNames) {
            name.add(pathname);
        }
        if (name.isEmpty()){
            System.out.println("There are no saves in saves directory");
            return true;
        }
        return false;
    }

    public static Character newCharacter(Scanner scanner) throws Exception{
        System.out.println("Enter your name: ");
        String name = scanner.nextLine();
        System.out.println("Choose your characters race: ");
        System.out.println("a: Human    b: Orc     c: Elf");
        String race = scanner.nextLine();
        while (!race.equals("a") & !race.equals("b") & !race.equals("c")){
            System.out.println("Invalid input. Try again: ");
            System.out.println("Choose your characters race: ");
            System.out.println("a: Human    b: Orc     c: Elf");
            race = scanner.nextLine();
            if (race.equals("a") | race.equals("b") | race.equals("c")){
                break;
            }
        }
        switch(race){
            case "a":
                race = "Human";
                break;
            case "b":
                race = "Orc";
                break;
            case "c":
                race = "Elf";
                break;
        }
        System.out.println("Choose your characters class: ");
        System.out.println("a: Warrior    b: Ranger     c: Mage");
        String classification = scanner.nextLine();
        while (!classification.equals("a") & !classification.equals("b") & !classification.equals("c")){
            System.out.println("Invalid input. Try again: ");
            System.out.println("Choose your characters class: ");
            System.out.println("a: Warrior    b: Ranger     c: Mage");
            classification = scanner.nextLine();
        }

        switch(classification){
            case "a":
                return new warior(race, name, "Warrior");
            case "b":
                return new ranger(race, name, "Ranger");
            case "c":
                return new mage(race, name, "Mage");
            default:
                throw new Exception();
        }
    }
}