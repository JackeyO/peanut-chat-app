package com.sici.chat.thread.task;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 任务上下文信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskContext {
    /**
     * 任务名称
     */
    private String taskName;
    
    /**
     * 任务描述
     */
    private String taskDescription;
    
    /**
     * 任务ID（可以是业务ID，如消息ID、用户ID等）
     */
    private String taskId;
    
    /**
     * 任务开始时间
     */
    private LocalDateTime startTime;
    
    /**
     * 父线程名称
     */
    private String parentThreadName;
    
    /**
     * 当前线程名称
     */
    private String currentThreadName;
    
    /**
     * 扩展信息
     */
    private Map<String, Object> extInfo = new ConcurrentHashMap<>();
    
    public TaskContext(String taskName) {
        this.taskName = taskName;
        this.startTime = LocalDateTime.now();
        this.parentThreadName = Thread.currentThread().getName();
    }
    
    public TaskContext(String taskName, String taskId) {
        this(taskName);
        this.taskId = taskId;
    }
    
    public TaskContext(String taskName, String taskId, String taskDescription) {
        this(taskName, taskId);
        this.taskDescription = taskDescription;
    }
    
    public void addExtInfo(String key, Object value) {
        this.extInfo.put(key, value);
    }
}