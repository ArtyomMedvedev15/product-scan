create sequence if not exists product_seq
    increment by 50;

create table if not exists product
(
    id            bigint not null
        primary key,
    category      varchar(255),
    color         varchar(255),
    description   varchar(255),
    name          varchar(255),
    serial_number varchar(255),
    uniq_code     varchar(255)
);
