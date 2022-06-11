# war-tomcat-pods

## 介绍

定义了一个 java-war 的初始化容器（*initContainers*），和一个标准的 Tomcat Server 容器 tomcat-server。

主要目的在于用一种“组合”方式，解决了 WAR 包与 Tomcat 容器之间耦合关系的问题。

> 如果把 WAR 包直接放在 Tomcat 镜像的 webapps 目录下，做成一个新的镜像运行起来。 可是，这时候，如果你要更新WAR包的内容，或者要升级Tomcat镜像，就要重新制作一个新的 发布镜像，非常麻烦。

所以通过 Volume 的方式， 将两个容器进行整合，即定义一个 app-volume：
- **_java-war_**: 将打包好的 war 包 *webapp.war* 文件挂载到 app-volume
- **_tomcat-server_**: 将 app-volume 挂载到 */usr/local/tomcat/webapps* 目录下

## 运行

```shell
$ zsh webapp/build.sh
$ kubectl apply -f application.yaml
```