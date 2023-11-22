package wanbo.com.suanfa;

import java.util.*;

public class RandomizedSet {


    /**
     * 380. O(1) 时间插入、删除和获取随机元素
     * 实现 RandomizedSet 类
     * RandomizedSet() 初始化 RandomizedSet对象
     * boolean insert(int val) 当val元素不存在时，向集合中插入该项，并返回true，否则返回false
     * boolean remove(int val) 当val元素存在时，从集合中移除该项，并返回true，否则返回false
     * int getRandom() 随机返回现有集合中的一项（测试用例保证调用此方法时集合中至少存在一个元素）。每个元素应该有 相同的概率 被返回。
     * <p>
     * 你必须实现类的所有函数，并满足每个函数的 平均 时间复杂度为 O(1) 。
     * <p>
     * <p>
     * 示例：
     * 输入
     * ["RandomizedSet", "insert", "remove", "insert", "getRandom", "remove", "insert", "getRandom"]
     * [[], [1], [2], [2], [], [1], [2], []]
     * 输出
     * [null, true, false, true, 2, true, false, 2]
     * <p>
     * 解释
     * RandomizedSet randomizedSet = new RandomizedSet();
     * randomizedSet.insert(1); // 向集合中插入 1 。返回 true 表示 1 被成功地插入。
     * randomizedSet.remove(2); // 返回 false ，表示集合中不存在 2 。
     * randomizedSet.insert(2); // 向集合中插入 2 。返回 true 。集合现在包含 [1,2] 。
     * randomizedSet.getRandom(); // getRandom 应随机返回 1 或 2 。
     * randomizedSet.remove(1); // 从集合中移除 1 ，返回 true 。集合现在包含 [2] 。
     * randomizedSet.insert(2); // 2 已在集合中，所以返回 false 。
     * randomizedSet.getRandom(); // 由于 2 是集合中唯一的数字，getRandom 总是返回 2 。
     * <p>
     * <p>
     * <p>
     * Your RandomizedSet object will be instantiated and called as such:
     * RandomizedSet obj = new RandomizedSet();
     * boolean param_1 = obj.insert(val);
     * boolean param_2 = obj.remove(val);
     * int param_3 = obj.getRandom();
     * <p>
     * 【设计】【数组】【哈希表】【数学】【随机化】
     * 变长数组 + hash表
     */
    List<Integer> arr;
    Map<Integer, Integer> index;
    Random random;


    public RandomizedSet() {
        arr = new ArrayList<>();//变长数组自动维护
        index = new HashMap<>();//利用hashMap的特性 保存key和数组中位置
        random = new Random();

    }

    public boolean insert(int val) {
        //判断是否包含
        if (index.containsKey(val)) {
            return false;
        }
        int size = arr.size();
        arr.add(val);
        index.put(val, size);
        return true;

    }

    public boolean remove(int val) {
        //注意需要填充删除的位置 不然后面的下标会整体改变
        if (!index.containsKey(val)) {
            return false;
        }

        Integer aim = index.get(val);
        Integer last = arr.get(arr.size() - 1);
        //将最后元素的位置放到这里
        arr.set(aim, last);//需要被移除的元素被覆盖了
        index.put(last, aim);//更新hashmap中的索引
        arr.remove(index.size() - 1);//移除最后的元素 非常巧妙 即减少元素挪动 又可以避免数组中只有一个元素的操作
        index.remove(val);//删除 移除元素的索引

        return true;
    }

    public int getRandom() {
        int i = random.nextInt(arr.size());
        return arr.get(i);
    }


}
