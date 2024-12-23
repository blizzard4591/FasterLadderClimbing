package net.jaspr.fasterladderclimbing;

import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class PlayerEventListener {

	@SubscribeEvent
	public void onPlayerTick(TickEvent.PlayerTickEvent event) {
		if (event.side.isClient()) {
			if (event.phase == TickEvent.Phase.START) {
				final Player player = event.player;

				final boolean isOnClimbable = player.onClimbable();
				final boolean isCrouching = player.isCrouching();

				if (isOnClimbable && !isCrouching) {
					EntityClimber climber = new EntityClimber(player);

					if (FasterLadderClimbingConfig.allowQuickDescension && climber.isFacingDownward() && !climber.isMovingForward() && !climber.isMovingBackward()) {
						climber.moveDownFarther();
					} else if (FasterLadderClimbingConfig.allowQuickAscension && climber.isFacingUpward() && climber.isMovingForward()) {
						climber.moveUpFarther();
					}
				}
			}
		}
	}

	private record EntityClimber(Player player) {

		private boolean isFacingDownward() {
				return player.getXRot() > 0;
			}

		private boolean isFacingUpward() {
			return player.getXRot() < 0;
		}

		private boolean isMovingForward() {
			return player.zza > 0;
		}

		private boolean isMovingBackward() {
			return player.zza < 0;
		}

		private float getElevationChangeUpdate() {
			return (float) Math.abs(player.getXRot() / 90.0) * (((float) FasterLadderClimbingConfig.speedModifier) / 10);
		}

		public void moveUpFarther() {
			int px = 0;
			float dx = getElevationChangeUpdate();
			Vec3 move = new Vec3(px, dx, px);
			player.move(MoverType.SELF, move);
		}

		public void moveDownFarther() {
			int px = 0;
			float dx = getElevationChangeUpdate();
			Vec3 move = new Vec3(px, (dx * -1), px);
			player.move(MoverType.SELF, move);
		}
	}

}
