package ru.tsu.kitidis.fsmtest2junit.toolsandutils;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;

public class ErrorStatusBuilder {

	public ErrorStatusBuilder() {
		// TODO Auto-generated constructor stub
	}
	
	public static MultiStatus createMultiStatus(String msg, Throwable t) {
		/*
		 * The followin is taken from http://www.vogella.com/tutorials/EclipseDialogs/article.html
		 * Created by Vogella
		 */
        List<Status> childStatuses = new ArrayList<>();
        StackTraceElement[] stackTraces = Thread.currentThread().getStackTrace();

        for (StackTraceElement stackTrace: stackTraces) {
            Status status = new Status(IStatus.ERROR,
                    "com.example.e4.rcp.todo", stackTrace.toString());
            childStatuses.add(status);
        }

        MultiStatus ms = new MultiStatus("com.example.e4.rcp.todo",
                IStatus.ERROR, childStatuses.toArray(new Status[] {}),
                t.toString(), t);
        return ms;
    }

}
