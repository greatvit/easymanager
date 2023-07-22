package ru.edosgolt.j2j.utils;

import org.springframework.stereotype.Component;

@Component
public class EditProject {

    public int editJmeterProject(long pidNumber) {
        // opt/jdk11/bin/java --add-opens java.desktop/sun.awt=ALL-UNNAMED
        // --add-opens java.desktop/sun.swing=ALL-UNNAMED
        // --add-opens java.desktop/javax.swing.text.html=ALL-UNNAMED
        // --add-opens java.desktop/java.awt=ALL-UNNAMED
        // --add-opens java.desktop/java.awt.font=ALL-UNNAMED
        // --add-opens=java.base/java.lang=ALL-UNNAMED
        // --add-opens=java.base/java.lang.invoke=ALL-UNNAMED
        // --add-opens=java.base/java.lang.reflect=ALL-UNNAMED
        // --add-opens=java.base/java.util=ALL-UNNAMED
        // --add-opens=java.base/java.text=ALL-UNNAMED
        // --add-opens=java.desktop/sun.awt.X11=ALL-UNNAMED
        // --add-opens=java.desktop/sun.awt.shell=ALL-UNNAMED -jar ./ApacheJMeter.jar -n -t simple_test.jmx
        return 0;
    }

}
