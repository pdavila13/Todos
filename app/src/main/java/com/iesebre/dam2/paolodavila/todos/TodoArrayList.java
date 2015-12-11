package com.iesebre.dam2.paolodavila.todos;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by pdavila on 13/11/15.
 */
public class TodoArrayList extends ArrayList<TodoItem>{

    //Adds a task to the appropriate place
    //Priority goes first unchecked with red > blue > green, then checked in chronological order
    public int insert(TodoItem ti){

        //Index where we can start adding the task depending if it's checked or not
        int startIndex;

        //Unchecked tasks should be higher up
        if(!ti.isDone()){
            startIndex = 0;

        } else {

            startIndex = this.size();

            //First occurrence of checked task
            for(int i = 0; i < this.size(); i++){
                if(this.get(i).isDone()){
                    startIndex = i;
                    break;
                }
            }
        }

        for(int i = startIndex; i < this.size(); i++){

            //Unchecked have priority over checked
            if(!ti.isDone() && this.get(i).isDone()){
                this.add(i, ti);
                return i;
            }
        }

        //Whatever
        this.add(ti);
        return this.size() - 1;
    }

    public void insert(TodoArrayList tal){
        for(TodoItem ti : tal){
            this.insert(ti);
        }
    }

    //Returns a list of all completed tasks
    public TodoArrayList getCompletedTasks(){
        Iterator<TodoItem> it = this.iterator();
        TodoArrayList completed = new TodoArrayList();

        while(it.hasNext()){
            TodoItem ti = it.next();

            if(ti.isDone()){
                completed.add(ti);
            }
        }

        return completed;
    }
}
