package de.luap42.mc.gameobjectmod;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;

import static java.awt.SystemColor.text;

public class FormatCommand  implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, String[] strings) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if(strings.length == 0) {
                return false;
            }

            if (!GameObjectMod.i.getConfig().getBoolean("allowFormat") && !player.isOp()) {
                player.sendMessage("ERROR: you do not have the permissions to execute this command");
                return true;
            }

            ItemStack currentItemStack = player.getInventory().getItemInMainHand();

            ItemMeta itemMeta;
            if (currentItemStack.hasItemMeta())
                itemMeta = currentItemStack.getItemMeta();
            else
                itemMeta = Bukkit.getItemFactory().getItemMeta(currentItemStack.getType());

            String displayName = itemMeta.getDisplayName();
            if (displayName == null || displayName.isEmpty()) {
                displayName = itemIDtoName(currentItemStack.getType().name().toLowerCase().replace("_", " "));
            }
            for (String option : strings) {
                displayName = applyOption(displayName, option);
            }
            itemMeta.setDisplayName(displayName);

            player.sendMessage("Formatting has been updated.");
            currentItemStack.setItemMeta(itemMeta);
        }

        return true;
    }

    private String applyOption(String displayName, String option) {
        switch (option) {
            case "clear":
                return clearFormatting(displayName);
            case "bold":
                return setBold(displayName);
            case "italic":
                return setItal(displayName);
            case "magic":
                return setMagic(displayName);
            case "yellow":
                return setColor(displayName, ChatColor.YELLOW);
            case "red":
                return setColor(displayName, ChatColor.RED);
            case "pink":
                return setColor(displayName, ChatColor.LIGHT_PURPLE);
            case "purple":
                return setColor(displayName, ChatColor.DARK_PURPLE);
            case "blue":
                return setColor(displayName, ChatColor.BLUE);
            case "aqua":
                return setColor(displayName, ChatColor.AQUA);
            case "green":
                return setColor(displayName, ChatColor.GREEN);
            case "darkgreen":
                return setColor(displayName, ChatColor.DARK_GREEN);
            case "gold":
                return setColor(displayName, ChatColor.GOLD);
            case "white":
                return setColor(displayName, ChatColor.WHITE);
            case "gray":
            case "grey":
                return setColor(displayName, ChatColor.GRAY);
            case "black":
                return setColor(displayName, ChatColor.BLACK);
            default:
                return displayName;
        }
    }

    private String clearFormatting(String displayName) {
        return ChatColor.stripColor(displayName);
    }
    private String setBold(String displayName) {
        return ChatColor.BOLD + displayName;
    }
    private String setItal(String displayName) {
        return ChatColor.ITALIC + displayName;
    }
    private String setMagic(String displayName) {
        return ChatColor.MAGIC + displayName;
    }
    private String setColor(String displayName, ChatColor col) {
        return col + displayName;
    }

    static String itemIDtoName(String id) {
        String regex = "\\b(.)(.*?)\\b";
        String result = Pattern.compile(regex).matcher(id).replaceAll(
            match -> match.group(1).toUpperCase() + match.group(2)
        );

        return result;
    }

}