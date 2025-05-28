package me.ghosty0656.glib;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;

import java.util.Collection;
import java.util.List;

public abstract class Command {

    private final String label;
    private final String description;
    private final Collection<String> aliases;

    protected Command(String label) {
        this(label, null, List.of());
    }

    protected Command(String label, Collection<String> aliases) {
        this(label, null, aliases);
    }

    protected Command(String label, String description) {
        this(label, description, List.of());
    }

    protected Command(String label, String description, Collection<String> aliases) {
        this.label = label;
        this.description = description;
        this.aliases = aliases;
    }

    public void register(Commands commands) {
        commands.register(build(Commands.literal(label)).build(), description, aliases);
    }

    protected abstract LiteralArgumentBuilder<CommandSourceStack> build(LiteralArgumentBuilder<CommandSourceStack> node);
}
