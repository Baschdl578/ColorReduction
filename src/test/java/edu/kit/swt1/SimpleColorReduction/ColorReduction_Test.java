package edu.kit.swt1.SimpleColorReduction;

import edu.kit.ipd.swt1.SimpleColorReduction.ColorReduction;
import edu.kit.ipd.swt1.SimpleColorReduction.SimpleColorReduction;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.assertTrue;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

/**
 * Created by Sebastian Schindler on 09.05.2014.
 */
public class ColorReduction_Test {
    private SimpleColorReduction reduct;
    /**
     * Setup Method, Creates new SimpleColorReduction Object
     */
    @Before public void setup() {
        reduct = new SimpleColorReduction();
    }

    @Test public void TestImageSetter() {
        try {
            BufferedImage in = ImageIO.read(new File("src/test/resources/Mario.png"));
            reduct.setSourceImage(in);

            assertTrue(reduct.getSource() == in);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test public void checkImageSetterForNull() {
        reduct.setSourceImage(null);
        assertTrue(reduct.getSource() == null);
    }

    @Test public void checkImageGeneration() {
        try {
            reduct.setSourceImage(ImageIO.read(new File("src/test/resources/Mario.png")));
            reduct.setDestBitDepth(3);
            reduct.generateImage();
            assertTrue(reduct.getReducedImage() != null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @After void TearDown() {
    }

}
