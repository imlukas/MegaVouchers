package dev.imlukas.megavouchers.util.schedulerutil.data;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.function.BooleanSupplier;

@Getter
@Setter
public class ScheduleData {

    private boolean sync;
    private long ticks;
    private Runnable runnable;
    private boolean repeating;
    private int startIn = 0;
    private long cancelIn = -1;
    private BooleanSupplier cancelIf;

    private JavaPlugin plugin;

}
