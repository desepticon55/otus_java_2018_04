package com.otus.homework3.binaryTree;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TreeNode<T> {
  private T value;
  TreeNode<T> left;
  TreeNode<T> right;

  public TreeNode(T value) {
    this.value = value;
  }

  @Override
  public String toString() {
    return "TreeNode{" +
            "value=" + value +
            ", left=" + left +
            ", right=" + right +
            '}';
  }
}
