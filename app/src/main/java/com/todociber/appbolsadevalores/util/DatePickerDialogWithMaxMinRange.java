package com.todociber.appbolsadevalores.util;

/**
 * Created by Todociber on 22/10/2016.
 */

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.util.Log;
import android.widget.DatePicker;

import java.util.Calendar;


public class DatePickerDialogWithMaxMinRange extends DatePickerDialog {
    private int maxYear = 0;
    private int maxMonth = 0;
    private int maxDay = 0;

    private int minYear = 0;
    private int minMonth = 0;
    private int minDay = 0;
    private Calendar m_minDateCalendar, m_maxDateCalendar, m_calendar;

    private boolean m_fired = false;

    @SuppressLint("NewApi")
    public DatePickerDialogWithMaxMinRange(Context context,
                                           OnDateSetListener callBack, Calendar p_minCalendar,
                                           Calendar p_maxCalendar, Calendar p_currentCalendar) {
        super(context, callBack, p_currentCalendar.get(Calendar.YEAR),
                p_currentCalendar.get(Calendar.MONTH), p_currentCalendar
                        .get(Calendar.DAY_OF_MONTH));

        m_minDateCalendar = (Calendar) p_minCalendar.clone();
        m_maxDateCalendar = (Calendar) p_maxCalendar.clone();
        m_calendar = (Calendar) p_currentCalendar.clone();

        this.minDay = m_minDateCalendar.get(Calendar.DAY_OF_MONTH);
        this.minMonth = m_minDateCalendar.get(Calendar.MONTH);
        this.minYear = m_minDateCalendar.get(Calendar.YEAR);
        this.maxDay = m_maxDateCalendar.get(Calendar.DAY_OF_MONTH);
        this.maxMonth = m_maxDateCalendar.get(Calendar.MONTH);
        this.maxYear = m_maxDateCalendar.get(Calendar.YEAR);

        try {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
                m_fired = false;
                this.getDatePicker().setMaxDate(
                        m_maxDateCalendar.getTime().getTime());
                this.getDatePicker().setMinDate(
                        m_minDateCalendar.getTime().getTime());
            } else {
                m_fired = true;
            }
        } catch (Throwable p_e) {
			/*
			 * Have suppressed the exception Time not between max and min date
			 * range The exception comes when the selected date is few
			 * milliseconds more or less than min/max date in devices of android
			 * 3.0 and above
			 */
            Log.d("m_maxDateCalendar", ""
                    + m_maxDateCalendar.getTime().getTime());
            Log.d("m_minDateCalendar", ""
                    + m_minDateCalendar.getTime().getTime());
            Log.d("m_calendar", "" + m_calendar.getTime().getTime());
            p_e.printStackTrace();
        }
    }

    @SuppressLint("LongLogTag")
    @Override
    public void onDateChanged(DatePicker p_view, int p_year, int p_monthOfYear,
                              int p_dayOfMonth) {
        super.onDateChanged(p_view, p_year, p_monthOfYear, p_dayOfMonth);

        if (m_fired) {
            m_calendar.set(Calendar.YEAR, p_year);
            m_calendar.set(Calendar.MONTH, p_monthOfYear);
            m_calendar.set(Calendar.DAY_OF_MONTH, p_dayOfMonth);

            if (m_calendar.after(m_maxDateCalendar)) {
                m_fired = true;
                if (maxMonth > 11) {
                    maxMonth = 11;
                }
                p_view.updateDate(maxYear, maxMonth, maxDay);
                Log.d("In onDateChanged() method", "fired==>" + m_fired);
            } else if (m_calendar.before(m_minDateCalendar)) {
                m_fired = true;
                if (minMonth > 11) {
                    minMonth = 11;
                }
                p_view.updateDate(minYear, minMonth, minDay);
                Log.d("In onDateChanged() method", "fired==>" + m_fired);
            } else {
                m_fired = true;
                p_view.updateDate(p_year, p_monthOfYear, p_dayOfMonth);
                Log.d("In onDateChanged() method", "fired==>" + m_fired);
            }
        } else {
            Log.d("In onDateChanged() method", "fired==>" + m_fired);
        }
    }

}

