package edu.kit.ipd.swt1.SimpleColorReduction;

import org.jis.Main;
import org.jis.options.Options;
import org.jis.plugins.JmjrstPlugin;
import org.kohsuke.MetaInfServices;

import javax.swing.JOptionPane;
import javax.swing.JFrame;
import java.io.File;
import java.util.Vector;

/**
 * Created by Sebastian Schindler on 19.06.2014.
 *
 * Integrates SimpleColorReduction into JMJRST as a plugin
 */
@MetaInfServices
public class PluginIntegrator extends JmjrstPlugin {
    private final boolean configurable = false;
    private final String name = "Simple Color Reduction";
    private final String menuText = null;
    private static org.jis.Main main;

    private static File[] files;

    public static File[] getFiles() {
        return files;
    }

    /**
     * Returns the name of the plugin
     * @return name
     */
    public String getName() {
        return this.name;
    }

    /**
     * returns the menuText of the plugin
     * @return menuText
     */
    public String getMenuText() {
        return this.menuText;
    }

    /**
     * Returns if the plugin is configurable
     * @return configurable
     */
    public boolean isConfigurable() {
        return this.configurable;
    }

    /**
     * Initializes the plugin
     * @param parentMain main Method of parent program
     */
    public void init(Main parentMain) {
        main = parentMain;
        System.out.println("Initializing...");
    }

    public static Main getMain() {
        return main;
    }

    /**
     * Configures the plugin
     * Not implemented because not configurable
     */
    public void configure() { }

    /**
     * Runs the plugin
     */
    public void run() {
        String[] args = new String[2];
        if (main.list.getSelectedValues().size() > 0) {
            if (main.list.getPictures().length != main.list.getSelectedValues().size()) {
                int response = JOptionPane.showConfirmDialog(main.list, main.mes.getString("Generator.23"), main.mes.getString("Generator.24"), JOptionPane.YES_NO_CANCEL_OPTION);
                switch (response)
                {
                    case JOptionPane.YES_OPTION:
                        Vector<File> vf = main.list.getSelectedValues();
                        files = new File[vf.size()];
                        for (int i = 0; i < files.length; i++) {
                            files[i] = vf.get(i);
                        }
                        break; // generate only the selected images
                    case JOptionPane.NO_OPTION:
                        files = main.list.getPictures();
                        break; // generate the whole directory
                    case JOptionPane.CANCEL_OPTION:
                        return; // do nothing
                    case JOptionPane.CLOSED_OPTION:
                        return; // do nothing
                }
            }
            args[0] = files[0].getPath();
            args[1] = Options.getInstance().getOutput_dir() + "\\" + files[0].getName();
            PluginUI.main(args);
        } else {
            JOptionPane.showMessageDialog(new JFrame(), "Bitte zuerst ein oder mehrere Bilder wählen");
        }
    }
}
