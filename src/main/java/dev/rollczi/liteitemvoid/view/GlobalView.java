package dev.rollczi.liteitemvoid.view;

import org.bukkit.entity.Player;

import java.util.Set;

public interface GlobalView<M extends ViewModel> {

    void update(M model);

    void refresh();

    Set<Player> closeAll();

    void show(Player player);

    void markClosed(Player player);

}
