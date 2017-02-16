package io.github.yylyingy.yiji.tools;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Yangyl on 2017/2/16.
 */

public class ThreadPoolTool {
    private static ExecutorService service = Executors.newCachedThreadPool();
    public static void exeTask(Runnable task){
        service.execute(task);
    }
}
