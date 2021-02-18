create sequence hibernate_sequence start with 1 increment by 1

    create table books (
       id bigint not null,
        author_name varchar(255) not null,
        available boolean,
        language varchar(255) not null,
        year bigint not null,
        title varchar(255) not null,
        primary key (id)
    )
