import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
}
