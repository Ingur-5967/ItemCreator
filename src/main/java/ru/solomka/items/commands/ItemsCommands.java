package ru.solomka.items.commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.solomka.items.core.ItemManager;

import java.util.ArrayList;
import java.util.List;

public class ItemsCommands implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String str, String[] args) {

        if(!(sender instanceof Player)) return true;

        Player player = (Player) sender;

        if(args.length == 0) {
            sender.sendMessage("Команда не может быть пустой");
            return true;
        }

        ItemManager itemManager;
        StringBuilder stringBuilder = new StringBuilder();

        switch (args[0].toLowerCase()) {
            case "create" : {

                List<String> array = new ArrayList<>();
                for(int i = 3; i < args.length; i++) {
                    stringBuilder.append(args[i]).append('\n');
                    array.add(args[i]);
                }

                if(stringBuilder.length() < 3) {
                    sender.sendMessage("Введи одну строчку лора");
                    return true;
                }

                itemManager = new ItemManager(player, args[1], new Object[]{args[2], array});
                itemManager.saveItem();
                break;
            }

            case "give" : {

                for(int i = 1; i < args.length; i++)
                    stringBuilder.append(args[i]).append(" ");


                if(stringBuilder.length() < 1) {
                    sender.sendMessage("Введите айди предмета");
                    return true;
                }

                int idItem;

                try {
                    idItem = Integer.parseInt(args[1]);
                } catch (NumberFormatException e) {
                    sender.sendMessage("введите корректное число");
                    return true;
                }

                itemManager = new ItemManager(player, null, new Object[]{idItem});
                itemManager.giveItem();
                break;
            }

            case "remove" : {
                itemManager = new ItemManager(player, "", new Object[]{args[1]});
                itemManager.removeItem();
                break;
            }
            default: sender.sendMessage("Неверные аргументы");
        }
        return true;
    }
}
