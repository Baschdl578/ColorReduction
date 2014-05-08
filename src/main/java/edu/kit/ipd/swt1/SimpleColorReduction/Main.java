package edu.kit.ipd.swt1.SimpleColorReduction;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 * Hauptklasse der Kommandozeilenschnittstelle zu
 * kit.edu.ipd.swt1.SimpleColorReduction
 *
 * @author tk
 *
 */
final class Main {
    /**
     * Privater Konstruktor verhindert Instanziierung
     */
    private Main() {
    }

    /**
     * Main-Methode: Liest Kommandozeilenargumente ein und ruft
     * kit.edu.ipd.swt1.SimpleColorReduction entsprechend auf.
     *
     * @param args
     *            Kommandozeilenargumente vom Benutzer
     */
    public static void main(String[] args) {
        CommandLine cmd = doCommandLineParsing(args);

		/*
		 * Fügen Sie hier Anweisungen zum Einlesen einer PNG-Datei gemäß der
		 * Hinweise auf dem Übungblatt ein.
		 */

        // Ändern Sie den Namen "SimpleColorReduction" nicht!
        ColorReduction scr = new SimpleColorReduction();

		/*
		 * Fügen Sie hier Anweisungen zum Aufruf Ihrer Implementierung ein.
		 * Dabei sollte die Farbanzahl des eingelesenen PNG-Bilds reduziert
		 * werden.
		 */

		/*
		 * Fügen Sie hier Anweisungen zum Schreiben einer PNG-Datei gemäß der
		 * Hinweise auf dem Übungblatt ein.
		 */
    }

    /**
     * Prüft Kommandozeilenargumente auf Gültigkeit
     *
     * (In dieser Methode brauchen Sie nichts zu ändern. Insbesondere ändern Sie
     * bitte nicht die Aufrufoptionen, mit denen dieses Programm auf der
     * Kommandozeile aufgerufen werden kann.)
     *
     * @param args
     *            Kommandozeilenargumente vom Benutzer
     * @return Bei Erfolg: CommandLine-Objekt mit den Werten der
     */
    static CommandLine doCommandLineParsing(String[] args) {
		/*
		 * Lese Kommandozeilenargumente ein
		 */
        Option sourceFileOpt = new Option("s", "sourceFile", true,
                "Die Ausgangsdatei");
        sourceFileOpt.setRequired(true);
        Option destFileOpt = new Option("d", "destFile", true, "Die Zieldatei");
        destFileOpt.setRequired(true);
        Option destColorDepth = new Option("c", "colorDepth", true,
                "Die Farbtiefe, auf die reduziert werden soll, in Bits");
        destColorDepth.setRequired(true);
        destColorDepth.setType(Integer.class);
        Options options = new Options();
        options.addOption(sourceFileOpt);
        options.addOption(destFileOpt);
        options.addOption(destColorDepth);

        // create the parser
        CommandLineParser parser = new BasicParser();
        CommandLine line = null;
        try {
            // parse the command line arguments
            line = parser.parse(options, args);
        } catch (ParseException exception) {
            // oops, something went wrong
            System.err
                    .println("Mit den Kommandozeilenargumenten stimmt etwas nicht: "
                            + exception.getMessage());
        }
        return line;
    }
}