package revision.protonmanexe.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import revision.protonmanexe.model.ToDo;
import revision.protonmanexe.service.TaskService;

@Controller
@RequestMapping(path="/task", produces=MediaType.TEXT_HTML_VALUE)
public class TaskController {

    @Autowired
    private TaskService taskSvc;

    private final static Logger logging = LoggerFactory.getLogger(TaskController.class); // instantiate logger

    // POST method to return task.html after submitting task
    @PostMapping
    public String addTask (@ModelAttribute ToDo t, Model model) {
        if (t.getTask() == null || t.getTask().trim().length() == 0) {
            model.addAttribute("msg", "*Please enter a task"); 
        } else {
            List <String> toDoList = t.getListOfTasks();
            toDoList.add(t.getTask()); // get task and save inside a list
            t.setListOfTasks(toDoList);
            t.setTask(null);
            model.addAttribute("msg", null); 
        }

        // check inputs
        logging.info("addfinishTask user name > " +t.getUserName());
        logging.info("addfinishTask task > " +t.getTask());
        logging.info("addfinishTask list > " +t.getListOfTasks());

        // add inputs to page
        model.addAttribute("userName", t.getUserName());
        model.addAttribute("todomodel", t); 
        model.addAttribute("contents", t.getListOfTasks()); 

        return "task";
    }

    // POST method to return index.html after submitting task
    @PostMapping("/list")
    public String saveTasks (@ModelAttribute ToDo t, Model model) {
        logging.info("Do save tasks: user name > " +t.getUserName());
        logging.info("Do save tasks: list > " +t.getListOfTasks());

        taskSvc.saveToDoList(t.getUserName(), t.getListOfTasks());
        logging.info("Save success!");

        model.addAttribute("getUserName", new ToDo()); // need this to avoid template parsing issue
        return "index";
    }

    @PostMapping("/empty")
    public String deleteList (@ModelAttribute ToDo t, Model model) {
        t.setListOfTasks(new ArrayList<>());
        logging.info("delete list > " +t.getListOfTasks());

        // add inputs to page
        model.addAttribute("userName", t.getUserName());
        model.addAttribute("todomodel", t); 
        model.addAttribute("contents", t.getListOfTasks()); 

        return "task"; 
    } 
}