package me.ghosty0656.glib;

import net.william278.uniform.paper.PaperUniform;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldLoadEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.*;

public abstract class GPlugin extends JavaPlugin implements Listener {

    protected PaperUniform commands;
    private final Set<String> expectedWorlds = new HashSet<>();
    private final Set<String> loadedWorlds = new HashSet<>();
    private boolean allWorldsLoaded = false;

    public void onPluginLoad() {}
    public void onPluginEnable() {}
    public void onWorldsLoaded() {}
    public void onPluginDisable() {}

    @Override
    public final void onLoad() {
        onPluginLoad();
    }

    @Override
    public final void onEnable() {
        commands = PaperUniform.getInstance(this);

        Bukkit.getPluginManager().registerEvents(this, this);

        // Get all worlds, loaded or not
        List<File> directories = Arrays.stream(getServer().getWorldContainer().listFiles()).filter(File::isDirectory).toList();
        for (File directory : directories)  {
            String[] contents = directory.list();
            if (contents != null && Arrays.asList(contents).contains("level.dat")) {
                expectedWorlds.add(directory.getName());
            }
        }

        onPluginEnable();
    }

    @EventHandler
    public final void onWorldLoad(WorldLoadEvent event) {
        loadedWorlds.add(event.getWorld().getName());

        // If all worlds are loaded, call the onWorldsLoaded function
        if (loadedWorlds.containsAll(expectedWorlds) && !allWorldsLoaded) {
            allWorldsLoaded = true;
            onWorldsLoaded();
        }
    }

    @Override
    public final void onDisable() {
        onPluginDisable();
    }
}
