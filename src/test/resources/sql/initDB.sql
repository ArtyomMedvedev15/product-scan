create sequence product_seq
    increment by 50;

create table if not exists product
(
    id            bigint  not null
    primary key,
    description   varchar(255),
    name          varchar(255),
    photo_url     varchar(255),
    serial_number varchar(255)
    );
