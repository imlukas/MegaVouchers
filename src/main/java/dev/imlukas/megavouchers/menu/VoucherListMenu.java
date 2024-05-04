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
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class VoucherListMenu extends Menu {

    private final VoucherRegistry registry;

    public VoucherListMenu(MegaVouchersPlugin plugin, Player viewer) {
        super(plugin, viewer);
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
            ItemUtil.addLore(displayItem, "<gray>[Left Click] to get 1x");
            ItemUtil.addLore(displayItem, "<gray>[Right Click] to get 5x");
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
