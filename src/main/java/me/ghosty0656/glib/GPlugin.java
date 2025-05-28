package me.ghosty0656.glib;

import de.exlll.configlib.ConfigLib;
import de.exlll.configlib.Serializer;
import de.exlll.configlib.YamlConfigurationProperties;
import de.exlll.configlib.YamlConfigurationStore;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import me.ghosty0656.glib.configuration.Config;
import me.ghosty0656.glib.configuration.Messages;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldLoadEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.nio.file.Path;
import java.util.*;

public abstract class GPlugin<C extends Config, M extends Messages> extends JavaPlugin implements Listener {

    private YamlConfigurationStore<C> configStore;
    private Path configFile;
    private final C defaultConfig;
    private C config;
    private final Class<C> configClass;

    private YamlConfigurationStore<M> messagesStore;
    private Path messagesFile;
    private final M defaultMessages;
    private M messages;
    private final Class<M> messagesClass;

    private final Set<Command> commands = new HashSet<>();

    private final Set<String> expectedWorlds = new HashSet<>();
    private final Set<String> loadedWorlds = new HashSet<>();
    private boolean allWorldsLoaded = false;

    protected GPlugin(C config, M messages) {
        this.defaultConfig = config;
        this.configClass = (Class<C>) config.getClass();
        this.defaultMessages = messages;
        this.messagesClass = (Class<M>) messages.getClass();
    }

    public void onPluginLoad() {}
    public void onPluginEnable() {}
    public void onWorldsLoaded() {}
    public void onPluginDisable() {}

    @Override
    public final void onLoad() {
        try {
            configFile = new File(getDataFolder(), "config.yml").toPath();
            YamlConfigurationProperties.Builder<?> configPropBuilder = ConfigLib.BUKKIT_DEFAULT_PROPERTIES.toBuilder();
            configPropBuilder.createParentDirectories(true);
            configPropBuilder.addSerializer(Component.class, new ComponentSerializer());
            configStore = new YamlConfigurationStore<>(configClass, configPropBuilder.build());

            messagesFile = new File(getDataFolder(), "messages.yml").toPath();
            YamlConfigurationProperties.Builder<?> messagePropBuilder = ConfigLib.BUKKIT_DEFAULT_PROPERTIES.toBuilder();
            messagePropBuilder.createParentDirectories(true);
            messagePropBuilder.addSerializer(Component.class, new ComponentSerializer());
            messagePropBuilder.header(
                    """
                    ===== MiniMessage =====
                    This plugin uses MiniMessage to format its messages.
                    You can find the format here: https://docs.advntr.dev/minimessage/format.html
                    
                    ===== Placeholders =====
                    Placeholders are encased in angle brackets (<>).
                    All available placeholders are listed above sections and messages.
                    A placeholder above a section works for all messages and subsections in that section.
                    
                    Global placeholders:
                    PLAYER_DISPLAY - The display name of the player receiving the message. Includes the prefix, suffix, color and style.
                    PLAYER_NAME ---- The raw name of the player receiving the message. No prefix, suffix, color or style.
                    """
            );
            messagesStore = new YamlConfigurationStore<>(messagesClass, messagePropBuilder.build());
        } catch (Exception ignored) {
            // Ignore
            // Sometimes throws exceptions for empty config classes which is intentional if it happens and should not throw an error
        }

        onPluginLoad();
    }

    @Override
    public final void onEnable() {
        getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS, event -> {
            Commands registry = event.registrar();
            for (Command command : commands) {
                command.register(registry);
            }
        });

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

    protected void registerCommand(Command command) {
        commands.add(command);
    }

    public void saveDefaultConfig() {
        if (configFile.toFile().exists()) return;
        configStore.save(defaultConfig, configFile);
    }

    public void saveConfig() {
        configStore.save(config, configFile);
    }

    public void reloadConfig() {
        config = configStore.load(configFile);
    }

    public C getPluginConfig() {
        if (config == null) {
            reloadConfig();
        }
        return config;
    }

    public void saveDefaultMessages() {
        if (messagesFile.toFile().exists()) return;
        messagesStore.save(defaultMessages, messagesFile);
    }

    public void saveMessages() {
        messagesStore.save(messages, messagesFile);
    }

    public void reloadMessages() {
        messages = messagesStore.load(messagesFile);
    }

    public M getMessages() {
        if (messages == null) {
            reloadConfig();
        }
        return messages;
    }

    private static final class ComponentSerializer implements Serializer<Component, String> {

        @Override
        public String serialize(Component component) {
            return MiniMessage.miniMessage().serialize(component);
        }

        @Override
        public Component deserialize(String string) {
            return MiniMessage.miniMessage().deserialize(string);
        }
    }
}