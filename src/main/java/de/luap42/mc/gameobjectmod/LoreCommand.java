package de.luap42.mc.gameobjectmod;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class LoreCommand  implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, String[] strings) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if(strings.length == 0) {
                return false;
            }

            if (!GameObjectMod.i.getConfig().getBoolean("allowAddLore") && !player.isOp()) {
                player.sendMessage("ERROR: you do not have the permissions to execute this command");
                return true;
            }

            ItemStack currentItemStack = player.getInventory().getItemInMainHand();

            ItemMeta itemMeta;
            if (currentItemStack.hasItemMeta())
                itemMeta = currentItemStack.getItemMeta();
            else
                itemMeta = Bukkit.getItemFactory().getItemMeta(currentItemStack.getType());

            String text = ChatColor.WHITE + String.join(" ", strings);

            itemMeta.setLore(List.of(String.join("\n" + ChatColor.WHITE, text.split("//")).split("\n")));

            player.sendMessage("Lore has been updated.");
            currentItemStack.setItemMeta(itemMeta);
        }

        return true;
    }
}