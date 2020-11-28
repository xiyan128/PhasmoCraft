package space.xiyan.phasmo;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class GhostTrace extends Item {

  public GhostTrace() {
    super(new Properties().group(ItemGroup.TOOLS));
  }

  @Override
  public int getItemStackLimit(ItemStack stack) {
    return 1;
  }
}
