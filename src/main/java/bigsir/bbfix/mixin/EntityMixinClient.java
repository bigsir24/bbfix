package bigsir.bbfix.mixin;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Entity.class, remap = false)
public abstract class EntityMixinClient {
	@Shadow
	@Nullable
	public World world;

	@Inject(method = "push(Lnet/minecraft/core/entity/Entity;)V", at = @At("HEAD"), cancellable = true)
	public void c(Entity entity, CallbackInfo ci) {
		if (this.world != null && this.world.isClientSide && !(entity instanceof Player)) {
			ci.cancel();
		}
	}
}
