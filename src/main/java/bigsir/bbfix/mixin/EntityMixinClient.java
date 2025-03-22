package bigsir.bbfix.mixin;

import bigsir.bbfix.BBFix;
import bigsir.bbfix.IClientUpdatesPhysics;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.util.phys.AABB;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Entity.class, remap = false)
public abstract class EntityMixinClient {
	@Shadow
	@Nullable
	public World world;

	@Shadow
	public boolean noPhysics;

	@Shadow
	@Final
	@NotNull
	public AABB bb;

	@Shadow
	public boolean onGround;

	@Inject(method = "push(Lnet/minecraft/core/entity/Entity;)V", at = @At("HEAD"), cancellable = true)
	public void c(Entity entity, CallbackInfo ci) {
		if (this.world != null && this.world.isClientSide && !(entity instanceof Player)) {
			ci.cancel();
		}
	}

	@Inject(method = "move", at = @At("HEAD"), cancellable = true)
	public void t(double xd, double yd, double zd, CallbackInfo ci) {
		if (this.world != null && this.world.isClientSide && noMovement()) {
			Entity thisRef = BBFix.cast(this);

			if (!this.noPhysics) {
				this.onGround = !this.world.getCubes(thisRef, this.bb.getInsetBoundingBox(0.025, 0, 0.025).move(0,-0.001,0)).isEmpty();
			}

			ci.cancel();
		}
	}

	@Unique
	private boolean noMovement() {
		Entity thisRef = BBFix.cast(this);
		return !(thisRef instanceof IClientUpdatesPhysics);
	}

	@Inject(method = "lerpTo", at =  @At(value = "INVOKE", target = "Lnet/minecraft/core/entity/Entity;setRot(FF)V", shift = At.Shift.AFTER), cancellable = true)
	public void f(double x, double y, double z, float yRot, float xRot, int i, CallbackInfo ci) {
		if (this.world != null && this.world.isClientSide) {
			ci.cancel();
		}
	}
}
