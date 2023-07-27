package fr.atlasworld.contentwork.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import fr.atlasworld.contentwork.ContentWork;
import fr.atlasworld.contentwork.api.common.item.CustomItemStack;
import fr.atlasworld.contentwork.api.common.item.Item;
import fr.atlasworld.contentwork.registering.DefaultRegistries;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextColor;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class GiveCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("contentwork")
                .then(Commands.literal("give")
                        .requires(source -> source.hasPermission(2))
                        .then(Commands.argument("targets", EntityArgument.players())
                                .then(Commands.argument("item", StringArgumentType.string())
                                        .suggests(((ctx, builder) -> GiveCommand.buildItemSuggestion(builder)))
                                        .executes(ctx -> execute(ctx.getSource(), EntityArgument.getPlayers(ctx, "targets"), ctx.getArgument("item", String.class), 1))
                                        .then(Commands.argument("count", IntegerArgumentType.integer(1))
                                                .executes(ctx -> execute(ctx.getSource(), EntityArgument.getPlayers(ctx, "targets"), ctx.getArgument("item", String.class), IntegerArgumentType.getInteger(ctx, "count"))))))));
    }

    private static CompletableFuture<Suggestions> buildItemSuggestion(SuggestionsBuilder builder) {
        DefaultRegistries.ITEM.getEntries()
                .map(Map.Entry::getKey)
                .forEach(key -> builder.suggest("\"" + key.asString() + "\""));
        return builder.buildFuture();
    }

    private static int execute(CommandSourceStack source, Collection<ServerPlayer> targets, String itemStr, int count) {
        Item item = DefaultRegistries.ITEM.get(NamespacedKey.fromString(itemStr));
        int itemMaxStackSize = item.getParent().getMaxStackSize();
        int max = itemMaxStackSize * 100;
        ItemStack itemStack = CustomItemStack.create(item, count);

        if (count > max) {
            source.getBukkitSender().sendMessage(Component.translatable("commands.give.failed.toomanyitems", Component.text(max), itemStack.getItemMeta().displayName()).style(Style.style(TextColor.color(255, 33, 21))));
            return 0;
        } else {
            for (ServerPlayer target : targets) {
                target.getBukkitEntity().getInventory().addItem(itemStack)
                        .forEach((key, value) -> {
                            int amountDrop = value.getAmount();
                            while (amountDrop > 0) {
                                int amount = Math.min(itemMaxStackSize, amountDrop);
                                amountDrop -= amount;

                                ItemStack stack = CustomItemStack.create(item, amount);
                                target.getBukkitEntity().getWorld().dropItem(target.getBukkitEntity().getLocation(), stack);
                            }
                        });
            }
        }

        return Command.SINGLE_SUCCESS;
    }
}
