package net.todd.scorekeeper;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

public class Constants {
    public static final String WINNER_NAME = "winner_name";
    public static final SimpleDateFormat DATE_STORAGE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final SimpleDateFormat DATE_DISPLAY_FORMAT = new SimpleDateFormat("MM/dd/yy");

    public static final DecimalFormat POINT_DISPLAY_FORMAT = new DecimalFormat("###,###");
}
