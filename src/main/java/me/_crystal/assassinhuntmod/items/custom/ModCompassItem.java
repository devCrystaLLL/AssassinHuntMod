package me._crystal.assassinhuntmod.items.custom;

import com.mojang.serialization.DataResult;
import me._crystal.assassinhuntmod.AssassinHuntMod;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CompassItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.slf4j.Logger;

import java.util.Objects;

public class ModCompassItem extends CompassItem {
    public ModCompassItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext ctx) {
        BlockPos blockPos = ctx.getClickedPos();
        Level lvl = ctx.getLevel();

        lvl.playSound(null, blockPos, SoundEvents.LODESTONE_COMPASS_LOCK, SoundSource.PLAYERS, 1.0F, 1.0F);
        Player player = ctx.getPlayer();
        ItemStack itemStack = ctx.getItemInHand();
        boolean $$5 = !player.getAbilities().instabuild && itemStack.getCount() == 1;

        if ($$5) {
            addLodestoneTags(lvl.dimension(), blockPos, itemStack.getOrCreateTag());

        } else {
            ItemStack itemStack1 = new ItemStack(Items.COMPASS, 1);
            CompoundTag nbt = itemStack1.hasTag() ? itemStack1.getTag().copy() : new CompoundTag();
            itemStack1.setTag(nbt);

            if (!player.getAbilities().instabuild) {
                itemStack1.shrink(1);
            }

            addLodestoneTags(lvl.dimension(), blockPos, nbt);
            if (!player.getInventory().add(itemStack1)) {
                player.drop(itemStack1, false);
            }
        }

        return InteractionResult.sidedSuccess(lvl.isClientSide);
    }

    private void addLodestoneTags(ResourceKey<Level> p_40733_, BlockPos p_40734_, CompoundTag p_40735_) {
        p_40735_.put("LodestonePos", NbtUtils.writeBlockPos(p_40734_));
        DataResult dataResult = Level.RESOURCE_KEY_CODEC.encodeStart(NbtOps.INSTANCE, p_40733_);
        Logger logger = AssassinHuntMod.LOGGER;
        Objects.requireNonNull(logger);
/*        dataResult.resultOrPartial(logger::error).ifPresent((p_40731_) -> {
            p_40735_.put("LodestoneDimension", p_40731_);
        });*/
        p_40735_.putBoolean("LodestoneTracked", true);
    }
}
