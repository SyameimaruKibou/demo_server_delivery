﻿# demo_server_delivery

本项目用于存放与服务端项目交互必要的model文件，接口规定以及其他资源等  
之后的内容不再放在qq群  
每次更新的时候会在这里通知更新内容与更新时间，各客户端根据更新内容与自己情况选择是否需要下载更新过的内容

//更新（20/4/22）
更新内容：
1.服务器中，理论上和主要业务有关的所有功能，具体看接口文件\n
2.修改了大部分数据库结构，所以model文件也基本修改过，强 烈 建 议重新全部下载model文件夹中所有源码
  model中具体修改了哪些内容：
  2.1.快件：
    对快件增加NextNode字段（TransNode类型），该字段的赋值与管理由服务器处理
  2.2.包裹/快件历史：
    现在历史中的SourceNode和TargetNode为TransNode类型，包含所有信息
  2.3.用户信息：
    现在UserInfo对象中包含x,y值，用于后续快递员与司机定位等相关的功能（客户端设置定时上传User x,y位置，需要定位时，从服务器拉取User信息即可）
  2.4.节点：
    （重要！）主码由int类型变为String类型；等级（Type）由二级变为三级；
3.关于更新过的业务代码如何运作可以随时再问我

注意点：
1.因为之前我把所有model（如ExpressSheet，TempSheet，History等）中的"Receiver"错拼成了"Reciever"，现在在新model中全都改回来了（更正为"Receiver"），所以估计客户端里许多相关赋值代码会出错，记得修改
2.你可以发现model中许多类中包含其他类的对象成员，但是有的暂时无法获得数据（比如ExpressSheet类中有ExpressHistroy对象的成员，但目前快件中不包括历史信息），这些会在未来得到解决
3.一时想不出，想到我再写
