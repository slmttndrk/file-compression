package com.filecompression;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.HashMap;
import java.util.PriorityQueue;

public class FileCompression {
    public static void main(String[] args) throws Exception {

        if(args.length < 1)
        {
            System.out.println("Please give a file path as command line argument!");
            System.out.println("Compress Example: java .\\FileCompression.java C:\\Users\\x\\text.txt");
            System.out.println("Decompress Example: java .\\FileCompression.java C:\\Users\\x\\text.txt d");
            return;
        }

        // parse arguments to extract properties of given file
        ArgumentsParser ap = new ArgumentsParser(args);

        // check if user wants to compress or decompress a file
        if (ap.getOption().equals("Compress"))
        {
            // extract text from given file
            String text = readUncompressedFile(ap);

            // create a map to store character-frequency pairs
            HashMap<Character, Integer> charFreqMap = new HashMap<>();

            // fill character-frequency map for each of the characters
            for(Character c : text.toCharArray())
                charFreqMap.put(c, charFreqMap.getOrDefault(c, 0) + 1);

            System.out.println("2. charFreqMap : ");
            charFreqMap.forEach((key, value) -> System.out.println(key + " -> " + value));

            // create a Huffman algorithm object to encode the text
            HuffmanStructure hs = new HuffmanStructure();

            // encode the text
            hs.encodeText(text, charFreqMap, ap);
        }
        else
        {
            Tuple<String, HashMap<Character, String>> tuple;
            tuple = readCompressedFile(ap);

            String huffmanText = tuple.x;
            HashMap<Character, String> charHuffmanMap = tuple.y;
            System.out.println("2. huffmanText : ");
            System.out.println(huffmanText);
            System.out.println("2. charHuffmanMap : ");
            charHuffmanMap.forEach((k,v) -> System.out.println(k + " -> " + v));

            // create a Huffman algorithm object to encode the text
            HuffmanStructure hs = new HuffmanStructure();

            // decode the huffman text
            hs.decodeText(huffmanText, charHuffmanMap, ap);
        }
    }

    private static String readUncompressedFile(ArgumentsParser ap)
    {
        StringBuilder builder = new StringBuilder();

        try (FileInputStream fis = new FileInputStream(ap.getFilePath()))
        {
            int ch;
            while ((ch = fis.read()) != -1) { builder.append((char) ch); }
        }
        catch (Exception e) { e.printStackTrace(); }

        System.out.println("1. text : ");
        System.out.println(builder);

        return builder.toString();
    }

    private static Tuple<String, HashMap<Character, String>> readCompressedFile(ArgumentsParser ap)
    {
        String huffmanText = "";
        HashMap<Character, String> charHuffmanMap = new HashMap<>();

        try (FileInputStream fis = new FileInputStream(ap.getFilePath());
             ObjectInputStream ois = new ObjectInputStream(fis))
        {
            huffmanText = (String) ois.readObject();
            charHuffmanMap = (HashMap<Character, String>) ois.readObject();

            System.out.println("1. huffmanText : ");
            System.out.println(huffmanText);
            System.out.println("1. charHuffmanMap : ");
            charHuffmanMap.forEach((k,v) -> System.out.println(k + " -> " + v));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return new Tuple<>(huffmanText, charHuffmanMap);
    }
}
