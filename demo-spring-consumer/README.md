### 基础文件
```
project
    |- bin
        |- start.sh*
        |- stop.sh*
        |- status.sh*
    |- docker
        |- Dockerfile*
        |- run.sh*
    |- src
        |- main
            |- java
            |- resources
                |- config
                    |- logback-console.xml*
                    |- logback-file.xml*
        |- test
            |- java
            |- resources
    |- .gitignore*
    |- assembly-2.1.0.xsd*
    |- assembly-build.xml*
    |- assembly-single.xml*
    |- logback-1.1.xsd*
    |- pom.xml
    |- README.md*
```
说明：
- 1、有`*`的文件代表是模板文件，新建工程时需要直接拷贝来使用
- 2、新的工程必须要在project/src/main/resources/config/目录下创建MainClass文件，里面填写启动类的全限定名，例如：`com.lbole.MainClass`
- 3、工程的project/src/main/resources/config/目录下还可以创建JavaOpts文件，填写通过`start.sh`脚本启动时的jvm参数，例如：`-Xms1G -Xmx1G`

### pom.xml基础依赖
```xml
<?xml version="1.0" encoding="UTF-8"?>
<project>
    
    <parent>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-parent</artifactId>
        <version>xxxx</version>
    </parent>
    <modelVersion>4.0.0</modelVersion>

    <groupId>xxx</groupId>
    <artifactId>xxx</artifactId>
    <version>xxx</version>

    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
        <java.version>1.8</java.version>
        <!-- docker相关配置属性 -->
        <maven.build.timestamp.format>yyMMddHHmmss</maven.build.timestamp.format>
        <docker.image.namespace>xxx/xxx</docker.image.namespace><!-- docker镜像仓库地址和命名空间 -->
        <docker.image.name>${project.artifactId}</docker.image.name><!-- 镜像名称 -->
        <docker.image.version>${project.version}.${maven.build.timestamp}</docker.image.version><!-- 镜像版本 -->
    </properties>
    
    <dependencies>
    
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-configuration-processor</artifactId>
            <optional>true</optional>
        </dependency>
        
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
    
    </dependencies>

    <build>
            
        <plugins>
            
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <configuration>
                    <source>${java.version}</source>
                    <target>${java.version}</target>
                </configuration>
            </plugin>
            
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-assembly-plugin</artifactId>
                <version>3.1.0</version>
                <executions>
                    <execution>
                        <id>assembly-single</id>
                        <phase>package</phase>
                        <goals>
                            <goal>single</goal>
                        </goals>
                        <configuration>
                            <finalName>${project.artifactId}-exec</finalName>
                        </configuration>
                    </execution>
                </executions>
                <configuration>
                    <!-- 不在打包的后面添加ID作为后缀 -->
                    <appendAssemblyId>false</appendAssemblyId>
                    <descriptors>
                        <descriptor>assembly-single.xml</descriptor>
                    </descriptors>
                </configuration>
            </plugin>
            
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
            
            <plugin>
                <groupId>io.fabric8</groupId>
                <artifactId>docker-maven-plugin</artifactId>
                <version>0.34.1</version>
                <!--<executions>
                    <execution>
                        <id>docker-build</id>
                        <phase>package</phase>
                        <goals>
                            <goal>build</goal>
                        </goals>
                    </execution>
                    <execution>
                        <id>docker-save</id>
                        <phase>package</phase>
                        <goals>
                            <goal>save</goal>
                        </goals>
                    </execution>
                </executions>-->
                <configuration>
                    <images>
                        <image>
                            <name>${docker.image.namespace}/${docker.image.name}:${docker.image.version}</name>
                            <alias>${docker.image.name}</alias>
                            <build>
                                <contextDir>${project.basedir}/docker/</contextDir>
                                <args>
                                    <JAR_FILE>${project.build.finalName}.jar</JAR_FILE>
                                </args>
                                <assembly>
                                    <mode>dir</mode>
                                    <name>/</name>
                                    <descriptor>${project.basedir}/assembly-build.xml</descriptor>
                                </assembly>
                            </build>
                        </image>
                    </images>
                    <saveAlias>${docker.image.name}</saveAlias>
                    <saveFile>target/${docker.image.name}-image.tar.gz</saveFile>
                </configuration>
            </plugin>
        
        </plugins>
    
    </build>

</project>
```
说明：
- 普通打包：`mvn clean package -DskipTests`。会输出一个`*-exec.tar.gz`的包。
- Docker构建镜像：`mvn clean package docker:build -DskipTests`，镜像会安装到本地。如果想在执行package的时候自动执行docker:build，则将docker插件中的docker-build目标注释打开。
- Docker镜像打包：`mvn clean package docker:build docker:save -DskipTests`，会输出一个`*-image.tar.gz`的包。如果想执行package的时候自动执行docker:save，则将docker插件中的docker-save目标注释代开。

注意：Docker相关操作需要有Docker环境

### 启动类基础代码
```java
@SpringBootApplication
public class XXXApplication {
    
    static {
        URL baseUrl = XXXApplication.class.getClassLoader().getResource("");
        Path baseDir = new File(Objects.requireNonNull(baseUrl).getPath()).toPath();
        setIfAbsent("base.dir", baseDir.toString());
        setIfAbsent("config.dir", baseDir.resolve("config").toString());
        setIfAbsent("logs.output", "console");
        setIfAbsent("logs.dir", baseDir.resolve("logs").toString());
        setIfAbsent("logs.namespace", "service");
    }
    
    public static String setIfAbsent(String key, String defaultValue) {
        String value = System.getProperty(key);
        if (null == value || 0 == value.length()) {
            value = defaultValue;
            System.setProperty(key, value);
        }
        return value;
    }
    
    public static void main(String[] args) {
        SpringApplication.run(XXXApplication.class, args);
    }
    
}
```

### spring的yaml配置中加入如下配置
```yaml
logging:
  config: ${config.dir}/logback-${logs.output}.xml
```
或者
```yaml
logging:
  config: classpath:config/logback-${logs.output}.xml
```
**注:** 所有配置文件需要放在./src/main/resources/config/下
