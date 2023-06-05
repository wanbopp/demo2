package wanbo.com;

import java.util.HashMap;
import java.util.Map;

/**
 * @version 1.0
 * @Author wanbo_pan
 * @Date 2023/4/17 8:49
 * @注释
 */
public class one {

    //TODO 1、无重复的最长字串
    //滑动窗口

        public int lengthOfLongestSubstring(String s) {
            // 记录字符上一次出现的位置
            int[] last = new int[128];
            for(int i = 0; i < 128; i++) {
                last[i] = -1;
            }
            int n = s.length();

            int res = 0;
            int start = 0; // 窗口开始位置
            for(int i = 0; i < n; i++) {
                int index = s.charAt(i);
                start = Math.max(start, last[index] + 1);
                res   = Math.max(res, i - start + 1);
                last[index] = i;
            }

            return res;
        }


}
