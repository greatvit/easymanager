package ru.edosgolt.j2j.agent.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.edosgolt.j2j.agent.model.ProcessDetail;
import ru.edosgolt.j2j.agent.model.paging.Page;
import ru.edosgolt.j2j.agent.model.paging.Paged;
import ru.edosgolt.j2j.agent.model.paging.Paging;
import ru.edosgolt.j2j.agent.scheduler.ScanProcess;

@Service
public class ProcessService {

    @Autowired
    private ScanProcess scanProcess;


    public Paged<ProcessDetail> getProcesses(int pageNumber, int size) {

        List<ProcessDetail> paged = new ArrayList<>();

        for (Map.Entry<String, ProcessDetail> entry : scanProcess.getScannedProcess().entrySet()) {
            paged.add( entry.getValue() );
        }

        int totalPages = paged.size() / size;
        return new Paged<>(new Page<>(paged, totalPages), Paging.of(totalPages, pageNumber, size));
    }


}