package bigsir.bbfix.mixin;

import bigsir.bbfix.IClientUpdatesPhysics;
import net.minecraft.client.entity.particle.Particle;
import net.minecraft.client.entity.player.PlayerLocal;
import net.minecraft.core.entity.EntityFallingBlock;
import net.minecraft.core.entity.EntityFishingBobber;
import net.minecraft.core.entity.EntityItem;
import net.minecraft.core.entity.projectile.Projectile;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = {PlayerLocal.class, EntityItem.class, Particle.class, EntityFallingBlock.class, EntityFishingBobber.class, Projectile.class}, remap = false)
public abstract class EntityMixinClientUpdate implements IClientUpdatesPhysics {
}
