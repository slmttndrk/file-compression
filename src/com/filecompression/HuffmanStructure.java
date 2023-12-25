package com.filecompression;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class HuffmanStructure {

    public void encodeText(String text, HashMap<Character, Integer> charFreqMap, ArgumentsParser ap) throws IOException
    {
        // define a string to store the resulting encoded text
        StringBuilder encodedText = new StringBuilder();

        // create a min-priority queue to apply Huffman algorithm
        PriorityQueue<Node> priorityQueue = new PriorityQueue<>();

        // fill min-priority queue with items in character-frequency map
        charFreqMap.forEach((k, v) -> priorityQueue.add(new Node(k, v)));
        System.out.println("3. priorityQueue : ");
        System.out.println(priorityQueue.peek().frequency);
        System.out.println("---------------------------");

        // combine nodes according to create Huffman tree
        PriorityQueue<Node> combinedPriorityQueue = combineNodes(priorityQueue);
        System.out.println("4. combinedPriorityQueue : ");
        System.out.println(combinedPriorityQueue.peek().frequency);
        System.out.println("---------------------------");

        // store top element of priority queue as root node of Huffman tree
        Node root = combinedPriorityQueue.peek();

        // create a map to store Huffman values of each character
        HashMap<Character, String> charHuffmanMap = new HashMap<>();

        // fill character-coded string map with items in Huffman tree
        encodeHuffman(root,"", charHuffmanMap);

        // loop over each character of given text to create encoded text
        for (Character c : text.toCharArray())
            encodedText.append(charHuffmanMap.get(c));

        System.out.println("EncodedString is : " + encodedText.toString());

        // define the output path to write encoded text
        String outputPath = ap.getDirPath() + ap.getOption() + "ed-" + ap.getFileName();

        // write encoded text to defined output path
        try (FileOutputStream fos = new FileOutputStream(outputPath);
                 ObjectOutputStream oos = new ObjectOutputStream(fos))
        {
            oos.writeObject(encodedText.toString());
            oos.writeObject(charHuffmanMap);
        }
    }

    public void decodeText(String huffmanText, HashMap<Character, String> charHuffmanMap, ArgumentsParser ap)
            throws IOException
    {
        {
            // define a string to store the resulting decoded text
//            StringBuilder decodedText = new StringBuilder();

//      if(Objects.isNull(root.leftChild) && Objects.isNull(root.rightChild))
//      {
//          for (int i=0; i<root.frequency; i++)
//              System.out.print(root.character);
//      }
//      else
//      {
//          System.out.println(decodeTree(root, encodedString));
//      }

//            return decodedText.toString();
        }

//        System.out.println("2. --------");
//        charHuffmanMap.forEach((k,v) -> System.out.println(k + " -> " + v));

        StringBuilder huffmanCode = new StringBuilder();
        StringBuilder decodedText = new StringBuilder();

        for (int i=0; i<huffmanText.length(); i++)
        {
            huffmanCode.append(huffmanText.charAt(i));

            if (charHuffmanMap.containsValue(huffmanCode.toString()))
            {
                decodedText.append(findChar(charHuffmanMap, huffmanCode));
                huffmanCode.setLength(0);
            }
        }

        System.out.println("DecodedText is : " + decodedText);

        // define the output path to write decoded text
        String outputPath = ap.getDirPath() + ap.getOption() + "ed-" + ap.getFileName();

        // write decoded text to defined output path
        try (FileOutputStream fos = new FileOutputStream(outputPath))
        {
            for (char c : decodedText.toString().toCharArray()) { fos.write(c); }
        }
        catch (Exception e) { e.printStackTrace(); }
    }

    private static PriorityQueue<Node> combineNodes(PriorityQueue<Node> priorityQueue)
    {
        // loop over each nodes
        while (priorityQueue.size() > 1)
        {
            // retrieve two least frequent node pairs
            Node leftChild = priorityQueue.poll();
            Node rightChild = priorityQueue.poll();

            // combine two least frequent node pairs
            int combinationFrequency = leftChild.frequency + rightChild.frequency;
            System.out.println(combinationFrequency);

            // store the combination into priority queue
            priorityQueue.add(new Node(null, combinationFrequency, leftChild, rightChild));
        }

        return priorityQueue;
    }

    private static void encodeHuffman(Node root, String huffmanString, HashMap<Character, String> charHuffmanMap)
    {
        if(Objects.isNull(root)) return;

        if(Objects.isNull(root.leftChild) && Objects.isNull(root.rightChild))
            charHuffmanMap.put(root.character, (huffmanString.length() > 0) ? huffmanString : "1");

        encodeHuffman(root.leftChild, huffmanString + "0", charHuffmanMap);
        encodeHuffman(root.rightChild, huffmanString + "1", charHuffmanMap);
    }

//    private static StringBuilder decodeTree(Node root, StringBuilder huffmanString)
//    {
//        StringBuilder decodedString = new StringBuilder();
////        Node current = root;
////
////        for (int i=0; i<huffmanString.length(); i++)
////        {
////            current = (huffmanString.charAt(i) == '0') ? current.leftChild : current.rightChild;
////
////            if(Objects.isNull(current.leftChild) && Objects.isNull(current.rightChild))
////            {
////                decodedString.append(current.character);
////                current = root;
////            }
////        }
//
//        return decodedString;
//    }

    private Character findChar(HashMap<Character, String> charHuffmanMap, StringBuilder huffmanCode)
    {
        List<Character> charList = charHuffmanMap
                .entrySet()
                .stream()
                .filter(entry -> entry.getValue().equals(huffmanCode.toString()))
                .map(Map.Entry::getKey).toList();

        for (Character c : charList) System.out.println(c);

        return charList.get(0);
    }
}
