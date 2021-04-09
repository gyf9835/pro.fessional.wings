# RestHalf - 复杂业务场景化的URL设计规范

wings中的URL设计，参考了RESTful和GraphQL，但又必须传统，故取名叫RestHalf。

## 1.复杂业务场景化

在RestHalf实践中，业务的复杂性主要来自于①业务自身在演化②人员认知在涌现。

* 做着做着，东西就变了，和原来做的不一是一个东西。
* 写着写着，就写明白了，之前的做法并不是最优的。

在技术方案领域，暂无`F=Ma`这种牛顿公式，好比ejb，rmi，wsdl一定要踩过。

* RESTful的不够用在于简单，资源仅是业务的参与者之一。
* GraphQL的不好用在于复杂，问题并未分解，又多一种SQL。

对于复杂的东西，唯有分解，对于变化的东西，唯有适应。

所谓场景化，即固定上下文和寻找确定性。场景化有以下参与者和约定

* 资源 - 数据流，任何资源都有唯一id，即便是从属关系。
* 事件 - 业务流，事件触发数据的产生，变化和消失。
* 功能 - 场景框，输入了什么，输出了什么。

## 