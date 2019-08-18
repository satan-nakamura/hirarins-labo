FROM centos:5.11

COPY CentOS-Base.repo libselinux.repo /etc/yum.repos.d/
RUN yum install -y postgresql-server \
    && mkdir -p /opt/pgdata \
    && chown postgres:postgres /opt/pgdata

RUN su -c "initdb -D /opt/pgdata" - postgres
COPY postgresql.conf /opt/pgdata/
RUN chown postgres:postgres /opt/pgdata/postgresql.conf
USER postgres
COPY create-tables.sql create-db.sh /tmp/
RUN echo "" >> /opt/pgdata/pg_hba.conf \
    && echo "host all all 0.0.0.0/0 password" >> /opt/pgdata/pg_hba.conf \
    && pg_ctl start -D /opt/pgdata \
    && sleep 3 \
    && /tmp/create-db.sh \
    && pg_ctl stop -D /opt/pgdata
EXPOSE 5432
CMD ["/usr/bin/postmaster", "-D", "/opt/pgdata"]
