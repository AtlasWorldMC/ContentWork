package fr.atlasworld.contentwork.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import fr.atlasworld.contentwork.registering.DefaultRegistries;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class GiveCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("contentwork")
                .then(Commands.literal("give")
                        .requires(source -> source.hasPermission(2))
                        .then(Commands.argument("targets", EntityArgument.players())
                                .then(Commands.argument("item", StringArgumentType.word())
                                        .suggests(((ctx, builder) -> GiveCommand.buildItemSuggestion(builder)))))));
    }

    private static CompletableFuture<Suggestions> buildItemSuggestion(SuggestionsBuilder builder) {
        DefaultRegistries.ITEM.getEntries()
                .map(Map.Entry::getKey)
                .forEach(key -> builder.suggest(key.asString()));
        return builder.buildFuture();
    }
}
