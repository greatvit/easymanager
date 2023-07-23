package ru.edosgolt.j2j.agent.scheduler;


import lombok.Data;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.edosgolt.j2j.agent.model.ProcessDetail;
import ru.edosgolt.j2j.agent.profile.ApplicationProfile;
import ru.edosgolt.j2j.utils.HttpClient;
import ru.edosgolt.j2j.utils.TinyStringUtils;

import java.util.LinkedHashMap;

import static java.lang.String.format;

@EnableScheduling
@Component
@Data
public class NetworkScan {

    private LinkedHashMap<String, ProcessDetail> globalProcessesList = new LinkedHashMap<>();

    @Autowired
    private ApplicationProfile apr;
    @Autowired
    private LocalScanProcesses localScanProcesses;

    @Scheduled(fixedDelay = 3000)
    private void networkScanner(){
        String hostsFile = TinyStringUtils.resourceToString( apr.getHostsResourceFile() );
        if (!hostsFile.isEmpty()) {
            scanHosts(hostsFile);
        }
    }

    private void scanHosts(String hostsList){
        globalProcessesList.clear();
        JSONObject jsonObject = new JSONObject(hostsList);
        JSONArray jsonArray = jsonObject.getJSONArray("hosts");
        jsonArray.forEach(item->{
            String hostName = (String) item;
            // если это локальный компьютер, просто копируем список процессов в глобальный список
            if ((hostName.contains("localhost")|| (hostName.contains("127.0.0.1")))){
                copyLocal2Global();
            }else{
                getRemoteList( format("%s/getlist", hostName));
            }
        });
    }

    private void copyLocal2Global(){
        localScanProcesses.getLocalProcessesList().forEach((k,v)->{
            globalProcessesList.put(k,v);
        });
    }

    private void getRemoteList(String url){
        HttpClient httpClient = new HttpClient();
        String result = httpClient.get(url);
    }


}
