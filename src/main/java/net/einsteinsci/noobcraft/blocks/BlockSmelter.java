package net.einsteinsci.noobcraft.blocks;

import java.util.Random;

import net.einsteinsci.noobcraft.ModMain;
import net.einsteinsci.noobcraft.gui.NoobCraftGuiHandler;
import net.einsteinsci.noobcraft.register.RegisterBlocks;
import net.einsteinsci.noobcraft.tileentity.TileEntitySmelter;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockSmelter extends BlockContainer
{
	@SideOnly(Side.CLIENT)
	private IIcon top;
	@SideOnly(Side.CLIENT)
	private IIcon front;
	
	private static boolean isLit;
	private final boolean isLit2; // strange why...
	private final Random random = new Random();
	
	public BlockSmelter(boolean lit)
	{
		super(Material.rock);
		
		if (lit)
		{
			setBlockName("smelterLit");
		}
		else
		{
			setBlockName("smelter");
			setCreativeTab(ModMain.tabNoobCraft);
		}
		
		setBlockTextureName(ModMain.MODID + ":" + getUnlocalizedName().substring(5));
		
		isLit2 = lit;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister iconregister)
	{
		blockIcon = iconregister.registerIcon(ModMain.MODID + ":smelterSide");
		front = iconregister.registerIcon(isLit2 ? ModMain.MODID + ":smelterLit" : ModMain.MODID + ":smelterUnlit");
		top = iconregister.registerIcon(ModMain.MODID + ":smelterTop");
	}
	
	@Override
	public IIcon getIcon(int side, int meta)
	{
		if (side == 1)
		{
			return top;
		}
		else
		{
			if (side == 0)
			{
				return top;
			}
			else
			{
				if (side != meta)
				{
					return blockIcon;
				}
				else
				{
					return front;
				}
			}
		}
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float clickX,
		float clickY, float clickZ)
	{
		player.openGui(ModMain.modInstance, NoobCraftGuiHandler.SMELTER_ID, world, x, y, z);
		
		return true;
	}
	
	@Override
	public Item getItemDropped(int par1, Random rand, int par3)
	{
		return Item.getItemFromBlock(RegisterBlocks.smelter);
	}
	
	@Override
	public Item getItem(World world, int x, int y, int z)
	{
		return Item.getItemFromBlock(RegisterBlocks.smelter);
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void onBlockAdded(World world, int x, int y, int z)
	{
		super.onBlockAdded(world, x, y, z);
		
		direction(world, x, y, z);
	}
	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack)
	{
		int dir = MathHelper.floor_double(entity.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
		
		if (dir == 0)
		{
			world.setBlockMetadataWithNotify(x, y, z, 2, 2);
		}
		
		if (dir == 1)
		{
			world.setBlockMetadataWithNotify(x, y, z, 5, 2);
		}
		
		if (dir == 2)
		{
			world.setBlockMetadataWithNotify(x, y, z, 3, 2);
		}
		
		if (dir == 3)
		{
			world.setBlockMetadataWithNotify(x, y, z, 4, 2);
		}
		
		if (stack.hasDisplayName())
		{
			((TileEntitySmelter)world.getTileEntity(x, y, z)).furnaceName(stack.getDisplayName());
		}
	}
	
	private void direction(World world, int x, int y, int z)
	{
		if (!world.isRemote)
		{
			Block direction = world.getBlock(x, y, z - 1);
			Block direction1 = world.getBlock(x, y, z + 1);
			Block direction2 = world.getBlock(x - 1, y, z);
			Block direction3 = world.getBlock(x + 1, y, z);
			byte byte0 = 3;
			
			if (direction.func_149730_j() && !direction1.func_149730_j())
			{
				byte0 = 3;
			}
			
			if (direction1.func_149730_j() && !direction.func_149730_j())
			{
				byte0 = 2;
			}
			
			if (direction2.func_149730_j() && !direction3.func_149730_j())
			{
				byte0 = 5;
			}
			
			if (direction3.func_149730_j() && !direction2.func_149730_j())
			{
				byte0 = 4;
			}
			
			world.setBlockMetadataWithNotify(x, y, z, byte0, 2);
		}
	}
	
	public static void updateBlockState(boolean lit, World world, int x, int y, int z)
	{
		int dir = world.getBlockMetadata(x, y, z);
		TileEntity tileEntity = world.getTileEntity(x, y, z);
		isLit = true;
		
		if (lit)
		{
			world.setBlock(x, y, z, RegisterBlocks.smelterLit);
		}
		else
		{
			world.setBlock(x, y, z, RegisterBlocks.smelter);
		}
		
		isLit = false;
		world.setBlockMetadataWithNotify(x, y, z, dir, 2);
		
		if (tileEntity != null)
		{
			tileEntity.validate();
			world.setTileEntity(x, y, z, tileEntity);
		}
	}
	
	// Drop stuff everywhere
	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int meta)
	{
		if (!isLit)
		{
			TileEntitySmelter smelter = (TileEntitySmelter)world.getTileEntity(x, y, z);
			if (smelter != null)
			{
				for (int i = 0; i < smelter.getSizeInventory(); i++)
				{
					ItemStack stack = smelter.getStackInSlot(i);
					
					if (stack != null)
					{
						float velX = random.nextFloat() * 0.6f + 0.1f;
						float velY = random.nextFloat() * 0.6f + 0.1f;
						float velZ = random.nextFloat() * 0.6f + 0.1f;
						
						while (stack.stackSize > 0)
						{
							int j = random.nextInt(21) + 10;
							
							j = Math.min(j, stack.stackSize);
							
							stack.stackSize -= j;
							EntityItem entityItem =
								new EntityItem(world, x + velX, y + velY, z + velZ, new ItemStack(stack.getItem(), j,
									stack.getItemDamage()));
							
							if (stack.hasTagCompound())
							{
								entityItem.getEntityItem()
								.setTagCompound((NBTTagCompound)stack.getTagCompound().copy());
							}
							
							float f3 = 0.025f;
							entityItem.motionX = (float)random.nextGaussian() * f3;
							entityItem.motionY = (float)random.nextGaussian() * f3 + 0.1f;
							entityItem.motionX = (float)random.nextGaussian() * f3;
							world.spawnEntityInWorld(entityItem);
						}
					}
				}
				
				world.func_147453_f(x, y, z, block);
			}
		}
		super.breakBlock(world, x, y, z, block, meta);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World world, int x, int y, int z, Random random)
	{
		if (isLit2)
		{
			int dir = world.getBlockMetadata(x, y, z);
			
			float f = x + 0.5f;
			float f1 = y + random.nextFloat() * 6.0f / 16.0f;
			float f2 = z + 0.5f;
			
			float f3 = 0.5f;
			float f4 = random.nextFloat() * 0.6f - 0.2f;
			
			if (dir == 4)
			{
				world.spawnParticle("smoke", f - f3, f1, f2 + f4, 0.0F, 0.0F, 0.0F);
				world.spawnParticle("flame", f - f3, f1, f2 + f4, 0.0F, 0.0F, 0.0F);
			}
			else if (dir == 5)
			{
				world.spawnParticle("smoke", f + f3, f1, f2 + f4, 0.0F, 0.0F, 0.0F);
				world.spawnParticle("flame", f + f3, f1, f2 + f4, 0.0F, 0.0F, 0.0F);
			}
			else if (dir == 2)
			{
				world.spawnParticle("smoke", f + f4, f1, f2 - f3, 0.0F, 0.0F, 0.0F);
				world.spawnParticle("flame", f + f4, f1, f2 - f3, 0.0F, 0.0F, 0.0F);
			}
			else if (dir == 3)
			{
				world.spawnParticle("smoke", f + f4, f1, f2 + f3, 0.0F, 0.0F, 0.0F);
				world.spawnParticle("flame", f + f4, f1, f2 + f3, 0.0F, 0.0F, 0.0F);
			}
		}
	}
	
	@Override
	public TileEntity createNewTileEntity(World world, int par2)
	{
		return new TileEntitySmelter();
	}
}