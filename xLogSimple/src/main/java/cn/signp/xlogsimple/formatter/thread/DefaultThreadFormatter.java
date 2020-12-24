package cn.signp.xlogsimple.formatter.thread;

/**
 * Formatted stack trace looks like:
 * <br>Thread: thread-name
 */
public class DefaultThreadFormatter implements ThreadFormatter {

    @Override
    public String format(Thread data) {
        return "Thread: " + data.getName();
    }
}

