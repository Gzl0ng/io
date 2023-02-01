package test;

import org.junit.jupiter.api.Test;

/**
 * @author:郭正龙
 * @data:2023/1/12
 */
public class test {
    @Test
    public void test1(){
        //有序列表
        int[] list = new int[10];
        for (int i = 0; i < list.length; i++) {
            list[i] = i*3;
        }

        //需要查找的值
        int compare = 6;

        //开始匹配
        int left = 0;
        int right = list.length-1;
        while (left<right){
            for (int i = 0; i < list.length; i++) {
                int mid = (left + right) / 2;
                if (list[mid]<compare){
                    left = mid+1;
                }else if (list[mid] > compare){
                    right = mid-1;
                }else {
                    System.out.println("下标为="+mid+ " 值为=" +list[mid]);
                    return;
                }
            }
        }

    }
}
