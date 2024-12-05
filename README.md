# gen-receipt-number

见sql脚本，mysql版

实现机制：
1. 多规则组合生成流水
2. 乐观锁防止跳号

测试用例：

POST: http://127.0.0.1:8080/system/documentRule/generateIdNumber
param: documentNameCode = ''
