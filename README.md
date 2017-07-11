# springboot

springboot 笔记

## 目录

- `A01-hello-world`(1.4.3)
- `A02-hibernate`(1.4.3)

## hello world

- 引入依赖

	```xml
	<parent>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-parent</artifactId>
		<version>1.4.3.RELEASE</version>
		<relativePath/> <!-- lookup parent from repository -->
	</parent>

	<dependencies>
		<!--包含spring-boot-starter、hibernate-validator、jackson-databind、spring-web、spring-webmvc-->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
		</dependency>
	</dependencies>
	```	
- 启动类 `SpringbootApplication.java`
	
	```java
	@RestController // @Controller
	@EnableAutoConfiguration // 开启自动配置
	public class SpringbootApplication {
		// 访问http://localhost:8080/
		@RequestMapping("/")
		String home() {
			return "Hello World!";
		}

		public static void main(String[] args) {
			SpringApplication.run(SpringbootApplication.class, args);
		}
	}
	```	
- 至此，无需其他任何配置

## 对hibernate的默认支持

- 引入数据库和jpa

	```xml
	<dependency>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-data-jpa</artifactId>
	</dependency>
	<dependency>
		<groupId>mysql</groupId>
		<artifactId>mysql-connector-java</artifactId>
		<scope>runtime</scope>
	</dependency>
	```
- 数据库配置

	```properties
	spring.datasource.url=jdbc:mysql://localhost/test
	spring.datasource.username=root
	spring.datasource.password=root
	## spring.datasource.driver-class-name=com.mysql.jdbc.Driver

	## spring.jpa.database=MYSQL
	# Show or not log for each sql query
	spring.jpa.show-sql=true
	# Hibernate ddl auto (create, create-drop, update)
	spring.jpa.hibernate.ddl-auto=update
	```
- `UserDao.java`示例

	```java
	// CrudRepository 已经定义好了基本增删查改相关方法
	public interface UserDao extends CrudRepository<User, Long> {
		// spring data jpa 根据属性名和查询关键字自动生成查询方法
		User findByUsername(String username);
	}
	```