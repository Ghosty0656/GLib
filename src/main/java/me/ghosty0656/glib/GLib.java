package me.ghosty0656.glib;

import me.ghosty0656.glib.commands.GLibCommand;

public final class GLib extends GPlugin {

    @Override
    public void onPluginLoad() {
        // Plugin load logic
    }

    @Override
    public void onPluginEnable() {
        commands.register(new GLibCommand());
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
