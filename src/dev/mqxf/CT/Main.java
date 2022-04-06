package dev.mqxf.CT;

import dev.mqxf.CT.GUI.Menu;
import org.bspfsystems.yamlconfiguration.file.YamlConfiguration;

import java.io.File;

public class Main {

    public static void main(String[] args) {
        Menu menu = new Menu();
        menu.setVisible(true);
    }

    public static String init(String s, String className) {
        if (className.isBlank() || className.isEmpty()) {
            return "Please enter a name for class";
        }
        if (className.contains(" ")) {
            return "Class name cannot contain spaces";
        }

        String path = System.getProperty("user.home") + "/CTData/";
        File dir = new File(path);
        File file;
        if (dir.exists() || dir.mkdirs()) {
            file = new File(dir, className + ".yml");
            if (!file.exists()) {
                try {
                    file.createNewFile();
                }
                catch (Exception e) {
                    return "Error creating file";
                }
            }
        }
        else {
            return "Error creating directory";
        }

        YamlConfiguration config = new YamlConfiguration();
        try {
            //config = YamlConfiguration.loadConfiguration(file);
            config.load(file);
        } catch (Exception e) {
            return "Error loading config";
        }

        String out = "";
        String[] split = s.split("\n");
        for (String sData : split) {
            sData = sData.replaceAll("\\s+", " ");
            sData = sData.replaceAll(",\\s", "@");
            String[] data = sData.split("\\s");
            String name = data[0].split("@")[1] + " " + data[0].split("@")[0];
            out = out + name + ": ";
            name = name.replaceAll("\\s", "");
            name = name.toLowerCase();
            int nuggets;
            int questions;
            int minutes;
            int percent;
            try {
                nuggets = Integer.parseInt(data[1]);
                questions = Integer.parseInt(data[2]);
                minutes = convertMinutes(data[3], data[4]);
                percent = Integer.parseInt(data[5].replaceAll("%", ""));

                //Nuggets
                if (config.contains(name + ".nuggets")) {
                    int old = config.getInt(name + ".nuggets");
                    config.set(name + ".nuggets", nuggets);
                    nuggets = nuggets - old;
                }
                else {
                    config.set(name + ".nuggets", nuggets);
                }
                out = out + nuggets + " nuggets, ";

                //Questions
                if (config.contains(name + ".questions")) {
                    int old = config.getInt(name + ".questions");
                    config.set(name + ".questions", questions);
                    questions = questions - old;
                }
                else {
                    config.set(name + ".questions", questions);
                }
                out = out + questions + " questions, ";

                //Time (saved in minutes)
                if (config.contains(name + ".minutes")) {
                    int old = config.getInt(name + ".minutes");
                    config.set(name + ".minutes", minutes);
                    minutes = minutes - old;
                }
                else {
                    config.set(name + ".minutes", minutes);
                }
                out = out + fromMins(minutes) + ", ";

                //Percentage
                if (config.contains(name + ".percent")) {
                    int old = config.getInt(name + ".percent");
                    config.set(name + ".percent", percent);
                    percent = percent - old;
                }
                else {
                    config.set(name + ".percent", percent);
                }
                out = out + percent + "%, ";

                config.save(file);

                if (questions > 0 && minutes > 0) {
                    out = out + "time/question: " + fromSeconds((minutes * 60) / questions);
                }
                else if (questions > 0) {
                    out = out + "time/question: 0ms 1μs";
                }
                else if (minutes > 0) {
                    out = out + "time/question: " + fromMins(minutes);
                }
                else {
                    out = out + "time/question: 0m 0s";
                }

            }
            catch (Exception e) {
                System.out.println("Error.");
            }
            out = out + "\n";
        }

        return out;
    }

    private static int convertMinutes(String a, String b) {
        int mins = 0;
        switch (a.charAt(a.length() - 1)) {
            case 's':
                break;
            case 'm':
                mins = Integer.parseInt(a.replaceAll("m", ""));
                break;
            case 'h':
                mins = Integer.parseInt(b.replaceAll("m", ""));
                mins = mins + (60 * Integer.parseInt(a.replaceAll("h", "")));
                break;
            case 'd':
                mins = 60 * Integer.parseInt(b.replaceAll("h", ""));
                mins = mins + (60 * 24 * Integer.parseInt(a.replaceAll("d", "")));
                break;
            case 'w':
                mins = 60 * 24 * Integer.parseInt(b.replaceAll("d", ""));
                mins = mins + (60 * 24 * 7 * Integer.parseInt(a.replaceAll("w", "")));
                break;
            case 'o':
                mins = 60 * 24 * 7 * Integer.parseInt(b.replaceAll("w", ""));
                mins = mins + (60 * 24 * 7 * 4 * Integer.parseInt(a.replaceAll("mo", "")));
                break;
            case 'y':
                mins = 60 * 24 * 7 * 4 * Integer.parseInt(b.replaceAll("mo", ""));
                mins = mins + (60 * 24 * 7 * 4 * 12 * Integer.parseInt(a.replaceAll("y", "")));
                break;
            default:
                mins = 0;
                break;
        }
        return mins;
    }

    public static String fromMins(int mins) {
        int hours = mins / 60;
        mins = mins - (hours * 60);
        if (hours >= 24) {
            int days = hours / 24;
            hours = hours - (days * 24);
            return days + "d " + hours + "h";
        }
        return hours + "h " + mins + "m";
    }

    public static String fromSeconds(int secs) {
        int mins = secs / 60;
        secs = secs - (mins * 60);
        if (mins >= 24) {
            int hours = mins / 60;
            mins = mins - (hours * 60);
            return hours + "h " + mins + "m";
        }
        return mins + "m " + secs + "s";
    }

    public static String substringBetween(final String str, final String open, final String close) {
        final int start = str.indexOf(open);
        if (start != -1) {
            int end = -2;
            end = str.indexOf(close, start + open.length());
            if (end != -1) {
                return str.substring(start + open.length(), end);
            }
        }
        return null;
    }

}