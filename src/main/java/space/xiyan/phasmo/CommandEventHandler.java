package space.xiyan.phasmo;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;

@Mod.EventBusSubscriber
public class CommandEventHandler {
    @SubscribeEvent
    public static void onServerStaring(FMLServerStartingEvent event) {
        CommandDispatcher<CommandSource> dispatcher = event.getServer().getCommandManager().getDispatcher();
        LiteralCommandNode<CommandSource> cmd = dispatcher.register(
                Commands.literal("phasmo").then(
                        Commands.literal("set")
                                .requires((commandSource) -> commandSource.hasPermissionLevel(0))
                                .then(Commands.argument("radius", IntegerArgumentType.integer(0))
                                .executes(SetRadiusCommand.instance))
                )
        );
        dispatcher.register(Commands.literal("ph").redirect(cmd));
    }
}