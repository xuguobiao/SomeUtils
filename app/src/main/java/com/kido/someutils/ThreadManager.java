package com.kido.someutils;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程统一管理类
 *
 * @author Kido
 * @email everlastxgb@gmail.com
 * @create_time 2016/5/25 20:29
 */
public class ThreadManager {

    private static final int DEFAULT_CORE_POOL_SIZE = 4;
    private static final int DEFAULT_MAX_POOL_SIZE = 8;
    private static final long KEEP_ALIVE_TIME_SECOND = 30L;

    private static final int THREAD_QUEUE_CAPACITY = 50;

    private volatile static ThreadManager sInstance = null;
    protected ThreadPoolExecutor mExecutorService = null;

    public static ThreadManager getInstance() {
        if (sInstance == null) {
            synchronized (ThreadManager.class) {
                if (sInstance == null) {
                    sInstance = new ThreadManager();
                }
            }
        }
        return sInstance;
    }

    private ThreadManager() {
        mExecutorService = new ThreadPoolExecutor(DEFAULT_CORE_POOL_SIZE,
                DEFAULT_MAX_POOL_SIZE,
                KEEP_ALIVE_TIME_SECOND,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(THREAD_QUEUE_CAPACITY),
                new ThreadPoolExecutor.DiscardOldestPolicy());
    }

    /**
     * 方便用于AsyncTask中替换默认串行线程池
     */
    public ThreadPoolExecutor getExecutorService() {
        return mExecutorService;
    }

    /**
     * 执行线程
     */
    public void execute(Runnable runnable) {
        mExecutorService.execute(runnable);
    }


    /**
     * 移除等待线程
     */
    public void remove(Runnable runnable) {
        mExecutorService.remove(runnable);
    }

    /**
     * 清除所有未执行线程
     */
    public void clearAll() {
        mExecutorService.getQueue().clear();
    }

    /**
     * 关闭线程池
     */
    public void close() {
        clearAll();
        mExecutorService.shutdownNow();
    }
}
