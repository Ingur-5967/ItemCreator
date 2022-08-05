package ru.solomka.items.core;

import lombok.Data;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.solomka.items.config.Yaml;
import ru.solomka.items.config.files.FileUtils;
import ru.solomka.items.core.builders.ItemBuilder;
import ru.solomka.items.core.enums.SearchItemType;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.bukkit.ChatColor.*;

@Data
public class ItemManager {

    private final Player p;
    private String material;
    private Object[] args;

    private final Yaml file;
    
    public ItemManager(Player p, String material, Object[] args) {
        this.p = p;
        this.material = material;
        this.args = args;
        file = FileUtils.getDefaultCfg("items");
    }

    public void giveItem(int id) {
        if(getIdItemOfParam(SearchItemType.OF_NON_NULL, id) == -1) {
            p.sendMessage("Предмет не найден");
            return;
        }

        List<Integer> slots = new ArrayList<>();
        for(int i = 0; i < 36; i++)
            slots.add(i);

        Optional<Integer> findSlot = Optional.of(slots.stream().filter(slot -> p.getInventory().getItem(slot) == null).findFirst().orElse(-1));

        if(findSlot.get() == -1) {
            p.sendMessage("Не найден пустой слот в инвентаре для выдачи предмета");
            return;
        }
        p.getInventory().setItem(findSlot.get(), getItem(id));
    }

    public void removeItem() {
        int searchedId = getIdItemOfParam(SearchItemType.OF_NAME, args[0].toString());
        if(searchedId == -1) {
            p.sendMessage("Неудалось удалить предмет!\nПричины: Предмет отсутствует в списке сохраненных");
            return;
        }
        file.set("Items." + searchedId, (Object) null);
        p.sendMessage("Успешно удален предмет под id - " + searchedId);
    }

    public void saveItem() {
        if(toItemStack() == null || getIdItemOfParam(SearchItemType.OF_NAME, args[0].toString()) != -1) {
            p.sendMessage("\nНеудалось сохранить предмет!\nПричины: Материал предмета указан некорректно или предмет с таким имен уже есть\n");
            return;
        }

        String[] defParams = {"Name", "Lore", "Material"};

        int id = findEmptyId();

        for(int i = 0; i < defParams.length; i++)
            file.set("Items." + id + "." + defParams[i], defParams[i].equals("Material") ? material.toUpperCase() : args[i]);

        p.sendMessage("Предмет успешно сохранен под id - " + id);
    }

    public @NotNull ItemStack getItem(int id) {
        String name = file.getString("Items." + id + ".Name");
        String material = file.getString("Items." + id + ".Material");
        List<String> lore = file.getStringList("Items." + id + ".Lore");

        return new ItemBuilder(new ItemStack(Material.getMaterial(material))).setName(name).setLore(lore).getReplacedItem();
    }

    private int findEmptyId() {
        for(int i = 0; i <= 1000; i++)
            if(file.getString("Items." + i) == null)
                return i;

        return -1;
    }

    private int getIdItemOfParam(SearchItemType type, Object ...params) {
        String path;
        boolean check;
        for(int id : getExistsRecords()) {
            path = type == SearchItemType.OF_NAME ? "Items." + id + ".Name" : "Items." + params[0].toString();
            check = type == SearchItemType.OF_NAME ? file.getString(path).equals(params[0].toString()) : file.getString(path) != null;
            if(check)
                return id;
        }
        return -1;
    }

    private @NotNull List<Integer> getExistsRecords() {
        List<Integer> records = new ArrayList<>();
        for(int i = 0; i <= 1000; i++)
            if(file.getString("Items." + i) != null)
                records.add(i);

        return records;
    }

    private @Nullable ItemStack toItemStack() {
        ItemStack item;
        try {
            item = new ItemStack(Material.getMaterial(material.toUpperCase()));
        } catch (NullPointerException e) {
            return null;
        }
        return item;
    }
}
