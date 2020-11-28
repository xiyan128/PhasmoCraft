package space.xiyan.phasmo.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.command.CommandSource;
import net.minecraft.util.text.StringTextComponent;
import space.xiyan.phasmo.PhasmoConfig;

public class SetRadiusCommand implements Command<CommandSource> {
  public static SetRadiusCommand instance = new SetRadiusCommand();

  @Override
  public int run(CommandContext<CommandSource> context) {
    int radius = IntegerArgumentType.getInteger(context, "radius");
//    int radius = 10;
    PhasmoConfig.COMMON.radius.set(radius);
    PhasmoConfig.COMMON.radius.save();
    context
            .getSource()
            .sendFeedback(new StringTextComponent("Detection radius set to: " + PhasmoConfig.COMMON.radius.get()), false);
    return 1;
  }
}
