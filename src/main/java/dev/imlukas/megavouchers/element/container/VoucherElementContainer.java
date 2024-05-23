package dev.imlukas.megavouchers.element.container;

import dev.imlukas.megavouchers.element.VoucherElement;
import dev.imlukas.megavouchers.element.impl.generic.impl.conditions.ConditionalVoucherElement;
import dev.imlukas.megavouchers.element.impl.generic.impl.conditions.type.ConditionalType;
import dev.imlukas.megavouchers.element.priority.VoucherElementPriority;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VoucherElementContainer {

    private final List<ConditionalVoucherElement> conditionals = new ArrayList<>();
    private final Map<VoucherElementPriority, List<VoucherElement>> elements = new HashMap<>();

    public VoucherElementContainer(List<VoucherElement> elements) {
        elements.forEach(this::addElement);
    }

    /**
     * Returns all elements present in this voucher
     *
     * @return all elements
     */
    public List<VoucherElement> getAllElements() {
        List<VoucherElement> elements = getElements();
        elements.addAll(getConditionals());
        return elements;
    }

    /**
     * Returns all conditionals present in this voucher
     *
     * @return all conditionals
     */
    public List<ConditionalVoucherElement> getConditionals() {
        return List.copyOf(conditionals);
    }

    /**
     * Returns all elements present in this voucher with the given priority, excludes conditionals
     *
     * @param priority the priority to get the elements for
     */
    public List<VoucherElement> getElements(VoucherElementPriority priority) {
        return List.copyOf(elements.getOrDefault(priority, new ArrayList<>()));
    }

    /**
     * Returns all elements present in this voucher, excludes conditionals
     *
     * @return all elements
     */
    public List<VoucherElement> getElements() {
        List<VoucherElement> elements = new ArrayList<>();

        for (List<VoucherElement> elementList : this.elements.values()) {
            elements.addAll(List.copyOf(elementList));
        }

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
     *
     * @param player the player to check the conditionals for
     * @param type   the type of the conditionals to check
     * @return true if all conditionals are met, false otherwise
     */
    public boolean meetsConditions(Player player, ConditionalType type) {
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
     *
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
