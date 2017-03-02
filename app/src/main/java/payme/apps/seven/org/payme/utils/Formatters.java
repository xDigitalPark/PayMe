package payme.apps.seven.org.payme.utils;

import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import payme.apps.seven.org.payme.PaymeApplication;
import payme.apps.seven.org.payme.R;

public class Formatters {
    private Context context;
    public Formatters(Context context) {
        this.context = context;
    }

    public String formatDate(Long millis) {
        Date date=new Date(millis);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        String day_str = ((day<10)?"0":"") + day;
        int year = cal.get(Calendar.YEAR);
        String year_str = ("" + year).substring(2);
        String dateText = day_str + " - " + getMonth(cal.get(Calendar.MONTH)) + " - " + year_str;
        return dateText;
    }

    private String getMonth(int month) {
        month++;
        switch (month) {
            case 1:
                return context.getString(R.string.month_january);
            case 2:
                return context.getString(R.string.month_february);
            case 3:
                return context.getString(R.string.month_march);
            case 4:
                return context.getString(R.string.month_april);
            case 5:
                return context.getString(R.string.month_may);
            case 6:
                return context.getString(R.string.month_june);
            case 7:
                return context.getString(R.string.month_july);
            case 8:
                return context.getString(R.string.month_august);
            case 9:
                return context.getString(R.string.month_september);
            case 10:
                return context.getString(R.string.month_october);
            case 11:
                return context.getString(R.string.month_november);
            case 12:
                return context.getString(R.string.month_december);
            default:
                return null;
        }
    }
}
