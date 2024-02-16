package dev.imlukas.megavouchers.util.schedulerutil.builders;

import dev.imlukas.megavouchers.util.schedulerutil.data.ScheduleBuilderBase;
import dev.imlukas.megavouchers.util.schedulerutil.data.ScheduleData;
import dev.imlukas.megavouchers.util.schedulerutil.data.ScheduleThread;
import lombok.Getter;

@Getter
public class ScheduleBuilderT2 implements ScheduleBuilderBase {

    private final ScheduleData data;

    ScheduleBuilderT2(ScheduleData data) {
        this.data = data;
    }

    public ScheduleThread run(Runnable runnable) {
        data.setRunnable(runnable);
        return new ScheduleThread(data);
    }

}
