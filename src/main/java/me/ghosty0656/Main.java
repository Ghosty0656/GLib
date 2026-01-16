package me.ghosty0656;

import com.hypixel.hytale.common.plugin.PluginManifest;
import com.hypixel.hytale.logger.HytaleLogger;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import me.ghosty0656.commands.GlibCommand;

import javax.annotation.Nonnull;

public class Main extends JavaPlugin {

    public static PluginManifest manifest;
    public static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();

    public Main(@Nonnull JavaPluginInit init) {
        super(init);
        Main.manifest = init.getPluginManifest();
    }

    @Override
    public void setup() {
        this.getCommandRegistry().registerCommand(new GlibCommand());
    }

    @Override
    protected void start() {
    }

    @Override
    protected void shutdown() {
    }
}