package dev.rollczi.liteitemvoid.deepvoid;

import dev.rollczi.liteitemvoid.view.GlobalView;
import dev.rollczi.liteitemvoid.view.ViewModel;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class DeepVoidView implements GlobalView<DeepVoidView.Model> {

    private final DeepVoidViewSettings settings;
    private final Server server;

    private final List<DeepVoidViewPage> pages = new ArrayList<>();
    private final Map<UUID, DeepVoidViewPage> viewers = new HashMap<>();

    public DeepVoidView(DeepVoidViewSettings settings, Server server) {
        this.server = server;
        this.settings = settings;
        this.pages.add(DeepVoidViewPage.create(0, this.server, this.settings));
    }

    @Override
    public void update(DeepVoidView.Model model) {
        DeepVoidViewPage lastPage = DeepVoidViewPage.create(0, this.server, this.settings);

        this.pages.clear();
        this.pages.add(lastPage);

        for (ItemStack item : model.items) {
            if (lastPage.next(item)) {
                continue;
            }

            DeepVoidViewPage finalLastPage = lastPage;
            DeepVoidViewPage nextPage = DeepVoidViewPage.create(this.pages.size(), this.server, this.settings);

            lastPage.applyNextPage(nextPage, uuid -> this.viewers.put(uuid, nextPage));
            nextPage.applyPreviousPage(lastPage, uuid -> this.viewers.put(uuid, finalLastPage));
            nextPage.next(item);

            lastPage = nextPage;
            this.pages.add(nextPage);
        }

        for (int index = 0; index < 54; index++) {
            if (lastPage.nextNoneAction(this.settings.fill())) {
                continue;
            }

            break;
        }

        this.closeAll();
    }

    @Override
    public void refresh() {
        for (UUID uuid : this.viewers.keySet()) {
            Player player = this.server.getPlayer(uuid);

            if (player == null) {
                continue;
            }

            player.updateInventory();
        }
    }

    @Override
    public Set<Player> closeAll() {
        Set<Player> players = new HashSet<>();

        for (UUID uuid : new HashSet<>(this.viewers.keySet())) {
            Player player = this.server.getPlayer(uuid);

            if (player == null) {
                continue;
            }

            player.closeInventory();
            players.add(player);
        }

        this.viewers.clear();

        return players;
    }

    @Override
    public void show(Player player) {
        DeepVoidViewPage deepVoidPage = this.pages.get(0);

        if (deepVoidPage == null) {
            throw new IllegalStateException("Internal error");
        }

        deepVoidPage.open(player);
        this.viewers.put(player.getUniqueId(), deepVoidPage);
    }

    @Override
    public void markClosed(Player player) {
        this.viewers.remove(player.getUniqueId());
    }

    public static class Model implements ViewModel {

        private final List<ItemStack> items;

        public Model(List<ItemStack> items) {
            this.items = items;
        }

    }

}
