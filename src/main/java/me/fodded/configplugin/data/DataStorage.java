package me.fodded.configplugin.data;

import lombok.Getter;
import me.fodded.configplugin.Main;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;

@Getter
public class DataStorage {

    public String on_join_message = "&7WELCOME &f&l%player%!"; // variable which is named players$scoreboard will be transformed into players.scoreboard
    public double max$allowed$reach = 4.3D;
    public int max_players_at_same_time = 10;
    public int bowi3 = 123214;
    public String PLARSON = "0_0";

    public void loadConfig(Main plugin) {
        File file = new File(plugin.getDataFolder() + File.separator + "config.yml");
        try {
            if (!file.exists()) {
                saveConfig(plugin, file);
                return;
            }

            if(checkConfig(plugin, file)) {
                return;
            }

            for (Field field : this.getClass().getFields()) {
                String name = field.getName().replace("$", ".");
                field.set(this.getClass(), plugin.getConfig().get(name));
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void saveConfig(Main plugin, File config) {
        try {
            for (Field field : this.getClass().getFields()) {
                String name = field.getName().replace("$", ".");
                Object value = field.get(this.getClass());
                plugin.getConfig().set(name, value);
                plugin.getConfig().save(config);
            }
        } catch (IllegalAccessException | IOException e) {
            e.printStackTrace();
        }
    }

    private boolean checkConfig(Main plugin, File file) {
        try {
            if (!doesConfigMatch(plugin)) {
                for (String path : plugin.getConfig().getConfigurationSection("").getKeys(true)) {
                    String name = path.replace("$", ".");
                    if (getExactField(name) == null) {
                        continue;
                    }
                    getExactField(name).set(this.getClass(), plugin.getConfig().get(name));
                }
                saveConfig(plugin, file);
                return true;
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean doesConfigMatch(Main plugin) {
        int fields = this.getClass().getFields().length;
        int paths = plugin.getConfig().getConfigurationSection("").getKeys(false).toArray().length;

        if(fields != paths) {
            return false;
        }
        return true;
    }

    private Field getExactField(String name) {
        for(Field field : this.getClass().getFields()) {
            if(field.getName().replace("$", ".").equalsIgnoreCase(name)) {
                return field;
            }
        }
        return null;
    }
}
