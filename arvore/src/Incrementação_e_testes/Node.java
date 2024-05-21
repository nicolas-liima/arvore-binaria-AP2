package Incrementação_e_testes;

public class Node {
    char value;
    Node left, right;

    Node(char item) {
        value = item;
        left = right = null;
    }
}

  public class Binarytree {
    Node root;

    int evaluateExpression(Node node) {
        if (node == null) {
            return 0;
        }

        if (node.left == null && node.right == null) {
            return Character.getNumericValue(node.value);
        }

        int leftEvaluate = evaluateExpression(node.left);
        int rightEvaluate = evaluateExpression(node.right);

        if (node.value == '+') {
            return leftEvaluate + rightEvaluate;
        }

        if (node.value == '-') {
            return leftEvaluate - rightEvaluate;
        }

        if (node.value == '*') {
            return leftEvaluate * rightEvaluate;
        }

        return leftEvaluate / rightEvaluate;
    }
}
