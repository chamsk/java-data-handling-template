package com.epam.izh.rd.online.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class SimpleBigNumbersService implements BigNumbersService {

    /**
     * Метод делит первое число на второе с заданной точностью
     * Например 1/3 с точностью 2 = 0.33
     * @param range точность
     * @return результат
     */
    @Override
    public BigDecimal getPrecisionNumber(int a, int b, int range) {
        return BigDecimal.valueOf(a).divide(BigDecimal.valueOf(b),range,BigDecimal.ROUND_DOWN);
    }

    /**
     * Метод находит простое число по номеру
     *
     * @param range номер числа, считая с числа 2
     * @return простое число
     */
    @Override
    public BigInteger getPrimaryNumber(int range) {
        List<BigInteger> numbers = new ArrayList<>();
        numbers.add(BigInteger.valueOf(2));
        BigInteger number = BigInteger.valueOf(2);
        while (true){
            boolean flag = true;
            number = number.add(BigInteger.ONE);
            BigInteger dividend = BigInteger.valueOf(3);
            if(number.mod(BigInteger.valueOf(2)).equals(BigInteger.valueOf(0))){
                flag = false;
            }
             while (true){
                  if(dividend.compareTo(number.subtract(BigInteger.ONE))>=0){
                     break;
                  }
                  if(number.mod(dividend).equals(BigInteger.valueOf(0))){
                      flag = false;
                  }
                 dividend =  dividend.add(BigInteger.valueOf(2));
               }
            if(flag == true){
                numbers.add(number);
            }
            if(numbers.size()==range+1){
                break;
            }
        }
        return numbers.get(range);
    }
}
