package ru.solomka.items.config.files;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import ru.solomka.items.Main;
import ru.solomka.items.config.Yaml;
import ru.solomka.items.config.enums.DirectorySource;

import java.io.File;

public class FileUtils {

    @Contract("_ -> new")
    public static @NotNull Yaml getDefaultCfg(String file) {
        return new Yaml(new File(Main.getInstance().getDataFolder(), file + ".yml"));
    }

    @Contract("_, _ -> new")
    public static @NotNull Yaml getDirectoryFile(String directory, String file) {
        return new Yaml(new File(Main.getInstance().getDataFolder() + "/" + directory + "/" + file + ".yml"));
    }

    public static Yaml getLangFile() {
        return getDirectoryFile(DirectorySource.LANG.getType(), getDefaultCfg("config").getString("Lang"));
    }
}
