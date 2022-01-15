package revision.protonmanexe.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import revision.protonmanexe.model.ToDo;
import revision.protonmanexe.service.TaskService;

@Controller
@RequestMapping(path="/", produces=MediaType .TEXT_HTML_VALUE) // if put "/", then must have a GETMAPPING method
public class IndexController {

    @Autowired
    private TaskService taskSvc;

    private final static Logger logging = LoggerFactory.getLogger(IndexController.class); // instantiate logger

    // GET method to return index.html (landing page)
    @GetMapping
    public String showIndex (Model model) {
        model.addAttribute("getUserName", new ToDo()); // need this to avoid template parsing issue
        return "index"; 
    } 

    // POST method to return task.html as secondary landing page
    @PostMapping
    public String addName (@ModelAttribute ToDo t, Model model) {
        List <String> toDoList = new ArrayList<>(); // array to store all tasks
        
        // check if userName's to-do list already exists
        if (taskSvc.hasUserNameKey(t.getUserName())) {
            toDoList = taskSvc.getToDoList(t.getUserName());
            logging.info("toDoList list > " +toDoList);
        } 
        t.setListOfTasks(toDoList);
        
        // add inputs to page
        model.addAttribute("userName", t.getUserName());
        model.addAttribute("todomodel", t); // this is important to ensure that text field is empty 
                                            // before reloading the page; otherwise, error
        model.addAttribute("msg", null); 
        model.addAttribute("contents", t.getListOfTasks()); 

        logging.info("addName user name > " +t.getUserName());
        logging.info("addName task > " +t.getTask());
        logging.info("addName list > " +t.getListOfTasks());

        return "task"; 
    }

}