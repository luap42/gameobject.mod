package de.luap42.mc.gameobjectmod;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class GameObjectMod extends JavaPlugin {

    FileConfiguration config = getConfig();
    public static GameObjectMod i = null;

    @Override
    public void onEnable() {
        GameObjectMod.i = this;
        config.addDefault("allowLock", true);
        config.addDefault("allowAddLore", true);
        config.addDefault("allowFormat", true);
        config.addDefault("allowSetCustomModel", true);
        config.addDefault("allowSetItemModel", true);
        config.addDefault("allowChangeGlint", true);
        config.addDefault("allowSaveAndApply", true);
        config.options().copyDefaults(true);
        saveConfig();

        this.getCommand("go-lock").setExecutor(new LockCommand());
        this.getCommand("go-unlock").setExecutor(new UnlockCommand());
        this.getCommand("go-lore").setExecutor(new LoreCommand());
        this.getCommand("go-format").setExecutor(new FormatCommand());
        this.getCommand("go-custommodel").setExecutor(new CustomModelCommand());
        this.getCommand("go-itemmodel").setExecutor(new ItemModelCommand());
        this.getCommand("go-glint").setExecutor(new GlintCommand());

        this.getCommand("go-save").setExecutor(new SaveCommand());
        this.getCommand("go-apply").setExecutor(new ApplyCommand());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
