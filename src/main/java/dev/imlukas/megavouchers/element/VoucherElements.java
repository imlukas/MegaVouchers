package dev.imlukas.megavouchers.element;

import dev.imlukas.megavouchers.element.impl.generic.impl.conditions.ConditionalVoucherElement;
import dev.imlukas.megavouchers.element.impl.generic.impl.conditions.type.ConditionalType;
import dev.imlukas.megavouchers.element.priority.VoucherElementPriority;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VoucherElements {

    private final List<ConditionalVoucherElement> conditionals = new ArrayList<>();
    private final Map<VoucherElementPriority, List<VoucherElement>> elements = new HashMap<>();

    public VoucherElements(List<VoucherElement> elements) {
        elements.forEach(this::addElement);
    }

    public List<ConditionalVoucherElement> getConditionals() {
        return List.copyOf(conditionals);
    }

    public List<VoucherElement> getElements(VoucherElementPriority priority) {
        List<VoucherElement> elements = new ArrayList<>(this.elements.get(priority));

        conditionals.forEach(conditional -> {
            if (conditional.getPriority() == priority) {
                elements.add(conditional);
            }
        });
        return elements;
    }

    public List<VoucherElement> getElements() {
        List<VoucherElement> elements = new ArrayList<>();

        for (List<VoucherElement> elementList : this.elements.values()) {
            elements.addAll(List.copyOf(elementList));
        }

        elements.addAll(conditionals);
        return elements;
    }


    public void addElement(VoucherElement element) {
        if (element instanceof ConditionalVoucherElement conditionalElement) {
            conditionals.add(conditionalElement);
            return;
        }

        elements.computeIfAbsent(element.getPriority(), k -> new ArrayList<>()).add(element);
    }

    public void removeElement(VoucherElement element) {
        elements.computeIfPresent(element.getPriority(), (k, v) -> {
            v.remove(element);
            return v;
        });
    }

    /**
     * Runs all conditionals of the given type
     * @param player the player to check the conditionals for
     * @param type the type of the conditionals to check
     * @return true if all conditionals are met, false otherwise
     */
    public boolean runConditionals(Player player, ConditionalType type) {
        for (ConditionalVoucherElement conditional : conditionals) {
            if (conditional.getType() != type) {
                continue;
            }

            if (!conditional.meetsRequirements(player)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Runs all elements in the given priority
     * @param player the player to run the elements for
     */
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
