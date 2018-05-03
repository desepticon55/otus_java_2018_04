package com.otus.homework3.binaryTree;

import java.util.Objects;

public class BinaryTree {
  private TreeNode<Integer> head;

  public BinaryTree() {
  }

  public void add(Integer value) {
    head = addTo(value, head);
  }

  private TreeNode<Integer> addTo(Integer value, TreeNode<Integer> node) {
    if (Objects.isNull(node)) {
      return new TreeNode<>(value);
    }

    if (node.getValue() < value) {
      TreeNode<Integer> n = new TreeNode<>(node.right.getValue());
      n.left = node.left;
      n.right = addTo(value, node.right);
      return n;
    } else {
      TreeNode<Integer> n = new TreeNode<>(node.left.getValue());
      n.right = node.right;
      n.left = addTo(value, node.left);
      return n;
    }
  }

  @Override
  public String toString() {

    return "BinaryTree{" +
            "head=" + head.toString() +
            '}';
  }

//  private Integer getValue(TreeNode<Integer> node) {
//    if (Objects.isNull(node.left) && Objects.isNull(node.right)) {
//      return node.getValue();
//    }
//  }
}

