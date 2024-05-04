package dev.imlukas.megavouchers.util.menu.element.buttons.custom;

import dev.imlukas.megavouchers.util.menu.element.buttons.button.PredicateButton;
import dev.imlukas.megavouchers.util.menu.layer.pagination.PaginableLayer;
import org.bukkit.inventory.ItemStack;

public class NextPageButton extends PredicateButton {

    public NextPageButton(ItemStack displayItem, PaginableLayer layer) {
        super(displayItem, () -> layer.getPage() < layer.getMaxPage());
        onLeftClick(layer::nextPage);
    }
}
