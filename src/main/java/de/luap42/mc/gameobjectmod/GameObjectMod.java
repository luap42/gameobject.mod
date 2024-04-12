package de.luap42.mc.gameobjectmod;

import org.bukkit.plugin.java.JavaPlugin;

public final class GameObjectMod extends JavaPlugin {

    @Override
    public void onEnable() {
        this.getCommand("go-lock").setExecutor(new LockCommand());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
