package dev.imlukas.megavouchers.util.schedulerutil.builders;

import dev.imlukas.megavouchers.util.schedulerutil.data.ScheduleBuilderBase;
import dev.imlukas.megavouchers.util.schedulerutil.data.ScheduleData;
import dev.imlukas.megavouchers.util.schedulerutil.data.ScheduleThread;
import dev.imlukas.megavouchers.util.schedulerutil.data.ScheduleTimestamp;
import lombok.Getter;

@Getter
public class RepeatableBuilder extends ScheduleThread implements ScheduleBuilderBase {

    private final ScheduleData data;


    RepeatableBuilder(ScheduleData data) {
        super(data);
        this.data = data;
    }

    public ScheduleTimestamp<ScheduleThread> during(long amount) {
        return new ScheduleTimestamp<>(new ScheduleThread(data), amount, data::setCancelIn);
    }
}
