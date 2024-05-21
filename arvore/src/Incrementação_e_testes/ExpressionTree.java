package Incrementação_e_testes;

import java.util.Stack;

public class ExpressionTree {
    public static Node buildExpression(String[] E) {
        Stack<Node> S = new Stack<>();
        Node T, T1, T2;

        for (String e : E) {
            if (!e.equals("(") && !e.equals(")")) {
                T = new Node(e);
                S.push(T);
            } else if (e.equals(")")) {
                T2 = S.pop();
                T = S.pop();
                T1 = S.pop();
                T.left = T1;
                T.right = T2;
                S.push(T);
            }
        }
        return S.pop();
    }

    static class Node {
        String value;
        Node left, right;

        Node(String item) {
            value = item;
            left = right = null;
        }
    }
}
