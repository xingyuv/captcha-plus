# Q&A

## 3.1 linux部署注意事项点选文字

### 3.1.1 字体乱码问题

点选文字中所用字体默认为宋体，linux不支持该字体，所以可能会出现以下图中情况，如图3-1所示。

![字体错误](https://mirror.anji-plus.com/captcha-web/static/font-error.png "字体错误")

图3-1 点选文字字体乱码

### 3.1.2 乱码解决方案

宋体黑体为例

#### 1、安装字体库

在CentOS 4.x开始用fontconfig来安装字体库，所以输入以下命令即可：

``` shell
sudo yum -y install fontconfig
```

这时在/usr/shared目录就可以看到fonts和fontconfig目录了（之前是没有的）：
接下来就可以给我们的字体库中添加中文字体了。

#### 2、首先在/usr/shared/fonts目录下新建一个目录chinese：

CentOS中，字体库的存放位置正是上图中看到的fonts目录，所以我们首先要做的就是找到中文字体文件放到该目录下，而中文字体文件在我们的windows系统中就可以找到，打开c盘下的Windows/Fonts目录：

#### 3、紧接着需要修改chinese目录的权限：

``` shell
sudo chmod -R 755 /usr/share/fonts/chinese
```

接下来需要安装ttmkfdir来搜索目录中所有的字体信息，并汇总生成fonts.scale文件，输入命令：

``` shell
sudo yum -y install ttmkfdir
```

然后执行ttmkfdir命令即可：

``` shell
ttmkfdir -e /usr/share/X11/fonts/encodings/encodings.dir
```

#### 4、最后一步就是修改字体配置文件了，首先通过编辑器打开配置文件 ：

``` shell
vim /etc/fonts/fonts.conf
```

可以看到一个Font list，即字体列表，在这里需要把我们添加的中文字体位置加进去：

``` shell
/usr/share/fonts/chinese
```

#### 5、然后输入:wq保存退出，最后别忘了刷新内存中的字体缓存，这样就不用reboot重启了：

``` shell
fc-cache
```

#### 6、这样所有的步骤就算完成了，最后再次通过fc-list看一下字体列表：

``` shell
fc-list
```
