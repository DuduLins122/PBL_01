
package com.floodfill.structures;

/**
 * Pilha encadeada (LIFO) — implementação própria.
 * Métodos principais:
 *  - push(E e): empilha um elemento
 *  - pop(): desempilha e retorna o elemento do topo
 *  - isEmpty(): verifica se está vazia
 *  - clear(): limpa a pilha
 */
public class LinkedStack<E> {

    private Node<E> top;
    private int size;

    public LinkedStack() {
        this.top = null;
        this.size = 0;
    }

    public void push(E e) {
        Node<E> n = new Node<>(e);
        n.next = top;     // novo nó aponta para o antigo topo
        top = n;          // topo passa a ser o novo nó
        size++;
    }

    public E pop() {
        if (isEmpty()) return null;
        E e = top.element;   // guardamos o elemento do topo
        top = top.next;      // movemos o topo para o nó anterior
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
        top = null;
        size = 0;
    }
}
