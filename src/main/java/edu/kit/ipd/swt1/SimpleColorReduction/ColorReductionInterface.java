// Ändern Sie die folgende Zeile nicht!
package edu.kit.ipd.swt1.SimpleColorReduction;
import java.awt.image.BufferedImage;

/**
 * Schnittstelle für eine einfache Farbreduktion eines Bildes
 *
 * (Die Schnittstelle gibt die Implementierung für unsere Übungszwecke vor;
 * grundsätzlich wäre die Aufgabenstellung auch anders lösbar.)
 */
public interface ColorReductionInterface {

    /**
     * Setze das Ausgangsbild, das reduziert werden soll.
     *
     * @param mySourceImage
     *            Das Ausgangsbild
     */
    void setSourceImage(BufferedImage mySourceImage);

    /**
     * Setze die Bittiefe, auf die das Ausgangsbild reduziert werden soll.
     *
     * @param myDestBitDepth
     *            Bittiefe (max. 24 und nur Vielfache von 3)
     */
    void setDestBitDepth(Integer myDestBitDepth);

    /**
     * Gibt das farbtiefenreduzierte Bild zurück.
     *
     * @return Das farbtiefenreduzierte Bild
     */
    BufferedImage getReducedImage();

}