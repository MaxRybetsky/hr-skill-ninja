SELECT pid,                       -- ID серверного процесса
       usename      AS user,      -- имя пользователя
       datname      AS database,  -- к какой БД подключён
       client_addr, client_port,  -- IP/порт клиента
       state,                     -- active | idle | idle in tx ...
       query                      -- текст запроса (если есть)
FROM   pg_stat_activity
ORDER  BY backend_start;
