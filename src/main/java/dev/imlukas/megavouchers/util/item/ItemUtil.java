package dev.imlukas.megavouchers.util.item;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;
import dev.imlukas.megavouchers.util.text.TextUtils;
import dev.imlukas.megavouchers.util.text.placeholder.Placeholder;
import net.kyori.adventure.text.Component;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.*;

public final class ItemUtil {

    private ItemUtil() {
    }

    public static void addLore(ItemStack item, List<String> toAdd) {
        addLore(item, toAdd.toArray(new String[0]));
    }

    public static void addLore(ItemStack item, String... toAdd) {
        ItemMeta meta = item.getItemMeta();
        List<Component> lore = meta.lore();

        Component[] toAddComponents = new Component[toAdd.length];

        for (int i = 0; i < toAdd.length; i++) {
            toAddComponents[i] = TextUtils.color(toAdd[i]);
        }

        if (lore == null) {
            lore = new ArrayList<>();
        }

        Collections.addAll(lore, toAddComponents);
        meta.lore(lore);
        item.setItemMeta(meta);
    }

    public static void setModelData(ItemStack item, int modelData) {
        ItemMeta meta = item.getItemMeta();
        meta.setCustomModelData(modelData);
        item.setItemMeta(meta);
    }

    public static <T> void replacePlaceholder(ItemStack item, T replacementObject,
                                              Collection<Placeholder<T>> placeholderCollection) {
        if (item == null || item.getItemMeta() == null) {
            return;
        }

        if (placeholderCollection == null || placeholderCollection.isEmpty()) {
            return;
        }

        Placeholder<T>[] placeholders = new Placeholder[placeholderCollection.size()];

        int index = 0;
        for (Placeholder<T> placeholder : placeholderCollection) {
            if (placeholder == null) {
                continue;
            }

            placeholders[index++] = placeholder;
        }

        // shrink array to fit
        if (index != placeholders.length) {
            Placeholder<T>[] newPlaceholders = new Placeholder[index];
            System.arraycopy(placeholders, 0, newPlaceholders, 0, index);
            placeholders = newPlaceholders;
        }

        replacePlaceholder(item, replacementObject, placeholders);
    }

    @SafeVarargs
    public static synchronized <T> void replacePlaceholder(ItemStack item, T replacementObject,
                                                           Placeholder<T>... placeholder) {
        if (item == null) {
            return;
        }

        ItemMeta meta = item.getItemMeta();

        if (meta == null) {
            return;
        }

        Component displayName = meta.displayName();

        if (displayName != null) {
            for (Placeholder<T> componentPlaceholder : placeholder) {
                displayName = componentPlaceholder.replace(displayName, replacementObject);
            }

            meta.displayName(displayName);
        }

        List<Component> lore = meta.lore();

        if (lore != null) {
            for (int i = 0; i < lore.size(); i++) {
                Component line = lore.get(i);

                for (Placeholder<T> componentPlaceholder : placeholder) {
                    line = componentPlaceholder.replace(line, replacementObject);
                }

                lore.set(i, line);
            }

            meta.lore(lore);
        }

        if (meta instanceof SkullMeta skullMeta) {

            boolean replaceProfile = true;
            if (skullMeta.hasOwner()) {
                String owner = skullMeta.getOwner();

                for (Placeholder<T> componentPlaceholder : placeholder) {
                    String oldOwner = owner;
                    owner = componentPlaceholder.replace(owner, replacementObject);

                    if (owner != null && !owner.equals(oldOwner)) {
                        skullMeta.setOwner(owner);
                        replaceProfile = false;
                    }
                }

            }

            if (replaceProfile) {
                try {

                    Field profileField = skullMeta.getClass().getDeclaredField("profile");
                    profileField.setAccessible(true);
                    GameProfile profile = (GameProfile) profileField.get(skullMeta);
                    profileField.setAccessible(false);

                    if (profile != null) {
                        PropertyMap propertyMap = profile.getProperties();
                        Collection<Property> properties = new HashSet<>(
                                propertyMap.get("textures")
                        );

                        propertyMap.removeAll("properties");

                        for (Property property : properties) {
                            String value = property.getName();
                            String signature = property.getSignature();

                            for (Placeholder<T> componentPlaceholder : placeholder) {
                                value = componentPlaceholder.replace(value, replacementObject);
                                signature = componentPlaceholder.replace(signature, replacementObject);
                            }

                            propertyMap.put("textures", new Property("textures", value, null));
                            break;
                        }

                    }
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }


        }
        item.setItemMeta(meta);
    }

}
