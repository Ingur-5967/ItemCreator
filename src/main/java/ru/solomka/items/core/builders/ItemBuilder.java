package ru.solomka.items.core.builders;

import lombok.Getter;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static org.bukkit.ChatColor.*;

public class ItemBuilder {

    @Getter private final ItemStack item;

    private final ItemMeta meta;

    public ItemBuilder(@NotNull ItemStack item) {
        this.item = item;
        meta = item.getItemMeta();
    }

    public ItemBuilder setName(String name) {
        meta.setDisplayName(translateAlternateColorCodes('&', name));
        item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder setLore(@NotNull List<String> lore) {
        if(lore.isEmpty()) return this;
        lore.replaceAll(s -> translateAlternateColorCodes('&', s));
        meta.setLore(lore);
        item.setItemMeta(meta);
        return this;
    }

    public ItemStack getReplacedItem() {
        return item;
    }
}

