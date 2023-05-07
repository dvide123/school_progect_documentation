package com.company;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Game {

    public void saveGame(Character character, world world, String path) throws FileNotFoundException {
        //Create path to your saves!
        final String PATH = path + character.getName() + ".json";

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("strength", character.getStrength());
        jsonObject.put("intelligence", character.getIntelligence());
        jsonObject.put("dexterity", character.getDexterity());
        jsonObject.put("defense", character.getDefense());
        jsonObject.put("vitality", character.getVitality());
        jsonObject.put("wisdom", character.getWisdom());
        jsonObject.put("health", character.getHealth());
        jsonObject.put("mana", character.getMana());
        jsonObject.put("level", character.getLevel());
        jsonObject.put("xp", character.getXp());
        jsonObject.put("world day", world.getDay());
        jsonObject.put("name", character.getName());
        jsonObject.put("race", character.getRace());
        jsonObject.put("class", character.getClassification());

        PrintWriter pw = new PrintWriter(PATH);
        pw.write(jsonObject.toJSONString());
        pw.flush();
        pw.close();

        // TODO: 6/19/2021 saveinventory

    }

    public ArrayList<String> loadInventory(String path, Character character) {
        ArrayList<String> arrayList = new ArrayList<>();
        return arrayList;
    }

    public Character loadCharacter(String path, Scanner scanner) throws IOException, ParseException, NullPointerException{
        String[] pathNames;
        File f = new File(path);
        FilenameFilter filter = (f1, name) -> name.endsWith(".json");

        pathNames = f.list(filter);
        ArrayList<String> name = new ArrayList<>();

        for (String pathname : pathNames) {
            name.add(pathname);
        }
        printSaveFiles(name);
        int characterNumber = scanner.nextInt();
        scanner.nextLine();
        while (characterNumber > name.size()){
            System.out.println("Invalid input, try again!");
            printSaveFiles(name);
            characterNumber = Integer.parseInt(scanner.nextLine());
        }

        Character character = new warior("Human", name.get(characterNumber-1).replace(".json", ""), "Warrior");
        world world = new world();

        Object obj = new JSONParser().parse(new FileReader(path + name.get(characterNumber-1)));
        JSONObject jsonObject = (JSONObject) obj;

        character.setLevel((long) jsonObject.get("level"));
        character.setIntelligence((long) jsonObject.get("intelligence"));
        character.setDefense((long) jsonObject.get("defense"));
        character.setDexterity((long) jsonObject.get("dexterity"));
        character.setHealth((long) jsonObject.get("health"));
        character.setMana((long) jsonObject.get("mana"));
        character.setStrength((long) jsonObject.get("strength"));
        character.setVitality((long) jsonObject.get("vitality"));
        character.setWisdom((long) jsonObject.get("wisdom"));
        character.setXp((long) jsonObject.get("xp"));
        character.setRace((String) jsonObject.get("race"));
        character.setName((String) jsonObject.get("name"));
        character.setClassification((String) jsonObject.get("class"));
        world.setDay((long) jsonObject.get("world day"));

        return character;
    }

    public static void printSaveFiles(ArrayList<String> arrayList){
        System.out.println("Choose your character: ");
        for (int i = 0; i < arrayList.size(); i++){
            System.out.println(i+1 + ": " + arrayList.get(i).replace(".json", ""));
        }
    }
}
© 2021 GitHub, Inc.
