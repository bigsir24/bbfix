package bigsir.bbfix.mixin;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.util.phys.AABB;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.List;

@Mixin(value = Mob.class, remap = false)
public abstract class MobMixin extends Entity{
	public MobMixin(@Nullable World world) {
		super(world);
	}

	@Redirect(method = "onLivingUpdate", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/world/World;getCubes(Lnet/minecraft/core/entity/Entity;Lnet/minecraft/core/util/phys/AABB;)Ljava/util/List;"))
	public List<AABB> c(World instance, Entity entity, AABB bb) {
		return instance.isClientSide ? null : instance.getCubes(entity, bb);
	}

	@Redirect(method = "onLivingUpdate", at = @At(value = "INVOKE", target = "Ljava/util/List;isEmpty()Z", ordinal = 0))
	public boolean b(List<AABB> instance) {
		return this.world.isClientSide || instance.isEmpty();
	}


}
