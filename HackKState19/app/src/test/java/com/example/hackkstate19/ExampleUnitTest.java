package com.example.hackkstate19;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        String fullText = "Deleting from an AVL tree is the same as a normal BST. Leaves can be straightforwardly deleted, nodes with a single child can have the child promoted, and nodes with both children can have their key replaced with either the maximal element in the left subtree (containing the lesser elements) or the minimal element in the right subtree (containing the greater elements). However, doing so may unbalance the tree. Deleting a node may reduce the height of some subtree which will affect the balance factor of its ancestors. The same rotations may be necessary to rebalance ancestor nodes.";
        String response = Summary.request("", "Okay", fullText, -1, 2);
        assertEquals("Deleting a node may reduce the height of some subtree which will affect the balance factor of its ancestors. The same rotations may be necessary to rebalance ancestor nodes.", response);
    }
}