package me.nayfeex.playerrendererfunidk.client;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FacingBlock;
import net.minecraft.datafixer.fix.ChunkPalettedStorageFix;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;

public class TailSemiBlock extends FacingBlock {

    public static BlockState state;

    public static float wind = 0.3F;
    public static final DirectionProperty FACING = Properties.FACING;
    public TailSemiBlock(Settings settings) {
        super(settings);
        this.state = getDefaultState();
        this.wind = wind;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

}
