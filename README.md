# demo
## 快速启动
1. 安装docker 
 ```
 brew cask install docker
 ``` 
2. 构建出quantization镜像
 ```
 mvn clean package docker:build
 ``` 
3. 修改docker-compose.yml或docker-compose-linux.yml
 ```
 windows和mac需指定fastdfs容器所使用网段和容器ip，linux只需指定fastdfs容器ip
 ``` 
4. 使用docker-compose编排容器    
 ```
 windows和mac使用命令docker-compose up -d，默认docker-compose.yml文件启动，
 linux需要使用命令docker-compose -f docker-compose-linux.yml up -d，指定docker-compose-linux.yml文件启动
 ```    
5. swagger文档访问地址  
```
 http://localhost:8888/swagger-ui.html
```   
备注：快速启动需要占用8888、3300、9999、22122、23000端口，如果出现端口冲突可自行修改


## 生成jks证书
```
 keytool -genkey -keystore quantization.jks -alias quantization -keyalg RSA -keypass quantization_password -storepass quantization_password
``` 
 ## 查看公钥信息
 ```
 keytool -list -rfc --keystore quantization.jks | openssl x509 -inform pem -pubkey
 ``` 

"# demo" 
