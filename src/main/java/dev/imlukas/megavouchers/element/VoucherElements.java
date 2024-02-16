package dev.imlukas.megavouchers.element;

import dev.imlukas.megavouchers.element.VoucherElement;
import dev.imlukas.megavouchers.element.priority.VoucherElementPriority;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VoucherElements {

    private final Map<VoucherElementPriority, List<VoucherElement>> elements = new HashMap<>();

    public VoucherElements(List<VoucherElement> elements) {
        elements.forEach(this::addElement);
    }

    public List<VoucherElement> getElements(VoucherElementPriority priority) {
        return elements.get(priority);
    }

    public List<VoucherElement> getElements() {
        List<VoucherElement> elements = new ArrayList<>();

        for (List<VoucherElement> elementList : this.elements.values()) {
            elements.addAll(List.copyOf(elementList));
        }

        return elements;
    }


    public void addElement(VoucherElement element) {
        elements.computeIfAbsent(element.getPriority(), k -> new ArrayList<>()).add(element);
    }

    public void removeElement(VoucherElement element) {
        elements.computeIfPresent(element.getPriority(), (k, v) -> {
            v.remove(element);
            return v;
        });
    }

    public void runElements(Player player) {
        for (VoucherElementPriority priority : VoucherElementPriority.values()) {
            List<VoucherElement> elements = getElements(priority);

            if (elements == null) {
                continue;
            }

            for (VoucherElement element : elements) {
                element.execute(player);
            }
        }
    }
}
