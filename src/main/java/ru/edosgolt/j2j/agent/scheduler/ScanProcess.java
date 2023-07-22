package ru.edosgolt.j2j.agent.scheduler;


import lombok.Data;
import org.apache.commons.lang3.SystemUtils;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.edosgolt.j2j.agent.model.ProcessDetail;

import java.util.LinkedHashMap;
import java.util.stream.Stream;

@EnableScheduling
@Component
@Data
public class ScanProcess {

    private LinkedHashMap<String, ProcessDetail> scannedProcess = new LinkedHashMap<>();

    @Scheduled(fixedDelay = 1000)
    private void watchDog(){
        scannedProcess.clear();
        Stream<ProcessHandle> liveProcesses = ProcessHandle.allProcesses();
        liveProcesses.filter(ProcessHandle::isAlive)
                .forEach(ph -> {
                    filterJavaProcessOnly(ph);
                });
        scannedProcess.toString();
    }

    private void filterJavaProcessOnly(ProcessHandle ph){
        String processName = getAmazingText( ph.info().command().toString() );
        String cpu = getAmazingText( ph.info().totalCpuDuration().toString() );
        String startDate = getAmazingText(ph.info().startInstant().toString());
        String commandLine = getAmazingText(ph.info().commandLine().toString());
        String arguments = getAmazingText(ph.info().arguments().toString());

        if  ((isWindowsProcess(processName))|| (isLinuxProcess(processName, commandLine))){
            ProcessDetail processDetail = new ProcessDetail();
            processDetail.setCpu(cpu);
            processDetail.setPid(ph.pid());
            processDetail.setStarttime( startDate );
            processDetail.setCommandLine(commandLine );
            processDetail.setCommand( processName );
            processDetail.setArgument(arguments);
            scannedProcess.put(String.valueOf(ph.pid()), processDetail);
        }
    }

    private boolean isWindowsProcess(String processName){
        boolean result = ((SystemUtils.IS_OS_WINDOWS) && ((processName.contains("java")) || (processName.contains("cmd.exe"))));
        return result;
    }

    private boolean isLinuxProcess(String processName, String arguments){
        boolean result = ((SystemUtils.IS_OS_LINUX) && ((!processName.contains("bash")) && (arguments.contains("ApacheJMeter.jar"))));
        return result;
    }

    private String getAmazingText(String rawText){
        return  rawText.replace("Optional[","")
                .replace("]","");

    }




}
