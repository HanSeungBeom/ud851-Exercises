package bumbums.toyappforexercise;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import bumbums.toyappforexercise.database.AppDatabase;
import bumbums.toyappforexercise.database.TaskEntry;

/**
 * Created by hanseungbeom on 2018. 5. 12..
 */

public class AddTaskViewModel extends ViewModel {
    private LiveData<TaskEntry> task;


    public AddTaskViewModel(AppDatabase database,int taskId) {
        task = database.taskDao().loadTaskById(taskId);
    }

    public LiveData<TaskEntry> getTask() {
        return task;
    }
}
