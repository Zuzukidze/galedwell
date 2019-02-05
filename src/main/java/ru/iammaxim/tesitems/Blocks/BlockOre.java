package ru.iammaxim.tesitems.Blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ru.iammaxim.tesitems.Player.IPlayerAttributesCapability;
import ru.iammaxim.tesitems.TESItems;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BlockOre extends BlockCrops {

    public Item crop;
    public BlockOre(Item crop, String name, float hardness, Side side) {
        this.crop = crop;
        this.setRegistryName(name);
        this.setUnlocalizedName(name);
        this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
        this.setHardness(hardness);
        this.setSoundType(SoundType.STONE);
        GameRegistry.register(this);
        ItemBlock ib = new ItemBlock(this);
        ib.setRegistryName(this.getRegistryName());
        GameRegistry.register(ib);
        if (side == Side.CLIENT)
            ModelLoader.setCustomModelResourceLocation(ib, 0, new ModelResourceLocation("tesitems:" + name));
    }

    protected Item getSeed() {
        return crop;
    }

    protected Item getCrop() {
        return crop;
    }

    public AxisAlignedBB getBoundingBox(IBlockState p_getBoundingBox_1_, IBlockAccess p_getBoundingBox_2_, BlockPos p_getBoundingBox_3_) {
        return FULL_BLOCK_AABB;
    }
    protected boolean canSustainBush(IBlockState p_canSustainBush_1_) {
        return true;
    }

    public void updateTick(World p_updateTick_1_, BlockPos p_updateTick_2_, IBlockState p_updateTick_3_, Random p_updateTick_4_) {
        super.updateTick(p_updateTick_1_, p_updateTick_2_, p_updateTick_3_, p_updateTick_4_);
        if (p_updateTick_1_.getLightFromNeighbors(p_updateTick_2_.up()) >= 9) {
            int i = this.getAge(p_updateTick_3_);
            if (i < this.getMaxAge()) {
                float f = getGrowthChance(this, p_updateTick_1_, p_updateTick_2_);
                if (ForgeHooks.onCropsGrowPre(p_updateTick_1_, p_updateTick_2_, p_updateTick_3_, p_updateTick_4_.nextInt((int)(25.0F / f) + 1) == 0)) {
                    p_updateTick_1_.setBlockState(p_updateTick_2_, this.withAge(i + 1), 2);
                    ForgeHooks.onCropsGrowPost(p_updateTick_1_, p_updateTick_2_, p_updateTick_3_, p_updateTick_1_.getBlockState(p_updateTick_2_));
                }
            }
        }

    }
    protected static float getGrowthChance(Block p_getGrowthChance_0_, World p_getGrowthChance_1_, BlockPos p_getGrowthChance_2_) {
        return 0.2f;
    }
    public boolean canBlockStay(World p_canBlockStay_1_, BlockPos p_canBlockStay_2_, IBlockState p_canBlockStay_3_) {
        return true;
    }
    public List<ItemStack> getDrops(IBlockAccess p_getDrops_1_, BlockPos p_getDrops_2_, IBlockState p_getDrops_3_, int p_getDrops_4_) {
        List<ItemStack> ret = new ArrayList();
        int age = this.getAge(p_getDrops_3_);
        Random rand = p_getDrops_1_ instanceof World ? ((World)p_getDrops_1_).rand : new Random();
        IPlayerAttributesCapability cap = TESItems.getCapability(harvesters.get());

        if (age >= this.getMaxAge() && cap.getAttribute("miner") > 1) {

            for(int i = 0; i < 2; ++i) {
                if (rand.nextInt(2 * this.getMaxAge()) <= age) {
                    ret.add(new ItemStack(this.getCrop(), 1, 0));
                }
            }
        }

        return ret;
    }
    public void dropBlockAsItemWithChance(World p_dropBlockAsItemWithChance_1_, BlockPos p_dropBlockAsItemWithChance_2_, IBlockState p_dropBlockAsItemWithChance_3_, float p_dropBlockAsItemWithChance_4_, int p_dropBlockAsItemWithChance_5_) {
        super.dropBlockAsItemWithChance(p_dropBlockAsItemWithChance_1_, p_dropBlockAsItemWithChance_2_, p_dropBlockAsItemWithChance_3_, p_dropBlockAsItemWithChance_4_, 0);
    }

    @Nullable
    public Item getItemDropped(IBlockState p_getItemDropped_1_, Random p_getItemDropped_2_, int p_getItemDropped_3_) {
        return this.isMaxAge(p_getItemDropped_1_) ? this.getCrop() : null;
    }

    public ItemStack getItem(World p_getItem_1_, BlockPos p_getItem_2_, IBlockState p_getItem_3_) {
        return new ItemStack(this.getSeed());
    }
    @Nullable
    public AxisAlignedBB getCollisionBoundingBox(IBlockState p_getCollisionBoundingBox_1_, World p_getCollisionBoundingBox_2_, BlockPos p_getCollisionBoundingBox_3_) {
        return p_getCollisionBoundingBox_1_.getBoundingBox(p_getCollisionBoundingBox_2_, p_getCollisionBoundingBox_3_);
    }
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.SOLID;
    }
    public EnumPlantType getPlantType(IBlockAccess p_getPlantType_1_, BlockPos p_getPlantType_2_) {
        return EnumPlantType.Crop;
    }
}
