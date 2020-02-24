package com.epam.izh.rd.online.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SimpleDateService implements DateService {

    /**
     * Метод парсит дату в строку
     *
     * @param localDate дата
     * @return строка с форматом день-месяц-год(01-01-1970)
     */
    @Override
    public String parseDate(LocalDate localDate) {
        String month = String.valueOf(localDate.getMonthValue());
        if(month.length()==1){
            month = "0" + month;
        }
        String day = String.valueOf(localDate.getDayOfMonth());
        if(day.length()==1){
            day = "0" + day;
        }
        return day + "-" + month + "-" + localDate.getYear();
    }

    /**
     * Метод парсит строку в дату
     *
     * @param string строка в формате год-месяц-день часы:минуты (1970-01-01 00:00)
     * @return дата и время
     */
    @Override
    public LocalDateTime parseString(String string) {
        return LocalDateTime.parse(string,DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

    /**
     * Метод конвертирует дату в строку с заданным форматом
     *
     * @param localDate исходная дата
     * @param formatter формат даты
     * @return полученная строка
     */
    @Override
    public String convertToCustomFormat(LocalDate localDate, DateTimeFormatter formatter) {
        return localDate.format(formatter);
    }

    /**
     * Метод получает следующий високосный год
     *
     * @return високосный год
     */
    @Override
    public long getNextLeapYear() {
        LocalDate date= LocalDate.now();
        if(date.isLeapYear()){
            return date.getYear();
        }
        do {
            date = date.plusYears(1);
        }
        while (!date.isLeapYear());
        return date.getYear();
    }

    /**
     * Метод считает число секунд в заданном году
     *
     * @return число секунд
     */
    @Override
    public long getSecondsInYear(int year) {
        long result =0l;
        if(LocalDate.of(year,1,1).isLeapYear()){
            result = 60*60*24*366;
        }
        else {
            result = 60*60*24*365;
        }
        return result;
    }


}
