package dev.imlukas.megavouchers.menu;

import dev.imlukas.megavouchers.MegaVouchersPlugin;
import dev.imlukas.megavouchers.util.item.ItemUtil;
import dev.imlukas.megavouchers.util.menu.element.buttons.button.Button;
import dev.imlukas.megavouchers.util.menu.element.buttons.custom.NextPageButton;
import dev.imlukas.megavouchers.util.menu.element.buttons.custom.PreviousPageButton;
import dev.imlukas.megavouchers.util.menu.layer.pagination.PaginableArea;
import dev.imlukas.megavouchers.util.menu.layer.pagination.PaginableLayer;
import dev.imlukas.megavouchers.util.menu.template.Menu;
import dev.imlukas.megavouchers.vouchers.Voucher;
import dev.imlukas.megavouchers.vouchers.registry.VoucherRegistry;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class VoucherListMenu extends Menu {

    private final ConfigurationSection configuration;
    private final VoucherRegistry registry;
    private final String leftClickLore, rightClickLore;

    public VoucherListMenu(MegaVouchersPlugin plugin, Player viewer) {
        super(plugin, viewer);
        this.configuration = applicator.getConfig();
        this.leftClickLore = configuration.getString("lores.left-click", "<gray>Left-click to get <yellow>1x");
        this.rightClickLore = configuration.getString("lores.right-click", "<gray>Right-click to get <yellow>5x");
        this.registry = plugin.getVoucherRegistry();
    }

    @Override
    public void setup() {
        Player viewer = getViewer();

        PaginableArea area = new PaginableArea(mask.selection("."));
        PaginableLayer paginableLayer = new PaginableLayer(menu, area);

        menu.addRenderable(paginableLayer);
        for (Map.Entry<String, Voucher> voucherEntry : registry.entrySet()) {
            Voucher voucher = voucherEntry.getValue();
            ItemStack displayItem = voucher.getDisplayItem().clone();
            ItemUtil.addLore(displayItem, leftClickLore);
            ItemUtil.addLore(displayItem, rightClickLore);
            Button voucherButton = new Button(displayItem);

            voucherButton.onLeftClick(() -> voucher.forceGive(viewer, 1));
            voucherButton.onRightClick(() -> voucher.forceGive(viewer, 5));

            area.addElement(voucherButton);
        }

        NextPageButton nextPage = new NextPageButton(applicator.getItem("n"), paginableLayer);
        PreviousPageButton previousPage = new PreviousPageButton(applicator.getItem("p"), paginableLayer);

        applicator.registerButton("c", this::close);
        applicator.registerElement("n", nextPage);
        applicator.registerElement("p", previousPage);

        menu.forceUpdate();
    }

    @Override
    public String getIdentifier() {
        return "voucher-list";
    }
}
