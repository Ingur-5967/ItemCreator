package ru.solomka.items;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.plugin.java.JavaPlugin;
import ru.solomka.items.commands.ItemsCommands;
import ru.solomka.items.config.Yaml;
import ru.solomka.items.config.enums.DirectorySource;
import ru.solomka.items.core.RegistrationService;

import java.io.File;
import java.io.IOException;

public final class Main extends JavaPlugin {

    @Getter private static Main instance;

    @Override
    public void onEnable() {
        instance = this;

        RegistrationService registrationService = new RegistrationService(this);

        registrationService.initConfigs(DirectorySource.NONE, "items");
        registrationService.registrationCmd(new CommandExecutor[]{new ItemsCommands()}, null, "item");

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
