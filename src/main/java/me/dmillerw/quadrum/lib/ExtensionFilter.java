package me.dmillerw.quadrum.lib;

import java.io.File;
import java.io.FilenameFilter;

/**
 * @author dmillerw
 */
public class ExtensionFilter implements FilenameFilter {

    public static final ExtensionFilter JSON = new ExtensionFilter("json");

    private String extension;

    private ExtensionFilter(String extension) {
        this.extension = extension;
    }

    @Override
    public boolean accept(File dir, String name) {
        return name.endsWith(this.extension);
    }
}
