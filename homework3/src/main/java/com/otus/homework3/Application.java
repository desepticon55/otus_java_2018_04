package com.otus.homework3;

import com.otus.homework3.binaryTree.BinaryTree;
import com.otus.homework3.binaryTree.Tree;

public class Application {

  public static void main(String[] args) {
    Tree tree = new BinaryTree();
    tree.add(8);
    tree.add(5);
    tree.add(10);
    tree.add(2);
    tree.add(7);
    tree.add(6);
    System.out.println(tree.toString());
    System.out.println(((BinaryTree) tree).findFirstElementWithParent(2));
    tree.remove(5);
    System.out.println(tree.toString());
    System.out.println(((BinaryTree) tree).getHead());
  }
}
