package me.fodded.configplugin;

import lombok.Getter;
import me.fodded.configplugin.data.DataStorage;
import me.fodded.configplugin.listeners.PlayerJoinListener;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    @Getter
    public static Main plugin;

    @Getter
    public static DataStorage configHandler = new DataStorage();

    @Override
    public void onEnable() {
        this.plugin = this;
        this.configHandler.loadConfig(plugin);

        getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);
    }

    @Override
    public void onDisable() {

    }
}