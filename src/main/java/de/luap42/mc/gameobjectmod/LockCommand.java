package de.luap42.mc.gameobjectmod;

import net.kyori.adventure.text.NBTComponent;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

public class LockCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, String[] strings) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if(strings.length != 1) {
                return false;
            }

            ItemStack currentItemStack = player.getInventory().getItemInMainHand();

            System.out.println(currentItemStack.getType().toString());

            ItemMeta itemMeta;
            if (currentItemStack.hasItemMeta())
                itemMeta = currentItemStack.getItemMeta();
            else
                itemMeta = Bukkit.getItemFactory().getItemMeta(currentItemStack.getType());

            PersistentDataContainer data = itemMeta.getPersistentDataContainer();
            NamespacedKey lockKey = NamespacedKey.fromString("locked");

            if(data.has(lockKey, PersistentDataType.STRING)) {
                player.sendMessage("Item is locked. Use /go-unlock <key> to unlock this item.");
                return true;
            } else {
                data.set(lockKey, PersistentDataType.STRING, strings[0]);
                player.sendMessage("Item locked successfully.");
            }
            currentItemStack.setItemMeta(itemMeta);
        }

        return true;
    }
}
