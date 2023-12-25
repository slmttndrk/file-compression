package com.filecompression;

public class Node implements Comparable<Node>{
    Character character;
    int frequency;
    Node leftChild = null;
    Node rightChild = null;

    Node(Character character, int frequency)
    {
        this.character = character;
        this.frequency = frequency;
    }

    Node(Character character, int frequency, Node leftChild, Node rightChild)
    {
        this.character = character;
        this.frequency = frequency;
        this.leftChild = leftChild;
        this.rightChild = rightChild;
    }

    // override the compareTo method to enable created Nodes to be sorted
    public int compareTo(Node n)
    {
        return this.frequency - n.frequency;
    }
}
