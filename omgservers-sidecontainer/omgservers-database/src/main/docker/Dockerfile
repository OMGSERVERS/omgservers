FROM postgres:16.1
CMD ["-c", "shared_preload_libraries=pg_stat_statements", "-c", "max_connections=512", "-c", "log_statement=ddl", "-c", "log_min_messages=info"]
