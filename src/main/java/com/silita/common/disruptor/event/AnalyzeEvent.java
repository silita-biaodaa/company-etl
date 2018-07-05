package com.silita.common.disruptor.event;

import com.silita.model.Test;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by zhangxiahui on 18/3/13.
 */
@Getter
@Setter
public class AnalyzeEvent {
    private Test test;
}
