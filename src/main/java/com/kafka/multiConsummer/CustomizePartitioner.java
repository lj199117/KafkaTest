package com.kafka.multiConsummer;

import kafka.producer.Partitioner;
import kafka.utils.VerifiableProperties;
/**
 * 指定关键字key，发送消息到指定partitions
 * 
 * 说明：如果需要实现自定义partitions消息发送，需要实现Partitioner接口
 * @author <a href="mailto:lijin@webull.com">李锦</a>
 * @since 0.1.0
 */
public class CustomizePartitioner implements Partitioner {
    public CustomizePartitioner(VerifiableProperties props) {
 
    }
    /**
     * 返回分区索引编号
     * @param key sendMessage时，输出的partKey
     * @param numPartitions topic中的分区总数
     * @return
     */
    @Override
    public int partition(Object key, int numPartitions) {
        System.out.println("key:" + key + "  numPartitions:" + numPartitions);
        String partKey = (String)key;
        if ("part2".equals(partKey))
            return 2;
//        System.out.println("partKey:" + key);
 
        return 0;
    }
}
