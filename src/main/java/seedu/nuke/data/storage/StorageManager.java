package seedu.nuke.data.storage;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import seedu.nuke.data.ModuleManager;
import seedu.nuke.directory.Module;
import seedu.nuke.exception.CorruptedFileException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class StorageManager {
    private String dataFileName;

    /**
     * Constructor with name of the data file as argument.
     *
     * @param dataFileName the name of the file in which the data is stored.
     */
    public StorageManager(String dataFileName) {
        this.dataFileName = dataFileName;
    }

    /**
     * load from json data file and return an ArrayList of Module objects.
     *
     * @return an ArrayList of Module objects.
     */
    public ArrayList<Module> load() {
        String jsonStr;
        try {
            jsonStr = loadJsonStringFromFile(dataFileName);
            return (ArrayList<Module>) JSON.parseArray(jsonStr, Module.class);
        } catch (IOException e) {
            System.out.println("Error when reading from " + dataFileName);
            return new ArrayList<>();
        }
    }

    private static String loadJsonStringFromFile(String dataFileName) throws IOException {
        String encoding = "utf8";
        File file = new File(dataFileName);
        Long fileLength = file.length();
        byte[] fileContent = new byte[fileLength.intValue()];
        FileInputStream in = new FileInputStream(file);
        in.read(fileContent);
        in.close();
        try {
            return new String(fileContent, encoding);
        } catch (UnsupportedEncodingException e) {
            System.err.println("The OS does not support " + encoding);
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Save the current ArrayList of Module objects into json file.
     */
    public void save() {
        String jsonStr = JSON.toJSONString(ModuleManager.getModuleList());
        try {
            FileWriter fw = new FileWriter(dataFileName);
            fw.write(jsonStr);
            fw.flush();
            fw.close();
        } catch (IOException e) {
            System.out.println("something wrong when writing to " + dataFileName);
        }
    }

    /**
     * Saves the Module List into a file.
     */
    public void saveList() {
        try {
            FileWriter fileWriter = new FileWriter(dataFileName);
            fileWriter.write(new Encoder(ModuleManager.getModuleList()).encode());
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads the Module List from the saved file.
     */
    public void loadList() {
        try {
            FileReader fileReader = new FileReader(dataFileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            ArrayList<Module> moduleList = new Decoder(bufferedReader).decode();
            ModuleManager.setModuleList(moduleList);
            bufferedReader.close();
            fileReader.close();
        } catch (IOException e) {
            //e.printStackTrace();
            ModuleManager.setModuleList(new ArrayList<>());
        } catch (CorruptedFileException e) {
            System.out.println("file is corrupted!\n");
            ModuleManager.setModuleList(new ArrayList<>());
        }
    }

    public static String saveModuleToString(ArrayList<Module> moduleList) {
        return JSON.toJSONString(moduleList);
    }
}
