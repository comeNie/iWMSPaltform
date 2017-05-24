package com.huamengtong.wms.constants;

public class MqConstants {

    //MQ消息延迟配置项配置从1级开始，各级延时的时间，可以修改这个指定级别的延时时间；
    //时间单位支持：s、m、h、d，分别表示秒、分、时、天，level=0 级表示不延时，level=1 表示 1 级延时，level=2 表示 2 级延时，以此类推
    // 1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h
    public static final int MQ_DELAY_LEVEL = 9;//MQ延时级别 5 分钟
    public static final int MQ_DELAY_LEVEL_10M = 14;//MQ延时级别 10 分钟

    public static final String PRODUCER_GROUP = "CWmsProducerGroup";
    public static final String CONSUMER_INSTANCENAME="CWmsConsumer";


    public static final String CONSUMER_GROUP_NAME = "ConsumerGroupName";
    public static final String CONSUMER_TOPIC ="ConsumerTopic";
    public static final String CONSUMER_TAG ="ConsumerTag";


    public static final String C_GROUP_NAME_OPERATION_LOG = CONSUMER_GROUP_NAME + "_operationLog";
    public static final String C_TOPIC_CONSUMER_LOG = CONSUMER_TOPIC + "_Log";
    public static final String C_TAG_OPERATON_LOG = CONSUMER_TAG +"_operationLog";

    public static final String C_GROUP_NAME_INVENTORY_LOG = CONSUMER_GROUP_NAME + "_inventoryLog";
    public static final String C_TAG_INVENTORY_LOG =CONSUMER_TAG +"_inventoryLog";

    public static final String C_GROUP_NAME_PRO_INVENTORY_LOG = CONSUMER_GROUP_NAME + "_proInventoryLog";
    public static final String C_TAG_PRO_INVENTORY_LOG = CONSUMER_TAG + "_proInventoryLog";

    public static final String C_GROUP_NAME_TRANSACTION_LOG = CONSUMER_GROUP_NAME + "_transactionLog";
    public static final String C_TAG_TRANSACTION_LOG =CONSUMER_TAG +"_transactionLog";

    public static final String PRODUCER_ORDER_GROUP = "CWmsProducerOrderGroup";

    public static final String C_GROUP_CONSUMER_ORDER = CONSUMER_GROUP_NAME + "_Order";
    public static final String C_TOPIC_CONSUMER_ORDER = CONSUMER_TOPIC + "_Order";
    public static final String C_TAG_ORDER = CONSUMER_TAG + "_order";

    public static final String C_GROUP_CONSUMER_ORDER_DELAY = CONSUMER_GROUP_NAME + "_OrderDelay";
    public static final String C_TOPIC_CONSUMER_ORDER_DELAY = CONSUMER_TOPIC + "_OrderDelay";
    public static final String C_TAG_ORDER_DELAY = CONSUMER_TAG + "_orderDelay";

    public static final String C_GROUP_CONSUMER_ORDER_DELIVER = CONSUMER_GROUP_NAME + "_OrderDeliver";
    public static final String C_TOPIC_CONSUMER_ORDER_DELIVER = CONSUMER_TOPIC + "_OrderDeliver";
    public static final String C_TAG_ORDER_DELIVER = CONSUMER_TAG + "_OrderDeliver";

    public static final String C_GROUP_CONSUMER_ORDER_DELIVER_DELAY = CONSUMER_GROUP_NAME + "_OrderDeliverDelay";
    public static final String C_TOPIC_CONSUMER_ORDER_DELIVER_DELAY = CONSUMER_TOPIC + "_OrderDeliverDelay";
    public static final String C_TAG_ORDER_DELIVER_DELAY = CONSUMER_TAG + "_OrderDeliverDelay";




}
