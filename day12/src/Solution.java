import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

class Solution {
    public int maxArea(int[] height){
        int maxWater = 0; //记录最大水量
        int left = 0;     //左值针：起始位置
        int right = height.length - 1; //右指针：末尾位置

        while(left < right){
            //计算当前水量：最小高度*水平距离
            int curHeight = Math.min(height[left], height[right]);
            int curWidth = right - left;
            int curWater = curHeight * curWidth;

            //更新最大水量
            maxWater = Math.max(maxWater, curWater);

            //贪心移动指针：移动较矮的柱子
            if(height[left] < height[right]){
                left++;   //左柱更矮，左指针右移
            } else {
                right--;  //右柱更矮/相等，右指针座椅
            }
        }
        return maxWater;
    }

    //三数之和
    public List<List<Integer>> threeSum(int[] nums){
        List<List<Integer>> res = new ArrayList<>();
        int n = nums.length;
        //边界处理：数组长度小于3，直接返回空集
        if(n < 3) return res;
        //数组排序
        Arrays.sort(nums);

        //遍历固定首元素nums[i], 作为三元组的a
        for(int i = 0; i < n; i++){
            if(nums[i] > 0) break;
            if (i > 0 && nums[i] == nums[i-1]) continue;;

            //初始化双指针，在i的右侧区间找b和c
            int j = i + 1;
            int k = n - 1;
            int target = -nums[i];

            while(j<k){
                int sum = nums[j] + nums[k];
                if(sum == target){
                    res.add(Arrays.asList(nums[i], nums[j], nums[k]));
                    //跳过左值针的重复值
                    while(j < k && nums[j] == nums[j+1]) j++;

                    while(j < k && nums[k] == nums[k-1]) k--;

                    j++;
                    k--;
                } else if (sum < target){
                    j++;
                } else{
                    k--;
                }
            }
        }
        return res;
    }
}
