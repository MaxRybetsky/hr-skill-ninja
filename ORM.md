### В коде
```java
@Entity
@Table(name = "person")
public class Person {
    @Id
    @GeneratedValue
    private UUID id;     // ↔️ person.id

    private String name; // ↔️ person.name

    private Integer age; // ↔️ person.age
}
```

### В SQL
```sql
CREATE TABLE person (
    id   UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    age  INT
);
```
