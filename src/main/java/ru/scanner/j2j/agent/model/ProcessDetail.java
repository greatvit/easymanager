package ru.scanner.j2j.agent.model;

import lombok.Data;

@Data
public class ProcessDetail {

    private String host = "";
    private String command = "";
    private String commandLine = "";
    private Long pid = 0L;
    private String cpu = "";
    private String memory = "";
    private String starttime;
    private String processHandle = "";
    private String name ="";
    private String argument = "";
    private String jmeterProject;
}
