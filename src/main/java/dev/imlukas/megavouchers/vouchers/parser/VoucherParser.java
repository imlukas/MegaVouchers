package dev.imlukas.megavouchers.vouchers.parser;

import dev.imlukas.megavouchers.MegaVouchersPlugin;
import dev.imlukas.megavouchers.util.file.YMLBase;
import dev.imlukas.megavouchers.util.item.parser.ItemParser;
import dev.imlukas.megavouchers.util.pdc.ItemPDCWrapper;
import dev.imlukas.megavouchers.vouchers.Voucher;
import dev.imlukas.megavouchers.element.VoucherElements;
import dev.imlukas.megavouchers.element.generic.registry.VoucherElementRegistry;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.File;

public class VoucherParser {

    private final MegaVouchersPlugin plugin;
    private final VoucherElementRegistry elementRegistry;

    public VoucherParser(MegaVouchersPlugin plugin) {
        this.plugin = plugin;
        this.elementRegistry = plugin.getElementRegistry();
    }

    public Voucher parse(File voucherFile) {
        // TODO: Write parser. Need to make configuration file first.
        YMLBase ymlBase = new YMLBase(plugin, voucherFile, true);
        FileConfiguration config = ymlBase.getConfiguration();


        ConfigurationSection itemSection = config.getConfigurationSection("item");
        ItemStack displayItem = ItemParser.from(itemSection);


        ConfigurationSection elementsSection = config.getConfigurationSection("elements");

        VoucherElements elements = parseElements(elementsSection);
        return null;
    }


    public VoucherElements parseElements(ConfigurationSection elementsSection) {
        for (String element : elementsSection.getKeys(false)) {
            return null;
        }

        return null;
    }
}
