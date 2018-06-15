package com.otus.homework3.binaryTree;

import lombok.Getter;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BinaryTree implements Tree{
  @Getter
  private TreeNode<Integer> head;

  private int count = 0;

  public BinaryTree() {
  }

  @Override
  public void add(Integer value) {
    if (Objects.isNull(head)) {
      head = new TreeNode<>(value);
    } else {
      addTo(value, head);
    }
  }

  private void addTo(Integer value, TreeNode<Integer> node) {
    TreeNode<Integer> n = new TreeNode<>(value);
    if (node.getValue() < value) {
      if (Objects.isNull(node.getRight())) {
        node.setRight(n);
      } else {
        addTo(value, node.getRight());
      }
    } else {
      if (Objects.isNull(node.getLeft())) {
        node.setLeft(n);
      } else {
        addTo(value, node.getLeft());
      }
    }
    count++;
  }

  @Override
  public Integer size() {
    return count;
  }

  @Override
  public boolean remove(Integer value) {
    Pair<TreeNode<Integer>, TreeNode<Integer>> firstElementWithParent = findFirstElementWithParent(value);
    TreeNode<Integer> current = firstElementWithParent.getRight();
    TreeNode<Integer> parent = firstElementWithParent.getLeft();

    if (Objects.isNull(current)) {
      return false;
    }

    remove(value, head);
    return true;
  }

  @Override
  public TreeNode<Integer> remove(Integer value, TreeNode<Integer> node) {
    if (Objects.isNull(node)) {
      throw new RuntimeException();
    }

    if (value.compareTo(node.getValue()) < 0) {
      node.setLeft(remove(value, node.getLeft()));
    } else if (value.compareTo(node.getValue()) > 0) {
      node.setRight(remove(value, node.getRight()));
    } else if (Objects.nonNull(node.getRight()) && Objects.nonNull(node.getLeft())) {
      node.setValue(findMin(node.getRight()).getValue());
      node.setRight(removeMin(node.getRight()));
    } else {
      node = Objects.nonNull(node.getLeft()) ? node.getLeft() : node.getRight();
    }
    return node;
  }

  @Override
  public TreeNode<Integer> removeMin(TreeNode<Integer> node) {
    if (Objects.isNull(node)) {
      throw new RuntimeException();
    }
    if (Objects.nonNull(node.getLeft())) {
      node.setLeft(removeMin(node.getLeft()));
      return node;
    } else {
      return node.getRight();
    }
  }

  @Override
  public TreeNode<Integer> findMin(TreeNode<Integer> node) {
    if (Objects.nonNull(node)) {
      while (Objects.nonNull(node.getLeft())) {
        node = node.getLeft();
      }
    }

    return node;
  }

  public Pair<TreeNode<Integer>, TreeNode<Integer>> findFirstElementWithParent(Integer value) {
    return findFirstElementWithParent(head, value);
  }

  private Pair<TreeNode<Integer>, TreeNode<Integer>> findFirstElementWithParent(TreeNode<Integer> node, Integer value) {
    TreeNode<Integer> current = node;
    TreeNode<Integer> parent = null;
    while (current != null) {
      if (value < current.getValue()) {
        parent = current;
        current = current.getLeft();
      } else if (value > current.getValue()) {
        parent = current;
        current = current.getRight();
      } else {
        break;
      }
    }
    return Pair.of(parent, current);
  }

  @Override
  public String toString() {
    if (head != null) {
      StringBuilder builder = new StringBuilder();
      List<String> output = output(head);
      for (String anOutput : output) {
        builder.append(anOutput);
        builder.append("\n");
      }
      return builder.toString();
    }
    return "Бинарное дерево пустое";
  }

  private List<String> output(TreeNode<Integer> node) {
    List<String> result = new ArrayList<>();
    if (node.getRight() != null) {
      List<String> temp = output(node.getRight());
      for (String aTemp : temp) {
        result.add("   " + aTemp);
      }
    }
    result.add(node.getValue().toString());
    if (node.getLeft() != null) {
      List<String> temp = output(node.getLeft());
      for (String aTemp : temp) {
        result.add("   " + aTemp);
      }
    }
    return result;
  }
}

