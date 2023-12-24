package com.filecompression;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Objects;
import java.util.PriorityQueue;

public class HuffmanStructure {
    private final String text;
    private final String option;
    private final HashMap<Character, Integer> charFreqMap;

    HuffmanStructure(String text, String option, HashMap<Character, Integer> charFreqMap)
    {
        this.text = text;
        this.option = option;
        this.charFreqMap = charFreqMap;
    }
    public String getCodedText() {
        // define a string to store the resulting coded text
        StringBuilder codedText = new StringBuilder();

        // check if user wants to compress or decompress a file
        if(this.option.equals("Compress"))
        {
            // create a min-priority queue to apply Huffman algorithm
            PriorityQueue<Node> priorityQueue = new PriorityQueue<>();

            // fill min-priority queue with items in character-frequency map
            this.charFreqMap.forEach((k, v) -> priorityQueue.add(new Node(k, v)));
            System.out.println("3. priorityQueue : ");
            System.out.println(priorityQueue.peek().frequency);

            // combine nodes according to create Huffman tree
            combineNodes(priorityQueue);

            // store top element of priority queue as root node of Huffman tree
            Node root = priorityQueue.peek();

            // create a map to store Huffman values of each character
            HashMap<Character, String> charHuffmanMap = new HashMap<>();

            // fill character-coded string map with items in Huffman tree
            encodeHuffman(root,"", charHuffmanMap);

            // loop over each character of given text to create coded text
            for (Character c : this.text.toCharArray())
                codedText.append(charHuffmanMap.get(c));

            System.out.println("EncodedString is : " + codedText.toString());
        }
        else
        {
//            if(Objects.isNull(root.leftChild) && Objects.isNull(root.rightChild))
//            {
//                for (int i=0; i<root.frequency; i++)
//                    System.out.print(root.character);
//            }
//            else
//            {
//                System.out.println(decodeTree(root, encodedString));
//            }
        }

        return codedText.toString();
    }

    private static void combineNodes(PriorityQueue<Node> priorityQueue)
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
    }

    private static void encodeHuffman(Node root, String huffmanString, HashMap<Character, String> charHuffmanMap)
    {
        if(Objects.isNull(root)) return;

        if(Objects.isNull(root.leftChild) && Objects.isNull(root.rightChild))
            charHuffmanMap.put(root.character, (huffmanString.length() > 0) ? huffmanString : "1");

        encodeHuffman(root.leftChild, huffmanString + "0", charHuffmanMap);
        encodeHuffman(root.rightChild, huffmanString + "1", charHuffmanMap);
    }

    private static StringBuilder decodeTree(Node root, StringBuilder huffmanString)
    {
        StringBuilder decodedString = new StringBuilder();
//        Node current = root;
//
//        for (int i=0; i<huffmanString.length(); i++)
//        {
//            current = (huffmanString.charAt(i) == '0') ? current.leftChild : current.rightChild;
//
//            if(Objects.isNull(current.leftChild) && Objects.isNull(current.rightChild))
//            {
//                decodedString.append(current.character);
//                current = root;
//            }
//        }

        return decodedString;
    }
}
