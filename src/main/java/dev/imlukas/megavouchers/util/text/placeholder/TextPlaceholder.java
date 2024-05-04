package dev.imlukas.megavouchers.util.text.placeholder;

import net.kyori.adventure.text.Component;
import org.intellij.lang.annotations.RegExp;

import java.util.function.Function;

public abstract class TextPlaceholder<I, Audience> {

    protected static String OPEN_CHAR = "%";
    protected static String CLOSE_CHAR = "%";
    protected final Function<Audience, String> replacement;
    @RegExp
    protected String placeholder;

    /**
     * Creates a new placeholder with a replacement function.
     *
     * @param placeholder The placeholder to replace
     * @param replacement The function to replace the placeholder with
     */
    protected TextPlaceholder(@RegExp String placeholder, Function<Audience, String> replacement) {
        this.placeholder = placeholder;
        this.replacement = replacement;
    }

    public static Placeholder<?> placeholderOf(@RegExp String placeholder, Component replacement) {
        return new Placeholder<>(placeholder, replacement);
    }

    public String getPlaceholder() {
        return placeholder;
    }

    public Function<Audience, String> getReplacement() {
        return replacement;
    }

    public abstract I replace(I text, Audience audience);
}
