package me.winterguardian.mobracers.vehicle.types;
import me.winterguardian.core.util.SoundEffect;
import me.winterguardian.mobracers.CourseMessage;
import me.winterguardian.mobracers.item.Item;
import me.winterguardian.mobracers.item.ItemType;
import me.winterguardian.mobracers.item.types.SugarItem;
import me.winterguardian.mobracers.item.types.WallItem;
import me.winterguardian.mobracers.item.types.WallItem.WallBlock;
import me.winterguardian.mobracers.vehicle.Vehicle;
import me.winterguardian.mobracers.vehicle.VehicleGUIItem;
import me.winterguardian.mobracers.vehicle.VehicleType;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;

public class SkeletonVehicle extends Vehicle
{
	
	@Override
	protected EntityType getEntityType()
	{
		return EntityType.SKELETON;
	}

	@Override
	public VehicleType getType()
	{
		return VehicleType.SKELETON;
	}

	@Override
	public boolean canChoose(Player p)
	{
		return true;
	}
	
	@Override
	public Item getItem(ItemType type)
	{
		switch(type)
		{
		case SUGAR:
			return new SugarItem(new SoundEffect(Sound.SKELETON_IDLE, 1, 1.5f));
		case WALL:
			return new WallItem(Arrays.asList(new WallBlock(Material.IRON_BLOCK, (byte)0)), new SoundEffect(Sound.SKELETON_HURT, 1, 0.8f));

		default:
			return type.getDefault();
		
		}
	}
	
	@Override
	public String getName()
	{
		return CourseMessage.VEHICLE_SKELETON_NAME.toString();
	}

	@Override
	public String getDescription()
	{
		return CourseMessage.VEHICLE_SKELETON_DESC.toString();
	}

	@Override
	public VehicleGUIItem getGUIItem()
	{
		return new VehicleGUIItem(getType(), 6, Material.MONSTER_EGG, 1, (short)51, "§7§l" + getName(), new ArrayList<String>());
	}
}
