package space.xiyan.phasmo;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.command.CommandSource;
import net.minecraft.util.text.StringTextComponent;

public class SetRadiusCommand implements Command<CommandSource> {
  public static SetRadiusCommand instance = new SetRadiusCommand();

  @Override
  public int run(CommandContext<CommandSource> context) {
    int radius = IntegerArgumentType.getInteger(context, "radius");
//    int radius = 10;
    Config.getInstance().setRadius(radius);
    context
        .getSource()
        .sendFeedback(new StringTextComponent("Detection radius set to: " + radius), false);
    return 1;
  }
}
