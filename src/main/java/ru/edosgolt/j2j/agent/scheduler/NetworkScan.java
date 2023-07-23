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
import ru.edosgolt.j2j.utils.TinyStringUtils;

import java.util.LinkedHashMap;

@EnableScheduling
@Component
@Data
public class NetworkScan {

    private LinkedHashMap<String, ProcessDetail> globalProcessesList = new LinkedHashMap<>();

    @Autowired
    private ApplicationProfile apr;

    @Scheduled(fixedDelay = 3000)
    private void networkScanner(){
        String hostsFile = TinyStringUtils.resourceToString( apr.getHostsResourceFile() );
        if (!hostsFile.isEmpty()) {
            scanHosts(hostsFile);
        }
    }

    private void scanHosts(String hostsList){
        JSONObject jsonObject = new JSONObject(hostsList);
        JSONArray jsonArray = jsonObject.getJSONArray("hosts");
        jsonArray.forEach(item->{
            String host = (String) item;

        });

    }


}
