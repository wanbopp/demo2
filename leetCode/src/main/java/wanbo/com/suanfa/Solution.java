package wanbo.com.suanfa;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.Period;
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


//    public static void main(String[] args) {
//        int[] ints = {1, 3, 1, 1, 4, 1, 1, 5, 1, 1, 6, 2, 2};
//        int i = new Solution().majorityElement(ints);
//    }


    /**
     * 189. 轮转数组
     * 给定一个整数数组 nums，将数组中的元素向右轮转 k 个位置，其中 k 是非负数
     * <p>
     * 示例 1:
     * <p>
     * 输入: nums = [1,2,3,4,5,6,7], k = 3
     * 输出: [5,6,7,1,2,3,4]
     * 解释:
     * 向右轮转 1 步: [7,1,2,3,4,5,6]
     * 向右轮转 2 步: [6,7,1,2,3,4,5]
     * 向右轮转 3 步: [5,6,7,1,2,3,4]
     * 示例 2:
     * <p>
     * 输入：nums = [-1,-100,3,99], k = 2
     * 输出：[3,99,-1,-100]
     * 解释:
     * 向右轮转 1 步: [99,-1,-100,3]
     * 向右轮转 2 步: [3,99,-1,-100]
     * <p>
     * 提示
     * 给定一个整数数组 nums 将数组中的元素向右轮转k个位置 其中k是非负数
     * 尽可能想出更多的解决方案，至少有三种不同的方法可以解决这个问题。
     * 你可以使用空间复杂度为O(1)的原地算法解决这个问题吗
     */
    //P1.暴力尝试
    public void rotate(int[] nums, int k) {
        //双指针尝试
        //考虑K可能大于数组长度 也有可能等于零
        if (k > nums.length) {
            //取余数
            k = k % nums.length;
        } else if (k % nums.length == 0) {
            return;
        }
        int num = nums.length - k;

        while (num > 0) {
            //平移一位
            int n = 0;
            while (n < k) {
                int temp = nums[num + n - 1];
                nums[num + n - 1] = nums[num + n];
                nums[num + n] = temp;
                n++;
            }
            num--;
        }
        System.out.println("nums = " + Arrays.toString(nums));
    }

    //P2.环状替换
    public void rotate1(int[] nums, int k) {
        // nums = [1,2,3,4,5,6,7]

    }

    //P3.数组翻转
    public void rotate2(int[] nums, int k) {
        // nums = [1,2,3,4,5,6,7]
        k = k % nums.length;
        //1、先翻转所有元素
        reverse(nums, 0, nums.length - 1);
        //2、分开两个区间、需要移动的元素  和   原始元素同时进行翻转
        reverse(nums, 0, k - 1);
        reverse(nums, k, nums.length - 1);
        System.out.println("nums = " + Arrays.toString(nums));
    }

    public void reverse(int[] nums, int start, int end) {
        while (start < end) {
            int temp = nums[start];
            nums[start] = nums[end];
            nums[end] = temp;
            start++;
            end--;
        }
    }


//    public static void main(String[] args) {
//        int[] nums = {1, 2, 3, 4, 5, 6, 7};
//        new Solution().rotate2(nums, 4);
//    }

    /**
     * 121.买股票的最佳时机
     * 给定一个数组 prices ，它的第 i 个元素 prices[i] 表示一支给定股票第 i 天的价格。
     * 你只能选择 某一天 买入这只股票，并选择在 未来的某一个不同的日子 卖出该股票。设计一个算法来计算你所能获取的最大利润。
     * 返回你可以从这笔交易中获取的最大利润。如果你不能获取任何利润，返回 0
     * <p>
     * 示例 1：
     * <p>
     * 输入：[7,1,5,3,6,4]
     * 输出：5
     * 解释：在第 2 天（股票价格 = 1）的时候买入，在第 5 天（股票价格 = 6）的时候卖出，最大利润 = 6-1 = 5 。
     * 注意利润不能是 7-1 = 6, 因为卖出价格需要大于买入价格；同时，你不能在买入前卖出股票。
     * 示例 2：
     * <p>
     * 输入：prices = [7,6,4,3,1]
     * 输出：0
     * 解释：在这种情况下, 没有交易完成, 所以最大利润为 0
     *
     * @param prices
     * @return
     */
    //P1 找到差额最大值
    public int maxProfit(int[] prices) {
        //一次遍历，同时记录过往价格最小值和最大利润
        //遍历时 最小值与当前值作比较、最大利润与当前利润比较
        int minPrice = Integer.MAX_VALUE;
        int maxProfit = 0;
        for (int price : prices) {
            if (price < minPrice) {
                minPrice = price;
            } else if (price - minPrice > maxProfit) {
                maxProfit = price - minPrice;
            }
        }
        return maxProfit;

    }

    //P2 通解 动态规划
    public int maxProfit1(int[] prices) {
        //判断价格是否存在
        if (prices == null || prices.length == 0) {
            return 0;
        }
        //初始化状态
        int length = prices.length;
        int[][] dp = new int[length][2];//二维数组
        //一维代表第几天 二维代表能交易几次  对应的值代表当天的收益 此时还要区分两个状态 当天是否持有股票
        dp[0][0] = 0;//第一天不持有股票 此时收益为0
        dp[0][1] = -prices[0]; //第一天持有股票价格 为第一天的价格
        for (int i = 1; i < length; i++) {
            //当天的两种情况
            dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1] + prices[i]);
            //最后一天不持有股票
            //最后一天之前已经没有了交易次数 表示之前已经买入 分为在昨天买入（今天卖出）和昨天不持有
            dp[i][1] = Math.max(dp[i - 1][1], -prices[i]);
            //最后一天持有股票
            //最后一天持有股票的的情况 表示只已经买入 分为昨天买入和今天买入

        }
        return dp[length - 1][0];

    }

    /**
     * 使用动态规划来解决这个问题，可以定义两个状态变量
     * 一个用于表示在第 i 天持有股票的最大利润，另一个用于表示在第 i 天不持有股票的最大利润。
     * 然后使用状态转移方程来更新这两个状态变量。
     * <p>
     * <p>
     * <p>
     * 这段代码使用动态规划来计算最大利润
     * 它在每一天考虑持有股票和不持有股票两种状态 并更新状态变量。
     * 最后返回最后一天不持有股票时的最大利润，即最终的最大利润。
     * 这个算法的时间复杂度为 O(n)，其中 n 是股票价格数组的长度。
     *
     * @param prices
     * @return
     */
    public int maxProfit2(int[] prices) {
        int n = prices.length;
        if (n == 0) {
            return 0; // 没有股票价格数据，无法获得利润
        }

        int[] dpHold = new int[n];  // 用于记录持有股票时的最大利润
        int[] dpNotHold = new int[n];  // 用于记录不持有股票时的最大利润

        dpHold[0] = -prices[0];  // 第一天持有股票的最大利润是负的买入价格
        dpNotHold[0] = 0;  // 第一天不持有股票的最大利润为0

        for (int i = 1; i < n; i++) {
            dpHold[i] = Math.max(dpHold[i - 1], -prices[i]);  // 在第 i 天持有股票，可以选择不操作或者买入
            //注意前一天不持有 今天买入 （只有这一次买入 注意关键点）
            dpNotHold[i] = Math.max(dpNotHold[i - 1], dpHold[i - 1] + prices[i]);  // 在第 i 天不持有股票，可以选择不操作或者卖出
        }

        return dpNotHold[n - 1];  // 返回最后一天不持有股票的最大利润，即最终的最大利润
    }


//    public static void main(String[] args) {
//        int[] prices = {7, 1, 5, 3, 6, 4};
//        int i = new Solution().maxProfit(prices);
//        System.out.println("i = " + i);


//        List<String> list = Arrays.asList("1", "2", "3");
//        list.add("4");
//
//    }

    /**
     * 121.买股票的最佳时机 II
     * 给你一个整数数组 prices ，其中 prices[i] 表示某支股票第 i 天的价格
     * 在每一天，你可以决定是否购买和/或出售股票。你在任何时候 最多 只能持有 一股 股票。你也可以先购买，然后在 同一天 出售
     * 返回 你能获得的 最大 利润 。
     * <p>
     * <p>
     * <p>
     * 示例 1：
     * 输入：prices = [7,1,5,3,6,4]
     * 输出：7
     * 解释：在第 2 天（股票价格 = 1）的时候买入，在第 3 天（股票价格 = 5）的时候卖出, 这笔交易所能获得利润 = 5 - 1 = 4
     * 随后，在第 4 天（股票价格 = 3）的时候买入，在第 5 天（股票价格 = 6）的时候卖出, 这笔交易所能获得利润 = 6 - 3 = 3
     * 总利润为 4 + 3 = 7
     * <p>
     * 示例 2：
     * 输入：prices = [1,2,3,4,5]
     * 输出：4
     * 解释：在第 1 天（股票价格 = 1）的时候买入，在第 5 天 （股票价格 = 5）的时候卖出, 这笔交易所能获得利润 = 5 - 1 = 4
     * 总利润为 4
     * <p>
     * 示例 3：
     * 输入：prices = [7,6,4,3,1]
     * 输出：0
     * 解释：在这种情况下, 交易无法获得正利润，所以不参与交易可以获得最大利润，最大利润为 0
     * <p>
     * <p>
     * 题解如果K为正无穷，则K和K-1可以看成是相同的，因此有
     * T[i-1][k-1][0] = T[i-1][k][0]
     * T[i-1][k-1][1] = T[i-1][k][1]
     * 每天仍有两个未知变量：T[i][k][0]和T[i][k][1]其中K为正无穷
     */
    public int maxProfit3(int[] prices) {
        if (prices == null || prices.length == 0) {
            return 0;//初始状态判断
        }
        int length = prices.length;
        int[][] dp = new int[length][2];
        dp[0][1] = -prices[0];
        dp[0][0] = 0;
        for (int i = 1; i < length; i++) {
            //注意这里 K都一致 可以取消
            dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1] + prices[i]);
            dp[i][1] = Math.max(dp[i - 1][1], dp[i][0] - prices[i]);//注意这个关键
        }
        return dp[length - 1][0];
    }


    /**
     * 55.跳跃游戏
     * 给你一个非负整数数组，你最初位于数组的第一个下标，
     * 数组中的每个元素代表你在该位置可以跳跃的最大长度。
     * 判断你是否能到达最后一个下标，如果可以返回true；否则返回false
     * 示例 1：
     * <p>
     * 输入：nums = [2,3,1,1,4]
     * 输出：true
     * 解释：可以先跳 1 步，从下标 0 到达下标 1, 然后再从下标 1 跳 3 步到达最后一个下标。
     * 示例 2：
     * <p>
     * 输入：nums = [3,2,1,0,4]
     * 输出：false
     * 解释：无论怎样，总会到达下标为 3 的位置。但该下标的最大跳跃长度是 0 ， 所以永远不可能到达最后一个下标。
     * <p>
     * 提示：
     * 1 <= nums.length <= 104
     * 0 <= nums[i] <= 105
     *
     * @param nums
     * @return
     */

//    public boolean canJump(int[] nums) {

//        TODO 以下是错误的
//        //为什么不能到 像实例二除了这个还有别的情况嘛
//        //存在递减的 3、1、1、0  3、0、0、0
//        //只要掉进这段就必定到达不了
//        //0前面存在一个大数 到0的距离正好的是数值 且每个数都小于大数到0的降序排列
//
//        //记录最大数  不符合条件则重新计数  直到符合条件或者到达数组边界
//        //条件是
//
//        //如果直接遍历最大数 判断到下一个0的位置 倒序遍历 如果等于=0 判断之后的值是否存在大于位序的值 存在即可
//        //继续遍历到下一个零的位置
//
//        int length = nums.length;
//        int point = -1;//0的位置
//        boolean result = true;//考虑初始能不能过去
//
//        if (nums.length == 0 || (nums.length == 1 && nums[0] == 0)) {
//            return true;
//        }
//
//        for (int i = length - 1; i >= 0; i--) {
//
//
//
////            if (nums[i] == 0) {//出现0 就可能过不去
////                //处理间断的情况 前面有0 到这个0 无法跳过
////                if (point != -1 && !result && i+1 != length) {
////                    return false;
////                }
////                point = i;
////                result = false;
////                continue;
////            }  不对能不能跳过  需要看前面所有的值
//
//
//
//
//
//            if (nums[i] > point - i && point != -1) {
//                //此时已经有0
//                //判断当前值 是否大于到0 的位序差
//                //小于不操作 result=false 大于point =-1 result= true;
//                point = -1;
//                result = true;
//            }
//            if (nums[i] >= point - i && point == length - 1) {
//                point = -1;
//                result = true;
//            }
//
//        }
//        return result;
//    }

    /**
     * 倒排 判断这个点 能否被跳跃 如果可以重置判断点
     * 不关注0 关注当前点 妙处是区间可以省略
     *
     * @param nums
     * @return
     */
    public boolean canJump(int[] nums) {
        int len = nums.length;
        if (len == 1) {
            return true;
        }
        int flag = len - 1;
        for (int i = len - 2; i >= 0; --i) {
            // 找到可以跳跃此位置的地方就更新跳跃位置
            if (flag - i <= nums[i]) {
                flag = i;
            }
            // 如果跳跃位置到了0就说明可以
            if (flag == 0 && i == 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * 贪心算法
     * 对于数组中的任意位置y,如何判断它是否可以到达？
     * 只要存在一个位置x,它本身可以到达，且x+nums[x] >= y 那么y也可以到达
     * 换句话说对于每一个可以到达的位置x 它是得x+1 ..... x+nums[x]都可以到达
     * 这样我们一次遍历书中的每一个位置，并实时维护最远可达到的位置
     * 对于当前遍历到的位置x,如果它在当前能达到位置的范围内,那么我们认为x位置可以从七点通过几次跳跃达到,而且当前最远可以达到位置为x+nums[x]
     * 在遍历过程中，如果最远可以达到的位置大于等于数组的最后一个位置，直接返回true 。反之结束后最后一个位置还是不可到达，则返回false
     *
     * @param nums
     * @return
     */
    public boolean canJump1(int[] nums) {
        int max = 1;
        if (nums.length == 1) {
            return true;
        }
        for (int i = 0; i < nums.length; i++) {
            //最远到达点大于数组长度
            if (max >= nums.length) {
                return true;
            }
            //当前点不可到达
            if (i > max - 1) {
                return false;
            }
            //更新最远达到位置
            max = i + 1 + nums[i] > max ? i + 1 + nums[i] : max;

        }
        return false;
    }

    /**
     * 45.跳跃游戏 II
     * 给定一个长度为 n 的 0 索引整数数组 nums。初始位置为 nums[0]。
     * 每个元素 nums[i] 表示从索引 i 向前跳转的最大长度。换句话说，如果你在 nums[i] 处，你可以跳转到任意 nums[i + j] 处:
     * <p>
     * 0 <= j <= nums[i]
     * i + j < n
     * 返回到达 nums[n - 1] 的最小跳跃次数。生成的测试用例可以到达 nums[n - 1]。
     * <p>
     * 示例 1:
     * 输入: nums = [2,3,1,1,4]
     * 输出: 2
     * 解释: 跳到最后一个位置的最小跳跃数是 2。
     * 从下标为 0 跳到下标为 1 的位置，跳 1 步，然后跳 3 步到达数组的最后一个位置。
     * 示例 2:
     * 输入: nums = [2,3,0,1,4]
     * 输出: 2
     * <p>
     * <p>
     * 提示:
     * 1 <= nums.length <= 104
     * 0 <= nums[i] <= 1000
     * 题目保证可以到达 nums[n-1]
     */
    //p1 贪心算法
    public int jump(int[] nums) {
        //计数次数
        int ans = 0;

        //标记每次跳跃的能达到的区间
        int left = 0;
        int right = 1;//能跳过第一个肯定不是0
        //遍历这个区间 这个区间的点能达到的最大区间
        while (right < nums.length) {
            int max = 0;
            for (int i = left; i < right; i++) {
                max = Math.max(max, nums[i] + i);//这里i在区间内前进
            }
            //重置下次遍历的起始位置
            left = right;
            right = max + 1;
            ans++;
        }
        return ans;
    }

    /**
     * 274. H 指数
     * 给你一个整数数组 citation ，其中citation[i] 表示研究者的第 i 篇论文被引用的次数。
     * 计算病返回该研究者的 H 指数
     * <p>
     * 根据维基百科上 H 指数的定义：H 代表高引用次数
     * 一名科研人员的 H 指数，是指他至少发表了 H 篇论文，并且每篇至少被引用 H 次。如果有多重可能的值，H 指数是指其中最大的那个
     * <p>
     * 示例 1：
     * 输入：citation = [3,0,6,1,5]
     * 解释：给定数组表示研究者共计五篇论文，每篇论文相应的被引用了 3,0,6,1,5 次
     * 由于研究者有三篇论文每篇至少被引用了3次，其余两篇每篇论文被引用不多于3次，所以他的H指数是 3
     * <p>
     * 示例 2：
     * 输入：citations = [1,3,1]
     * 输出：1
     * <p>
     * 提示：
     * n == citations.length
     * 1 <= n <= 5000
     * 0 <= citations[i] <= 1000
     * <p>
     * 【数组】【计数排序】【排序】
     */
    //P1 排序后遍历
    public int hIndex(int[] citations) {
        if (citations.length == 1) {
            return citations[0] > 0 ? 1 : 0;
        }
        //冒泡倒叙排序
        for (int i = citations.length - 1; i > 0; i--) {
            boolean flag = true;//增加一个标示 如果此次遍历没有发生交换表明已经 排序完成
            for (int j = 0; j < i; j++) {//冒泡排序要最大的相当于 把最小的往后挪 挪到最后一位  下次遍历可以减少遍历次数
                if (citations[j] < citations[j + 1]) {
                    int temp = citations[j + 1];
                    citations[j + 1] = citations[j];
                    citations[j] = temp;
                    flag = false;
                }
            }
            if (flag) {
                break;
            }
        }

        //遍历这个集合 利用H指数的特性 数组从大到小排序后 citation[h] >= h+1 满足H指数 继续向后遍历
        for (int i = 0; i < citations.length; i++) {
            if (citations[i] < i + 1) {
                return i;
            }
        }

        return citations.length;

    }

    //P2 计数排序
    //P3二分搜索

    public static void main(String[] args) {
        int i = new Solution().hIndex(new int[]{11,15});

    }


}
