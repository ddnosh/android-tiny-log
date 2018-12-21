# tiny-log
A tiny log library for android

### Solution
1. we will use Android Log API as "Log.d(TAG, content)" finally,
and actually we should think how to generate tag and content better.
2. Android Log API is a static methoid, and be invoked frequently.
so we should not new objects frequently to invoke like that.
3. easy to use, and more configurations to choose.

### Technology
1. log to file with multiple threads;

### Function
1. switch to log to console or not;
2. switch to log to file or not;
3. click the log to jump to the code position automatically;

### TODO
1. save as Json format;
2. save as Xml format;
3. add thread name and thread id to content output;
4. save log to customized file;
5. save log to file by customized file size;
6. use thread pool when log to file;
