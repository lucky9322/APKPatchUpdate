# 增量更新lib-How To Use

1. 主工程项目完成依赖此module
2. 调用如下方法

```
 //并行任务
                new BsPatchWorker(MainActivity.this, new BsPatchWorker.InstallCallback() {
                    @Override
                    public void patchResult(boolean result) {

                    }
                }).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
```