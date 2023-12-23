package com.filecompression;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

public class FileCompression {
    public static void main(String[] args) throws Exception {
        System.out.println("Welcome, this is a file compression application! \n");

        StringBuilder filePath = new StringBuilder(args[0]);
        StringBuilder dirPath = new StringBuilder();
        StringBuilder fileName = new StringBuilder();
        StringBuilder option =
                (args.length>1 && args[1].equals("d")) ? new StringBuilder(args[1]) : new StringBuilder();

        fileParser(filePath, dirPath, fileName);

        String text = readFileAsString(filePath);

        HashMap<Character, Integer> charFreq= new HashMap<>();

        createCharacterFrequency(charFreq, text);
        // charFreq.forEach((key, value) -> System.out.println(key + " -> " + value));

        HuffmanTree hf = new HuffmanTree();
        hf.createTree(charFreq);

        writeStringAsFile(dirPath, option, fileName, text);

    }

    // parser method to parse given filepath
    private static void fileParser(StringBuilder filePath, StringBuilder dirPath, StringBuilder fileName)
    {
        int splitIndex = filePath.lastIndexOf("\\") + 1;
        dirPath.append(filePath.substring(0,splitIndex));
        fileName.append(filePath.substring(splitIndex));
    }

    // convert text file into string
    private static String readFileAsString(StringBuilder filePath) throws IOException
    {
        return new String(Files.readAllBytes(Paths.get(String.valueOf(filePath))));
    }

    // calculate frequencies of each character
    private static void createCharacterFrequency(HashMap<Character, Integer> charFreq, String text) {
        for(Character c : text.toCharArray())
            charFreq.put(c, charFreq.getOrDefault(c, 0) + 1);
    }

    // convert string into text file
    private static void writeStringAsFile(StringBuilder dirPath, StringBuilder option,
                                          StringBuilder fileName, String text) throws IOException {

        if(option.isEmpty()){
            String filePath = dirPath + "Compressed-" + fileName;
            Files.writeString(Path.of(filePath), text, StandardCharsets.UTF_8);
        }
    }
}
