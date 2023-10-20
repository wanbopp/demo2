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

//    public static void main(String[] args) {
//        int[] ints = new int[]{0, 0, 1, 1, 1, 2, 2, 3, 3, 4};
//        Solution solution = new Solution();
//        int i = solution.removeDuplicates1(ints);
//    }


    /**
     * 80.删除有序数组的重复项 II
     *
     * @param nums
     * @return
     * @Description 给你一个有序数组 nums 请你原地删除重复出现的元素
     * 使得出现次数超过两次的元素只出现两次 返回删除后数组的新长度。
     * 不要使用额外的数组空间，你必须在原地修改输入数组 并在使用 O(1)额外空间的条件下完成。
     */
    public int removeDuplicates2(int[] nums) {
//        输入：nums = [0,0,1,1,1,1,2,3,3]
//        输出：7, nums = [0,0,1,1,2,3,3]
        //关键是有序
        //无论slow前面的两个数是否重复 slow与fast的自身交换
        // slow 标记需要更换的位置，fast一直向前只需一次遍历
        int slow = 2;
        int fast = 2;
        while (fast < nums.length) {
            if (nums[slow - 2] != nums[fast]) {
                nums[slow] = nums[fast];
                slow++;
            }
            fast++;
        }
        return slow;
    }

//    public static void main(String[] args) {
//        int[] nums = {0, 0, 1, 1, 1, 1, 2, 2, 3, 3, 4, 4};
//        int i = new Solution().removeDuplicates2(nums);
//    }

/**
 * 在阅读本题前，读者们可以先尝试解决「26. 删除有序数组中的重复项」。
 *
 * 因为给定数组是有序的，所以相同元素必然连续。我们可以使用双指针解决本题，遍历数组检查每一个元素是否应该被保留，如果应该被保留，就将其移动到指定位置。具体地，我们定义两个指针 slow\textit{slow}slow 和 fast\textit{fast}fast 分别为慢指针和快指针，其中慢指针表示处理出的数组的长度，快指针表示已经检查过的数组的长度，即 nums[fast]\textit{nums}[\textit{fast}]nums[fast] 表示待检查的第一个元素，nums[slow−1]\textit{nums}[\textit{slow} - 1]nums[slow−1] 为上一个应该被保留的元素所移动到的指定位置。
 *
 * 因为本题要求相同元素最多出现两次而非一次，所以我们需要检查上上个应该被保留的元素 nums[slow−2]\textit{nums}[\textit{slow} - 2]nums[slow−2] 是否和当前待检查元素 nums[fast]\textit{nums}[\textit{fast}]nums[fast] 相同。当且仅当 nums[slow−2]=nums[fast]\textit{nums}[\textit{slow} - 2] = \textit{nums}[\textit{fast}]nums[slow−2]=nums[fast] 时，当前待检查元素 nums[fast]\textit{nums}[\textit{fast}]nums[fast] 不应该被保留（因为此时必然有 nums[slow−2]=nums[slow−1]=nums[fast]\textit{nums}[\textit{slow} - 2] = nums[\textit{slow} - 1] = \textit{nums}[\textit{fast}]nums[slow−2]=nums[slow−1]=nums[fast]）。最后，slow\textit{slow}slow 即为处理好的数组的长度。
 *
 * 特别地，数组的前两个数必然可以被保留，因此对于长度不超过 222 的数组，我们无需进行任何处理，对于长度超过 222 的数组，我们直接将双指针的初始值设为 222 即可。
 *
 * 作者：力扣官方题解
 * 链接：https://leetcode.cn/problems/remove-duplicates-from-sorted-array-ii/
 * 来源：力扣（LeetCode）
 * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
 */
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

    /**
     * 169.多数元素
     * 给定一个大小为 n 的数组 nums ，返回其中的多数元素。多数元素是指在数组中出现次数 大于 ⌊ n/2 ⌋ 的元素。
     * 你可以假设数组是非空的，并且给定的数组总是存在多数元素。
     * <p>
     * 注意分析 这样的条件会造成什么什么样的结果 从结果入手
     * <p>
     * 示例 1：
     * 输入：nums = [3,2,3]
     * 输出：3
     * <p>
     * 示例 2：
     * 输入：nums = [2,2,1,1,1,2,2]
     * 输出：2
     */
    public int majorityElement(int[] nums) {
        //排序
        //冒泡
        for (int i = 0; i < nums.length; i++) {
            for (int j = i; j < nums.length; j++) {
                if (nums[i] > nums[j]) {
                    int temp = nums[i];
                    nums[i] = nums[j];
                    nums[j] = temp;
                }
            }

        }
        //双指针找到一个出现次数大于一n/2的
        int left = 0;
        int right = 1;
        int num = 1;
        while (right < nums.length) {
            if (num > nums.length / 2) {
                break;
            }
            if (nums[left] != nums[right]) {
                left = right;
                right++;
                num = 1;
            } else {
                right++;
                num++;
            }
        }
        return nums[left];
    }

    //排序利用中间元素
    public int majorityElement1(int[] nums) {
        //排序
        //中间元素必定是出现次数大于 n/2 的元素
        for (int i = 0; i < nums.length; i++) {
            for (int j = i; j < nums.length; j++) {
                if (nums[i] > nums[j]) {
                    int temp = nums[i];
                    nums[i] = nums[j];
                    nums[j] = temp;
                }
            }

        }
        return nums[nums.length / 2];

    }
    //使用hash表记录次数


        public static void main(String[] args) {
        int[] ints = {1,3,1,1,4,1,1,5,1,1,6,2,2};
        int i = new Solution().majorityElement(ints);
    }


}
