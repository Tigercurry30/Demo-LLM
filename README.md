# 项目(Demo-LLM)结构

```
├─src
   ├─main
      ├─java
      │  └─com
      │      └─example
      │          ├─config
      │          ├─constants
      │          ├─controller
      │          ├─entity
      │          │  ├─po
      │          │  ├─query
      │          │  └─vo
      │          ├─mapper
      │          ├─memory
      │          ├─model
      │          ├─repository
      │          ├─service
      │          │  └─impl
      │          ├─sql
      │          ├─tools
      │          └─utils
      └─resources
          ├─mapper
          ├─static
          └─templates
```

# 功能模块

## 聊天机器人

### 功能

​	调用qwen-omni-turbo模型，基于会话记忆实现用户与大模型的聊天功能，支持文本、音频和图像等多模态输入。通过将会话历史保存在Redis中，支持会话历史查询。

### 实现

#### ChatController

功能：接收用户访问请求，请求路径：“/ai/chat”。

1. 保存会话类型和Id记录（type, chatId）
2. 请求模型（纯文本or多模态）

#### RedisHistoryRepository

功能：基于Redis实现HistoryRepository接口，完成会话记录存储和会话历史查询

## 猜词游戏(Prompt Engineering)

### 功能

​	调用qwq-plus模型，基于纯Prompt模式实现猜词功能。具体而言，每一次conversation代表一次游戏，在游戏开始时，模型会预先想出一个地名（限定在中国/XX省/XX市or外国的一些常见地区如波士顿、阿姆斯特丹等），用户有7次机会进行提问，模型根据内容生成答案。

### 实现

#### GameController

功能：接收用户访问请求，请求路径：“/ai/game”。

1. 保存会话类型和Id记录（type, chatId）
2. 设置系统提示词
3. 请求模型（纯文本or多模态）

## 智能客服(Function Calling)

​	调用qwq-plus模型，基于Function Calling模式实现客服助手。

## ChatPDF(RAG)

## 会话历史管理

### HistoryRepository

1. ```
   void insertHistoryIds(String type, String chatId);    //保存每条会话记录
   ```

2. ```
   List<String> getAllHistoryIdsByType(String type);     //根据会话Type查询会话历史
   ```

## 会话记忆实现

### RedisMemory

功能：基于Redis实现了ChatMmory接口。add/get/clear将会话记忆存储在Redis中。

<img src="C:\Users\Tigercurry\AppData\Roaming\Typora\typora-user-images\image-20250416141023869.png" alt="image-20250416141023869" style="zoom:55%;" />

# 快速开始
