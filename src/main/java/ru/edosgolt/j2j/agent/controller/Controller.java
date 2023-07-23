package ru.edosgolt.j2j.agent.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.edosgolt.j2j.agent.service.ProcessService;
import ru.edosgolt.j2j.utils.EditProject;
import ru.edosgolt.j2j.utils.KillProcess;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;


@org.springframework.stereotype.Controller
@RequestMapping("/")
@Slf4j
public class Controller {

    private final ProcessService processService;

    @Autowired
    public Controller(ProcessService processService) {
        this.processService = processService;
    }

    @GetMapping
    public String list(@RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size, Model model) {
        model.addAttribute("employees", processService.getProcesses(pageNumber, size));
        return "index";
    }

    @RequestMapping(value = "/kill/{pid}", method = POST)
    public String terminateProcess(@PathVariable("pid") long pid)  {
        KillProcess killProcess = new KillProcess();
        killProcess.forceKillProcessByPid(pid);
        return "redirect:/";
    }

    @RequestMapping(value = "/edit/{pid}", method = POST)
    public String editProject(@PathVariable("pid") long pid)  {
        EditProject editProject = new EditProject();
        editProject.editJmeterProject(pid);
        return "redirect:/";
    }

    @RequestMapping(value = "/getlist", method = GET)
    public String gettingProcessesList(){
        String result = processService.toString();
        return result;
    }

}
