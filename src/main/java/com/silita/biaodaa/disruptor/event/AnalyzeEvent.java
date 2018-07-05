package com.silita.biaodaa.disruptor.event;

import com.silita.biaodaa.model.Test;
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
