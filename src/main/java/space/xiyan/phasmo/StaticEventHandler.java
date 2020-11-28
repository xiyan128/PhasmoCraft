package space.xiyan.phasmo;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Mod.EventBusSubscriber()
public class StaticEventHandler {

  public static final int TIME_GAP_MAX = 20;
  public static final int TIME_GAP_MIN = 3;
  private static final HashMap<PlayerEntity, Long> nextTime = new HashMap<>();
  private static final String TAG_NAME = "tracing";

  @SubscribeEvent
  public static void alertMobsAround(LivingEvent.LivingUpdateEvent event) {
    if (event.getEntity() instanceof PlayerEntity) {
      PlayerEntity player = (PlayerEntity) event.getEntity();
      World world = player.getEntityWorld();
      if (!world.isRemote) return;

      // determine if reached the last stored triggered time
      if (world.getGameTime() < nextTime.getOrDefault(player, 0L)) return;

      final ItemStack ghostTrace =
              (player.getHeldItem(Hand.MAIN_HAND).getItem() instanceof GhostTrace)
                      ? player.getHeldItem(Hand.MAIN_HAND)
                      : ((player.getHeldItem(Hand.OFF_HAND).getItem() instanceof GhostTrace)
                      ? player.getHeldItem(Hand.MAIN_HAND)
                      : null);
      if (ghostTrace == null) return;
      CompoundNBT nbt = ghostTrace.getOrCreateTag();
      if (!nbt.contains(TAG_NAME)) nbt.putInt(TAG_NAME, 0);

      int radius = PhasmoConfig.COMMON.radius.get();

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
                      .filter(e -> ghostTrace.getTag().getInt(TAG_NAME) == e.getType().hashCode())
                      .filter(e -> player.getDistance(e) <= radius)
                      .collect(Collectors.toList());
      if (entities.isEmpty()) return;
      Entity toTrace = entities.get(0);
      for (Entity e : entities) {
        if (player.getDistance(e) < toTrace.getDistance(toTrace)) toTrace = e;
      }
      SoundEvent sound = SoundEventRegistry.meaSound.get();
      player.playSound(sound, 10f, getPitch(player.getDistance(toTrace), radius));
      nextTime.put(player, world.getGameTime() + getTimeGap(player.getDistance(toTrace), radius));
    }
  }

  private static float getPitch(double distance, double maxDistance) {
    final double A = -Math.log(Math.E) / Math.log(maxDistance);
    final double B = PhasmoConfig.COMMON.maxPitch.get();
    return (float) (B + A * Math.log(distance) / Math.log(Math.E));
  }

  private static long getTimeGap(double distance, double maxDistance) {
    return (long)
            ((PhasmoConfig.COMMON.timeGapMax.get() - PhasmoConfig.COMMON.timeGapMin.get())
                    / maxDistance
                    * distance
                    + PhasmoConfig.COMMON.timeGapMin.get());
  }

  @SubscribeEvent
  public static void setEntityToTrace(AttackEntityEvent event) {
    PlayerEntity player = event.getPlayer();
    //    World world = player.getEntityWorld();
    if (player.isSneaking() && player.getHeldItem(Hand.MAIN_HAND).getItem() instanceof GhostTrace) {
      event.setCanceled(true);

      Entity entity = event.getTarget();
      String entityName = entity.getType().getName().getString();
      ItemStack stack = player.getHeldItem(Hand.MAIN_HAND);
      CompoundNBT nbt = stack.hasTag() ? stack.getTag() : new CompoundNBT();
      nbt.putInt(TAG_NAME, entity.getType().hashCode());
      stack.setTag(nbt);
      player.sendStatusMessage(new StringTextComponent("Now Tracing: " + entityName), true);
      stack.setDisplayName(ITextComponent.getTextComponentOrEmpty("Tracing: " + entityName));
    }
  }
}
