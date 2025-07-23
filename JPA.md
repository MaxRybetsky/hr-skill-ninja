### JPA vs Hibernate

| Категория               | JPA (Jakarta (ex. Java) Persistence API)                        | Hibernate ORM                                                                       |
|-------------------------|-----------------------------------------------------------------|-------------------------------------------------------------------------------------|
| Что это                 | Спецификация (API + контракт) для ORM                           | Реализация ORM; один из провайдеров JPA                                             |
| Статус                  | Стандарт, одобряемый Jakarta EE (ранее JCP)                     | Open-source проект                                                                  |
| Пакет                   | `jakarta.persistence.*`                                         | `org.hibernate.*`                                                                   |
| Главный интерфейс       | `EntityManager` (`EntityManagerFactory`)                        | `Session` (`SessionFactory`)  (`Session` реализует `EntityManager`)                 |
| Управление транзакциями | Через `EntityTransaction`                                       | Собственные `begin()/commit()`;                                                     |
| Язык запросов           | JPQL / Criteria API / native SQL                                | HQL (= расширение JPQL) / Criteria                                                  |
| Переносимость           | Высокая: можно сменить провайдер (EclipseLink, OpenJPA…)        | Только Hibernate; код с `Session`, `@Filters` и пр. — непереносим                   |
| Аннотации               | `@Entity`, `@Id`, `@OneToMany`, … ‑ стандартные                 | Понимает все JPA-аннотации + собственные (`@Filter`, `@BatchSize`, …)               |
| Spring-стартер          | `spring-boot-starter-data-jpa` (подключает любой JPA-провайдер) | При прямом использовании нужен `spring-orm` или вручную настраивать`SessionFactory` |
