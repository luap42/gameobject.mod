package de.luap42.mc.gameobjectmod;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

public class GlintCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, String[] strings) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if(strings.length != 1) {
                return false;
            }

            ItemStack currentItemStack = player.getInventory().getItemInMainHand();

            ItemMeta itemMeta;
            if (currentItemStack.hasItemMeta())
                itemMeta = currentItemStack.getItemMeta();
            else
                itemMeta = Bukkit.getItemFactory().getItemMeta(currentItemStack.getType());

            String todo = strings[0];

            if (todo.equals("yes")) {
                itemMeta.setEnchantmentGlintOverride(true);
                currentItemStack.setItemMeta(itemMeta);

                player.sendMessage("Glint has been shown.");
            } else if (todo.equals("no")) {
                itemMeta.setEnchantmentGlintOverride(false);
                currentItemStack.setItemMeta(itemMeta);

                player.sendMessage("Glint has been hidden.");
            } else {
                itemMeta.setEnchantmentGlintOverride(null);
                currentItemStack.setItemMeta(itemMeta);

                player.sendMessage("Glint override has been cleared.");
            }
        }

        return true;
    }
}
