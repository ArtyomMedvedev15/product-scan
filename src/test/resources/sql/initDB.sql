create sequence if not exists product_seq
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

insert into product(id,description,name,photo_url,serial_number)values (778,'test','test','test','test');
insert into product(id,description,name,photo_url,serial_number)values (779,'test','test','test','test2');
insert into product(id,description,name,photo_url,serial_number)values (780,'test','test','test','test3');