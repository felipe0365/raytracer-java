package br.com.compassuol;

import br.com.compassuol.image.Image;
import br.com.compassuol.image.ImageColor;

import java.io.IOException;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) throws IOException {
        final int W = 256;
        final int H = 192;

        try(Image image = new Image(W, H)) {
            for (int x = 0; x < W; x++) {
                for (int y = 0; y < H; y++) {
                    int r = (int) (((float) x) / W * ImageColor.MAX);
                    int g = (int) (((float) y) / H * ImageColor.MAX);
                    int b = 0x88;

                    image.plotPixel(x, y, new ImageColor(r,g,b));
                }

            image.save(Paths.get(args[0]));
            }
        }
    }
}