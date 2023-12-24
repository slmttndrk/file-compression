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

        if (ap.getOption().equals("Compress"))
        {
            // extract text from given file
            String text = "";
            try (FileInputStream fis = new FileInputStream(ap.getFilePath()))
            {
                StringBuilder builder = new StringBuilder();
                int ch;
                while ((ch = fis.read()) != -1)
                {
                    builder.append((char) ch);
                }
                text = builder.toString();

                System.out.println("1. text : ");
                System.out.println(text);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

            // create a map to store character-frequency pairs
            HashMap<Character, Integer> charFreqMap = new HashMap<>();

            // fill character-frequency map for each of the characters
            for(Character c : text.toCharArray())
                charFreqMap.put(c, charFreqMap.getOrDefault(c, 0) + 1);

            System.out.println("2. charFreqMap : ");
            charFreqMap.forEach((key, value) -> System.out.println(key + " -> " + value));

            // encode or decode given text according to Huffman algorithm
            HuffmanStructure hs = new HuffmanStructure(text, ap.getOption(),charFreqMap);

            // retrieve the encoded or decoded text
            String codedText = hs.getCodedText();

            // define the output path to write coded text
            String outputPath = ap.getDirPath() + ap.getOption() + "ed-" + ap.getFileName();

            // write coded text to defined output path
            Files.writeString(Path.of(outputPath), codedText, StandardCharsets.UTF_8);
        }
        else
        {
            String huffmanText = "";
            Node root;
            try (FileInputStream fis = new FileInputStream(ap.getFilePath());
                 ObjectInputStream ois = new ObjectInputStream(fis))
            {
                huffmanText = (String) ois.readObject();
                root = (Node) ois.readObject();

                System.out.println("1. huffmanText : ");
                System.out.println(huffmanText);
                System.out.println("1. rootFrequency : ");
                System.out.println(root.frequency);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}
