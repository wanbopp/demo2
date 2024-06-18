package wanbo.com.suanfa;

import com.sun.deploy.util.StringUtils;
import javafx.scene.chart.Chart;
import lombok.Data;
import org.checkerframework.checker.units.qual.C;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @version 1.0
 * @Author wanbo_pan
 * @Date 2023/10/16 9:05
 * @注释
 */
@Data
public class Solution {
    private Integer num = 0;

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


    /**
     * 238. 除自身以外数组的乘积
     * 给你一个整数数组 nums，返回 数组 answer ，其中 answer[i] 等于 nums 中除 nums[i] 之外其余各元素的乘积 。
     * <p>
     * 题目数据 保证 数组 nums之中任意元素的全部前缀元素和后缀的乘积都在  32 位 整数范围内。
     * 请 不要使用除法，且在 O(n) 时间复杂度内完成此题。
     * <p>
     * 示例 1:
     * 输入: nums = [1,2,3,4]
     * 输出: [24,12,8,6]
     * <p>
     * 示例 2:
     * 输入: nums = [-1,1,0,-3,3]
     * 输出: [0,0,9,0,0]
     * <p>
     * 提示：
     * 2 <= nums.length <= 105
     * -30 <= nums[i] <= 30
     * 保证 数组 nums之中任意元素的全部前缀元素和后缀的乘积都在  32 位 整数范围内
     * <p>
     * 【数组】【前缀和】
     * 进阶：你可以在 O(1) 的额外空间复杂度内完成这个题目吗？（ 出于对空间复杂度分析的目的，输出数组 不被视为 额外空间。）
     */
    public int[] productExceptSelf(int[] nums) {
        //最简单的方法是使用除法

        //前缀乘积、后缀乘积
        //问题就转化为通过一次遍历求出前后缀元素的乘积


        //遍历
        int[] l = new int[nums.length];
        l[0] = 1;//注意这个初始值
        int[] r = new int[nums.length];
        r[nums.length - 1] = 1;

        int[] result = new int[nums.length];

        for (int i = 1; i < nums.length; i++) {
            l[i] = nums[i - 1] * l[i - 1];
        }
        for (int i = nums.length - 2; i >= 0; i--) {
            r[i] = nums[i + 1] * r[i + 1];
        }


        for (int i = 0; i < result.length; i++) {
            result[i] = l[i] * r[i];
        }

        return result;
    }

    //F2  空间复杂度 O(1)O(1)O(1) 的方法


    /**
     * 134. 加油站
     * <p>
     * 在一条环路上有 n 个加油站，其中第 i 个加油站有汽油 gas[i] 升。
     * 你有一辆油箱容量无限的的汽车，从第 i 个加油站开往第 i+1 个加油站需要消耗汽油 cost[i] 升。你从其中的一个加油站出发，开始时油箱为空。
     * 给定两个整数数组 gas 和 cost ，如果你可以按顺序绕环路行驶一周，则返回出发时加油站的编号，否则返回 -1 。<font color="red">如果存在解，则 保证 它是 唯一 的。</font>
     * <p>
     * 示例 1:
     * 输入: gas = [1,2,3,4,5], cost = [3,4,5,1,2]
     * 输出: 3
     * 解释:
     * 从 3 号加油站(索引为 3 处)出发，可获得 4 升汽油。此时油箱有 = 0 + 4 = 4 升汽油
     * 开往 4 号加油站，此时油箱有 4 - 1 + 5 = 8 升汽油
     * 开往 0 号加油站，此时油箱有 8 - 2 + 1 = 7 升汽油
     * 开往 1 号加油站，此时油箱有 7 - 3 + 2 = 6 升汽油
     * 开往 2 号加油站，此时油箱有 6 - 4 + 3 = 5 升汽油
     * 开往 3 号加油站，你需要消耗 5 升汽油，正好足够你返回到 3 号加油站。
     * 因此，3 可为起始索引。
     * <p>
     * 示例 2:
     * 输入: gas = [2,3,4], cost = [3,4,3]
     * 输出: -1
     * 解释:
     * 你不能从 0 号或 1 号加油站出发，因为没有足够的汽油可以让你行驶到下一个加油站。
     * 我们从 2 号加油站出发，可以获得 4 升汽油。 此时油箱有 = 0 + 4 = 4 升汽油
     * 开往 0 号加油站，此时油箱有 4 - 3 + 2 = 3 升汽油
     * 开往 1 号加油站，此时油箱有 3 - 3 + 3 = 3 升汽油
     * 你无法返回 2 号加油站，因为返程需要消耗 4 升汽油，但是你的油箱只有 3 升汽油。
     * 因此，无论怎样，你都不可能绕环路行驶一周。
     * <p>
     * 提示:
     * gas.length == n
     * cost.length == n
     * 1 <= n <= 105
     * 0 <= gas[i], cost[i] <= 104
     * <p>
     * <p>
     * 【数组】【贪心算法】
     *
     * @param gas
     * @param cost
     * @return
     */
    public int canCompleteCircuit(int[] gas, int[] cost) {
//        总结：如果x到不了y+1（但能到y），那么从x到y的任一点出发都不可能到达y+1。
//        因为从其中任一点出发的话，相当于从0开始加油，而如果从x出发到该点则不一定是从0开始加油，可能还有剩余的油。
//        既然不从0开始都到不了y+1，那么从0开始就更不可能到达y+1了...

        //P1 暴力破解
        //考虑从任意点出发
//        for (int i = 0; i < gas.length; i++) {
//            int j = i;
//            int remain = gas[i];//剩余
//
//            while (remain - cost[j] >= 0) {
//                //条件是能到达下一站
//
//                //更新燃料前进
//                remain = remain - cost[j] + gas[(j + 1) % gas.length];
//                //更新位置
//                j = (j + 1) % gas.length;
//
//                //如果回到了原点
//                if (j == i) {
//                    return i;
//                }
//            }
//
//        }
//        return -1;


        //分析暴力破解的缺陷
        //每一轮都会重复的走一遍i前面的位置
        //优化:每考虑一个点 时记录他能到达的最远位置和达到最远距离时剩余的油量

        //p2 记录能到到的最远位置
//        int[] farIndex = new int[gas.length];
//        for (int i = 0; i < farIndex.length; i++) {
//            farIndex[i] = -1;
//        }
//        //记录到到最远位置时的剩余油量
//        int[] farIndexRemain = new int[gas.length];
//
//
//        for (int i = 0; i < gas.length; i++) {
//            int j = i;
//            int remain = gas[i];//剩余
//
//            while (remain - cost[j] >= 0) {
//                //条件是能到达下一站
//
//                //到达下一个点的剩余
//                remain = remain - cost[j];
//
//                //更新位置
//                j = (j + 1) % gas.length;
//
//                //判断下一个点有没有走过
//                if (farIndex[j] != -1) {
//                    //当前点走过 更新位置和燃料剩余
//                    j = farIndex[j];
//                    remain = remain + farIndexRemain[j];
//                } else {
//                    //加上当前点的补给
//                    remain = remain + gas[j];
//                }
//
//
//                //如果回到了原点
//                if (j == i) {
//                    return i;
//                }
//            }
//
//            //记录当前点的能达到的最远位置
//            farIndex[i] = j;
//            farIndexRemain[i] = remain;
//        }
//        return -1;


        //P3继续优化
        //当考虑i能到达最远的时候，假设是j。那么i+1到j之间的节点是不是就不可能绕一圈了？
        //假设i+1的节点能绕一圈，那么意味着从i+1开始一定能到达j+1。
        //又因为从i能到达i+1，所以i也能到达j+1。
        //但事实上，i最远能到达j。产生矛盾，所有i+1的节点一定不能绕一圈。同理，其他也是一样的证明。
        //所以下次的i我们不需要从i+1开始考虑，直接从j+1开始考虑即可。
        //还有一种情况，就是因为达到末尾的时候，回到0
        //如果i最远能够到达j，根据上边的结论i+1到j之间的节点都不可能绕一圈了。想象成一个圆，所以i后面的节点就都不需要考虑了，直接返回-1即可
        //问题的关键是 如果选择i作为起点 恰好无法走到 j,那么i和j中间的点，都不可能作为起点？？？？
        for (int i = 0; i < gas.length; i++) {
            int j = i;
            int remain = gas[i];//剩余

            while (remain - cost[j] >= 0) {
                //条件是能到达下一站

                //减去花费 加上补给
                remain = remain - cost[j] + gas[(j + 1) % gas.length];

                //更新位置
                j = (j + 1) % gas.length;

                //j 回到了 i
                if (j == i) {
                    return i;
                }
            }
            //最远距离绕道了之前 所以i后面的都不可能绕一圈了
            if (j < i) {
                return -1;
            }

            //i直接跳到j,外层for循环执行++，相当于从j+1 开始考虑
            i = j;
        }

        return -1;

        //P3 这道题肯定不是通过简单的剪枝来优化暴力解法的效率，而是需要我们发现一些 隐藏较深的规律，从而减少一些融于的计算

    }


    /**
     * 135.分发糖果
     * n个孩子站成一排。给你一个整数数组ratings表示给每个孩子评分。
     * 你需要按照以下要求，给这些孩子分发糖果：
     * 1、每个孩子至少分配到一个糖果
     * 2、相邻两个孩子评分更高的孩子会获得更多糖果。
     * 请你给孩子们分发糖果，计算并返回需要准备的最少的糖果的数量。
     * 实例1：
     * 输入：ratings = [1,0,2]
     * 输出：5
     * 解释：你可以分别给这三个孩子分发 2、1、2 个糖果。
     */

    public int candy(int[] ratings) {
        //区分递增和递减 注意分界点属于两边
        int dec = 0;//当前递减序列长度
        int inc = 1;//当前递减序列前递增序列的长度

        int pre = 1;//前一个同学分配的糖果数量

        int candy = 1;//记录糖果数量

        for (int i = 1; i < ratings.length; i++) {
            //比较前一个值
            if (ratings[i] >= ratings[i - 1]) {
                dec = 0;//递减序列归零
                pre = ratings[i] == ratings[i - 1] ? 1 : pre + 1;//前一个值 如果前面分值=当前分值 则当前糖果为1 否则前面糖果+1
                candy += pre;//糖果总数量
                inc = pre;//更新递增序列 等于当前的糖果数
            } else {
                //inc = 0;//递增归零 （每次转折时已经被充值了）
                dec++;//递减+1

                //如果前面的的递增序列等于后面的递减序列 后面增加糖果时需要考虑这个元素
                if (dec == inc) {
                    dec++;
                }

                //递减序列中所有人的糖果数+1
                candy += dec;

                pre = 1;//递减序列的最小元素分得糖果=1

            }


        }

        return candy;
    }


    /**
     * 42.接雨水
     * 给定n个非负整数表示每个宽度为1的柱子的高度图，计算按此排列的柱子，下雨之后能积多少雨水
     * <p>
     * 示例：
     * 输入：height = [0,1,0,2,1,0,1,3,2,1,2,1]
     * 输出：6
     * <p>
     * 示例 2：
     * 输入：height = [4,2,0,3,2,5]
     * 输出：9
     * <p>
     * 提示：
     * n == height.length
     * 1 <= n <= 2 * 104
     * 0 <= height[i] <= 105
     * <p>
     * 【栈】【数组】【双指针】【动态规划】【单调栈】
     */

    public int trap(int[] height) {
        //关键是找到每个位置能存储的水量

        //P1 动态规划
        //将现实问题与动态规划关联起来，找到每一个位置的左右的最大高度，min(leftMax[i],rightMax[i]) - height[i] 等于当前位置的储水量

        //定义一个左边最大值数组、右边最大值数组
//        int[] leftMax = new int[height.length];//表示当前点的 左边的最大高度
//        int[] rightMax = new int[height.length];//表示当前点的 右边的最大高度
//
//        //确定初始状态
//        leftMax[0] = height[0];
//        rightMax[height.length - 1] = height[height.length - 1];
//        int capacity = 0;
//
//        //确定关联关系
//        for (int i = 1; i < height.length; i++) {
//            //当前点最高时 leftMax的值该是多少
//            leftMax[i] = Math.max(leftMax[i - 1], height[i]);
//        }
//        for (int i = height.length - 2; i >= 0; i--) {
//            rightMax[i] = Math.max(rightMax[i + 1], height[i]);
//        }
//
//        for (int i = 0; i < height.length; i++) {
//            capacity += Math.min(leftMax[i], rightMax[i]) - height[i];
//        }

        //        return capacity;

        //时间复杂度：O(n),其中数组height的长度，计算leftMax[] 和 rightMax[]的元素各需要遍历一次，计算能接到的雨水总量还需要遍历一次
        //空间复杂度：O(n),其中n 是数组height的长度。需要创建两个长度为n的数组 leftMax和rightMax


        //P2 单调栈
        //一次遍历 单调栈 遇到不是单调时开始弹栈计算
        //需要一个栈 一个总数计数器
        Deque<Integer> stack = new ArrayDeque<>();
        int capacity = 0;

        //一次循环
        for (int i = 0; i < height.length; i++) {
            //判断是不是单调栈 不是单调栈开始出栈计算 直到继续单调
            while (!stack.isEmpty() && height[i] > height[stack.peek()]) {
                //开始计算


                //弹出元素
                Integer pop = stack.pop();

                //如果栈元素全部弹出
                if (stack.isEmpty()) {
                    break;
                }
                //计算当前元素 和 栈顶元素 和 height[i] 雨水面积
                Integer top = stack.peek();
                int weight = i - top - 1;
                int tall = Math.min(height[top], height[i]) - height[pop];
                capacity += weight * tall;

            }
            //是单调栈-入栈 继续向前

            stack.push(i);
        }
        return capacity;


        //P3 双指针


    }

    /**
     * 13.罗马数字转整数
     * 罗马数字包含以下七种字符 I，V，X，L，C，D和 M
     * 字符          数值
     * I             1
     * V             5
     * X             10
     * L             50
     * C             100
     * D             500
     * M             1000
     * 例如：数字 2 写作 II ，即为两个并列I。12 写作 XII，27写作XXVII
     * 通常情况下，罗马数字中小的数字在大的数字右边。但也存在特例
     * I可以放在V或者X的左边表示 4 或者 9
     * X可以放在L或者C的左边表示 40 或者 90
     * C可以放在D或M的左边表示 400 或者 900
     * 给定一个罗马数字转换为整数
     * <p>
     * 示例 1:
     * 输入: s = "III"
     * 输出: 3
     * <p>
     * 示例 2:
     * 输入: s = "IV"
     * 输出: 4
     * <p>
     * 示例 3:
     * 输入: s = "IX"
     * 输出: 9
     * <p>
     * 示例 4:
     * 输入: s = "LVIII"
     * 输出: 58
     * 解释: L = 50, V= 5, III = 3
     * <p>
     * 示例 5:
     * 输入: s = "MCMXCIV"
     * 输出: 1994
     * 解释: M = 1000, CM = 900, XC = 90, IV = 4
     */
    public int romanToInt(String s) {
        //一个map（hash表）存入映射关系，同时记录最大值
        //字符串转换为字符数组遍历 从左向右遍历

        HashMap<String, Integer> map = new HashMap<>();
        map.put("I", 1);
        map.put("V", 5);
        map.put("X", 10);
        map.put("L", 50);
        map.put("C", 100);
        map.put("D", 500);
        map.put("M", 1000);


        char[] chars = s.toCharArray();
        int n = chars.length;

        int num = 0;
        int flag = 0;
        for (int i = n - 1; i >= 0; i--) {
            char aChar = chars[i];
            Integer i1 = map.get(String.valueOf(aChar));
            if (i1 >= flag) {
                num += i1;
                flag = i1;
            } else {
                num = num - i1;
            }
        }
        return num;
    }

    /**
     * 12.整数转罗马数字
     * 罗马数字包含以下七种字符 I，V，X，L，C，D和 M
     * 字符          数值
     * I             1
     * V             5
     * X             10
     * L             50
     * C             100
     * D             500
     * M             1000
     * 例如：数字 2 写作 II ，即为两个并列I。12 写作 XII，27写作XXVII
     * 通常情况下，罗马数字中小的数字在大的数字右边。但也存在特例
     * I可以放在V或者X的左边表示 4 或者 9
     * X可以放在L或者C的左边表示 40 或者 90
     * C可以放在D或M的左边表示 400 或者 900
     * 给定一个整数转换为罗马数字
     * <p>
     * 示例 1:
     * 输入: num = 3
     * 输出: "III"
     * <p>
     * 示例 2:
     * 输入: num = 4
     * 输出: "IV"
     * <p>
     * 示例 3:
     * 输入: num = 9
     * 输出: "IX"
     * <p>
     * 示例 4:
     * 输入: num = 58
     * 输出: "LVIII"
     * 解释: L = 50, V = 5, III = 3.
     * <p>
     * 示例 5:
     * 输入: num = 1994
     * 输出: "MCMXCIV"
     * 解释: M = 1000, CM = 900, XC = 90, IV = 4.
     * <p>
     * 提示：
     * 1 <= num <= 3999
     */
    public String intToRoman(int num) {
//        //P1硬编码解法  整数每个位 只能对应9种状态
//        String[] ones = new String[]{"", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX"};
//        String[] tens = new String[]{"", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC"};
//        String[] hundreds = new String[]{"", "C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM"};
//        String[] thousands = new String[]{"", "M", "MM", "MMM"};
//
//        //巧妙解法不用循环直接拿到 每个位的值
//        StringBuilder stringBuilder = new StringBuilder();
//        stringBuilder.append(thousands[num / 1000]);
//        stringBuilder.append(hundreds[num % 1000 / 100]);
//        stringBuilder.append(tens[num % 100 / 10]);
//        stringBuilder.append(ones[num % 10]);
//        return stringBuilder.toString();

        //P2模拟 (类似贪心算法)(需要枚举出 所有的数字-罗马数的映射情况)
        //罗马数字的唯一表示法:对于罗马数字从左到右的每一位，选择尽可能大的符号值。对于140 第一位选择C 接下来选择40

        HashMap<Integer, String> map = new LinkedHashMap<>();
        map.put(1000, "M");
        map.put(900, "CM");
        map.put(500, "D");
        map.put(400, "CD");
        map.put(100, "C");
        map.put(90, "XC");
        map.put(50, "L");
        map.put(40, "XL");
        map.put(10, "X");
        map.put(9, "IX");
        map.put(5, "V");
        map.put(4, "IV");
        map.put(1, "I");

        StringBuilder builder = new StringBuilder();
        while (num > 0) {
            for (Map.Entry<Integer, String> entry : map.entrySet()) {
                if (num >= entry.getKey()) {
                    num -= entry.getKey();
                    builder.append(entry.getValue());
                    break;
                }
            }
        }

        return builder.toString();

    }

    /**
     * 58. 最后一个单词的长度
     * 给你一个字符串s,由若干单词组成，单词前后用一些空格字符隔开。返回字符串中最后一个单词的长度
     * 单词是指仅有字母组成、不包含任何任何字符的最大字符串
     * <p>
     * 示例 1：
     * 输入：s = "Hello World"
     * 输出：5
     * 解释：最后一个单词是“World”，长度为5。
     * <p>
     * 示例 2：
     * 输入：s = "   fly me   to   the moon  "
     * 输出：4
     * 解释：最后一个单词是“moon”，长度为4。
     * <p>
     * 示例 3：
     * 输入：s = "luffy is still joyboy"
     * 输出：6
     * 解释：最后一个单词是长度为6的“joyboy”。
     * <p>
     * 提示：
     * 1 <= s.length <= 104
     * s 仅有英文字母和空格 ' ' 组成
     * s 中至少存在一个单词
     */
    public int lengthOfLastWord(String s) {
        char[] chars = s.toCharArray();
        //倒叙遍历
        int after = 0;
        for (int i = chars.length - 1; i >= 0; i--) {
            //如果是 空格 指针往前
            if (chars[i] != ' ') {
                after++;
            } else if (after > 0 && chars[i] == ' ') {
                break;
            }
        }
        return after;

    }


    /**
     * 14.最长公共前缀
     * 编写一个函数来查找字符串数组中的最长公共前缀。
     * 如果不存在公共前缀，返回空字符串 ""。
     * <p>
     * 示例 1：
     * 输入：strs = ["flower","flow","flight"]
     * 输出："fl"
     * <p>
     * 示例 2：
     * 输入：strs = ["dog","racecar","car"]
     * 输出：""
     * 解释：输入不存在公共前缀。
     * <p>
     * 提示：
     * 1 <= strs.length <= 200
     * 0 <= strs[i].length <= 200
     * strs[i] 仅由小写英文字母组成
     * 【字典树】【字符串】
     */
    public String longestCommonPrefix(String[] strs) {
        String pre = "";//可以直接是第一个元素
//        //P1横向扫描
//        //依次向后扫描记录最大公共前缀遇到空字符串直接返回
//        for (int i = 0; i < strs.length; i++) {
//            String temp = "";
//            if (i == 0) {//第一个元素
//                temp = strs[i];
//            } else if (strs[i] == "" || pre == "") {//遍历到 "" 或者过程中 pre =="" 直接返回
//                return "";
//            } else {//找到pre和strs[i]的最大公众前缀
//                int min = Math.min(pre.length(), strs[i].length());
//                char[] preCharArray = pre.toCharArray();
//                char[] nowCharArray = strs[i].toCharArray();
//                for (int j = 0; j < min; j++) {
//                    if (preCharArray[j] != nowCharArray[j]) {
//                        break;
//                    } else {
//                        temp = temp + preCharArray[j];
//                    }
//                }
//            }
//            pre = temp;
//        }
//        //时间复杂度 O(mn)m字符串的平均长度 n字符串的数量
//        //空间复杂度 O(1);


        //P2纵向扫描
        //比较每个字符串i位置的字符相等数目 当i位置相等数目不等于strs.length()时 此时i即为最大公共前缀的长度;
        pre = strs[0];
        for (int i = 0; i < strs[0].length(); i++) {
            int count = strs.length;
            char c = strs[0].toCharArray()[i];

            //遍历查看后面的所有字符串 在当前i位置是否 都相等
            for (int j = 1; j < count; j++) {
                //不想等 或者 最后一次循环(包括别的字符串达到最大长度) 返回结果
                if (i == strs[j].length() || strs[j].toCharArray()[i] != c) {
                    pre = strs[0].substring(0, i);
                }
            }
        }

        return pre;

    }

    /**
     * 151.反转字符串中的单词
     * 给你一个字符串s,请你反转字符串中的单词顺序
     * 单词 是由非空格字符组成的字符串。S中至少一个空格将字符串中的单词分隔开
     * 返回的单词顺序颠倒且单词之间用空格链接的结果字符串
     * 注意输入的字符串s中可能会存在前导空格、尾随空格或单词间的多个空格。返回的结果字符中，单词应当仅用单个空格分割，切不包含任何额外的空格
     * <p>
     * 示例 1：
     * 输入：s = "the sky is blue"
     * 输出："blue is sky the"
     * <p>
     * 示例 2：
     * 输入：s = "  hello world  "
     * 输出："world hello"
     * 解释：反转后的字符串中不能存在前导空格和尾随空格。
     * <p>
     * 示例 3：
     * 输入：s = "a good   example"
     * 输出："example good a"
     * 解释：如果两个单词间有多余的空格，反转后的字符串需要将单词间的空格减少到仅有一个。
     * <p>
     * 提示：
     * 1 <= s.length <= 104
     * s 包含英文大小写字母、数字和空格 ' '
     * s 中 至少存在一个 单词
     */
    public String reverseWords(String s) {
        //P1双指针
        char[] chars = s.toCharArray();
        int length = chars.length;
        int left = length - 1;
        int right = length;

        String result = "";
        while (left >= -1) {

            if (left == -1) {//如果到达第一个元素
                if (left - right != -1) { //如果left和right不在同一位置 且当前left 为空格 截下这个单词拼接到result上 且末尾+空格
                    result = result + s.substring(left + 1, right);
                }
                break;
            }

            if (chars[left] == ' ') {//当前left 为空格
                //如果left和right不在同一位置 截下这个单词拼接到result上 且末尾+空格
                if (left - right != -1) {
                    result = result + s.substring(left + 1, right) + " ";
                }
                //left继续前移 right重置到原先left的位置
                right = left;

            }
            left--;


        }

        //祛除末尾的空格
        if (result.endsWith(" ")) {
            result = result.substring(0, result.length() - 1);
        }

        return result;


        //p2 Java自带的官方API split reverse 和join完成
    }

    /**
     * 6.Z字形变换
     * 将一个给定字符串s根据给定的行数numRows, 以从上往下，从左到右进行Z字型排列
     * 比如输入字符串为"PAYPALISHIRING"行数为3时，排列如下(字符串进行行数3 进行Z字形排列)
     * P   A   H   N
     * A P L S I I G
     * Y   I   R
     * 之后，你的输出需要从左往右逐行读取，产生一个新的字符串。比如 "PAHNAPLSIIGYIR"
     * <p>
     * 示例 1：
     * 输入：s = "PAYPALISHIRING", numRows = 3
     * 输出："PAHNAPLSIIGYIR"
     * <p>
     * 示例 2：
     * 输入：s = "PAYPALISHIRING", numRows = 4
     * 输出："PINALSIGYAHRPI"
     * 解释：
     * P     I    N
     * A   L S  I G
     * Y A   H R
     * P     I
     * <p>
     * 示例 3：
     * 输入：s = "A", numRows = 1
     * 输出："A"
     * <p>
     * <p>
     * 提示：
     * 1 <= s.length <= 1000
     * s 由英文字母（小写和大写）、',' 和 '.' 组成
     * 1 <= numRows <= 1000
     */
    public String convert(String s, int numRows) {
        //P1利用二维矩阵模拟 字符串转换到矩阵内在读取
        int length = s.length();
        //如果s长度小于numRows || 列数为 1 直接返回
        if (length <= numRows || numRows == 1) {
            return s;
        }

        //确认矩阵大小 高度=numRows
        //一个周期内 占用多少列 1+r-2 = r-1列
        //共有周期 length/2r-2
        //占用多少字符r+r-2 个字符
        int m = (length / (2 * numRows - 2) + 1) * (numRows - 1);
        int n = numRows;

        char[][] matrix = new char[m][n];
        //确认写入 规则
        //周期性 编号除以 2r-2 取余 小于等于r向下 大于r向右上 等于0呢也是右上
        int x = 0;
        int y = 0;
        char[] chars = s.toCharArray();
        for (int i = 0; i < length; i++) {
            matrix[x][y] = chars[i];
            int temp = i % (2 * numRows - 2);//在周期内的位置
            if (temp < numRows - 1) {
                y++;
            } else {
                x++;
                y--;
            }
        }


        //矩阵读取
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (matrix[j][i] != 0 && matrix[j][i] != ' ') {
                    builder.append(matrix[j][i]);
                }
            }
        }
        return builder.toString();

    }


    /**
     * 28. 找出字符串中第一个匹配项的下标
     */
    public int strStr(String haystack, String needle) {
        //双指针 只遍历一次 目标序别
        int m = 0;
        int n = 0;

        char[] chars = haystack.toCharArray();
        char[] aim = needle.toCharArray();

        while (m <= chars.length) {
            if (n == aim.length) {
                return m - n;
            } else if (m == chars.length) {
                return -1;
            } else if (chars[m] == aim[n]) {
                m++;
                n++;
            } else if (n != 0) {
                m = m - n + 1;
                n = 0;
            } else {
                m++;
            }
        }

        return -1;

    }


    /**
     * 68. 文本左右对齐
     * 给定一个是单词数组 words 和一个长度 maxWidth，重新排班单词使其成为每行恰好有maxWidth个字符
     * 且左右两端的文本对齐
     * 你应该使用 贪心算法 来放置单词，也就是说，尽可能多的往每行中放置单词。必要时使用 ' '填充，
     * 使得每行恰好有MaxWidth个字符
     * 要求尽可能均匀分配单词间的空格数量。如果某一行单词间的空格不能均匀分配，则左侧放置的空格数要多于右侧的空格数
     * 文本最后一行应为左对齐，且单词之间不插入额外的空格
     * <p>
     * 注意；单词是指非空格字符组成的字符序列
     * 每个单词的长度大于0，小于等于maxWidth
     * 输入单词数组字少包含一个单词
     * <p>
     * 示例 1:
     * 输入: words = ["This", "is", "an", "example", "of", "text", "justification."], maxWidth = 16
     * 输出:
     * [
     * "This    is    an",
     * "example  of text",
     * "justification.  "
     * ]
     * <p>
     * <p>
     * 示例 2:
     * 输入:words = ["What","must","be","acknowledgment","shall","be"], maxWidth = 16
     * 输出:
     * [
     * "What   must   be",
     * "acknowledgment  ",
     * "shall be        "
     * ]
     * 解释: 注意最后一行的格式应为 "shall be    " 而不是 "shall     be",
     * 因为最后一行应为左对齐，而不是左右两端对齐。
     * 第二行同样为左对齐，这是因为这行只包含一个单词。
     * <p>
     * 示例 3:
     * 输入:words = ["Science","is","what","we","understand","well","enough","to","explain","to","a","computer.","Art","is","everything","else","we","do"]，maxWidth = 20
     * 输出:
     * [
     * "Science  is  what we",
     * "understand      well",
     * "enough to explain to",
     * "a  computer.  Art is",
     * "everything  else  we",
     * "do                  "
     * ]
     * <p>
     * 提示:
     * 1 <= words.length <= 300
     * 1 <= words[i].length <= 20
     * words[i] 由小写英文字母和符号组成
     * 1 <= maxWidth <= 100
     * words[i].length <= maxWidth
     */
    public List<String> fullJustify(String[] words, int maxWidth) {
        // 遍历这个串找到字符长度和字符个数
        List<String> strings = new ArrayList<>();
        int right = 0;// 记录当前行最右边单词的位置
        int n = words.length;

        while (true) {
            int left = right;// 当前当前行的 第一个单词在words的位置
            int sumLen = 0;// 统计这一行单词的长度之和

            // 通过循环确定当前行能容纳的单词数
            while (right < n && sumLen + words[right].length() + right - left <= maxWidth) {// right- left表示最少的空格数
                sumLen = sumLen + words[right].length();
                right++;// right ++（先把right赋值给运算语句在 ++）
            }

            // 如果是最后一行，单词左对齐，且单词之间只有一个空格，在行末填充剩余空格
            if (right == n) {
                StringBuilder builder = new StringBuilder();
                builder.append(join(words, left, right, " "));
                builder.append(blank(maxWidth - builder.length()));// 填充剩余空格

                strings.add(builder.toString());
                return strings;
            }

            // 计算单词数
            int wordsNum = right - left;
            // 剩余的位置 全部的空格数
            int spaceNum = maxWidth - sumLen;

            // 当前行只有一个单词，该单词左对齐，在行末填充剩余空格
            if (wordsNum == 1) {
                StringBuffer buffer = new StringBuffer(words[left]);
                buffer.append(blank(spaceNum));

                strings.add(buffer.toString());
                continue;
            }
            // 当前行不止一个单词
            int avgSpaces = spaceNum / (wordsNum - 1);
            int extraSpaces = spaceNum % (wordsNum - 1);
            StringBuffer sb = new StringBuffer();
            sb.append(join(words, left, left + extraSpaces + 1, blank(avgSpaces + 1))); // 拼接额外加一个空格的单词
            sb.append(blank(avgSpaces));
            sb.append(join(words, left + extraSpaces + 1, right, blank(avgSpaces))); // 拼接其余单词
            strings.add(sb.toString());
        }

    }

    // blank 返回长度为 n 的由空格组成的字符串
    public String blank(int n) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < n; ++i) {
            sb.append(' ');
        }
        return sb.toString();
    }

    // join 返回用 sep 拼接 [left, right) 范围内的 words 组成的字符串
    public StringBuffer join(String[] words, int left, int right, String sep) {
        StringBuffer sb = new StringBuffer(words[left]);
        for (int i = left + 1; i < right; ++i) {
            sb.append(sep);
            sb.append(words[i]);
        }
        return sb;
    }


    /**
     * 125.验证回文串
     * 如果将多有大写字符转换为小写字符、并移除所有非字母或数字字符后，短语正这读和反着读一样。则可认为该短语是一个回文串
     * 字母和数字都属于字母数字字符
     * 给你一个字符串s ,如果它是回文串，则返回true：否则返回false。
     */
    public boolean isPalindrome(String s) {
        char[] chars = s.toCharArray();
        //双指针 一次遍历
        int left = 0;
        int right = s.length() - 1;
        while (left < right) {
            //左右任意为数字
            if (!Character.isLetterOrDigit(chars[left])) {
                left++;
            }
            if (!Character.isLetterOrDigit(chars[right])) {
                right--;
            }

            //比较字符串
            if (Character.isLetterOrDigit(chars[left]) && Character.isLetterOrDigit(chars[right])) {
                if (Character.toLowerCase(chars[left]) == Character.toLowerCase(chars[right])) {
                    left++;
                    right--;
                } else {
                    return false;
                }
            }

        }
        return true;
    }

    /**
     * 392.判断子序列
     * 给定字符串 s 和 t,判断s是否是t的子序列
     * 字符串的一个子序列是原始字符串删除一些（也可以不删除）字符而不改变剩余字符串相对位置形成的新字符串。（例如，"abc"是"abcde"的一个子序列）
     * 而"aec"不是
     * <p>
     * 进阶：如果有大量的输入S，乘坐S1、S2、、、、Sk 其中k>= 10亿，你需要依次检查他们是否为T的子序列。在这种情况下，你会怎么样改变代码
     * <p>
     * 【双指针】【字符串】【动态规划】
     */
    public boolean isSubsequence(String s, String t) {
        //P1 双指针
        //注意题中的意思 删除一些字符 所以不需要管删除的字符是否包含目标字符 直接向后对比即可
//        int left = 0;
//        int right = 0;
//        while (left < s.length() && right < t.length()) {
//            if (s.charAt(left) == t.charAt(right)) {
//                left++;
//                right++;
//            } else {
//                right++;
//            }
//        }
//
//        if (left == s.length()) {
//            return true;
//        } else {
//            return false;
//        }

        //考虑动态规划
        //思路：考虑前面的双指针的做法，我们注意到我们有大量的时间用于在t中找到下一个匹配字符。
        //这样我们可以预处理对于t的每一个位置，从该位置开始往后每一个字符第一次出现的位置。
        //我们可以使用动态规划的方法实现预处理，
        // 令 f[i][j]表示字符串t中，从位置i开始往后 字符j 第一次出现的位置。
        //再进行状态转移时，
        // 如果t中的位置i的字符就是j，那么f[i][j] = i ,否则j出现在位置i+1开始往后，即f[i][j] = f[i+1][j] TODO 状态转移的关键
        //因此我们要倒过来进行动态规划，从后往前枚举i 末尾的状态是确认的

        //状态转移方程
        //f[i][j] = i (t.[i] = j) : f[i+1][j] (t[i!=j])

        //假定下标从0开始，那么f[i][j]中有 0 <= i <= m-1,
        //对于临界状态f[m-1][.] 置 f[m][.] = m,(边界&&初始) 让f[m-1][.] 可以正常转移。
        // 这样如果f[i][j] = m,则表示从位置i开始往后不存在字符j
        //这样我们可以利用f数组，每次O(1)的跳到下一个位置，直到位置变为m或者s中的每一个字符串都匹配成功

        //初始化f[][]数组
        int m = t.length();
        int[][] f = new int[m + 1][26]; //为什么要 +1 难道是状态转移方程 需要向后一位


        //f[][] 临界状态填充 在m位置的26个字符 的下一个出现的位置都为在字符串的位置为m m为不合法字符用来表示不存在
        for (int i = 0; i < 26; i++) {
            f[m][i] = m;
        }


        //f[][] 根据状态转移方程 填充数值
        for (int i = m - 1; i >= 0; i--) {
            for (int j = 0; j < 26; j++) {
                if (t.charAt(i) == j + 'a') {
                    f[i][j] = i;
                } else {
                    f[i][j] = f[i + 1][j];
                }
            }
        }


        int add = 0;
        for (int i = 0; i < s.length(); i++) {
            if (f[add][s.charAt(i) - 'a'] == m) {//任意到达不可能到达的临界值时 返回false
                return false;
            }
            add = f[add][s.charAt(i) - 'a'] + 1;
        }

        return true;
    }


    /**
     * 167. 两数之和 II - 输入有序数组
     * 给你一个下标从1 开始的整数数组 numbers，该数组已按 非递减顺序排序
     * 请你从数组中找出满足相加之和等于目标数target的两个数
     * 你可以假设每次 只对应唯一答案，而且你不可以使用重复使用相同的元素
     */

    public int[] twoSum(int[] numbers, int target) {
        int[] ints = new int[2];
//        //P1双指针暴力破解
//        //关键点非递减顺序排列 可以减少双指针循环时间
//        int left = 0;
//        int right = 1;
//        while (right < numbers.length) {
//            //先判断和是否正确
//            if (numbers[left] + numbers[right] == target) {
//                break;
//                //右边界可能已经大于target 次轮遍历结束
//            } else if (numbers[right] + numbers[left] >= target || right == numbers.length - 1) {
//                left++;
//                right = left + 1;
//
//            } else {
//                right++;
//            }
//        }
//        ints[0] = left+1;
//        ints[1] = right+1;
//        return ints;
        //勾八恶心，超时了

        //双指针+利用非递减性质 减少比较次数
        //初始时，指针在数组两侧，比较两个位置的和，
        // 如果大于目标值，则右边大，右边左移一位
        //如果小于目标值则左边偏小，左半右移一位
        int left = 0;
        int right = numbers.length - 1;

        while (left < right) {
            if (numbers[left] + numbers[right] == target) {
                break;
            } else if (numbers[left] + numbers[right] >= target) {
                right--;
            } else {
                left++;
            }
        }
        ints[0] = left + 1;
        ints[1] = right + 1;
        return ints;


        //P3遍历数组 再利用二分找 与当前元素相加和等于目标值的元素

    }

    /**
     * 11.盛水最多的容器
     * 给定一个长度为 n 的整数数组 height。有 n 条垂直线，第 i 条线的两个端点是 {i,0} 和 {i,height[i]}
     * 找出其中的两条线，是得他们与X轴共同构成的容器可以容纳最多的水
     * 返回容器可以存储的最大水量
     * 说明你不能倾斜容器
     */

    public int maxArea(int[] height) {
        //找到盛水最多的容器  暴力肯定会超时 数组长度为10的5次方

        //双指针
        int left = 0;
        int right = height.length - 1;
        int max = 0;
        while (left < right) {
            int are = Math.min(height[left], height[right]) * (right - left);
            max = Math.max(are, max);
            if (height[left] <= height[right]) {
                left++;
            } else {
                right--;
            }

        }

        return max;

    }


    /**
     * 15. 三数之和
     * 给你一个整数数组 nums[] ,判断是否存在三元组
     * nums[i] + nums[j] + nums[k] = 0
     * 其中i j k 三个数互不相等
     * 请返回所有和为 0 且不为零的所有三元组
     * 注意：答案不可以重复
     */

    public List<List<Integer>> threeSum(int[] nums) {
        //只要数值不要下标 首先考虑排序
        //排序之后 固定一个元素 从最小元素开始
        //从固定元素下一个 和 末尾元素 两个指针开始向内 移动

        //定义返回的数据类型
        List<List<Integer>> lists = new ArrayList<>();

        //排序
        Arrays.sort(nums);

        for (int first = 0; first < nums.length; first++) {
            //判断是否和上次枚举的值不相同
            if (first > 0 && nums[first] == nums[first - 1]) {
                continue;
            }

            //c对应的指针指向数组的最右端
            int third = nums.length - 1;
            int target = -nums[first];//需要的 b + c 对应的和
            //第二个元素从first左端开始
            for (int second = first + 1; second < nums.length; second++) {

                //需要和上一次枚举的枚举的值不一样
                if (second > first + 1 && nums[second] == nums[second - 1]) {
                    continue;
                }

                //需要保证b指针在c指针的左侧
                //当nums[second] + nums[third] < target 时 右侧指针左移
                while (second < third && nums[second] + nums[third] > target) {
                    third--;
                }


                //如果指针重合，随着b后续增加
                //就不会满足 a+b+c = 0 并且 b<c 的c了，可以退出循环
                if (second == third) {
                    break;//退出第二层循环
                }

                //当nums[second] + nums[third] < target 时
                //进入下一次循环 左侧指针右移

                //当nums[second] + nums[third] = target 时
                if (nums[second] + nums[third] == target) {
                    //添加进结果集
                    ArrayList<Integer> list = new ArrayList<>();
                    list.add(nums[first]);
                    list.add(nums[second]);
                    list.add(nums[third]);
                    lists.add(list);
                }


            }

        }
        return lists;


    }
/********************************************************滑动窗口********************************************************/
    /**
     * 209. 长度最小的子数组
     * 给定一个含有n个正整数数组和一个正整数target
     * 找出数组中满足其总和大于等于target的长度最小的 连续子数组，
     * 并返回其长度。如果不存在符合条件的子数组，则返回0
     */
    public int minSubArrayLen(int target, int[] nums) {

//        //TODO P1 自己的解法 想当于前缀和+找最短长度 每次满足条件 长度减一进行下次循环
//        int[] prefixSum = new int[nums.length];
//        //前缀和
//        prefixSum[0] = nums[0];
//        for (int i = 1; i < nums.length; i++) {
//            prefixSum[i] = prefixSum[i - 1] + nums[i];
//        }
//
//
//        if (target > prefixSum[nums.length - 1]) {//目标值大于 数组和最大值 不存在直接返回零
//            return 0;
//        }
//        //全是正整数 所以窗口的长度一定小于target
//        //考虑是否需要排序， 如果排序长度就不好说了 所以一定是没办法排序
//        int width = Math.min(target, nums.length - 1);//存在target 大于 nums.length的情况
//        //width做窗口的大小
//        while (width >= 0) {//窗口逐渐减小
//            int left = 0;
//            int right = width;//每次开始滑动时 窗口状态
//            //固定窗口大小开始滑动 遇到符合条件的这次循环结束 减少窗口大小 继续下一次循环
//            while (right <= nums.length - 1) {
//
//                if (left != right) {
//                    if (((prefixSum[right] - prefixSum[left] + nums[left]) >= target)) {
//                        //如果当前窗口的总和已经大于 目标值
//                        width--;
//                        break;
//                    } else {
//                        left++;
//                        right++;
//                    }
//                } else if (left == right) {
//                    //此时width已经为0
//                    if (nums[right] >= target) {
//                        return 1;
//                    } else {
//                        left++;
//                        right++;
//                    }
//                }
//                //如果到达右边界 直接返回当前 width+1;
//                if (right == nums.length) {
//                    return width + 2;
//                }
//            }
//        }
//
//        //窗口的最大长度一定小于
//        return 0;


        //TODO 真正的滑动窗口
        //定义两个指针 start和end 分别表示子数组（滑动窗口的窗口）的开始位置和结束位置
        //维护变量 sum 存储子数组中的元素和（即从nums[start] 到 nums[end]的元素和）
        //初试状态下，start和end 都指向下标0，sum值为0
        //每一次迭代，将nums[end] 加到sum,如果sum >= s，则更新数组的最小长度（此时子数组的最小长度为end- start + 1）
        //然后将nums[start] 从中减去 并将start右移，直到sums < s,在此过程中更新子数组的最小长度。在每一次迭代的最后，将end右移。
        int length = nums.length;

        int ans = Integer.MAX_VALUE;//标记是否发生了变化
        int start = 0;
        int end = 0;
        int sum = 0;
        while (end < length) {
            sum = sum + nums[end];
            while (sum >= target) {//往前滑动的条件 不满足目标值向前滑动  直到满足条件 左窗口试探减小
                ans = Math.min(ans, end - sum + 1);
                sum = sum - nums[start];
                start++;
            }
            end++;
        }
        return ans == Integer.MAX_VALUE ? 0 : ans;//如果一直不满足条件 此时ans仍为最大值不发生不变化

    }


    /**
     * 3. 最长无重复字串
     * 给定一个字符串 s ，请你找出其中不含有重复字符的最长字符串长度
     * 示例 1:
     * 输入: s = "abcabcbb"
     * 输出: 3
     * 解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。
     * <p>
     * 示例 2:
     * 输入: s = "bbbbb"
     * 输出: 1
     * 解释: 因为无重复字符的最长子串是 "b"，所以其长度为 1。
     * <p>
     * 示例 3:
     * 输入: s = "pwwkew"
     * 输出: 3
     * 解释: 因为无重复字符的最长子串是 "wke"，所以其长度为 3。
     * 请注意，你的答案必须是 子串 的长度，"pwke" 是一个子序列，不是子串。
     * <p>
     * 提示：
     * 0 <= s.length <= 5 * 104
     * s 由英文字母、数字、符号和空格组成
     */
    int lengthOfLongestSubstring(String s) {
        // 需要维护hash表中每一个字符的位置
        // 遇到hash表中已经存在的元素，从hash表中取出已经存在的元素，start从这个元素的位置开始，put新的元素的值
        // 考虑初始状态：都从零开始
        HashMap<Character, Integer> hashMap = new HashMap<>();
        int ans = 0;// 默认是0
        int start = 0;
        int end = 0;
        int length = s.length();
        while (end < length && start <= end) {
            if (hashMap.containsKey(s.charAt(end))) {
                // hashMap.get(s.charAt(end)) 之前的key都需要remove掉
                while (hashMap.containsKey(s.charAt(end)) && start <= hashMap.get(s.charAt(end))) {// hashMap.get(s.charAt(end)))当前hashmap中包含的跟end位置相同字符的位置
                    hashMap.remove(s.charAt(start));
                    start++;
                }

            }
            hashMap.put(s.charAt(end), end);
            ans = Math.max(ans, hashMap.size());
            end++;
        }
        return ans;
    }
/********************************************************矩阵********************************************************/

    /**
     * 36.有效的数独
     * 请你判断一个 9*9 的数独是否有效，只需要根据一下规则，验证已经填入的数字是否有效即可
     * 数字 1-9 在每一行只能出现一次。
     * 数字 1-9 在每一列只能出现一次。
     * 数字 1-9 在每一个以粗实线分隔的 3x3 宫内只能出现一次。
     * <p>
     * 注意：
     * 一个有效的数独（部分已被填充）不一定是可解的
     * 只需要根据以上规则，验证已经填入的数字是否有效即可
     * 空白格用 。表示
     */
    public boolean isValidSudoku(char[][] board) {
        //目的 需要记录每一个行 每一列  每一个3*3 的格子内 不存在相同数字
        //使用两个二维数组 记录每一行、每一列数字出现的次数

        //使用三维数组 3*3 记录每一个方格内的数值出现的次数

        //如何只遍历一次，关键是 i/3 j/3 正好落在方格里，因此进行一次二重循环 即可拿到所有情况

        //char - '0' - 1 字符转换为 数值

        //初始三个数组
        int[][] rows = new int[9][9];
        int[][] colums = new int[9][9];
        int[][][] subboxes = new int[3][3][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                char c = board[i][j];
                if ('.' != c) {
                    //行、列 所在位置的元素值+1
                    int index = c - '0' - 1;
                    rows[i][index]++;
                    colums[j][index]++;
                    //小格子元素++
                    subboxes[i / 3][j / 3][index]++;// i/3 j/3 自动去整了
                    //判断加过之后元素值是否大于2
                    if (rows[i][index] > 1 || colums[j][index] > 1 || subboxes[i / 3][j / 3][index] > 1) {
                        return false;
                    }
                }
            }

        }
        return true;

    }

    /**
     * 54.螺旋矩阵
     * 给你你一个 m 行 n 列的矩阵 matrix ，请按照顺时针顺序返回矩阵中所有的元素
     * <p>
     * <img src = "img.png">
     * </p>
     * 输入：matrix = [[1,2,3],[4,5,6],[7,8,9]]
     * 输出：[1,2,3,6,9,8,7,4,5]
     * 核心思想是 模拟
     */


    public List<Integer> spiralOrder(int[][] matrix) {
        ArrayList<Integer> order = new ArrayList<>();
        int rows = matrix.length;
        int columns = matrix[0].length;

        //是否已经访问过
        boolean[][] visited = new boolean[rows][columns];

        //空数组判断
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return order;
        }

        int total = rows * columns;//用以遍历
        int row = 0, column = 0;

        //控制方向
        int[][] directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};//TODO 非常巧妙 四个方向
        int directionIndex = 0;

        for (int i = 0; i < total; i++) {
            order.add(matrix[row][column]);//放入数组
            visited[row][column] = Boolean.TRUE;//增加一访问标记 默认是false
            //获取当前方向下的另外一个位置是否合法
            int nextRow = row + directions[directionIndex][0];
            int nextColumn = column + directions[directionIndex][1];
            //判断是否合法 超出边界或者已经访问过 转向（即directionIndex下一个）
            if (nextRow < 0 || nextRow >= rows || nextColumn < 0 || nextColumn >= columns || visited[nextRow][nextColumn]) {
                directionIndex = (directionIndex + 1) % 4;
            }
            row = row + directions[directionIndex][0];
            column = column + directions[directionIndex][1];
        }
        return order;


    }

    /**
     * 48.旋转图像
     * 给定一个 n*n 的矩阵 matrix 表示一个图像，请你将图像顺时针旋转 90 度
     * 你必须在原地旋转图像，这就意味着你需要直接修改输入的二维矩阵
     *
     * @param matrix
     */
    public void rotate(int[][] matrix) {
        //P1 使用新的矩阵
        //观察新的结果
        //对于矩阵中的第 i 行，第 j 个元素，
        //在旋转后，它出现在倒数第 i 列 第j个元素
        //转换为行列关系
        //(i,j)   (j,n-i-1)
        //新建新的矩阵把原来的矩阵元素遍历进去
        int length = matrix.length;
        int high = matrix[0].length;
        int[][] matrixMirror = new int[length][high];

        for (int i = 0; i < length; i++) {
            for (int j = 0; j < high; j++) {
                matrixMirror[j][length - i - 1] = matrix[i][j];
            }
        }
//        matrix = matrixMirror;


        //P2 不使用额外的空间 在原先的矩阵上进行操作
        //考虑翻转 对折，先上下反折，再住对角线对折

        //上下替换 只遍历上半部分
        int temp;
        for (int i = 0; i < length / 2; i++) {
            for (int j = 0; j < high; j++) {
                temp = matrix[i][j];
                matrix[i][j] = matrix[length - i - 1][j];
                matrix[length - i - 1][j] = temp;
            }
        }

        //主对角线翻折
        for (int i = 0; i < length; i++) {
            for (int j = i; j < high; j++) {
                temp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = temp;
            }
        }


    }


    /**
     * 73.矩阵置零
     * 给定一个 M * N 的矩阵，如果一个元素为0，则将其所在行列所有元素都设为 0
     * 请使用 原地 算法
     *
     * @param matrix
     */
    public void setZeroes(int[][] matrix) {
        //什么是原地算法
        //P1 遍历一遍记录需要置零的行和列，第二次遍历对对应行和列置零
        //使用了 O（n+m）的空间
        int row = matrix.length;
        int column = matrix[0].length;
        //new一个新的数组记录 需要的变化
        int[] zeroRows = new int[row];
        int[] zeroColumns = new int[column];


        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                if (matrix[i][j] == 0) {
                    zeroRows[i] = 1;
                    zeroColumns[j] = 1;
                }
            }
        }

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                if (zeroRows[i] == 1 || zeroColumns[j] == 1) {
                    matrix[i][j] = 0;
                }
            }
        }
        //P2 如何就使用一个常亮实现


    }

    /**
     * 289.生命游戏
     * 给定一个 m * n 个格子的面板，每一个格子都可以看成是一个细胞。每个细胞都具有一个初始状态：1 即为活细胞，或者 0 即为死细胞。
     * 每个细胞与其八个相邻位置（水平、垂直、对角线）的细胞都遵循一下四条生存定律
     * 1、如果活细胞周围八个位置的活细胞数少于两个，则该位置活细胞死亡
     * 2、如果活细胞周围八个位置有两个或三个活细胞，则该位置活细胞仍然存活
     * 3、如果活细胞周围八个位置超过三个活细胞，则该位置活细胞死亡
     * 4、如果死细胞周围正好有三个活细胞，则该位置死细胞复活
     * 下个状态是通过上述规则同时应用于当前状态下的每个细胞所行程的，其中细胞的出生和死亡是同时发生的，
     *
     * @param board
     */
    public void gameOfLife(int[][] board) {
        //P1 b

        int row = board.length;
        int column = board[0].length;
        int[][] boardCopy = new int[row][column];

        //副本
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                boardCopy[i][j] = board[i][j];
            }
        }

//        for (int i = 0; i < row; i++) {
//            for (int j = 0; j < column; j++) {
//                int temp = 0;
//                //左上 存在 中上 中右存在
//                //temp ++ 返回是自增之前的值
//                //++ temp
//                if (0 <= i - 1 && j - 1 >= 0) {
//                    int i1 = boardCopy[i - 1][j - 1] == 1 ? ++temp : temp;//左上
//                }
//                if (i - 1 >= 0) {
//                    int i2 = boardCopy[i - 1][j] == 1 ? ++temp : temp;//中上
//
//                }
//                if (j - 1 >= 0) {
//                    int i3 = boardCopy[i][j - 1] == 1 ? ++temp : temp;//中左
//
//                }
//
//                if (i + 1 < row && j + 1 < column) {
//                    int i1 = boardCopy[i + 1][j + 1] == 1 ? ++temp : temp;//左下
//
//                }
//                if (j + 1 < column) {
//
//                    int i2 = boardCopy[i][j + 1] == 1 ? ++temp : temp;//中右
//                }
//
//                if (i + 1 < row) {
//                    int i3 = boardCopy[i + 1][j] == 1 ? ++temp : temp;//中下
//                }
//
//                if (i + 1 < row && 0 <= j - 1) {
//                    int i1 = boardCopy[i + 1][j - 1] == 1 ? ++temp : temp;   //左下
//                }
//
//                if (i - 1 >= 0 && j + 1 < column) {
//                    int i1 = boardCopy[i - 1][j + 1] == 1 ? ++temp : temp; //右上
//
//                }
//
//
//                //逻辑判断
//                if (boardCopy[i][j] == 1) {
//                    if (temp < 2 || temp > 3) {
//                        board[i][j] = 0;
//                    }
//                }
//                if (boardCopy[i][j] == 0 && temp == 3) {
//                    board[i][j] = 1;
//                }
//
//            }
//        }


        //P2 使用类似于方向数组 获取到周边的八个格子（可能存在无效）数组进行判断
        //类似于方向数组，这个用来控制周围的子格子位置，判断位置是否合法，在判断活细胞数量
        //定义方向数组用于查找八个领居
        int[] directions = {-1, 0, 1};


        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                int temp = 0;

                //通过方向数组计算邻居 TODO 巧妙
                for (int x : directions) {
                    for (int y : directions) {
                        if (x == 0 && y == 0) {//跳过自身
                            continue;
                        }
                        //判断次数没变 更加整洁
                        int r = i + x;
                        int c = j + y;
                        if (r >= 0 && r < row && c >= 0 && c < column && boardCopy[r][c] == 1) {
                            temp++;
                        }


                    }
                }
                // 逻辑判断
                if (boardCopy[i][j] == 1) {
                    if (temp < 2 || temp > 3) {
                        board[i][j] = 0; // 规则1和规则3
                    }
                } else {//细节减少了一次判断
                    if (temp == 3) {
                        board[i][j] = 1; // 规则4
                    }
                }

            }
        }
        //P3 原地算法，通过定义新的状态，既可以知道原先的状态，又可以知道现在的状态。再一次遍历，讲符合状态转换为现在的状态
    }

    /**
     * 接收 function  返回一个Predicate 断言
     */

    public static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
        Map<Object, Boolean> seen = new ConcurrentHashMap<>();//保存 function 返回的属性值
        return object -> {//返回 T -> boolean Predicate类型函数
            Object key = Optional
                    .ofNullable(object)//将function封装成 Optional
                    .map(keyExtractor)// 执行function映射 实际是就是实体类的get方法
                    .orElse(null);//否则返回 null
            if (key == null) return false;//null 直接返回
            return seen.putIfAbsent(key, Boolean.TRUE) == null; //不是null put进map
            // putIfAbsent方法，如果已存在这个key 返回key值，否则返回null
        };

    }

    static class Dish {//类
        String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static void main(String[] args) {
//        Dish dish1 = new Dish();
//        dish1.setName("111");
//        Dish dish2 = new Dish();
//        dish1.setName("222");
//        Dish dish3 = new Dish();
//        dish1.setName("333");
//
//        List<Dish> dishes = Arrays.asList(dish1, dish2, dish3);
//        List<Dish> collect = dishes.stream().filter(distinctByKey(Dish::getName)).collect(Collectors.toList());

        int[][] matrix = new int[][]{{0, 1, 0}, {0, 0, 1}, {1, 1, 1}, {0, 0, 0}};
        Solution solution = new Solution();
        solution.gameOfLife(matrix);


    }
}

