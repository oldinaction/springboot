### Spring Data Rest

> - 参考文档：https://springcloud.cc/spring-data-rest-zhcn.html 、 https://docs.spring.io/spring-data/rest/docs/3.1.0.RELEASE/reference/html/

> - 如何在返回数据的根节点插入一个属性(如code)？？？
> - 如何和swagger整合(整合2.9.2不显示方法)？？？

- Spring Data JPA是基于Spring Data的repository之上，可以将repository自动输出为REST资源。目前支持将Spring Data JPA、Spring Data MongoDB、Spring Data Neo4j等自动转换成REST服务
- 引入依赖(基于Spring JPA项目测试)

```xml
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-data-rest</artifactId>
</dependency>
<!-- 可选(不太好用)。HAL Browser：可直接测试API接口。访问 http://localhost:8080/api/ 即可看到 UI 界面(此时配置了rest根节点为 /api)。可使用POSTMAN代替 -->
<dependency>
	<groupId>org.springframework.data</groupId>
	<artifactId>spring-data-rest-hal-browser</artifactId>
</dependency>
```
- Repository `public interface PersonRepository extends JpaRepository<Person, Long> {}`
- 引入依赖后访问 `http://localhost:8080/persons` 即可看到返回(启动项目时也可以看到相应端点)

```js
// HAL（Hypertxt Application Language）风格REST。Spring Hateoas
// http://localhost:8080/persons
{
	_embedded: {
		persons: [{
				name: "smalle",
				age: 18,
				address: "上海",
				_links: {
					self: {
						href: "http://localhost:8080/persons/1"
					},
					person: {
						href: "http://localhost:8080/persons/1"
					}
				}
			},
			{
				name: "aezo",
				age: 20,
				address: "北京",
				_links: {
					self: {
						href: "http://localhost:8080/persons/2"
					},
					person: {
						href: "http://localhost:8080/persons/2"
					}
				}
			}
		]
	},
	_links: {
		self: {
			href: "http://localhost:8080/persons{?page,size,sort}",
			templated: true
		},
		profile: {
			href: "http://localhost:8080/profile/persons"
		}
	},
	page: {
		size: 20,
		totalElements: 2,
		totalPages: 1,
		number: 0
	}
}

// http://localhost:8080/persons/1
{
	name: "smalle",
	age: 18,
	address: "上海",
	_links: {
		self: {
			href: "http://localhost:8080/api/people/1"
		},
		person: {
			href: "http://localhost:8080/api/people/1"
		}
	}
}
```
- 扩展配置

```yml
spring:
  data:
	rest:
	  # 自定义根路径. 此时访问 http://localhost:8080/api/xxx
      base-path: /api
```

```java
@RepositoryRestResource(path = "people") // 修改默认的节点路径(实体名加s)。此时访问 http://localhost:8080/api/people
public interface PersonRepository extends JpaRepository<Person, Long> {
	@RestResource(path = "nameStartsWith") // 自定义服务暴露为REST资源，访问 http://localhost:8080/api/people/search/nameStartsWith?name=sma
	Person findByNameStartsWith(@Param("name") String name);
}
```
- 访问
	- 获取列表(GET) `http://localhost:8080/api/people`
	- 获取某个资源(GET) `http://localhost:8080/api/people/1`
	- 查询(GET) `http://localhost:8080/api/people/search/nameStartsWith?name=sma`
	- 分页排序(GET) `http://localhost:8080/api/people?page=1&size=2&sort=age,desc`
	- 保存(POST) `http://localhost:8080/api/people`
	- 更新(PUT) `http://localhost:8080/api/people/1`
	- 删除(DELETE) `http://localhost:8080/api/people/1`
- 在model的字段上加`@JsonIgnore`注解，Spring Data Rest会忽略此字段(结果中无此字段)
- 在model的字段上加`@JsonProperty("newName")`注解，可修改字段输出的名称
- `Projection`使用
```java
// (1) @Projection使用
// @Projection必须在domain(model)包或者自包才会被扫描到
@Projection(name="list", types=Person.class) // 基于Person实现一个投射(可以定义多个)，http://localhost:8080/api/people?projection=list
public interface ListPeople {
    // 此时只会返回id、name属性
    Long getId();

    String getName();

	// 自定义一个字段
    @Value("#{target.name}---#{target.age}") // 这里把Person中的name和age合并成一列，这里需要注意String getFullInfo();方法名前面一定要加get，不然无法序列化为JSON数据
    String getFullInfo();
}

// (2) @Projection定义的数据格式还可以直接配置到Repository之上，配置之后返回的JSON数据会按照 ListPeople 定义的数据格式进行输出
@RepositoryRestResource(path="people", excerptProjection=ListPeople.class)
public interface UserRepository extends JpaRepository<User, Long>{}

// (3) 获取关联实体信息
// # 1
@Entity
public class Card {
    @Id
    @GeneratedValue
    private Long id;

    private String cardNo;

    private Date expirationDate;

    @OneToOne // 必须有关联关系才可以获取到关联对象
    private Person person;

	// ... 省略get/set
}
// # 2
@Projection(name="list", types=Card.class)
public interface ListCard {
    String getCardNo();
    Person getPerson();
}
// # 3
@RepositoryRestResource(excerptProjection = ListCard.class) // http://localhost:8080/api/cards 此时可以获取到Person信息，无此注解默认无法获取
public interface CardRepository extends JpaRepository<Card, Long>  {}
```
- Spring Data Rest Events 提供了AOP方式的开发，定义了10种不同事件
	- 资源保存前 @HandleBeforeCreate
	- 资源保存后 @HandleAfterCreate
	- 资源更新前 @HandleBeforeSave
	- 资源更新后 @HandleAfterSave
	- 资源删除前 @HandleBeforeDelete
	- 资源删除后 @HandleAfterDelete
	- 关系创建前 @HandleBeforeLinkSave
	- 关系创建后 @HandleAfterLinkSave
	- 关系删除前 @HandleBeforeLinkDelete
	- 关系删除后 @HandleAfterLinkDelete
- 结合Spring Security

```java
@PreAuthorize("hasRole('ROLE_USER')") 
public interface PreAuthorizedOrderRepository extends CrudRepository<Order, UUID> {

	@PreAuthorize("hasRole('ROLE_ADMIN')") 
	@Override
	void deleteById(UUID aLong);
}
```