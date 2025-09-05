
package com.floodfill;

import com.floodfill.image.FloodFill;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Entrada do programa.
 * Forma de escrita didática:
 *  - parâmetros simples
 *  - comentários explicando a lógica
 *  - nomes de métodos em inglês (push, pop, enqueue, dequeue)
 *
 * Se nenhum argumento for passado, usamos valores padrão.
 */
public class Main {
    public static void main(String[] args) {
        String inputPath = args.length > 0 ? args[0] : "input.png";
        int startX = args.length > 1 ? parseInt(args[1], 10) : 10;
        int startY = args.length > 2 ? parseInt(args[2], 10) : 10;
        int newColor = args.length > 3 ? parseHexColor(args[3]) : parseHexColor("#9B30FF");
        int stepInterval = args.length > 4 ? parseInt(args[4], 200) : 200;
        String algorithm = args.length > 5 ? args[5].toLowerCase() : "both";

        BufferedImage input;
        try {
            input = ImageIO.read(new File(inputPath));
        } catch (IOException e) {
            System.err.println("Não foi possível abrir a imagem: " + inputPath);
            System.err.println("Coloque um PNG na raiz do projeto com nome 'input.png' ou informe o caminho por parâmetro.");
            return;
        }

        FloodFill ff = new FloodFill(input);

        try {
            if (algorithm.equals("stack") || algorithm.equals("both")) {
                BufferedImage outStack = ff.withStack(startX, startY, newColor, stepInterval, "out/stack/frames");
                FloodFill.saveImage(outStack, "out/stack/final.png");
                System.out.println("Final (Stack) salvo em: out/stack/final.png");
            }

            if (algorithm.equals("queue") || algorithm.equals("both")) {
                BufferedImage outQueue = ff.withQueue(startX, startY, newColor, stepInterval, "out/queue/frames");
                FloodFill.saveImage(outQueue, "out/queue/final.png");
                System.out.println("Final (Queue) salvo em: out/queue/final.png");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static int parseInt(String s, int def) {
        try { return Integer.parseInt(s); } catch (Exception e) { return def; }
    }

    /** Converte "#RRGGBB" ou "RRGGBB" para ARGB com alpha 255. */
    private static int parseHexColor(String hex) {
        String h = hex.trim();
        if (h.startsWith("#")) h = h.substring(1);
        if (h.length() != 6) return 0xFF9B30FF; // roxo padrão
        int rgb = Integer.parseInt(h, 16);
        return 0xFF000000 | rgb;
    }
}
