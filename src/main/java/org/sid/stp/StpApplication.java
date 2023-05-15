package org.sid.stp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StpApplication {

    public static void main(String[] args) {
        SpringApplication.run(StpApplication.class, args);
        String welcomeMessage =
                ""
                        + "\n"
                        + " __          __  _                            _                \n"
                        + " \\ \\        / / | |                          | |        |  \\     \n"
                        + "  \\ \\  /\\  / /__| | ___ ___  _ __ ___   ___  | |_ ___   | \\n"
                        + "   \\ \\/  \\/ / _ \\ |/ __/ _ \\| '_ ` _ \\ / _ \\ | __/ _ \\   \\ \\\n"
                        + "    \\  /\\  /  __/ | (_| (_) | | | | | |  __/ | || (_) | | |  \n"
                        + "     \\/  \\/ \\___|_|\\___\\___/|_| |_| |_|\\___|  \\__\\___/  \\_\\\n"
                        + "                                                                                \n"
                        + "                                                                                \n"
                        + "     ^\n"
                        + "   /'|'\\\n"
                        + "  / \\|/ \\\n"
                        + "  | \\|/ |\n"
                        + "   \\ | /\n"
                        + "    \\|/\n"
                        + "     |\n"
                        + "                       \n";
        System.out.println(welcomeMessage);
    }

}
