package generator;

import map.BinaryMask;
import map.FloatMask;
import map.SCMap;
import util.Gradient;

import java.awt.*;
import java.awt.image.BufferedImage;

public strictfp class Preview {
    static final Gradient HEATMAP_GRADIENT;
    static final float HEATMAP_SATURATION = 1f;
    static final float HEATMAP_GAMMA = 1f;

    static{
        HEATMAP_GRADIENT = new Gradient();
        HEATMAP_GRADIENT.addColor(0f, new Color(48, 48, 154)); // Dark magenta
        HEATMAP_GRADIENT.addColor(0.33f, new Color(0, 153, 255)); // Pale blue
        HEATMAP_GRADIENT.addColor(0.45f, new Color(0, 207, 104)); // Blueish green
        HEATMAP_GRADIENT.addColor(0.6f, new Color(255, 255, 150)); // Chick yellow
        HEATMAP_GRADIENT.addColor(0.8f, new Color(129, 92, 83)); // Brown
        HEATMAP_GRADIENT.addColor(1f, new Color(255, 255, 255)); // White
    }

    static void generate(BufferedImage image, SCMap map, BinaryMask cliffs){
        for (int x = 0; x < 256; x++) {
            for (int y = 0; y < 256; y++) {

                int scaledX = x * 2;
                int scaledY = y * 2;

                float elevation =
                        ((float)map.getHeightmap().getRaster().getSample(scaledX,scaledY,0))
                                * SCMap.HEIGHTMAP_SCALE;

                float delta = elevation * (1/60f); // Magic number to make the colors more readable
                if (cliffs.get(x,y)){
                    delta += 0.033f;
                }
                delta = Math.min(Math.max(delta, 0f), 1f);

                Color color = HEATMAP_GRADIENT.evaluate(delta);
                float[] hsb = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);
                Color desaturated = Color.getHSBColor(hsb[0], hsb[1] * HEATMAP_SATURATION, hsb[2] * HEATMAP_GAMMA);
                image.setRGB(x, y, desaturated.getRGB());
            }
        }
    }
}
