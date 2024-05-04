package dev.imlukas.megavouchers.vouchers.parser;

import dev.imlukas.megavouchers.MegaVouchersPlugin;
import dev.imlukas.megavouchers.element.VoucherElement;
import dev.imlukas.megavouchers.element.container.VoucherElementContainer;
import dev.imlukas.megavouchers.element.impl.generic.registry.ElementProviderRegistry;
import dev.imlukas.megavouchers.util.file.YMLBase;
import dev.imlukas.megavouchers.util.io.IOUtils;
import dev.imlukas.megavouchers.util.item.parser.ItemParser;
import dev.imlukas.megavouchers.vouchers.ConfigurableVoucher;
import dev.imlukas.megavouchers.vouchers.Voucher;
import dev.imlukas.megavouchers.vouchers.data.VoucherData;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static dev.imlukas.megavouchers.ManagedJavaPlugin.getLog;

public class VoucherParser {

    private final MegaVouchersPlugin plugin;
    private final ElementProviderRegistry elementRegistry;

    public VoucherParser(MegaVouchersPlugin plugin) {
        this.plugin = plugin;
        this.elementRegistry = plugin.getElementRegistry();
    }

    public List<Voucher> parseAll() {
        List<Voucher> vouchers = new ArrayList<>();
        File folder = new File(plugin.getDataFolder(), "vouchers");

        IOUtils.traverseAndLoad(folder, file -> {
            Voucher voucher = parse(file);

            if (voucher == null) {
                getLog().warning("Failed to parse voucher " + file.getName());
                return;
            }

            getLog().info("Parsed voucher " + voucher.getIdentifier());
            vouchers.add(voucher);
        });

        return vouchers;
    }

    private Voucher parse(File voucherFile) {
        YMLBase ymlBase = new YMLBase(plugin, voucherFile, true);
        FileConfiguration config = ymlBase.getConfiguration();
        ConfigurationSection section = config.getConfigurationSection("voucher");

        if (section == null) {
            getLog().warning("No voucher section found in " + voucherFile.getName());
            return null;
        }

        String identifier = voucherFile.getName().replace(".yml", "");
        getLog().info("Parsing voucher: " + identifier);

        ConfigurationSection itemSection = section.getConfigurationSection("item");
        ItemStack displayItem = ItemParser.fromOrDefault(itemSection, Material.PAPER);

        ConfigurationSection elementsSection = section.getConfigurationSection("elements");

        if (elementsSection == null) {
            getLog().warning("No elements found for voucher: " + identifier);
            return null;
        }

        VoucherElementContainer elements = parseElements(elementsSection);
        getLog().info("Parsed " + elements.getAllElements().size() + " elements for voucher: " + identifier);

        VoucherData data = new VoucherData(identifier, displayItem, elements);
        return new ConfigurableVoucher(plugin, data);
    }


    private VoucherElementContainer parseElements(ConfigurationSection elementsSection) {
        List<VoucherElement> elements = new ArrayList<>();

        for (String elementId : elementsSection.getKeys(false)) {
            ConfigurationSection elementSection = elementsSection.getConfigurationSection(elementId);

            if (elementSection == null) {
                continue;
            }

            VoucherElement element = elementRegistry.tryParse(elementSection);

            if (element == null) {
                continue;
            }

            getLog().info("Parsed element " + elementId);
            elements.add(element);
        }

        return new VoucherElementContainer(elements);
    }
}
