package ru.edosgolt.j2j.utils;

import org.apache.commons.lang3.SystemUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class KillProcess {

    public int forceKillProcessByPid(long pidNumber) {
        Process process;
        if (SystemUtils.IS_OS_WINDOWS) {
            try {
                process = Runtime.getRuntime()
                        .exec(String.format("taskkill /F /T /PID %s", pidNumber));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            try {
                process = Runtime.getRuntime()
                        .exec(String.format("kill -9 %s", pidNumber));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return 0;
    }
}
