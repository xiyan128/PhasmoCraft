package space.xiyan.phasmo;

import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemRegistry {
  public static final DeferredRegister<Item> ITEMS =
      DeferredRegister.create(ForgeRegistries.ITEMS, "phasmo");
  public static RegistryObject<Item> ghost_trace = ITEMS.register("ghost_trace", GhostTrace::new);
}
