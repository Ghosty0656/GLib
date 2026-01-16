package me.ghosty0656.commands;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import fi.sulku.hytale.TinyMsg;
import me.ghosty0656.Main;

import javax.annotation.Nonnull;

public class GlibCommand extends AbstractPlayerCommand {

    public GlibCommand() {
        super("glib", "Get information about GLib");
    }

    @Override
    protected void execute(@Nonnull CommandContext ctx, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef player, @Nonnull World world) {
        player.sendMessage(
                TinyMsg.parse(
                        "<color:aqua>[GLib]</color> Using GLib version %s"
                                .formatted(Main.manifest.getVersion())
                )
        );
    }
}
