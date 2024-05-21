package source;

import exceptions.InvalidPositionException;
import exceptions.NonEmptyTreeException;
import position.Position;

public class LinkedBinarySearchTree<E extends Comparable<E>> extends LinkedBinaryTree<E> {

    // Método para inserir um elemento na árvore binária de pesquisa
    public void insert(E element) throws NonEmptyTreeException, InvalidPositionException {
        if (isEmpty()) {
            addRoot(element);
        } else {
            insert(root(), element);
        }
    }

    // Método auxiliar para inserir um elemento na árvore binária de pesquisa
    protected void insert(Position<E> position, E element) throws InvalidPositionException {
        if (position.element().compareTo(element) > 0) {
            if (hasLeft(position)) {
                insert(left(position), element);
            } else {
                addLeft(position, element);
            }
        } else {
            if (hasRight(position)) {
                insert(right(position), element);
            } else {
                addRight(position, element);
            }
        }
    }

    // Método para adicionar um nodo à esquerda de um nodo existente
    protected Position<E> addLeft(Position<E> parent, E element) throws InvalidPositionException {
        BTPosition<E> parentNode = checkPosition(parent);
        BTPosition<E> leftNode = createNode(element, null, null, parentNode);
        parentNode.setLeft(leftNode);
        size++;
        return leftNode;
    }

    // Método para adicionar um nodo à direita de um nodo existente
    protected Position<E> addRight(Position<E> parent, E element) throws InvalidPositionException {
        BTPosition<E> parentNode = checkPosition(parent);
        BTPosition<E> rightNode = createNode(element, null, null, parentNode);
        parentNode.setRight(rightNode);
        size++;
        return rightNode;
    }
}
