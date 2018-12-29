# tiny-log
A tiny log library for android

![在这里插入图片描述](https://img-blog.csdnimg.cn/20181223135243751.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2Rkbm9zaA==,size_16,color_FFFFFF,t_70)

### Solution
1. we will use Android Log API as "Log.d(TAG, content)" finally,
and actually we should think how to generate tag and content better.
2. Android Log API is a static methoid, and be invoked frequently.
so we should not new objects frequently to invoke like that.
3. easy to use, and more configurations to choose.

### Function
1. switch to log to console or not;
2. switch to log to file or not;
3. click the log to jump to the code position automatically;
4. log to file by size;

### Technology
1. Desing Pattern
    1. static
2. Skill Point
    1. ExecutorService
    2. synchronized
    3. new Throwable()

### Usage
1. initialize
TinyLog.config().setEnable(BuildConfig.DEBUG).setWritable(true).setLogPath(getLogDir()).setFileSize(1).setLogCallBack(mLogCallBack).apply();  
You can init TinyLog like that, and you can choose enable, writable, logpath, filesize and callback or not;
if you don't set, they also have default value.

2. api
    1. TinyLog.v/d/i/w/e("content");
    2. TinyLog.v/d/i/w/e("tag", "content");
    3. TinyLog.v/d/i/w/e("tag", "content", "arg1");
    4. TinyLog.e("content", e, "arg1")
    5. TinyLog.e("tag", "content", e, "arg1")

### TODO
1. save as Json format;
2. save as Xml format;
3. save log to customized file;
