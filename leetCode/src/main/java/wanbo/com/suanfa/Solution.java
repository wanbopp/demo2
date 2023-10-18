package wanbo.com.suanfa;

import java.util.*;

/**
 * @version 1.0
 * @Author wanbo_pan
 * @Date 2023/10/16 9:05
 * @注释
 */
public class Solution {

    public void merge(int[] nums1, int m, int[] nums2, int n) {
        // 最简单的合并-排序
//        for (int i = 0; i < n; i++) {
//            nums1[m + i] = nums2[i];
//        }
//        boolean flag = true;
//        // 冒泡排序
//        for (int i = 0; i < nums1.length; i++) {
//            for (int j = i; j < nums1.length - 1; j++) {
//                if (nums1[j] > nums1[j + 1]) {
//                    int temp = nums1[j];
//                    nums1[j] = nums1[j + 1];
//                    nums1[j + 1] = temp;
//                    flag = false;
//                }
//
//            }
//
//            if (flag) {
//                break;
//            }
//        }
//        System.out.println("nums1 = " + Arrays.toString(nums1));

        //双指针
        int p1 = 0;
        int p2 = 0;
        int[] sorted = new int[m + n];
        int cur;
        while (p1 < m || p2 < n) {
            //先判断p1 p2是否已经到头了
            if (p1 == m) {
                cur = nums2[p2];
                p2++;
            } else if (p2 == n) {
                cur = nums1[p1];
                p1++;
            } else if (nums1[p1] < nums2[p2]) {
                cur = nums1[p1];
                p1++;
            } else {
                cur = nums2[p2];
                p2++;
            }
            //最后添加 利用p1 p2每次循环只增加一次
            sorted[p1 + p2 - 1] = cur;


        }
        nums1 = sorted;
        System.out.println("cur = " + Arrays.toString(nums1));
    }

    //27、移除元素
    public int removeElement(int[] nums, int val) {
        //思路是移除元素后排序，返回长度
        //循环标记最小值
        int i = 0;
        int min = 0;
        int flag = nums.length;
        while (i < nums.length) {
            if (min > nums[i]) {
                min = nums[i];
            }
            i++;
        }
        i = 0;
        //目标值设置为最小值 设置几次就相当于去除了几个
        while (i < nums.length) {
            if (nums[i] == val) {
                nums[i] = min - 1;
                flag--;
            }
            i++;
        }
        //冒泡排序 排序过程中将目标值设置为最小值减1
        for (int j = 0; j < nums.length; j++) {
            for (int k = 0; k < nums.length - 1; k++) {
                if (nums[k] < nums[k + 1]) {
                    int temp = nums[k];
                    nums[k] = nums[k + 1];
                    nums[k + 1] = temp;
                }
            }
        }
        return flag;

    }

//    class Solution {
//        public int removeElement(int[] nums, int val) {
//            int n = nums.length;
//            int left = 0;
//            for (int right = 0; right < n; right++) {
//                if (nums[right] != val) {
//                    nums[left] = nums[right];
//                    left++;
//                }
//            }
//            return left;
//        }
//    }

    //移除元素-双指针
    public int removeElement1(int[] nums, int val) {
        int n = nums.length;
        int right = nums.length - 1;
        int left = 0;
        while (right > left) {
            if (nums[right] == val) {
                right--;
                n--;
            } else if (nums[left] == val) {
                nums[left] = nums[right];
                left++;
                right--;
                n--;
            } else {
                left++;
            }
        }
        return n;
    }


    //26.移除重复元素
    public int removeDuplicates(int[] nums) {
//        [0,0,1,1,1,2,2,3,3,4]
        //双指针尝试
        int left = 1;
        int right = 1;
        HashSet<Integer> set = new HashSet<>();
        set.add(nums[0]);
        while (right < nums.length) {
            if (set.add(nums[right])) {
                nums[left] = nums[right];//交换
                //第一次出现
                //左指针+1 右指针=左指针+1
                left++;
                right = left;
            } else {
                right++;
            }
        }
        return left;
    }

    //双指针优化
    public int removeDuplicates1(int[] nums) {
        //利用有序
        //从第二个元素开始
        //left 不会回溯 只遍历一次
        int left = 1;
        int right = 1;


        while (right < nums.length) {
            if (nums[right] != nums[left - 1]) {
                nums[left] = nums[right];
                left++;
            }
            right++;
        }
        return left;


    }

    public static void main(String[] args) {
        int[] ints = new int[]{0, 0, 1, 1, 1, 2, 2, 3, 3, 4};
        Solution solution = new Solution();
        int i = solution.removeDuplicates1(ints);
    }


//    public static void main(String[] args) {
//        int[] nums1 = {1, 2, 3, 0, 0, 0};
//        int m = 3;
//        int[] nums2 = {2, 5, 6};
//        int n = 3;
//        Solution solution = new Solution();
//        solution.merge(nums1, m, nums2, n);
//    }

//    public static void main(String[] args) {
//        int[] nums = {0,1,2,2,3,0,4,2};
//        int val = 2;
//        Solution solution = new Solution();
//        int i = solution.removeElement1(nums, val);
//        System.out.println("i = " + i);
//    }


}
