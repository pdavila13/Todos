package com.iesebre.dam2.paolodavila.todos;

import com.google.gson.Gson;

/**
 * Created by pdavila on 13/11/15.
 */
public class TodoItem {

    private String name;
    private int priority;
    private boolean done;
    private boolean checkbox;

    public TodoItem() {
        this.name = "";
        this.done = false;
        this.priority = 1;
        this.checkbox= false;
    }

    public TodoItem(String name, boolean done, int priority) {
        this.name = name;
        this.done = done;
        this.priority = priority;
    }

    static public TodoItem create(String serializedData) {
        // Use GSON to instantiate this class using the JSON representation of the state
        Gson gson = new Gson();
        return gson.fromJson(serializedData, TodoItem.class);
    }

    public String serialize() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) { this.name = name; }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) { this.done = done; }

    public int getPriority() { return priority; }

    public void setPriority(int priority) { this.priority = priority; }

    public boolean isCheckbox() { return checkbox; }

    public void setCheckbox(boolean checkbox) { this.checkbox = checkbox; }

    @Override
    public String toString() {
        return "{ name: " + name + ", done: " + done + ", priority: " + priority + " }";
    }
}
