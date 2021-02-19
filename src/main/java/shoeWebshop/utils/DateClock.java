package shoeWebshop.utils;

import javafx.geometry.Pos;
import javafx.scene.control.Label;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateClock {

    public DateClock(Label label) {
        String dateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        label.setText(dateTime);
        label.setAlignment(Pos.CENTER_RIGHT);
        label.setVisible(true);
    }

}
