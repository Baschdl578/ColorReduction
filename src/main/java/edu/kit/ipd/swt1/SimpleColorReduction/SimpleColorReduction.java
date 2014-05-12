package edu.kit.ipd.swt1.SimpleColorReduction;

import java.awt.image.BufferedImage;
import java.awt.Color;

/**
 * Reduces the Bitdepth of an Image
 * Created by Sebastian Schindler on 08.05.2014.
 */
public class SimpleColorReduction implements ColorReduction {

    private BufferedImage source;
    private BufferedImage target;
    private int targetDepth;

    /**
     * Setter for the source Image
     * @param mySourceImage new BufferedImage
     */
    public void setSourceImage(BufferedImage mySourceImage) {
        this.source = mySourceImage;
    }

    /**
     * Setter for the new Depth
     * @param myDestBitDepth new depth (int divisible by 3)
     */
    public void setDestBitDepth(Integer myDestBitDepth) {
        this.targetDepth = myDestBitDepth;
    }

    /**
     * Getter for the reduced Image
     * @return new Image
     */
    public BufferedImage getReducedImage() {
        return this.target;
    }

    /**
     * Genereates the reducedImage
     */
    public void generateImage() {
        double divider = 256 / (((double) targetDepth) / 3);

        target = new BufferedImage(source.getWidth(), source.getHeight(), source.getType());

        // Does this for every pixel:
        for (int i = source.getMinY(); i < source.getHeight(); i++) {
            for (int j = source.getMinX(); j < source.getWidth(); j++) {

                //Get old colors and Alpha
                int rgb = source.getRGB(j, i);
                Color color = new Color(rgb);
                int red = color.getRed();
                int green = color.getGreen();
                int blue = color.getBlue();
                int alpha = color.getAlpha();

                int newRed = 0;
                int newGreen = 0;
                int newBlue = 0;

                //Set new colors
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
                //Create new Image with old Alpha and new colors
                Color newColors = new Color(newRed, newGreen, newBlue, alpha);
                target.setRGB(j, i, newColors.getRGB());
            }
        }

    }

    /**
     * Getter for the source Image
     * @return source image as BufferedImage
     */
    public BufferedImage getSource() {
        return this.source;
    }
}
