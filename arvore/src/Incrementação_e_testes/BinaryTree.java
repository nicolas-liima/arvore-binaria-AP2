package Incrementação_e_testes;

public class BinaryTree{
	static class Node {
		String value;
		Node left, right;

		Node(String item) {
			value = item;
			left = right = null;
		}
	}

	public  void binaryPostorder(Node v) {
		if (v != null) {
			binaryPostorder(v.left); // de forma recursiva percorre a subárvore esquerda
			binaryPostorder(v.right); // de forma recursiva percorre a subárvore direita
			System.out.println(v.value); // executa a ação prevista para o nodo v
		}
	}
}

