package com.filecompression;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.*;

public class HuffmanStructure {

    public void encodeText(String text, HashMap<Character, Integer> charFreqMap, ArgumentsParser ap) throws IOException
    {
        // define a string to store the resulting encoded text
        StringBuilder encodedText = new StringBuilder();

        // create a min-priority queue to apply Huffman algorithm
        PriorityQueue<Node> priorityQueue = new PriorityQueue<>();

        // fill min-priority queue with items in character-frequency map
        charFreqMap.forEach((k, v) -> priorityQueue.add(new Node(k, v)));

        // combine nodes according to create Huffman algorithm
        PriorityQueue<Node> combinedPriorityQueue = combineNodes(priorityQueue);

        // store top element of priority queue as root node of Huffman algorithm tree
        Node root = combinedPriorityQueue.peek();

        // create a map to store Huffman values of each character
        HashMap<Character, String> charHuffmanMap = new HashMap<>();

        // fill character-coded string map with items in Huffman algorithm tree
        encodeHuffman(root,"", charHuffmanMap);

        // loop over each character of given text to create encoded text
        for (Character c : text.toCharArray())
            encodedText.append(charHuffmanMap.get(c));

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
    {
        // define a string to store Huffman code of a character
        StringBuilder huffmanCode = new StringBuilder();

        // define a string to store the resulting decoded text
        StringBuilder decodedText = new StringBuilder();

        // loop over each character of given text to create decoded text
        for (int i=0; i<huffmanText.length(); i++)
        {
            huffmanCode.append(huffmanText.charAt(i));

            if (charHuffmanMap.containsValue(huffmanCode.toString()))
            {
                decodedText.append(findChar(charHuffmanMap, huffmanCode));
                huffmanCode.setLength(0);
            }
        }

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

            // store the combination into priority queue
            priorityQueue.add(new Node(null, combinationFrequency, leftChild, rightChild));
        }

        return priorityQueue;
    }

    private static void encodeHuffman(Node root, String huffmanString, HashMap<Character, String> charHuffmanMap)
    {
        // base case for recursion
        if(Objects.isNull(root)) return;

        // check if it is a leaf node, in other words, check if it is a character
        if(Objects.isNull(root.leftChild) && Objects.isNull(root.rightChild))
            charHuffmanMap.put(root.character, (huffmanString.length() > 0) ? huffmanString : "1");

        // search recursively all Huffman algorithm tree
        encodeHuffman(root.leftChild, huffmanString + "0", charHuffmanMap);
        encodeHuffman(root.rightChild, huffmanString + "1", charHuffmanMap);
    }

    // find the character from character-Huffman map, which refers to the given Huffman code
    private Character findChar(HashMap<Character, String> charHuffmanMap, StringBuilder huffmanCode)
    {
        return charHuffmanMap
                .entrySet()
                .stream()
                .filter(entry -> entry.getValue().equals(huffmanCode.toString()))
                .map(Map.Entry::getKey).toList().get(0);
    }
}
