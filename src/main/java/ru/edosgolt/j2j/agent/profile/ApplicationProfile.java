package ru.edosgolt.j2j.agent.profile;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
@Data
public class ApplicationProfile {

    @Value("classpath:hosts.json")
    Resource hostsResourceFile;


}
