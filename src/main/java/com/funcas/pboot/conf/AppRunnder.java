package com.funcas.pboot.conf;

import com.funcas.pboot.common.util.WordUtils;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

/**
 * @author funcas
 * @version 1.0
 * @date 2019年01月15日
 */
@Component
public class AppRunnder implements ApplicationRunner, Ordered {

    /**
     * 执行优先级
     * @return
     */
    @Override
    public int getOrder(){
        return 1;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // 在这里可以初始化一些启动后执行的逻辑块
        WordUtils.getLicense();
        System.out.println("############################################");
        System.out.println("#             Congratulations!             #");
        System.out.println("#                                          #");
        System.out.println("#        System start-up successfully      #");
        System.out.println("#               enjoy yourself             #");
        System.out.println("#                                          #");
        System.out.println("############################################");
    }
}
