package de.luap42.mc.gameobjectmod;

import net.kyori.adventure.text.NBTComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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

import java.util.List;

public class ApplyCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, String[] strings) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if(strings.length != 1) {
                return false;
            }

            String saveName = strings[0];

            ItemStack currentItemStack = player.getInventory().getItemInMainHand();

            ItemMeta itemMeta;
            if (currentItemStack.hasItemMeta())
                itemMeta = currentItemStack.getItemMeta();
            else
                itemMeta = Bukkit.getItemFactory().getItemMeta(currentItemStack.getType());

            PersistentDataContainer itemData = itemMeta.getPersistentDataContainer();
            PersistentDataContainer playerData = player.getPersistentDataContainer();
            NamespacedKey lockKey = NamespacedKey.fromString("locked");

            boolean hasData = fetchString(playerData, saveName, "exists").equals("yes");

            if(!hasData) {
                player.sendMessage("No saved GameObject data under [" + saveName + "].");
                return true;
            }

            String savedDisplayName = fetchString(playerData, saveName, "display_name");
            String currentDisplayName = itemMeta.getDisplayName();
            boolean hadNoDisplayName = false;

            if (currentDisplayName.isEmpty()) {
                currentDisplayName = FormatCommand.itemIDtoName(currentItemStack.getType().name().toLowerCase().replace("_", " "));
                hadNoDisplayName = true;
            }

            if (savedDisplayName == null && !hadNoDisplayName) {
                player.sendMessage("No display name found (weird...).");
                return true;
            }
            else if(!hadNoDisplayName && !ChatColor.stripColor(savedDisplayName).equals(ChatColor.stripColor(currentDisplayName))) {
                player.sendMessage("Display name must match '" + ChatColor.stripColor(savedDisplayName) + "', was '" + ChatColor.stripColor(currentDisplayName) + "'.");
                return true;
            }

            if (!hadNoDisplayName)
                itemMeta.setDisplayName(savedDisplayName);

            String savedLore = fetchString(playerData, saveName, "lore");
            if (savedLore != null) {
                itemMeta.setLore(List.of(savedLore.split("//")));
            }

            String savedCustomModel = fetchString(playerData, saveName, "custom_model");
            if (savedCustomModel != null) {
                itemMeta.setCustomModelData(Integer.parseInt(savedCustomModel));
            }

            String savedGlint = fetchString(playerData, saveName, "glint");
            if (savedGlint != null && !savedGlint.isEmpty()) {
                itemMeta.setEnchantmentGlintOverride(savedGlint == "true");
            }

            String isLocked = fetchString(playerData, saveName, "locked");
            if (isLocked.equals("true")) {
                String secretLockKey = fetchString(playerData, saveName, "lock_key");
                itemData.set(lockKey, PersistentDataType.STRING, secretLockKey);
            }

            currentItemStack.setItemMeta(itemMeta);
        }

        return true;
    }

    private String fetchString(PersistentDataContainer playerData, String saveName, String key) {
        NamespacedKey nkey = NamespacedKey.fromString(saveName + "." + key);
        if (playerData.has(nkey, PersistentDataType.STRING)) {
            String data = playerData.get(nkey, PersistentDataType.STRING);

            if (data.length() == 0)
                return null;
            else
                return data;
        } else {
            return null;
        }
    }
}
