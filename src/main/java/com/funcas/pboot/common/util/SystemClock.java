/**
 * Copyright (c) 2011-2020, hubin (jobob@qq.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.funcas.pboot.common.util;

import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import java.sql.Timestamp;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * <p>
 * 高并发场景下System.currentTimeMillis()的性能问题的优化
 * </p>
 * <p>
 * System.currentTimeMillis()的调用比new一个普通对象要耗时的多（具体耗时高出多少我还没测试过，有人说是100倍左右）<br>
 * System.currentTimeMillis()之所以慢是因为去跟系统打了一次交道<br>
 * 后台定时更新时钟，JVM退出时，线程自动回收<br>
 * 10亿：43410,206,210.72815533980582%<br>
 * 1亿：4699,29,162.0344827586207%<br>
 * 1000万：480,12,40.0%<br>
 * 100万：50,10,5.0%<br>
 * </p>
 *
 * @author hubin
 * @Date 2016-08-01
 */
public class SystemClock {

    private final long period;
    private final AtomicLong now;

    private SystemClock(long period) {
        this.period = period;
        this.now = new AtomicLong(System.currentTimeMillis());
        scheduleClockUpdating();
    }

    private static class InstanceHolder {
        public static final SystemClock INSTANCE = new SystemClock(1);
    }

    private static SystemClock instance() {
        return InstanceHolder.INSTANCE;
    }

    private void scheduleClockUpdating() {
        ScheduledExecutorService scheduler = new ScheduledThreadPoolExecutor(5,
                new BasicThreadFactory.Builder().namingPattern("System Clock").daemon(true).build());
        scheduler.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                now.set(System.currentTimeMillis());
            }
        }, period, period, TimeUnit.MILLISECONDS);
    }

    private long currentTimeMillis() {
        return now.get();
    }

    public static long now() {

        return instance().currentTimeMillis();
    }
    
	public static String nowDate() {
		return new Timestamp(instance().currentTimeMillis()).toString();
	}

}
