# Day 1 Notes

## 今日目标
1. 复刻原项目模块结构
2. 复刻父子 pom
3. 复刻 hello-service-api
4. 复刻 RpcRequest / RpcResponse / RpcMessage

## 模块职责
- rpc-framework-common: 公共工具、枚举、异常、SPI 等
- rpc-framework-simple: RPC 核心实现
- hello-service-api: 服务接口与 DTO
- example-server: 服务提供者示例
- example-client: 服务消费者示例

## 今日理解
- RpcRequest/RpcResponse 是业务调用模型
- RpcMessage 是协议传输模型
- hello-service-api 是服务契约层

## 明日计划
- 复刻 rpc-framework-common
- 初步复刻 ServiceProvider、RpcRequestHandler、Socket 通信