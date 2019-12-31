package com.jxdinfo.hussar.util.bigdataexcel;

import java.util.concurrent.Semaphore;

/**
 * @author wenqingkuan
 * @date 2019-10-21 11:27
 */
public abstract class BaseThreadPool {
    private Semaphore semaphore;

    {
        init();
    }

    /**
     * 初始化方法,此方法会在构造方法之前,属性之后执行
     */
    protected abstract void init();

    /**
     * 构造方法执行
     */
    public BaseThreadPool(){
        this(5);
    }

    /**
     * 构造方法执行
     * @param permits 并发数
     */
    public BaseThreadPool(int permits){
        if (permits<1) {
            throw new RuntimeException("并发数至少为1");
        }
        semaphore = new Semaphore(permits);
        afterConstructor(permits);
    }

    /**
     * 此为核心执行方法,
     * @param execute 回调接口,此为用户实现其核心执行内容
     */
    public void execute(Execute execute){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    afterInitThread();
                    semaphore.acquire();
                    beforeExecute();
                    execute.execute();
                    afterExecute();
                }catch (Exception e){
                    e.printStackTrace();
                    exeception(e);
                }finally {
                    semaphore.release();
                    finallz();
                }
            }
        }).start();
    }

    /**
     * 在构造方法执行之后执行此方法
     * @param permits
     */
    protected abstract void afterConstructor(int permits);

    /**
     * 当线程初始化完成,但是还没来得及获取线程锁的时候,执行此方法
     */
    protected abstract void afterInitThread();

    /**
     * 在业务代码执行之前执行此方法
     */
    protected abstract void beforeExecute();

    /**
     * 在实际业务代码执行之后,执行此方法
     */
    protected abstract void afterExecute();

    /**
     * 当出异常时执行此方法
     */
    protected abstract void exeception(Exception e);

    /**
     * 当整个执行业务结束,不论是否出异常,都会执行此方法
     */
    protected abstract void finallz();

    public interface Execute{
        void execute();
    }
}
