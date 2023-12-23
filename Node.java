public class Node {
    Character character;
    int frequency;
    Node leftChild = null;
    Node rightChild = null;

    public Node(Character character, int frequency, Node leftChild, Node rightChild)
    {
        this.character = character;
        this.frequency = frequency;
        this.leftChild = leftChild;
        this.rightChild = rightChild;
    }
}
