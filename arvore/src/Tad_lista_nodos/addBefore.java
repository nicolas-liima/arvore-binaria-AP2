package Tad_lista_nodos;

class Node<E> {
    E element;
    Node<E> next;

    public Node(E element, Node<E> next) {
        this.element = element;
        this.next = next;
    }
}

class NodeList<E> {
    Node<E> head;
    Node<E> tail;

    public NodeList() {
        head = null;
        tail = null;
    }

    // Método addBefore(p, e)
    public void addBefore(Node<E> p, E e) {
        Node<E> newNode = new Node<>(e, null);
        Node<E> current = head;
        Node<E> previous = null;

        while (current != null && current != p) {
            previous = current;
            current = current.next;
        }

        if (current == null) {
            // Node p não encontrado, não faz nada
            return;
        }

        newNode.next = current;
        if (previous != null) {
            previous.next = newNode;
        } else {
            // Se o nó p é o primeiro da lista, atualiza a cabeça da lista
            head = newNode;
        }
    }

    // Método addFirst(e)
    public void addFirst(E e) {
        Node<E> newNode = new Node<>(e, head);
        if (head == null) {
            tail = newNode;
        }
        head = newNode;
    }

    // Método addLast(e)
    public void addLast(E e) {
        Node<E> newNode = new Node<>(e, null);
        if (tail == null) {
            head = newNode;
            tail = newNode;
            return;
        }
        tail.next = newNode;
        tail = newNode;
    }
}
