package com.sici.common.constant.redis.key;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.common.constant.utilsredis.key
 * @author: 20148
 * @description:
 * @create-date: 11/21/2024 8:44 PM
 * @version: 1.0
 */

public class Solution {
    public static boolean isPrime(int x) {
        for (int i = 2; i <= x / i; i ++) {
            if (x % i == 0) return false;
        }
        return true;
    }
    public static void main(String[] args) {
        for (int i = 101; i <= 200; i ++) {
            if (isPrime(i)) {
                System.out.print(i + " ");
            }
        }
        System.out.println();
    }
}
