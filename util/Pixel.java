
package com.floodfill.util;

/**
 * DTO simples representando um "pixel" (coordenada x, y).
 * Usado para empilhar/enfileirar durante o algoritmo.
 */
public class Pixel {
    public final int x;
    public final int y;
    public Pixel(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
