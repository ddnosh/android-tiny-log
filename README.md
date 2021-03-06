# android-tiny-log
[![Download](https://api.bintray.com/packages/ddnosh/maven/tinylog/images/download.svg) ](https://bintray.com/ddnosh/maven/tinylog/_latestVersion)  
A tiny log library for android

![在这里插入图片描述](https://github.com/ddnosh/githubusercontent/blob/master/image/android-tiny-log-readme.png?raw=true)

### Solution
1. we will use Android Log API as "Log.d(TAG, content)" finally,
and actually we should think how to generate tag and content better.
2. Android Log API is a static method, and be invoked frequently.
so we should not new objects frequently to invoke like that.
3. easy to use, and more configurations to choose.

### Function
1. switch to log to console or not;
2. switch to log to file or not;
3. click the log to jump to the code position automatically;
4. log to file by customized size;
5. output content can be encrypted;

### Technology
1. Desing Pattern
    1. Singleton Pattern
2. Skill Point
    1. ExecutorService
    2. synchronized
    3. new Throwable()

### Usage
1. initialize
TinyLog.config().setEnable(BuildConfig.DEBUG).setWritable(true).setLogPath(getLogDir()).setFileSize(1).setEncrypt("1234567812345678").setLogCallBack(mLogCallBack).apply();
You can init TinyLog like that, and you can choose enable, writable, logpath, filesize, encrypt and callback or not;
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
4. multi process support;
