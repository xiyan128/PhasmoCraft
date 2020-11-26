package space.xiyan.phasmo;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextComponent;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Mod.EventBusSubscriber()
public class StaticEventHandler {

  public static final int TIME_GAP = 20;

  @SubscribeEvent
  public static void alertMobsAround(LivingEvent.LivingUpdateEvent event) {
    if (event.getEntity() instanceof PlayerEntity) {
      PlayerEntity player = (PlayerEntity) event.getEntity();
      World world = player.getEntityWorld();
      if (!world.isRemote) return;
      if (world.getGameTime() % TIME_GAP != 0) return;
      if (!(player.getHeldItem(Hand.MAIN_HAND).getItem() instanceof GhostTrace
          || player.getHeldItem(Hand.OFF_HAND).getItem() instanceof GhostTrace)) return;

      int radius = Config.getInstance().getRadius();

      List<Entity> entities =
          player
              .getEntityWorld()
              .getEntitiesWithinAABBExcludingEntity(
                  player,
                  player
                      .getBoundingBox()
                      .expand(radius, radius, radius)
                      .expand(-radius, -radius, -radius))
              .stream()
              .filter(
                  e ->
                      Objects.equals(
                          e.getType().getRegistryName(), Config.getInstance().getTracingEntity()))
              .filter(e -> player.getDistance(e) <= radius)
              .collect(Collectors.toList());
      if (entities.size() > 0) {
        SoundEvent sound = SoundEventRegistry.meaSound.get();
        player.playSound(sound, 10f, 1f);
      }
    }
  }

  @SubscribeEvent
  public static void setEntityToTrace(AttackEntityEvent event) {
    PlayerEntity player = event.getPlayer();
    World world = player.getEntityWorld();
    if (player.isSneaking() && player.getHeldItem(Hand.MAIN_HAND).getItem() instanceof GhostTrace) {
      event.setCanceled(true);
      Entity entity = event.getTarget();
      Config.getInstance().setTracingEntity(entity.getType().getRegistryName());
      player.sendStatusMessage(new StringTextComponent("Now Tracing: " + entity.getType().getName().getString()), true);
    }
  }
}
