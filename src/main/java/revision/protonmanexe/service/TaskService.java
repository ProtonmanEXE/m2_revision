package revision.protonmanexe.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import revision.protonmanexe.repository.TaskRepo;

@Service
public class TaskService {

    private static final Logger logging = LoggerFactory.getLogger(TaskService.class);

    @Autowired
    private TaskRepo taskRepo;

    public boolean hasUserNameKey (String userName) {
        Optional<String> opt = taskRepo.get(userName);
        return opt.isPresent();
    }

    public List<String> getToDoList (String userName) {
        Optional<String> opt = taskRepo.get(userName);
        List<String> list = new LinkedList<>();
        if (opt.isPresent())
            for (String t: opt.get().split("\\|"))
                list.add(t);
        return list;
    }

    public void saveToDoList (String userName, List<String> listOfTasks) {
        String l = listOfTasks.stream().collect(Collectors.joining("|"));
        this.saveToDoString(userName, l);
    }

    public void saveToDoString (String userName, String tasks) {
        logging.info("Saving > " +tasks);
        taskRepo.save(userName, tasks);
    }

}