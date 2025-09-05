
package com.floodfill.image;

import com.floodfill.structures.LinkedQueue;
import com.floodfill.structures.LinkedStack;
import com.floodfill.util.Pixel;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Núcleo do algoritmo Flood Fill.
 * Há duas versões equivalentes, que diferem apenas na estrutura de armazenar vizinhos:
 *  - withStack: usa Pilha (LIFO)
 *  - withQueue: usa Fila  (FIFO)
 *
 * Regras (descritas nos materiais enviados):
 *  1) Empilhamos/enfileiramos um ponto inicial e guardamos a cor de fundo (targetColor).
 *  2) Loop: retiramos um pixel, checamos limites (Index Out Of Bounds) e se a cor atual == targetColor.
 *  3) Se atender, pintamos com a nova cor e adicionamos os 4 vizinhos laterais.
 *  4) Pixels fora da imagem ou de outra cor são ignorados (nunca pintados).
 *  5) O processo termina quando não há mais pixels para processar.
 */
public class FloodFill {

    private final BufferedImage original;
    private final int width;
    private final int height;

    public FloodFill(BufferedImage img) {
        this.original = img;
        this.width = img.getWidth();
        this.height = img.getHeight();
    }

    /* Utilitário: salva um frame incremental */
    private void saveFrame(BufferedImage img, String dir, int frameIndex) {
        try {
            File folder = new File(dir);
            if (!folder.exists()) folder.mkdirs();
            String name = String.format("%s/frame_%05d.png", dir, frameIndex);
            ImageIO.write(img, "png", new File(name));
        } catch (IOException e) {
            System.err.println("Falha ao salvar frame: " + e.getMessage());
        }
    }

    /* Copia profunda da imagem para registrar frames sem sobrescrever a original */
    private BufferedImage copy(BufferedImage src) {
        BufferedImage out = new BufferedImage(src.getWidth(), src.getHeight(), BufferedImage.TYPE_INT_ARGB);
        for (int y = 0; y < src.getHeight(); y++) {
            for (int x = 0; x < src.getWidth(); x++) {
                out.setRGB(x, y, src.getRGB(x, y));
            }
        }
        return out;
    }

    public BufferedImage withStack(int startX, int startY, int newColor, int stepInterval, String framesDir) {
        BufferedImage canvas = copy(original);
        int targetColor = canvas.getRGB(startX, startY);
        if (targetColor == newColor) return canvas; // nada a fazer

        LinkedStack<Pixel> stack = new LinkedStack<>();
        stack.push(new Pixel(startX, startY));

        int painted = 0;
        int frame = 0;
        saveFrame(canvas, framesDir, frame++); // estado inicial

        while (!stack.isEmpty()) {
            Pixel p = stack.pop();

            if (p.x < 0 || p.x >= width || p.y < 0 || p.y >= height) {
                // fora da matriz, ignorar
                continue;
            }

            // checa cor alvo (somente pinta se igual à cor de fundo guardada)
            if (canvas.getRGB(p.x, p.y) != targetColor) {
                // cor diferente da região alvo, ignorar
                continue;
            }

            // pinta
            canvas.setRGB(p.x, p.y, newColor);
            painted++;

            // empilha vizinhos (4-neighborhood)
            stack.push(new Pixel(p.x + 1, p.y));
            stack.push(new Pixel(p.x - 1, p.y));
            stack.push(new Pixel(p.x, p.y + 1));
            stack.push(new Pixel(p.x, p.y - 1));

            if (stepInterval > 0 && (painted % stepInterval == 0)) {
                saveFrame(canvas, framesDir, frame++);
            }
        }

        // frame final
        saveFrame(canvas, framesDir, frame++);
        return canvas;
    }

    public BufferedImage withQueue(int startX, int startY, int newColor, int stepInterval, String framesDir) {
        BufferedImage canvas = copy(original);
        int targetColor = canvas.getRGB(startX, startY);
        if (targetColor == newColor) return canvas;

        LinkedQueue<Pixel> queue = new LinkedQueue<>();
        queue.enqueue(new Pixel(startX, startY));

        int painted = 0;
        int frame = 0;
        saveFrame(canvas, framesDir, frame++);

        while (!queue.isEmpty()) {
            Pixel p = queue.dequeue();

            if (p.x < 0 || p.x >= width || p.y < 0 || p.y >= height) {
                continue;
            }

            if (canvas.getRGB(p.x, p.y) != targetColor) {
                continue;
            }

            canvas.setRGB(p.x, p.y, newColor);
            painted++;

            queue.enqueue(new Pixel(p.x + 1, p.y));
            queue.enqueue(new Pixel(p.x - 1, p.y));
            queue.enqueue(new Pixel(p.x, p.y + 1));
            queue.enqueue(new Pixel(p.x, p.y - 1));

            if (stepInterval > 0 && (painted % stepInterval == 0)) {
                saveFrame(canvas, framesDir, frame++);
            }
        }

        saveFrame(canvas, framesDir, frame++);
        return canvas;
    }

    /* Utilitário: exporta a imagem final */
    public static void saveImage(BufferedImage img, String path) {
        try {
            File f = new File(path);
            f.getParentFile().mkdirs();
            ImageIO.write(img, "png", f);
        } catch (IOException e) {
            System.err.println("Falha ao salvar imagem: " + e.getMessage());
        }
    }
}
