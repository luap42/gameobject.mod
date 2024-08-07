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

public class SaveCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, String[] strings) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if(strings.length == 0 || strings.length > 2) {
                return false;
            }

            if (!GameObjectMod.i.getConfig().getBoolean("allowSaveAndApply") && !player.isOp()) {
                player.sendMessage("ERROR: you do not have the permissions to execute this command");
                return true;
            }

            String saveName = strings[0];
            String secretKey = null;

            if(strings.length == 2) {
                secretKey = strings[1];
            }

            ItemStack currentItemStack = player.getInventory().getItemInMainHand();

            ItemMeta itemMeta;
            if (currentItemStack.hasItemMeta())
                itemMeta = currentItemStack.getItemMeta();
            else
                itemMeta = Bukkit.getItemFactory().getItemMeta(currentItemStack.getType());

            PersistentDataContainer itemData = itemMeta.getPersistentDataContainer();
            PersistentDataContainer playerData = player.getPersistentDataContainer();
            NamespacedKey lockKey = NamespacedKey.fromString("locked");

            if (itemData.has(lockKey)) {
                if (secretKey == null) {
                    player.sendMessage("Could not save: this item stack is LOCKED, pass the secret key as second command parameter");
                    return false;
                } else if (!itemData.get(lockKey, PersistentDataType.STRING).equals(secretKey)) {
                    player.sendMessage("Could not save: invalid secret key passed.");
                    return false;
                }
            }

            storeString(playerData, saveName, "exists", "yes");

            if (itemMeta.hasDisplayName())
                storeString(playerData, saveName, "display_name", itemMeta.getDisplayName());
            else
                storeString(playerData, saveName, "display_name", "");

            if(itemMeta.hasLore())
                storeString(playerData, saveName, "lore", String.join("//", itemMeta.getLore()));
            else
                storeString(playerData, saveName, "lore", "");

            if(itemMeta.hasCustomModelData())
                storeString(playerData, saveName, "custom_model", String.valueOf(itemMeta.getCustomModelData()));
            else
                storeString(playerData, saveName, "custom_model", "");

            if(itemMeta.hasEnchantmentGlintOverride())
                storeString(playerData, saveName, "glint", String.valueOf(itemMeta.getEnchantmentGlintOverride()));
            else
                storeString(playerData, saveName, "glint", "");

            storeString(playerData, saveName, "locked", String.valueOf(itemData.has(lockKey)));

            if(itemData.has(lockKey))
                storeString(playerData, saveName, "lock_key", itemData.get(lockKey, PersistentDataType.STRING));

            player.sendMessage("Saved GameObject data under [" + saveName + "].");
        }

        return true;
    }

    private void storeString(PersistentDataContainer playerData, String saveName, String key, String value) {
        NamespacedKey nkey = NamespacedKey.fromString(saveName + "." + key);
        playerData.set(nkey, PersistentDataType.STRING, value);
    }
}
