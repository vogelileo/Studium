import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestTree {

    @Test
    public void testInsert() {
        IBinarySearchTree<Integer> bst = new BinarySearchTree<>();
        bst.addNode(5);
        assertEquals(1, bst.size());
        assertEquals(5, bst.getRoot().value);

        bst.addNode(2);
        assertEquals(2, bst.size());
        assertEquals(5, bst.getRoot().value);
        assertEquals(2, bst.getRoot().left.value);

        bst.addNode(8);
        assertEquals(3, bst.size());
        assertEquals(5, bst.getRoot().value);
        assertEquals(2, bst.getRoot().left.value);
        assertEquals(8, bst.getRoot().right.value);
    }

    @Test
    public void testTree() {
        IBinarySearchTree<Integer> bst = new BinarySearchTree<>();
        bst.addNode(5);
        bst.addNode(2);
        bst.addNode(1);
        bst.addNode(6);
        bst.addNode(4);
        bst.addNode(7);
        
        Integer[] arr = {1, 2, 4, 5, 6, 7};
        List<Integer> expected = new ArrayList<>(Arrays.asList(arr));
        assertEquals(expected, bst.inorderTraversal());
    }

    @Test
    public void testSibling() {
        IBinarySearchTree<Integer> bst = new BinarySearchTree<>();
        bst.addNode(5);
        bst.addNode(2);
        bst.addNode(8);

        assertEquals(8, bst.sibling(bst.getRoot().left).value);
    }

    @Test
    public  void testRightLeftNodes(){
        IBinarySearchTree<Integer> bst = new BinarySearchTree<>();
        Node<Integer> rootNode =  bst.addNode(5);
        bst.addNode(2);
        bst.addNode(8);
        assertEquals(rootNode.left, bst.left(rootNode) );
        assertEquals(rootNode.right, bst.right(rootNode) );

    }

    @Test
    public void testParent(){
        IBinarySearchTree<Integer> bst = new BinarySearchTree<>();
        bst.addNode(5);
        Node<Integer> leftNode = bst.addNode(2);
        Node<Integer> rightNode = bst.addNode(8);

        assertEquals(5, bst.parent(rightNode).value);
        assertEquals(5, bst.parent(leftNode).value);
    }

    @Test
    public void testChildren() {
        IBinarySearchTree<Integer> bst = new BinarySearchTree<>();
        Node<Integer> rootNode = bst.addNode(5);
        Node<Integer> leftNode = bst.addNode(2);
        Node<Integer> rightNode = bst.addNode(8);

        Iterable<Node<Integer>> actualChildren = bst.children(rootNode);
        List<Node<Integer>> expectedChildren = Arrays.asList(leftNode, rightNode);

        // Convert actualChildren to a List for comparison
        List<Node<Integer>> actualChildrenList = new ArrayList<>();
        for (Node<Integer> node : actualChildren) {
            actualChildrenList.add(node);
        }

        assertTrue(actualChildrenList.containsAll(expectedChildren) && expectedChildren.containsAll(actualChildrenList));
        assertEquals(2, bst.numChildren(rootNode));
    }

    @Test
    public void testIsInternal() {
        // Initialize the binary search tree
        IBinarySearchTree<Integer> bst = new BinarySearchTree<>();
        bst.addNode(5);  // Root node
        Node<Integer> leftNode = bst.addNode(2);
        Node<Integer> rightNode = bst.addNode(8);
        Node<Integer> leftLeftNode = bst.addNode(1);  // Adding a left child for leftNode

        // Testing internal nodes (nodes with at least one child)
        assertTrue(bst.isInternal(bst.root()));         // Root node has children
        assertTrue(bst.isInternal(leftNode));        // Left node has a child (leftLeftNode)

        // Testing external nodes (nodes with no children)
        assertFalse(bst.isInternal(leftLeftNode));  // Left-Left node has no children
        assertFalse(bst.isInternal(rightNode));     // Right node has no children

        // Testing isExternal method indirectly
        assertTrue(bst.isExternal(leftLeftNode));  // Left-Left node is external
        assertTrue(bst.isExternal(rightNode));     // Right node is external
    }

    @Test
    public void testIsExternal() {
        IBinarySearchTree<Integer> bst = new BinarySearchTree<>();
        bst.addNode(5);  // Root node
        Node<Integer> leftNode = bst.addNode(2);
        Node<Integer> rightNode = bst.addNode(8);
        Node<Integer> leftLeftNode = bst.addNode(1);  // Adding a left child for leftNode

        // Testing isExternal directly
        assertFalse(bst.isExternal(bst.root()));        // Root node is internal
        assertFalse(bst.isExternal(leftNode));       // Left node is internal
        assertTrue(bst.isExternal(leftLeftNode));    // Left-Left node is external
    }

    @Test
    public void testIsRoot() {
        // Initialize the binary search tree
        IBinarySearchTree<Integer> bst = new BinarySearchTree<>();
        bst.addNode(5);  // Root node
        Node<Integer> leftNode = bst.addNode(2);
        Node<Integer> rightNode = bst.addNode(8);

        // Testing isRoot method
        assertTrue(bst.isRoot(bst.root()));            // Root node should return true
        assertFalse(bst.isRoot(leftNode));           // Left node is not root
        assertFalse(bst.isRoot(rightNode));         // Right node is not root
    }

}
