package com.jxdinfo.hussar.util.bigdataexcel;

import com.baomidou.mybatisplus.toolkit.IdWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author wenqingkuan
 * @date 2019-10-21 11:28
 */
public class SimpleThreadPool extends BaseThreadPool {

    private static Logger LOGGER = LoggerFactory.getLogger(SimpleThreadPool.class);

    private int permins;
    private ArrayBlockingQueue<String> queue;
    private AtomicInteger total;// 线程总数
    private AtomicInteger core;// 核心池中的线程数
    private AtomicInteger wait;// 等待数

    public SimpleThreadPool(int permits){
        super(permits);
    }

    @Override
    protected void afterConstructor(int permits) {
        this.permins=permits;
    }


    @Override
    protected void init() {
        queue = new ArrayBlockingQueue<String>(100);
        total = new AtomicInteger(0);
        core = new AtomicInteger(0);
        wait = new AtomicInteger(0);
    }

    @Override
    protected void afterInitThread() {
        total.addAndGet(1);
        wait.addAndGet(1);
        String threadId = IdWorker.get32UUID();
        Thread.currentThread().setName(threadId);
        LOGGER.debug("线程["+threadId+"]初始化完成");
    }

    @Override
    protected void beforeExecute() {
        String name = Thread.currentThread().getName();
        LOGGER.debug("线程["+name+"]进入核心池...");
        wait.addAndGet(-1);
        core.addAndGet(1);
        String uuid = IdWorker.get32UUID();
        try {
            queue.put(uuid);
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void afterExecute() {
        core.addAndGet(-1);
    }

    @Override
    protected void exeception(Exception e) {
        throw new RuntimeException(e);
    }

    @Override
    protected void finallz() {
        String poll = queue.poll();
        String threadId = Thread.currentThread().getName();
        LOGGER.debug("线程["+threadId+"]出去了");
    }

    public void callback(Callback callback){
        callback.callback(total.get(),core.get(),wait.get());
    }

    /**
     * 获取总数,调用此方法,程序会进入500毫秒等待,然后判定是否有等待线程,如果有则递归,如果没有则返回
     * @return
     */
    public int getTotal() throws InterruptedException {
        Thread.currentThread().sleep(1500);
        if (queue.isEmpty()) {
            return total.get();
        } else{
            return getTotal();
        }
    }

    public interface Callback{
        void callback(int total,int core,int wait);
    }
}
