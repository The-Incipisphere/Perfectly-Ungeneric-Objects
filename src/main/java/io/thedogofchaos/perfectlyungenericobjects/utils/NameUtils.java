package io.thedogofchaos.perfectlyungenericobjects.utils;

public class NameUtils {
    public String getNameWithSuffix(String name, String suffix) {
        return String.format("%s_%s", name, suffix);
    }
    public String getNameWithPrefix(String name, String prefix) {
        return String.format("%s_%s", prefix, name);
    }
}
