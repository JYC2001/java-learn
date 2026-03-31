import com.example.Main;

class Array_Solution {
    //删除有序数组中的重复项
    public int removeDuplicates(int[] nums){
        if(nums == null || nums.length == 0) return 0;
        int slow = 0;

        for(int fast = 1; fast < nums.length; fast++){
            if(nums[fast] != nums[slow]){
                slow++;
                nums[slow] = nums[fast];
            }
        }
        return slow + 1;
    }
    //移除元素
    public int removeElement(int[] nums, int val){
        //左值针：找等于val的位置
        int left = 0;
        //右指针：找不等于val的位置
        int right = nums.length -1;
        while(left <= right){
            if(nums[left] == val){
                nums[left] = nums[right];
                right--;
            } else {
                left++;
            }
        }
        return left;
    }
    //最大子数组和
    public int maxSubArray(int[] nums){
        //当前子数组的和
        int curSum = nums[0];
        //记录全局最大和
        int maxSum = nums[0];

        for(int i = 1; i < nums.length; i++){
            //核心：要么把当前数加入前面的子数组
            //要么以当前数作为薪资数组的起点
            curSum = Math.max(nums[i], curSum + nums[i]);

            maxSum = Math.max(maxSum, curSum);
        }
        return maxSum;
    }
}
