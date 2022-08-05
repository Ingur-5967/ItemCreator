package ru.solomka.items.config;

import lombok.NoArgsConstructor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;
import ru.solomka.items.Main;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.bukkit.configuration.file.YamlConfiguration.loadConfiguration;

@NoArgsConstructor
public class Yaml {

    private YamlConfiguration yaml = new YamlConfiguration();
    private File file;

    public Yaml(@NotNull File file) {
        this.file = file;
        if (file.exists()) try {
            load();
        } catch (IOException | InvalidConfigurationException e) {
            yaml = null;
            e.printStackTrace();
        }
    }

    public Yaml(String path, String name, boolean mode) {
        if (!new File(Main.getInstance().getDataFolder() + "/" + path, name).exists()) {
            File dir = new File(Main.getInstance().getDataFolder() + "/" + path + "/");
            dir.mkdir();
            File file = new File(dir, name);
            YamlConfiguration cfg = loadConfiguration(file);
            cfg.options().copyDefaults(mode);
            Main.getInstance().saveResource(path + "/" + name, false);
        }
    }

    public Yaml(String name) {
        if (!new File(Main.getInstance().getDataFolder(), name).exists()) {
            File file = new File(Main.getInstance().getDataFolder(), name);
            YamlConfiguration cfg = loadConfiguration(file);
            cfg.options().copyDefaults(true);
            Main.getInstance().saveResource(name, false);
        }
    }

    public FileConfiguration getFileConfiguration() {
        return yaml;
    }

    public void load() throws IOException, InvalidConfigurationException {
        yaml.load(file);
    }

    public void save() throws IOException {
        yaml.save(file);
    }

    public void reload() {
        try {
            save();
            load();
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public void set(String path, Object ...value) {
        if(value.length > 1) {
            if(getStringList(path) == null) set(path, (Object) null);
            for (Object o : value)
                getStringList(path).add(o.toString());
        }
        else
            yaml.set(path, value[0]);

        reload();
    }

    public long getLong(String path) {
        return yaml.getLong(path);
    }

    public double getDouble(String path) {
        return yaml.getDouble(path);
    }

    public String getString(String path) {
        return yaml.getString(path);
    }

    public int getInt(String path) { return yaml.getInt(path); }

    public boolean getBoolean(String path) {
        return yaml.getBoolean(path);
    }

    public List<Long> getLongList(String path) {
        return yaml.getLongList(path);
    }

    public List<Integer> getIntList(String path) {
        return yaml.getIntegerList(path);
    }

    public List<Double> getDoubleList(String path) {
        return yaml.getDoubleList(path);
    }

    public List<String> getStringList(String path) {
        return yaml.getStringList(path);
    }
}