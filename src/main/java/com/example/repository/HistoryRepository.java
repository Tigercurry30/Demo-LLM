package com.example.repository;

/*
    会话管理
 */
import java.util.List;

public interface HistoryRepository {

    /**
     * 保存会话记录
     * @param type   业务类型：chat\service\pdf
     * @param chatId 会话Id
     */
    void insertHistoryIds(String type, String chatId);

    /**
     * 查询所有会话记录
     * @param type 业务类型：chat\service\pdf
     * @return     会话Id列表
     */
    List<String> getAllHistoryIdsByType(String type);

}
