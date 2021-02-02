package me.winterguardian.core.entity.custom;

import me.winterguardian.core.DynamicComponent;
import me.winterguardian.core.entity.custom.CustomEntityType;
import org.bukkit.plugin.Plugin;

public class CustomEntityManager extends DynamicComponent
{
	@Override
	protected void register(Plugin plugin)
	{
		CustomEntityType.registerEntities();
	}

	@Override
	protected void unregister(Plugin plugin)
	{
		if(!this.isEnabled())
			CustomEntityType.unregisterEntities();
	}
}
