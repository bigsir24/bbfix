package bigsir.bbfix.mixin;

import com.mojang.nbt.tags.CompoundTag;
import com.mojang.nbt.tags.DoubleTag;
import com.mojang.nbt.tags.ListTag;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.util.phys.AABB;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Entity.class, remap = false)
public abstract class EntityMixin {

	@Shadow
	protected abstract @NotNull ListTag newDoubleList(double[] array);

	@Shadow
	@Final
	@NotNull
	public AABB bb;

	@Shadow
	public float heightOffset;

	@Inject(method = "saveWithoutId", at = @At("TAIL"))
	public void s(CompoundTag tag, CallbackInfo ci) {
		ListTag bbTag = newDoubleList(new double[]{this.bb.minX, this.bb.minY, this.bb.minZ, this.bb.maxX, this.bb.maxY, this.bb.maxZ});
		tag.putList("bb", bbTag);
		tag.putFloat("ho", this.heightOffset);
	}


	@Inject(method = "load", at = @At("TAIL"))
	public void l(CompoundTag tag, CallbackInfo ci) {
		ListTag bbTag = tag.getList("bb");
		if(bbTag != null && bbTag.tagCount() == 6) {
			this.bb.minX = ((DoubleTag)bbTag.tagAt(0)).getValue();
			this.bb.minY = ((DoubleTag)bbTag.tagAt(1)).getValue();
			this.bb.minZ = ((DoubleTag)bbTag.tagAt(2)).getValue();
			this.bb.maxX = ((DoubleTag)bbTag.tagAt(3)).getValue();
			this.bb.maxY = ((DoubleTag)bbTag.tagAt(4)).getValue();
			this.bb.maxZ = ((DoubleTag)bbTag.tagAt(5)).getValue();
		}
		if (tag.containsKey("ho")) {
			this.heightOffset = tag.getFloat("ho");
		}

	}
}
