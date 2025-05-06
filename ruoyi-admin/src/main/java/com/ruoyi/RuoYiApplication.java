package com.ruoyi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 启动程序
 *
 * @author ruoyi
 */
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
@EnableScheduling
public class RuoYiApplication
{
    public static void main(String[] args)
    {
        // System.setProperty("spring.devtools.restart.enabled", "false");
        SpringApplication.run(RuoYiApplication.class, args);
//        System.out.println(
//                " █     █░ ▒█████   ███▄    █ ▓█████▄ ▓█████  ██▀███    █████▒ █    ██  ██▓    \n" +
//                "▓█░ █ ░█░▒██▒  ██▒ ██ ▀█   █ ▒██▀ ██▌▓█   ▀ ▓██ ▒ ██▒▓██   ▒  ██  ▓██▒▓██▒    \n" +
//                "▒█░ █ ░█ ▒██░  ██▒▓██  ▀█ ██▒░██   █▌▒███   ▓██ ░▄█ ▒▒████ ░ ▓██  ▒██░▒██░    \n" +
//                "░█░ █ ░█ ▒██   ██░▓██▒  ▐▌██▒░▓█▄   ▌▒▓█  ▄ ▒██▀▀█▄  ░▓█▒  ░ ▓▓█  ░██░▒██░    \n" +
//                "░░██▒██▓ ░ ████▓▒░▒██░   ▓██░░▒████▓ ░▒████▒░██▓ ▒██▒░▒█░    ▒▒█████▓ ░██████▒\n" +
//                "░ ▓░▒ ▒  ░ ▒░▒░▒░ ░ ▒░   ▒ ▒  ▒▒▓  ▒ ░░ ▒░ ░░ ▒▓ ░▒▓░ ▒ ░    ░▒▓▒ ▒ ▒ ░ ▒░▓  ░\n" +
//                "  ▒ ░ ░    ░ ▒ ▒░ ░ ░░   ░ ▒░ ░ ▒  ▒  ░ ░  ░  ░▒ ░ ▒░ ░      ░░▒░ ░ ░ ░ ░ ▒  ░\n" +
//                "  ░   ░  ░ ░ ░ ▒     ░   ░ ░  ░ ░  ░    ░     ░░   ░  ░ ░     ░░░ ░ ░   ░ ░   \n" +
//                "    ░        ░ ░           ░    ░       ░  ░   ░                ░         ░  ░\n" +
//                "                              ░                                                \n");

        System.out.println("                                                                                                        ,--,    \n" +
                "                    ,----..            ,--.                                                          ,---.'|    \n" +
                "           .---.   /   /   \\         ,--.'|    ,---,        ,---,.,-.----.       ,---,.              |   | :    \n" +
                "          /. ./|  /   .     :    ,--,:  : |  .'  .' `\\    ,'  .' |\\    /  \\    ,'  .' |         ,--, :   : |    \n" +
                "      .--'.  ' ; .   /   ;.  \\ ,`--.'`|  ' :,---.'     \\ ,---.'   |;   :    \\ ,---.'   |       ,'_ /| |   ' :    \n" +
                "     /__./ \\ : |.   ;   /  ` ;|   :  :  | ||   |  .`\\  ||   |   .'|   | .\\ : |   |   .'  .--. |  | : ;   ; '    \n" +
                " .--'.  '   \\' .;   |  ; \\ ; |:   |   \\ | ::   : |  '  |:   :  |-,.   : |: | :   :  :  ,'_ /| :  . | '   | |__  \n" +
                "/___/ \\ |    ' '|   :  | ; | '|   : '  '; ||   ' '  ;  ::   |  ;/||   |  \\ : :   |  |-,|  ' | |  . . |   | :.'| \n" +
                ";   \\  \\;      :.   |  ' ' ' :'   ' ;.    ;'   | ;  .  ||   :   .'|   : .  / |   :  ;/||  | ' |  | | '   :    ; \n" +
                " \\   ;  `      |'   ;  \\; /  ||   | | \\   ||   | :  |  '|   |  |-,;   | |  \\ |   |   .':  | | :  ' ; |   |  ./  \n" +
                "  .   \\    .\\  ; \\   \\  ',  / '   : |  ; .''   : | /  ; '   :  ;/||   | ;\\  \\'   :  '  |  ; ' |  | ' ;   : ;    \n" +
                "   \\   \\   ' \\ |  ;   :    /  |   | '`--'  |   | '` ,/  |   |    \\:   ' | \\.'|   |  |  :  | : ;  ; | |   ,/     \n" +
                "    :   '  |--\"    \\   \\ .'   '   : |      ;   :  .'    |   :   .':   : :-'  |   :  \\  '  :  `--'   \\'---'      \n" +
                "     \\   \\ ;        `---`     ;   |.'      |   ,.'      |   | ,'  |   |.'    |   | ,'  :  ,      .-./           \n" +
                "      '---\"                   '---'        '---'        `----'    `---'      `----'     `--`----'               \n");



    }
}
