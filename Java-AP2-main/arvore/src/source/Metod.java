package source;
import exceptions.BoundaryViolationException;
import exceptions.EmptyTreeException;
import exceptions.InvalidPositionException;
import exceptions.NonEmptyTreeException;
import position.Position;

import java.util.Scanner;
import java.util.Stack;

public class Metod {

    public static void main(String[] args) throws InvalidPositionException, NonEmptyTreeException, EmptyTreeException, BoundaryViolationException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Digite a expressão aritmética totalmente parentizada: ");
        String expression = scanner.nextLine();
    	String expression = "(((5+2)*(2-1))/(((2+9)+(7-2)-1)*8))";   	// A expressão do slide esta errada, falta parenteses então decida qual o jeito certo
        int[] count = new int[1];
        int[] values = {31, 25, 42, 12, 36, 56, 62, 75, 90}; // Valores para inserir na árvore
        Node root = null;
        
        for (int value : values) {
            root = insert(root, value);
        }

        // Construir a árvore a partir da expressão
        LinkedBinaryTree<String> tree = buildExpression(expression);

        // Imprimir a estrutura da árvore
        System.out.println("buildExpression");
        System.out.println("Estrutura da Árvore:");
        printTree(tree, tree.root(), 0);


        // Percorrer a árvore em pós-ordem binário
        System.out.println();
        System.out.println("binaryPostorder");
        System.out.print("Percurso em pós-ordem: ");
        binaryPostorder(tree, tree.root());
        System.out.println();

        // Avaliar a expressão aritmética
        double result = evaluateExpression(tree, tree.root());
        System.out.println();
        System.out.println("evaluateExpression");
        System.out.println("Resultado da expressão: " + result);
        
        // Percorrer a árvore em ordem inorder
        System.out.println();
        System.out.println("inorder");
        System.out.print("Percurso em ordem inorder: ");
        inorder(tree, tree.root());
        System.out.println();
        
        System.out.println();
        System.out.println("makerBTSearch");
        System.out.println("Caminhamento inorder da BST:");
        inorderTraversal(root);
        
        System.out.println();
        System.out.println();
        System.out.println("inorderWithCoordinates");
        System.out.println("Coordenadas dos nodos:");
        inorderWithCoordinates(tree, tree.root(), 0, count); 
        
     // Percorrer a árvore usando o caminhamento de Euler e imprimir os nodos
        System.out.println();
        System.out.println("eulerTourPrint");
        System.out.print("Árvore Binária de Expressão (caminhamento de Euler): ");
        eulerTour(tree, tree.root());
        System.out.println();
        
     // Imprimir a expressão aritmética a partir da árvore usando printExpression
        System.out.println("Expressão aritmética a partir da árvore:");
        printExpression(tree, tree.root());
        System.out.println();
        
        System.out.println();
        System.out.println("makerBTSearch");
        System.out.println("Caminhamento inorder da BST:");
        inorderTraversal(root);
        
        System.out.println();
        System.out.println();
        System.out.println("Número de nodos esquerdos e externos: " + countLeftExternalNodes(root));
        System.out.println();
        System.out.println("Número de nodos direitos e externos: " + countRightExternalNodes(root));
    
    }


    // Método para construir a árvore binária a partir da expressão aritmética
    public static LinkedBinaryTree<String> buildExpression(String expression) throws InvalidPositionException, NonEmptyTreeException, EmptyTreeException, BoundaryViolationException {
        Stack<LinkedBinaryTree<String>> stack = new Stack<>();
        int n = expression.length();

        for (int i = 0; i < n; i++) {
            char ei = expression.charAt(i);

            // Se ei é uma variável (número) ou operador
            if (Character.isLetterOrDigit(ei) || isOperator(ei)) {
                LinkedBinaryTree<String> T = new LinkedBinaryTree<>();
                T.addRoot(String.valueOf(ei));
                stack.push(T);
            } else if (ei == '(') {
                continue; // Ignorar o parêntese de abertura
            } else if (ei == ')') {
                // Combinar as últimas três árvores
                LinkedBinaryTree<String> T2 = stack.pop(); // E2
                LinkedBinaryTree<String> T = stack.pop(); // Operador
                LinkedBinaryTree<String> T1 = stack.pop(); // E1

                // Anexar E1 e E2 como subárvores do operador
                T.attach(T.root(), T1, T2);
                stack.push(T);
            }
        }
        return stack.pop(); // A árvore final será o único item restante na pilha
    }

    
    public static void printTree(LinkedBinaryTree<String> tree, Position<String> pos, int depth) throws InvalidPositionException, BoundaryViolationException {
        if (pos != null) {
            for (int i = 0; i < depth; i++) {
                System.out.print("  ");
            }
            System.out.println(pos.element());
            if (tree.hasLeft(pos)) {
                printTree(tree, tree.left(pos), depth + 1);
            }
            if (tree.hasRight(pos)) {
                printTree(tree, tree.right(pos), depth + 1);
            }
        }
    }
    
    
    private static boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }

    // Método de avaliação da expressão aritmética
    public static double evaluateExpression(LinkedBinaryTree<String> tree, Position<String> v) throws InvalidPositionException, BoundaryViolationException {
        if (tree.isInternal(v)) {
            // Se o nodo é interno, avalie as subárvores esquerda e direita
            String operator = v.element();
            double x = evaluateExpression(tree, tree.left(v));
            double y = evaluateExpression(tree, tree.right(v));

            // Retorne o resultado da aplicação do operador
            switch (operator) {
                case "+":
                    return x + y;
                case "-":
                    return x - y;
                case "*":
                    return x * y;
                case "/":
                    return x / y;
                default:
                    throw new IllegalArgumentException("Operador desconhecido: " + operator);
            }
        } else {
            // Se o nodo é uma folha, retorne o valor armazenado nele
            return Double.parseDouble(v.element());
        }
    }


    // Método para percorrer a árvore em pós-ordem binário
    public static void binaryPostorder(LinkedBinaryTree<String> tree, Position<String> v) throws InvalidPositionException, BoundaryViolationException {
        if (tree.hasLeft(v)) {
            binaryPostorder(tree, tree.left(v));
        }
        if (tree.hasRight(v)) {
            binaryPostorder(tree, tree.right(v));
        }
        System.out.print(v.element());
    }

    // Método para percorrer a árvore em ordem inorder
    public static void inorder(LinkedBinaryTree<String> tree, Position<String> v) throws InvalidPositionException, BoundaryViolationException {
        if (tree.hasLeft(v)) {
            inorder(tree, tree.left(v)); // percorre recursivamente a subárvore esquerda
        }

        System.out.print(v.element()); // execute a ação “de visita” sobre o nodo v

        if (tree.hasRight(v)) {
            inorder(tree, tree.right(v)); // percorre recursivamente a subárvore direita
        }
    }

    // Método auxiliar para imprimir a árvore binária em formato hierárquico

    public static void inorderWithCoordinates(LinkedBinaryTree<String> tree, Position<String> v, int depth, int[] count) {
        if (tree.hasLeft(v)) {
            inorderWithCoordinates(tree, tree.left(v), depth + 1, count); // Visita a subárvore esquerda
        }

        // Imprime o nó e suas coordenadas x e y
        System.out.println("x(" + v.element() + ") = " + count[0] + ", y(" + v.element() + ") = " + depth);
        count[0]++; // Incrementa o contador para a próxima coordenada x

        if (tree.hasRight(v)) {
            inorderWithCoordinates(tree, tree.right(v), depth + 1, count); // Visita a subárvore direita
        }
    }


    // Classe auxiliar para manter o estado do contador durante a recursão
 

    public static void eulerTour(LinkedBinaryTree<String> tree, Position<String> v) throws InvalidPositionException, BoundaryViolationException {
        if (v != null) {
            System.out.print(v.element()); // Visita o nó v "pela esquerda"
            if (tree.hasLeft(v)) {
                eulerTour(tree, tree.left(v)); // Visita a subárvore esquerda de v
            }
            System.out.print(v.element()); // Visita o nó v "por baixo"
            if (tree.hasRight(v)) {
                eulerTour(tree, tree.right(v)); // Visita a subárvore direita de v
            }
            System.out.print(v.element()); // Visita o nó v "pela direita"
        }
    }
    public static void printExpression(LinkedBinaryTree<String> tree, Position<String> v) throws InvalidPositionException, BoundaryViolationException {
        if (tree.isInternal(v)) {
            System.out.print("(");
        }

        if (tree.hasLeft(v)) {
            printExpression(tree, tree.left(v));
        }

        System.out.print(v.element());

        if (tree.hasRight(v)) {
            printExpression(tree, tree.right(v));
        }

        if (tree.isInternal(v)) {
            System.out.print(")");
        }
}
 // Classe interna para representar um nó da árvore
    public static class Node {
        int value;
        Node left;
        Node right;

        Node(int value) {
            this.value = value;
            left = null;
            right = null;
        }
    }

    // Método para inserir um novo valor na árvore
    public static Node insert(Node root, int value) {
        if (root == null) {
            return new Node(value);
        }
        if (value < root.value) {
            root.left = insert(root.left, value);
        } else if (value > root.value) {
            root.right = insert(root.right, value);
        }
        return root;
    }

    // Método para realizar o caminhamento inorder
    public static void inorderTraversal(Node root) {
        if (root != null) {
            inorderTraversal(root.left);
            System.out.print(root.value + ", ");
            inorderTraversal(root.right);
        }
    }
 // Método para contar os nodos esquerdos e externos de uma árvore binária
    public static int countLeftExternalNodes(Node node) {
        if (node == null) {
            return 1; // Nodo externo
        }
        // Conta recursivamente nos nodos esquerdos e adiciona os nodos externos direitos
        return countLeftExternalNodes(node.left) + (node.right == null ? 1 : 0);
    }

    // Método para contar os nodos direitos e externos de uma árvore binária
    public static int countRightExternalNodes(Node node) {
        if (node == null) {
            return 1; // Nodo externo
        }
        // Conta recursivamente nos nodos direitos e adiciona os nodos externos esquerdos
        return countRightExternalNodes(node.right) + (node.left == null ? 1 : 0);
    }
}
