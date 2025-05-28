package me.ghosty0656.glib.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import me.ghosty0656.glib.Command;
import me.ghosty0656.glib.GLib;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.CommandSender;


public class GLibCommand extends Command {

    public GLibCommand() {
        super("glib");
    }

    @Override
    protected LiteralArgumentBuilder<CommandSourceStack> build(LiteralArgumentBuilder<CommandSourceStack> node) {
        return node.executes(this::execute);
    }

    private int execute(CommandContext<CommandSourceStack> ctx) {
        CommandSender executor = ctx.getSource().getExecutor();
        if (executor == null) {
            executor = ctx.getSource().getSender();
        }
        String version = GLib.getPlugin(GLib.class).getPluginMeta().getVersion();
        executor.sendMessage(
                Component.text("Using GLib %s".formatted(version))
                        .color(NamedTextColor.AQUA)
        );
        return 1;
    }
}
