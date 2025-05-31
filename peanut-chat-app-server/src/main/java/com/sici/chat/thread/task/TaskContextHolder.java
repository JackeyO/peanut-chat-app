package com.sici.chat.thread.task;

import com.sici.chat.context.RequestInfo;
import com.sici.chat.util.RequestHolder;
import org.slf4j.MDC;

/**
 * 任务上下文持有器
 * 使用ThreadLocal保存任务上下文信息
 */
public class TaskContextHolder {
    
    private static final ThreadLocal<TaskContext> TASK_CONTEXT_THREAD_LOCAL = new ThreadLocal<>();
    
    /**
     * MDC键名
     */
    public static final String MDC_TASK_NAME = "taskName";
    public static final String MDC_TASK_ID = "taskId";
    public static final String MDC_THREAD_NAME = "threadName";
    public static final String MDC_PARENT_THREAD = "parentThread";
    
    /**
     * 设置任务上下文
     */
    public static void setTaskContext(TaskContext taskContext) {
        // 设置当前线程名称
        taskContext.setCurrentThreadName(Thread.currentThread().getName());
        
        // 保存到ThreadLocal
        TASK_CONTEXT_THREAD_LOCAL.set(taskContext);
        
        // 同时设置到MDC，方便在日志中使用
        setMDC(taskContext);
    }
    
    /**
     * 获取任务上下文
     */
    public static TaskContext getTaskContext() {
        return TASK_CONTEXT_THREAD_LOCAL.get();
    }
    
    /**
     * 清理任务上下文
     */
    public static void clearTaskContext() {
        TASK_CONTEXT_THREAD_LOCAL.remove();
        clearMDC();
    }
    
    /**
     * 设置MDC
     */
    private static void setMDC(TaskContext taskContext) {
        if (taskContext != null) {
            MDC.put(MDC_TASK_NAME, taskContext.getTaskName());
            MDC.put(MDC_TASK_ID, taskContext.getTaskId());
            MDC.put(MDC_THREAD_NAME, taskContext.getCurrentThreadName());
            MDC.put(MDC_PARENT_THREAD, taskContext.getParentThreadName());
        }
    }
    
    /**
     * 清理MDC
     */
    private static void clearMDC() {
        MDC.remove(MDC_TASK_NAME);
        MDC.remove(MDC_TASK_ID);
        MDC.remove(MDC_THREAD_NAME);
        MDC.remove(MDC_PARENT_THREAD);
    }
    
    /**
     * 复制父线程的RequestInfo到当前线程
     * 适用于跨线程传递请求上下文
     */
    public static void copyRequestInfoFromParent(RequestInfo parentRequestInfo) {
        if (parentRequestInfo != null) {
            RequestHolder.set(parentRequestInfo);
        }
    }
    
    /**
     * 获取当前任务的完整信息字符串
     */
    public static String getCurrentTaskInfo() {
        TaskContext context = getTaskContext();
        if (context == null) {
            return String.format("Thread[%s]", Thread.currentThread().getName());
        }
        
        StringBuilder sb = new StringBuilder();
        sb.append("Task[").append(context.getTaskName()).append("]");
        
        if (context.getTaskId() != null) {
            sb.append(" ID[").append(context.getTaskId()).append("]");
        }
        
        sb.append(" Thread[").append(context.getCurrentThreadName()).append("]");
        
        if (context.getParentThreadName() != null) {
            sb.append(" ParentThread[").append(context.getParentThreadName()).append("]");
        }
        
        return sb.toString();
    }
}