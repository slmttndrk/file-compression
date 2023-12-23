package com.filecompression;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Objects;
import java.util.PriorityQueue;

public class HuffmanTree {
    public static void createTree(HashMap<Character, Integer> charFreq) {

        PriorityQueue<Node> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(node -> node.frequency));
        charFreq.forEach((key, value) -> priorityQueue.add(new Node(key, value)));

        while (priorityQueue.size() > 1)
        {
            Node leftChild = priorityQueue.poll();
            Node rightChild = priorityQueue.poll();

            int combinationFrequency = leftChild.frequency + rightChild.frequency;

            priorityQueue.add(new Node(null, combinationFrequency, leftChild, rightChild));
        }

        Node root = priorityQueue.peek();
        HashMap<Character, String> huffmanHashMap = new HashMap<>();

        encodeTree(root,"", huffmanHashMap);

        StringBuilder encodedString = new StringBuilder();
        charFreq.forEach((key, value) -> encodedString.append(huffmanHashMap.get(key)));
        System.out.println("EncodedString is : " + encodedString);


    }

    private static void encodeTree(Node root, String huffmanString, HashMap<Character, String> huffmanHashMap)
    {
        if(Objects.isNull(root)) return;

        if(Objects.isNull(root.leftChild) && Objects.isNull(root.rightChild))
            huffmanHashMap.put(root.character, huffmanString.length() > 0 ? huffmanString : "1");

        encodeTree(root.leftChild, huffmanString + "0", huffmanHashMap);
        encodeTree(root.rightChild, huffmanString + "1", huffmanHashMap);
    }
}
