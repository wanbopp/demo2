package wanbo.com.suanfa;

import java.util.Arrays;

/**
 * @Author wanbo_pp
 * @Date 2025/2/10 14:50
 * @description: 常用排序算法
 */
public class sort {

    /**
     * 快速排序
     * 一种基于分治的排序算法，选择一个基准值，
     * 然后将数组划分为两部分，递归排序这两部分，最终完成排序
     * 优化点在在于基准值的选取（一般是选择第一个或者最后一个元素）
     */
    public static void quickSort(int[] arr, int left, int right) {
        if (left < right) {
            int pivotIndex = partition(arr, left, right);
            quickSort(arr, left, pivotIndex);//左边递归
            quickSort(arr, pivotIndex + 1, right);//右边递归
        }
    }

    private static int partition(int[] arr, int left, int right) {
        //选择最左边元素为基准
        int pivot = arr[left];
        int i = left - 1;
        int j = right + 1;
        while (true) {
            do {
                i++;
            } while (arr[i] < pivot);

            do {
                j--;
            } while (arr[j] > pivot);

            if (i >= j) {
                return j;
            }
            swap(arr, i, j);

        }


    }

    public static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static void main(String[] args) {
        int[] arr = {3, 6, 8, 10, 1, 2, 1};
        quickSort(arr, 0, arr.length - 1);
        System.out.println(Arrays.toString(arr)); // [1, 1, 2, 3, 6, 8, 10]
    }

}
