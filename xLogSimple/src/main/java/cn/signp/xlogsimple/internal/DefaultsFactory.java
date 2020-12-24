package cn.signp.xlogsimple.internal;

/*
 * Copyright 2016 Elvis Hew
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */




import java.util.Map;

import cn.signp.xlogsimple.flattener.DefaultFlattener;
import cn.signp.xlogsimple.flattener.Flattener;
import cn.signp.xlogsimple.flattener.Flattener2;
import cn.signp.xlogsimple.formatter.border.BorderFormatter;
import cn.signp.xlogsimple.formatter.border.DefaultBorderFormatter;
import cn.signp.xlogsimple.formatter.message.json.DefaultJsonFormatter;
import cn.signp.xlogsimple.formatter.message.json.JsonFormatter;
import cn.signp.xlogsimple.formatter.message.object.ObjectFormatter;
import cn.signp.xlogsimple.formatter.message.throwable.DefaultThrowableFormatter;
import cn.signp.xlogsimple.formatter.message.throwable.ThrowableFormatter;
import cn.signp.xlogsimple.formatter.message.xml.DefaultXmlFormatter;
import cn.signp.xlogsimple.formatter.message.xml.XmlFormatter;
import cn.signp.xlogsimple.formatter.stacktrace.DefaultStackTraceFormatter;
import cn.signp.xlogsimple.formatter.stacktrace.StackTraceFormatter;
import cn.signp.xlogsimple.formatter.thread.DefaultThreadFormatter;
import cn.signp.xlogsimple.formatter.thread.ThreadFormatter;
import cn.signp.xlogsimple.printer.Printer;
import cn.signp.xlogsimple.printer.file.FilePrinter;
import cn.signp.xlogsimple.printer.file.backup.BackupStrategy;
import cn.signp.xlogsimple.printer.file.backup.FileSizeBackupStrategy;
import cn.signp.xlogsimple.printer.file.clean.CleanStrategy;
import cn.signp.xlogsimple.printer.file.clean.NeverCleanStrategy;
import cn.signp.xlogsimple.printer.file.nameing.ChangelessFileNameGenerator;
import cn.signp.xlogsimple.printer.file.nameing.FileNameGenerator;

/**
 * Factory for providing default implementation.
 */
public class DefaultsFactory {

    private static final String DEFAULT_LOG_FILE_NAME = "log";

    private static final long DEFAULT_LOG_FILE_MAX_SIZE = 1024 * 1024; // 1M bytes;

    /**
     * Create the default JSON formatter.
     */
    public static JsonFormatter createJsonFormatter() {
        return new DefaultJsonFormatter();
    }

    /**
     * Create the default XML formatter.
     */
    public static XmlFormatter createXmlFormatter() {
        return new DefaultXmlFormatter();
    }

    /**
     * Create the default throwable formatter.
     */
    public static ThrowableFormatter createThrowableFormatter() {
        return new DefaultThrowableFormatter();
    }

    /**
     * Create the default thread formatter.
     */
    public static ThreadFormatter createThreadFormatter() {
        return new DefaultThreadFormatter();
    }

    /**
     * Create the default stack trace formatter.
     */
    public static StackTraceFormatter createStackTraceFormatter() {
        return new DefaultStackTraceFormatter();
    }

    /**
     * Create the default border formatter.
     */
    public static BorderFormatter createBorderFormatter() {
        return new DefaultBorderFormatter();
    }

    /**
     * Create the default {@link Flattener}.
     */
    public static Flattener createFlattener() {
        return new DefaultFlattener();
    }

    /**
     * Create the default {@link Flattener2}.
     */
    public static Flattener2 createFlattener2() {
        return new DefaultFlattener();
    }

    /**
     * Create the default printer.
     */
    public static Printer createPrinter() {
        return Platform.get().defaultPrinter();
    }

    /**
     * Create the default file name generator for {@link FilePrinter}.
     */
    public static FileNameGenerator createFileNameGenerator() {
        return new ChangelessFileNameGenerator(DEFAULT_LOG_FILE_NAME);
    }

    /**
     * Create the default backup strategy for {@link FilePrinter}.
     */
    public static BackupStrategy createBackupStrategy() {
        return new FileSizeBackupStrategy(DEFAULT_LOG_FILE_MAX_SIZE);
    }

    /**
     * Create the default clean strategy for {@link FilePrinter}.
     */
    public static CleanStrategy createCleanStrategy() {
        return new NeverCleanStrategy();
    }

    /**
     * Get the builtin object formatters.
     *
     * @return the builtin object formatters
     */
    public static Map<Class<?>, ObjectFormatter<?>> builtinObjectFormatters() {
        return Platform.get().builtinObjectFormatters();
    }
}

