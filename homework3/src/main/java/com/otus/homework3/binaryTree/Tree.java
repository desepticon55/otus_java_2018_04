package com.otus.homework3.binaryTree;

public interface Tree {
  void add(Integer value);
  boolean remove(Integer value);
  TreeNode<Integer> remove(Integer value, TreeNode<Integer> node);
  Integer size();
  TreeNode<Integer> removeMin(TreeNode<Integer> node);
  TreeNode<Integer> findMin(TreeNode<Integer> node);
}
