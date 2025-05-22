package me.ghosty0656.glib.commands;

import me.ghosty0656.glib.GLib;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.william278.uniform.CommandUser;
import net.william278.uniform.Permission;
import net.william278.uniform.annotations.CommandNode;
import net.william278.uniform.annotations.PermissionNode;
import net.william278.uniform.annotations.Syntax;

@CommandNode(
        value = "glib",
        description = "",
        permission = @PermissionNode(
                value = "glib.command",
                defaultValue = Permission.Default.IF_OP
        )
)
public class GLibCommand {

    @Syntax
    public void execute(CommandUser user) {
        String version = GLib.getPlugin(GLib.class).getPluginMeta().getVersion();
        user.getAudience().sendMessage(
                Component.text("Using GLib %s".formatted(version))
                        .color(NamedTextColor.AQUA)
        );
    }
}
