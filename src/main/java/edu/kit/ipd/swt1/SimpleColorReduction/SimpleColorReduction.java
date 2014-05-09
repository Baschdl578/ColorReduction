package edu.kit.ipd.swt1.SimpleColorReduction;

import java.awt.image.BufferedImage;
import java.awt.Color;

/**
 * Created by Sebastian Schindler on 08.05.2014.
 */
public class SimpleColorReduction implements ColorReduction {

    private BufferedImage source;
    private BufferedImage target;
    private int targetDepth;

    public void setSourceImage(BufferedImage mySourceImage) {
        this.source = mySourceImage;
    }

    public void setDestBitDepth(Integer myDestBitDepth) {
        this.targetDepth = myDestBitDepth;
    }

    public BufferedImage getReducedImage() {
        return this.target;
    }

    public void generateImage() {
        double divider = 256 / (((double) targetDepth) / 3);

        target = new BufferedImage(source.getWidth(), source.getHeight(), source.getType());

        // Does this for every pixel:
        for (int i = source.getMinY(); i < source.getHeight(); i++) {
            for (int j = source.getMinX(); j < source.getWidth(); j++) {
                int rgb = source.getRGB(j, i);
                int red = (rgb >> 16) & 0x000000FF;
                int green = (rgb >>8 ) & 0x000000FF;
                int blue = (rgb) & 0x000000FF;

                int newRed = 0;
                int newGreen = 0;
                int newBlue = 0;

                double temp = 0;
                while (temp <= 255) {
                    int lower = (int) Math.round(temp);
                    double upper = temp + divider;
                    if (red >= lower && red < upper) {
                        newRed = lower;
                    }
                    if (green >= lower && green <= upper) {
                        newGreen = lower;
                    }
                    if (blue >= lower && blue <= upper) {
                        newBlue = lower;
                    }

                    temp = temp + divider;
                }
                Color newColors = new Color(newRed, newGreen, newBlue);

                target.setRGB(j, i, newColors.getRGB());
            }
        }

    }




    public BufferedImage getSource() {
        return this.source;
    }
}
