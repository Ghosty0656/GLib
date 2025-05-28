package me.ghosty0656.glib;

import me.ghosty0656.glib.commands.GLibCommand;
import me.ghosty0656.glib.configuration.Config;
import me.ghosty0656.glib.configuration.Messages;

public final class GLib extends GPlugin<Config, Messages> {

    public GLib() {
        super(new Config(), new Messages());
    }

    @Override
    public void onPluginLoad() {
        // Plugin load logic
        registerCommand(new GLibCommand());
    }

    @Override
    public void onPluginEnable() {
        // Plugin enable logic
    }

    @Override
    public void onWorldsLoaded() {
        // Post worlds loaded logic
    }

    @Override
    public void onPluginDisable() {
        // Plugin disable logic
    }
}
