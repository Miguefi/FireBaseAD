package com.example.firebasead.database.Listeners;

public abstract class TaskListener extends AbstractEventListener {
    public abstract void OnSuccess();
    public abstract void OnFail();
}
