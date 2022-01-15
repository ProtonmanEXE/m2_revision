package revision.protonmanexe.model;

import java.io.Serializable;
import java.util.List;

public class ToDo implements Serializable {
    
    private String userName;
    private String task;
    private List <String> listOfTasks;

    public ToDo () {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public ToDo (String task) {
        this.task = task;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public List <String> getListOfTasks() {
        return listOfTasks;
    }

    public void setListOfTasks(List <String> listOfTasks) {
        this.listOfTasks = listOfTasks;
    }

}
