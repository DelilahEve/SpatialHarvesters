package dunkmania101.spatialharvesters.objects.tile_entities.base;

import dunkmania101.spatialharvesters.data.CustomProperties;
import dunkmania101.spatialharvesters.data.CustomValues;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class TickingRedstoneEnergyMachineTE extends CustomEnergyMachineTE {
    private final boolean countTicks;
    protected boolean active = false;
    protected int ticks = 0;

    public TickingRedstoneEnergyMachineTE(BlockEntityType<?> tileEntityTypeIn, boolean canExtract, boolean canReceive, boolean countTicks) {
        super(tileEntityTypeIn, canExtract, canReceive);

        this.countTicks = countTicks;
    }

    public TickingRedstoneEnergyMachineTE(BlockEntityType<?> tileEntityTypeIn, boolean canExtract, boolean canReceive) {
        this(tileEntityTypeIn, canExtract, canReceive, false);
    }

    public static void tick(BlockEntity blockEntity) {
        if (blockEntity instanceof TickingRedstoneEnergyMachineTE) {
            ((TickingRedstoneEnergyMachineTE) blockEntity).internalTick();
        }
    }

    public void internalTick() {
        if (getWorld() != null && !getWorld().isRemote()) {
            if (getWorld().isBlockPowered(pos)) {
                setActive(false);
                getWorld().addParticle(RedstoneParticleData.REDSTONE_DUST, getPos().getX(), getPos().getY(), getPos().getZ(), 5, 5, 5);
            } else {
                customTickActions();
                if (this.countTicks) {
                    this.ticks++;
                }
            }
            if (getBlockState().hasProperty(CustomProperties.ACTIVE)) {
                if (getBlockState().get(CustomProperties.ACTIVE) != getActive()) {
                    getWorld().setBlockState(getPos(), getBlockState().with(CustomProperties.ACTIVE, getActive()));
                }
            }
        }
    }

    public void customTickActions() {
    }

    public void setCountedTicks(int ticks) {
        this.ticks = ticks;
    }

    public void resetCountedTicks() {
        setCountedTicks(0);
    }

    public int getCountedTicks() {
        return this.ticks;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean getActive() {
        return this.active;
    }

    @Override
    public CompoundNBT saveSerializedValues() {
        CompoundNBT nbt = super.saveSerializedValues();
        if (this.countTicks) {
            nbt.putInt(CustomValues.countedTicksKey, getCountedTicks());
        }
        return nbt;
    }

    @Override
    public void setDeserializedValues(CompoundNBT nbt) {
        super.setDeserializedValues(nbt);
        if (nbt.contains(CustomValues.countedTicksKey)) {
            this.ticks = nbt.getInt(CustomValues.countedTicksKey);
        }
    }
}
