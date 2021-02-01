package me.winterguardian.mobracers.item;

import java.util.Random;

import me.winterguardian.mobracers.item.types.BowItem;
import me.winterguardian.mobracers.item.rarity.ExemptionRarity;
import me.winterguardian.mobracers.item.rarity.IncreasingRarity;
import me.winterguardian.mobracers.item.rarity.InfiniteRarity;
import me.winterguardian.mobracers.item.types.BombItem;
import me.winterguardian.mobracers.item.types.CloudItem;
import me.winterguardian.mobracers.item.types.FakeItem;
import me.winterguardian.mobracers.item.types.FireballItem;
import me.winterguardian.mobracers.item.types.GrapplingHookItem;
import me.winterguardian.mobracers.item.types.GroundPoundItem;
import me.winterguardian.mobracers.item.types.MissileItem;
import me.winterguardian.mobracers.item.types.NullItem;
import me.winterguardian.mobracers.item.types.ShieldItem;
import me.winterguardian.mobracers.item.types.LeapItem;
import me.winterguardian.mobracers.item.types.SugarItem;
import me.winterguardian.mobracers.item.types.WallItem;
import me.winterguardian.mobracers.item.types.BooItem;
import me.winterguardian.mobracers.item.types.SquidItem;
import me.winterguardian.mobracers.item.types.TeleportItem;
import me.winterguardian.mobracers.item.types.SwordItem;
import me.winterguardian.mobracers.item.types.LightningItem;


public enum ItemType
{
	RANDOM(RandomItem.class, new InfiniteRarity()),

	TELEPORT(TeleportItem.class, new ExemptionRarity(1, 20)),
	MISSILE(MissileItem.class, new ExemptionRarity(1, 15)),
	BOO(BooItem.class, new ExemptionRarity(1, 20)),
	LIGHTNING(LightningItem.class, new ExemptionRarity(1, 15)),
	
	GRAPPLING_HOOK(GrapplingHookItem.class, new IncreasingRarity(false, 25, 50)),
	GROUND_POUND(GroundPoundItem.class, new IncreasingRarity(false, 0, 100)),
	
	WALL(WallItem.class, new IncreasingRarity(true, 0, 100)),
	CLOUD(CloudItem.class, new IncreasingRarity(true, 0, 50)),

	LEAP(LeapItem.class, new IncreasingRarity(false, 0, 80)),
	BOW(BowItem.class, new ExemptionRarity(1, 30)),
	SUGAR(SugarItem.class, new IncreasingRarity(false, 0, 100)),
	SHIELD(ShieldItem.class, new ExemptionRarity(1, 20)),
	SQUID(SquidItem.class, new ExemptionRarity(1, 30)),
	FAKE_ITEM(FakeItem.class, new IncreasingRarity(true, 0, 100)),
	BOMB(BombItem.class, new ExemptionRarity(60)),
	SWORD(SwordItem.class, new IncreasingRarity(true, 0, 50)),
	FIREBALL(FireballItem.class, new IncreasingRarity(true, 0, 50)),
	;
	
	private Class<? extends Item> itemClass;
	private ItemRarity rarity;
	
	ItemType(Class<? extends Item> itemClass, ItemRarity rarity)
	{
		this.itemClass = itemClass;
		this.rarity = rarity;
	}
	
	public Item getDefault()
	{
		try
		{
			return itemClass.getConstructor((Class<Object>[])null).newInstance((Object[])null);
		}
		catch (Exception e)
		{
			return new NullItem();
		}
	}
	
	public ItemRarity getItemRarity()
	{
		return this.rarity;
	}
	
	public static ItemType getRandom(int position, int players)
	{
		int total = 0;
		
		for(ItemType type : values())
			total += type.getItemRarity().getOdds(position, players);
		
		float current = 0, r = new Random().nextFloat();
		
		for(ItemType type : values())
		{
			float previous = current;
			current += type.getItemRarity().getOdds(position, players);
			if(r >= previous / total && r < current / total)
				return type;
		}
		return null;
		
	}
}
