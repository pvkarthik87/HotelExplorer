/**
 * pvkarthik
 */
package com.karcompany.hotelexplorer.logging;

public interface ILogger {
    void log(int priority, String tag, String msg);

    void log(int priority, String tag, String msg, Throwable tr);
}
