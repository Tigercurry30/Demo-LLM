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
2. 请求模型（纯文本or多模态）chatClient

#### RedisHistoryRepository

功能：基于Redis实现HistoryRepository接口，完成会话记录存储和会话历史查询

## 猜词游戏(Prompt Engineering)

### 功能

​	调用qwq-plus模型，基于纯Prompt模式实现猜词功能。在游戏开始时，模型会预先想出一个地名（限定在中国/XX省/XX市or外国的一些常见地区如波士顿、阿姆斯特丹等），用户有7次机会进行提问，模型根据内容生成答案。

### 实现

#### GameController

功能：接收用户访问请求，请求路径：“/ai/game”。

1. 保存会话类型和Id记录（type, chatId）
2. 设置系统提示词
3. 请求模型gameChatClient

## 智能客服(Function Calling)

​	调用qwq-plus模型，基于Function Calling模式实现客服助手。实现一个24小时在线的AI智能客服，向用户提供培训课程咨询服务，帮用户预约线下课程试听。
### 业务流程
了解、分析用户的兴趣、学历等信息  ——  查询课程信息  ——  给用户推荐课程  ——  查询校区信息  ——  引导用户预约试听  ——  引导学生留下联系方式  ——  新增课程试听预约单
### Function Calling: 
  1. Tool Definition  
  2. Tool Execution
     2.1 Receiving Request
     2.2 Dispatching Tool Call Request
     2.3 Executing Tool Call
     2.4 Sending Tool Call Response
  3. Response Generation
### 实现

#### CustomServiceController
功能：接收用户访问请求，请求路径：“/ai/service”。
1. 保存会话类型和Id记录（type, chatId）
2. 请求模型serviceChatClient
#### CourseTools
功能：实现数据库操作（根据条件查询课程、查询校区信息、新增预约单等）

## ChatPDF(RAG)
调用text-embedding-v3将文本向量化；调用qwen-plus模型，基于RAG模式实现根据PDF内容回答问题的功能。
### 业务流程
存储PDF作为知识库  ——  根据问题检索知识库  ——  对话大模型  ——  返回答案
1. 存储PDF作为知识库
1.1 读取用户相传的PDF并拆分为Document
1.2 调用text-embedding-v3向量模型将Document向量化
1.3 将Document存储到SimpleVectorStore向量数据库中
2. 根据问题检索知识库
2.1 用户问题向量化
2.2 从SimpleVectorStore向量数据库中检索出最匹配的Document
3. 对话大模型
3.1 将检索到的Document和用户问题拼接为提示词
3.2 请求模型
4. 返回答案
### 实现
#### PdfController
功能：接收用户访问请求，请求路径：“/ai/pdf”。
1. 保存会话类型和Id记录（type, chatId）
2. 文件上传、下载、拆分为Document并写入向量数据库、请求模型
#### FileRepository
1. 保存文件、记录文件与conversationId的对应关系
2. 根据conversationId查询PDF文件



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
功能：基于Redis实现了ChatMemory接口。add/get/clear将会话记忆存储在Redis中。

# 快速开始
## git clone
```git clone git@github.com:Tigercurry30/Demo-LLM.git```

## 修改配置文件
1. API_KEY
2. redis
3. datasource
4. model也可切换为自己喜欢的