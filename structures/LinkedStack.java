
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
        n.next = top;     
        top = n;         
        size++;
    }

    public E pop() {
        if (isEmpty()) return null;
        E e = top.element;   
        top = top.next;      
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
