package com.filecompression;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.HashMap;

public class FileCompression {
    public static void main(String[] args) throws Exception {

        // check command line arguments
        if(args.length < 1)
        {
            System.out.println("Please give a file path as command line argument!");
            System.out.println("Compress Ex.: \n java .\\FileCompression.java C:\\Users\\x\\text.txt");
            System.out.println("Decompress Ex. (d letter): \n java .\\FileCompression.java C:\\Users\\x\\text.txt d");
            return;
        }

        // parse arguments to extract properties of given file
        ArgumentsParser ap = new ArgumentsParser(args);

        // check if user wants to compress or decompress a file
        if (ap.getOption().equals("Compress"))
        {
            // extract text from given filepath
            String text = readUncompressedFile(ap.getFilePath());

            // create a map to store character-frequency pairs
            HashMap<Character, Integer> charFreqMap = new HashMap<>();

            // fill character-frequency map for each of the characters
            for(Character c : text.toCharArray())
                charFreqMap.put(c, charFreqMap.getOrDefault(c, 0) + 1);

            // create a Huffman algorithm object to encode the text
            HuffmanStructure hs = new HuffmanStructure();

            // encode the text
            hs.encodeText(text, charFreqMap, ap);
        }
        else
        {
            // extract huffman text and character-huffman map from given filepath as file properties
            HashMap<String, HashMap<Character, String>> fileProperties = readCompressedFile(ap.getFilePath());

            // store huffman text and character-huffman map which are retrieved as key-value pairs
            String huffmanText = fileProperties.keySet().iterator().next();
            HashMap<Character, String> charHuffmanMap = fileProperties.values().iterator().next();

            // create a Huffman algorithm object to decode the text
            HuffmanStructure hs = new HuffmanStructure();

            // decode the huffman text
            hs.decodeText(huffmanText, charHuffmanMap, ap);
        }
    }

    private static String readUncompressedFile(String filePath)
    {
        StringBuilder builder = new StringBuilder();

        try (FileInputStream fis = new FileInputStream(filePath))
        {
            int ch;
            while ((ch = fis.read()) != -1) { builder.append((char) ch); }
        }
        catch (Exception e) { e.printStackTrace(); }

        return builder.toString();
    }

    private static HashMap<String, HashMap<Character, String>> readCompressedFile(String filePath)
    {
        String huffmanText = "";
        HashMap<Character, String> charHuffmanMap = new HashMap<>();
        HashMap<String, HashMap<Character, String>> fileProperties = new HashMap<>();

        try (FileInputStream fis = new FileInputStream(filePath);
             ObjectInputStream ois = new ObjectInputStream(fis))
        {
            huffmanText = (String) ois.readObject();
            charHuffmanMap = (HashMap<Character, String>) ois.readObject();
            fileProperties.put(huffmanText, charHuffmanMap);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return fileProperties;
    }
}
