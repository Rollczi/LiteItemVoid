package dev.rollczi.liteitemvoid.deepvoid;

import net.kyori.adventure.text.Component;
import org.bukkit.inventory.ItemStack;

public interface DeepVoidViewSettings {

    Component title();

    ItemStack corners();

    ItemStack fill();

    ItemStack nextPage();

    ItemStack previousPage();

}
