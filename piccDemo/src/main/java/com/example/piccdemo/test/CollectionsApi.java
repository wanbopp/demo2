package com.example.piccdemo.test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

/**
 * @version 1.0
 * @Author wanbo_pan
 * @Date 2023/9/14 10:36
 * @注释
 */
public class CollectionsApi {
    public static void main(String[] args) {
//        BigDecimal proportion = new BigDecimal(1);
//        BigDecimal proportion1 = new BigDecimal(3);
//
//        System.out.println(proportion.divide(proportion1, 10, BigDecimal.ROUND_HALF_UP));


//        List<String> list = Arrays.asList("1", "2", "3", "4", "5");
//        List<String> list1 = Arrays.asList("2", "3", "4", "5");
//        list.removeAll(list1);
//        System.out.println("list = " + list);
         // 一个简单的冒泡排序
int[] arr = {6, 3, 8, 2, 9, 1};
int temp = 0;
for (int i = 0; i < arr.length - 1; i++) {
    for (int j = 0; j < arr.length - 1 - i; j++) {
        if (arr[j] > arr[j + 1]) {
            temp = arr[j];
            arr[j] = arr[j + 1];
            arr[j + 1] = temp;
        }

}

    }
}
