/*
 * Copyright 2020 rannuo1010@gmail.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.fshop.component.redis;

import com.google.common.base.Preconditions;
import com.google.common.hash.Funnel;
import com.google.common.hash.Hashing;

/**
 * Description：布隆过滤器
 *
 * @author rannuo1010@gmail.com
 * @date 2020/2/22
 */
public class BloomFilter<T> {
    /**
     * hash函数的个数
     */
    private int numOfHashFunctions;
    private int bitSize;
    private Funnel<T> funnel;

    /**
     * @param funnel    漏斗
     * @param errorRate 错误率，期望错误率越低，需要的空间就越大（例如：0.0001代表0.01%）
     * @param capacity  初始容量，当实际元素的数量超过这个初始化容量时，误判率上升
     */
    public BloomFilter(Funnel<T> funnel, double errorRate, int capacity) {
        Preconditions.checkArgument(funnel != null, "funnel不能为空");
        this.funnel = funnel;
        bitSize = calcOptimalNumOfBits(capacity, errorRate);
        numOfHashFunctions = calcOptimalNumOfHashFunctions(capacity, bitSize);
    }

    /**
     * 计算哈希偏移
     *
     * @param value
     * @return
     */
    int[] calcHashOffset(T value) {
        int[] offset = new int[numOfHashFunctions];
        long hash64 = Hashing.murmur3_128().hashObject(value, funnel).asLong();
        int hash1 = (int) hash64;
        int hash2 = (int) (hash64 >>> 32);
        for (int i = 1; i <= numOfHashFunctions; i++) {
            int nextHash = hash1 + i * hash2;
            if (nextHash < 0) {
                nextHash = ~nextHash;
            }
            offset[i - 1] = nextHash % bitSize;
        }
        return offset;
    }

    /**
     * 计算最佳布隆过滤器容量（计算公式：m=-n*lnp / (ln2)^2）
     *
     * @param n 插入元素个数
     * @param p 错误率
     * @return 布隆过滤器容量
     */
    private int calcOptimalNumOfBits(long n, double p) {
        if (p == 0) {
            p = Double.MIN_VALUE;
        }
        return (int) (-n * Math.log(p) / (Math.log(2) * Math.log(2)));
    }

    /**
     * 计算最佳哈希函数数（计算公式：k=m/n * ln2）
     *
     * @param n 插入元素个数
     * @param m 布隆过滤器容量
     * @return 哈希函数个数
     */
    private int calcOptimalNumOfHashFunctions(long n, long m) {
        return Math.max(1, (int) Math.round((double) m / n * Math.log(2)));
    }

}
