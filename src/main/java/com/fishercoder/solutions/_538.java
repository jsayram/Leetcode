package com.fishercoder.solutions;

import com.fishercoder.common.classes.TreeNode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TreeMap;

/**
 * 538. Convert BST to Greater Tree
 *
 * Given a Binary Search Tree (BST), convert it to a Greater Tree such that every key of the original BST is changed
 * to the original key plus sum of all keys greater than the original key in BST.

 Example:

 Input: The root of a Binary Search Tree like this:
     5
   /   \
  2    13

 Output: The root of a Greater Tree like this:
     18
   /   \
  20   13
 */

public class _538 {

    public static class BSTSolution {
        /**
         * Traverse in this order: right -> root -> left
         */
        public TreeNode convertBST(TreeNode root) {
            dfs(root, 0);
            return root;
        }

        private int dfs(TreeNode root, int val) {
            if (root == null) {
                return val;
            }
            int right = dfs(root.right, val);
            root.val += right;
            int left = dfs(root.left, root.val);
            return left;
        }
    }

    public static class GenericSolution {
        //This solution is generic for both BST and regular binary trees
        public TreeNode convertBST(TreeNode root) {
            if (root == null) {
                return root;
            }
            List<Integer> list = new ArrayList<>();
            putNodeToList(list, root);
            Collections.sort(list);
            int[] sums = new int[list.size()];
            sums[list.size() - 1] = 0;
            for (int i = list.size() - 2; i >= 0; i--) {
                sums[i] = sums[i + 1] + list.get(i + 1);
            }
            TreeMap<Integer, Integer> treeMap = new TreeMap<>();
            for (int i = 0; i < list.size(); i++) {
                treeMap.put(list.get(i), sums[i]);
            }
            TreeNode result = new TreeNode(treeMap.get(list.get(0)));
            return generateResultRoot(root, treeMap, result);
        }

        private TreeNode generateResultRoot(TreeNode root, TreeMap<Integer, Integer> treeMap, TreeNode result) {
            if (root != null) {
                result.val = treeMap.get(root.val) + root.val;
            }
            if (root.left != null) {
                result.left = new TreeNode(0);
                generateResultRoot(root.left, treeMap, result.left);
            }
            if (root.right != null) {
                result.right = new TreeNode(0);
                generateResultRoot(root.right, treeMap, result.right);
            }
            return result;
        }

        private void putNodeToList(List<Integer> list, TreeNode root) {
            if (root != null) {
                list.add(root.val);
            }
            if (root.left != null) {
                putNodeToList(list, root.left);
            }
            if (root.right != null) {
                putNodeToList(list, root.right);
            }
        }
    }

}