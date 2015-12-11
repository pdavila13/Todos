package com.iesebre.dam2.paolodavila.todos;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    //Definició de la constant SHARED_PREFERENCES_TODOS assignant un string
    private static final String SHARED_PREFERENCES_TODOS = "SP_TODOS";
    private static final String TODO_LIST = "todo_list";

    // Per utilitzar-lo des de qualsevol lloc
    private Gson gson;

    public TodoArrayList tasks;
    public TodoArrayList removed;
    private CustomListAdapter adapter;
    String taskName;


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        super.onStop();

        //Serialize our TaskArrayList to Json
        Type arrayTodoList = new TypeToken<TodoArrayList>(){}.getType();
        String serializedData = gson.toJson(tasks, arrayTodoList);

        //Save tasks in SharedPreferences
        SharedPreferences todos = getSharedPreferences(SHARED_PREFERENCES_TODOS, 0);
        SharedPreferences.Editor editor = todos.edit();

        editor.putString("TaskArrayList", serializedData);
        editor.commit();

//        Gson gson = new Gson();
//        Type arrayTodoList = new TypeToken<TodoArrayList>() {}.getType();
//        String json = gson.toJson(todoList, arrayTodoList);
//
//        String initial_json = "";   //TODO: tasks.toJson();
//        SharedPreferences.Editor editor = todos.edit();
//        editor.putString(TODO_LIST, initial_json);
//        editor.commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Arraylist to save all our tasks
        tasks = new TodoArrayList();

        //Completed tasks we have removed
        removed = new TodoArrayList();

        //Gson to serialize our objects to Json to save
        gson = new Gson();

        //SharedPreferences stores all data which we want to be permanent
        SharedPreferences todos = getSharedPreferences(SHARED_PREFERENCES_TODOS, 0);

        //Return null if preference doesn't exist
        String todoList = todos.getString(TODO_LIST, null);

//        Snackbar.make(view, todoList , Snackbar.LENGTH_LONG)
//            .setAction("Action", null).show();
//
//        Toast.makeText(this, todoList, Toast.LENGTH_SHORT).show();
//
//
//        [
//         {"name": "Comprar llet", "done": true, "priority": 2},
//         {"name": "Comprar pa", "done": true, "priority": 1},
//         {"name": "Fer exercicis", "done": false, "priority": 3},
//         {"name": "Estudiar", "done": false, "priority": 4}
//        ]

        /*
        if(todoList == null){
            String initial_json = "[\n" +
                    "         {\"name\": \"Comprar llet\", \"done\": true, \"priority\": 2},\n" +
                    "         {\"name\": \"Comprar pa\", \"done\": true, \"priority\": 1},\n" +
                    "         {\"name\": \"Fer exercicis\", \"done\": false, \"priority\": 3},\n" +
                    "         {\"name\": \"Estudiar\", \"done\": false, \"priority\": 4}\n" +
                    "        ]";
            SharedPreferences.Editor editor = todos.edit();
            editor.putString(TODO_LIST, initial_json);
            editor.commit();
            todoList = todos.getString(TODO_LIST, null);
        }

        Log.d("TAG_PROVa", "********************************************");
        Log.d("TAG_PROVA", todoList);
        Log.d("TAG_PROVa", "********************************************");
*/
        //Gson to serialize our objects to Json to save
        this.gson = new Gson();

        //Deserializes any taskarraylist we have saved
        Type arrayTodoList = new TypeToken<TodoArrayList>() {}.getType();
        TodoArrayList temp = gson.fromJson(todoList,arrayTodoList);

        //If we successfully loaded a TaskArrayList from SharedPreferences, take it
        if(temp != null){
            tasks = temp;
        } else {
            //Erros TODO
        }

        ListView todolv = (ListView) findViewById(R.id.todolistview);

        //We bind our arrayList of tasks to the adapter
        adapter = new CustomListAdapter(this, tasks);
        todolv.setAdapter(adapter);

        //Set up the toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Intent intent = new Intent(MainActivity.this, Main2Activity.class);
//                startActivity(intent);
//
//                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        //.setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camara) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void removeTask(View view){

        //Removes all completed tasks and notifies the view
        removed = tasks.getCompletedTasks();
    }

    public void showAddTaskForm(View view) {
        taskName = "";
        EditText taskNameText;

//        MaterialDialog dialog
//        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//        } = new MaterialDialog.Builder(this).

        MaterialDialog dialog = new MaterialDialog.Builder(this).
                title("Afegir tasca").
                customView(R.layout.form_add_task, true).
                negativeText("Cancel·lar").
                positiveText("Afegir").
                negativeColor(Color.parseColor("#2196F3")).
                positiveColor(Color.parseColor("#2196F3")).
                onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog dialog, DialogAction which) {
                        final TodoItem todoItem = new TodoItem();
//                        EditText et = (EditText) dialog.getCustomView().findViewById(R.id.task_title);
//                        todoItem.setName(et.toString());
                        todoItem.setName(taskName);
                        //todoItem.setDone(true);
                        //todoItem.setPriority(1);

                        tasks.add(todoItem);
                        //tasks.remove(1);

                        adapter.notifyDataSetChanged();
                    }
                }).
                build();
        dialog.show();

        taskNameText = (EditText) dialog.getCustomView().findViewById(R.id.task_title);

        //If we name a task and it has a priority, enable positive button
        taskNameText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {
                //Code here:
            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                taskName = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {
                //Code here:
            }
        });
    }

    TodoItem edit_temp;

    public void showEditTaskFrom(View view){
        EditText taskNameText;

        //edit_temp = (TodoItem) view.getTag();

        //taskName = edit_temp.getName();

        //Creates a dialog for adding a new task
        MaterialDialog dialog = new MaterialDialog.Builder(this)
                .title("Editar tasca")
                .customView(R.layout.form_add_task, true)
                .negativeText("Cancelar")
                .positiveText("Actualitzar")
                .negativeColor(Color.parseColor("#2196F3"))
                .positiveColor(Color.parseColor("#2196F3"))
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog dialog, DialogAction which) {
                        final TodoItem todoItem = new TodoItem();
                        edit_temp.setName(taskName);

                        //tasks.add(todoItem);

                        adapter.notifyDataSetChanged();
                    }
                })
                .build();
        dialog.show();

        taskNameText = (EditText) dialog.getCustomView().findViewById(R.id.task_title);

        //If we name a task and it has a priority, enable positive button
        taskNameText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {
                //Code here:
            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                taskName = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {
                //Code here:
            }
        });
    }

    //We should save our instance here, currently we do nothing
    public void onSaveInstanceState(Bundle savedState) {
        super.onSaveInstanceState(savedState);
    }
}
