package ru.scanner.j2j.agent.scheduler;


import lombok.Data;
import org.apache.commons.lang3.SystemUtils;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.scanner.j2j.agent.model.ProcessDetail;

import java.util.LinkedHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Stream;

@EnableScheduling
@Component
@Data
public class LocalScanProcesses {

    private LinkedHashMap<String, ProcessDetail> localProcessesList = new LinkedHashMap<>();

    @Scheduled(fixedDelay = 1000)
    private void watchDog(){
        localProcessesList.clear();
        Stream<ProcessHandle> liveProcesses = ProcessHandle.allProcesses();
        liveProcesses.filter(ProcessHandle::isAlive)
                .forEach(ph -> {
                    discoveryJmeterProcesses(ph);
                });
        localProcessesList.toString();
    }

    private void discoveryJmeterProcesses(ProcessHandle ph){
        String processName = getAmazingText( ph.info().command().toString() );
        String cpu = getAmazingText( ph.info().totalCpuDuration().toString() );
        String startDate = getAmazingText(ph.info().startInstant().toString());
        String commandLine = getAmazingText(ph.info().commandLine().toString());
        String arguments = getAmazingText(ph.info().arguments().toString());

        if  ((isWindowsJmeter(processName) && isChildrenJavaProcessPresent(ph)) ||
                (isLinuxJmeter(processName, commandLine))){
            ProcessDetail processDetail = new ProcessDetail();
            processDetail.setHost("localhost");
            processDetail.setCpu(cpu);
            processDetail.setPid(ph.pid());
            processDetail.setStarttime( startDate );
            processDetail.setCommandLine(commandLine );
            processDetail.setCommand( processName );
            processDetail.setArgument(arguments);
            localProcessesList.put(String.valueOf(ph.pid()), processDetail);
        }
    }

    private boolean isWindowsJmeter(String processName){
        boolean result = ((SystemUtils.IS_OS_WINDOWS) && ((processName.contains("cmd.exe"))));
        return result;
    }

    private boolean isLinuxJmeter(String processName, String arguments){
        boolean result = ((SystemUtils.IS_OS_LINUX) && ((!processName.contains("bash")) && (arguments.contains("ApacheJMeter.jar"))));
        return result;
    }

    private String getAmazingText(String rawText){
        return  rawText.replace("Optional[","")
                .replace("]","");
    }

    private boolean isChildrenJavaProcessPresent(ProcessHandle ph){
        AtomicBoolean result = new AtomicBoolean(false);
        if (ph.children().count()>0){
            ph.children().forEach(child -> {
                if (child.info().command().toString().contains("java")) {
                    result.set(true);
                }
            });
        }
        return result.get();
    }




}
