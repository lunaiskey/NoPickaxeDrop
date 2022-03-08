package me.lunaiskey.pyrex.nopickdrop;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class NoPickDrop extends JavaPlugin {

    @Override
    public void onEnable() {

        Bukkit.getPluginManager().registerEvents(new PickaxeDropListener(),this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
