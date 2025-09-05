
package com.floodfill.structures;

/**
 * Nó genérico para listas encadeadas.
 * Cada elo possui um "dado" (E) e uma referência para o próximo nó.
 * Segue a mesma lógica dos materiais: caixinhas conectadas por "setas".
 */
class Node<E> {
    E element;
    Node<E> next;

    Node(E e) {
        this.element = e;
        this.next = null;
    }
}
