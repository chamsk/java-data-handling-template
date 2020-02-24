package com.epam.izh.rd.online.service;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SimpleRegExpService implements RegExpService {

    /**
     * Метод должен читать файл sensitive_data.txt (из директории resources) и маскировать в нем конфиденциальную информацию.
     * Номер счета должен содержать только первые 4 и последние 4 цифры (1234 **** **** 5678). Метод должен содержать регулярное
     * выражение для поиска счета.
     *
     * @return обработанный текст
     */
    @Override
    public String maskSensitiveData() {
        String result = "";
        Pattern pattern;
        Matcher matcher;
        StringBuffer sf = new StringBuffer();
        try( BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("src/main/resources/sensitive_data.txt")))) {
            pattern = Pattern.compile("\\d{4}\\s\\d{4}\\s\\d{4}\\s\\d{4}");
            while (reader.ready()){
                matcher = pattern.matcher(reader.readLine());
                  while (matcher.find()) {
                      String[] temp = matcher.group().split(" ");
                      temp[1] = "****";
                      temp[2] = "****";
                      matcher.appendReplacement(sf, temp[0] + " " + temp[1] + " " + temp[2] + " " + temp[3]);
                  }
                matcher.appendTail(sf);
                result+=sf;
                sf.setLength(0);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Метод должен считыввать файл sensitive_data.txt (из директории resources) и заменять плейсхолдер ${payment_amount} и ${balance} на заданные числа. Метод должен
     * содержать регулярное выражение для поиска плейсхолдеров
     *
     * @return обработанный текст
     */
    @Override
    public String replacePlaceholders(double paymentAmount, double balance) {
        String result = "";
        Pattern paymentPattern = Pattern.compile("\\$\\{payment_amount\\}");;
        Pattern balancePattern = Pattern.compile("\\$\\{balance\\}");;
        Matcher matcher;
        StringBuffer buffer = new StringBuffer();
        try( BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("src/main/resources/sensitive_data.txt")))) {
            while (reader.ready()){
                String temp = "";
                matcher = paymentPattern.matcher(reader.readLine());
                   while (matcher.find()) {
                       matcher.appendReplacement(buffer, String.format("%.0f", paymentAmount));
                   }
                matcher.appendTail(buffer);
                temp+=buffer;
                buffer.setLength(0);
                matcher = balancePattern.matcher(temp);
                  while (matcher.find()){
                     matcher.appendReplacement(buffer,String.format("%.0f", balance));
                 }
                matcher.appendTail(buffer);
                temp=String.valueOf(buffer);
                buffer.setLength(0);
                result += temp;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
