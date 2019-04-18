create table SKU
(
    ID           bigint         not null,
    NAME         varchar(255),
    DESCRIPTION  varchar(255),
    RETAIL_PRICE numeric(12, 6) not null,
    PRIMARY KEY (ID)
);

create table ITEM
(
    ID          bigint         not null,
    DESCRIPTION varchar(255),
    UNIT_COST   numeric(12, 6) not null,
    PRIMARY KEY (ID)
);

create table COMPOSITION
(
    ID       bigint  not null,
    ITEM_ID  bigint  not null,
    QUANTITY integer not null,
    SKU_ID   bigint  not null,
    PRIMARY KEY (ID)
);

create table SALE
(
    ID   bigint    not null,
    DATE timestamp not null,
    PRIMARY KEY (ID)
);

create table SALE_ITEM
(
    ID         bigint         not null,
    SKU_ID     bigint         not null,
    SALE_ID    bigint         not null,
    QUANTITY   integer        not null,
    SALE_PRICE numeric(12, 6) not null,
    PRIMARY KEY (ID)
);

create table INVENTORY_HEADER
(
    ID           bigint      not null,
    TYPE         varchar(50) not null,
    DATE         timestamp   not null,
    DESCRIPTION  varchar(255),
    SALE_ITEM_ID bigint,
    PRIMARY KEY (ID)
);

create table INVENTORY_DETAIL
(
    ID               bigint      not null,
    QUANTITY         integer     not null,
    DIRECTION        varchar(50) not null,
    INVENTORY_HDR_ID bigint      not null,
    ITEM_ID          bigint      not null,
    PRIMARY KEY (ID)
);

create sequence if not exists SEQ_SKU_ID start with 10000;
create sequence if not exists SEQ_ITEM_ID start with 10000;
create sequence if not exists SEQ_COMPOSITION_ID start with 10000;
create sequence if not exists SEQ_SALE_ID start with 10000;
create sequence if not exists SEQ_SALE_ITEM_ID start with 10000;
create sequence if not exists SEQ_INVENTORY_HEADER_ID start with 10000;
create sequence if not exists SEQ_INVENTORY_DETAIL_ID start with 10000;

alter table COMPOSITION
    add constraint FK_SKU_COMPOSITION foreign key (SKU_ID) references SKU (ID) on delete cascade;
alter table COMPOSITION
    add constraint FK_ITEM_COMPOSITION foreign key (ITEM_ID) references ITEM (ID);
alter table SALE_ITEM
    add constraint FK_SKU_SALE_ITEM foreign key (SKU_ID) references SKU (ID);
alter table SALE_ITEM
    add constraint FK_SALE_SALE_ITEM foreign key (SALE_ID) references SALE (ID);
alter table INVENTORY_HEADER
    add constraint FK_SALE_ITEM_INVENTORY_HDR foreign key (SALE_ITEM_ID) references SALE_ITEM (ID);
alter table INVENTORY_DETAIL
    add constraint FK_INVENTORY_HDR_INVENTORY_DTL foreign key (INVENTORY_HDR_ID) references INVENTORY_DETAIL (ID);
alter table INVENTORY_DETAIL
    add constraint FK_ITEM_INVENTORY_DTL foreign key (ITEM_ID) references ITEM (ID) on delete cascade;

grant select, insert, update, delete on table SKU to operations;
grant select, insert, update, delete on table ITEM to operations;
grant select, insert, update, delete on table COMPOSITION to operations;
grant select, insert on table SALE to operations;
grant select, insert on table SALE_ITEM to operations;
grant select, insert on table INVENTORY_HEADER to operations;
grant select, insert on table INVENTORY_DETAIL to operations;

grant usage, select on sequence SEQ_SKU_ID to operations;
grant usage, select on sequence SEQ_ITEM_ID to operations;
grant usage, select on sequence SEQ_COMPOSITION_ID to operations;
grant usage, select on sequence SEQ_SALE_ID to operations;
grant usage, select on sequence SEQ_SALE_ITEM_ID to operations;
grant usage, select on sequence SEQ_INVENTORY_HEADER_ID to operations;
grant usage, select on sequence SEQ_INVENTORY_DETAIL_ID to operations;
