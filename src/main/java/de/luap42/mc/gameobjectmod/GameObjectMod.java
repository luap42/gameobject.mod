package de.luap42.mc.gameobjectmod;

import org.bukkit.plugin.java.JavaPlugin;

public final class GameObjectMod extends JavaPlugin {

    @Override
    public void onEnable() {
        this.getCommand("go-lock").setExecutor(new LockCommand());
        this.getCommand("go-unlock").setExecutor(new UnlockCommand());
        this.getCommand("go-lore").setExecutor(new LoreCommand());
        this.getCommand("go-format").setExecutor(new FormatCommand());
        this.getCommand("go-custommodel").setExecutor(new CustomModelCommand());

        this.getCommand("go-save").setExecutor(new SaveCommand());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
