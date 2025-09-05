
package com.floodfill.structures;

/**
 * Fila encadeada (FIFO) — implementação própria.
 * Mantém referências para o primeiro (front) e o último (rear) nós.
 * Métodos principais:
 *  - enqueue(E e): insere no final
 *  - dequeue(): remove do início e retorna
 *  - isEmpty(): verifica se está vazia
 *  - clear(): limpa a fila
 */
public class LinkedQueue<E> {

    private Node<E> front; // primeiro
    private Node<E> rear;  // último
    private int size;

    public LinkedQueue() {
        this.front = null;
        this.rear = null;
        this.size = 0;
    }

    public void enqueue(E e) {
        Node<E> n = new Node<>(e);
        if (isEmpty()) {
            front = n;
            rear = n;
        } else {
            rear.next = n; // o antigo último aponta para o novo nó
            rear = n;      // atualizamos o último
        }
        size++;
    }

    public E dequeue() {
        if (isEmpty()) return null;
        E e = front.element;
        front = front.next;   // movemos o primeiro para o próximo
        if (front == null) {  // se esvaziou, rear também vira null
            rear = null;
        }
        size--;
        return e;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void clear() {
        front = null;
        rear = null;
        size = 0;
    }
}
