package dunkmania101.spatialharvesters.objects.blocks;

import dunkmania101.spatialharvesters.init.TileEntityInit;
import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

public class SoilHarvesterBlock extends HarvesterBlock {
    public SoilHarvesterBlock(Properties properties, int tierIn) {
        super(properties, tierIn);
    }

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return TileEntityInit.SOIL_HARVESTER.get().create();
    }
}
