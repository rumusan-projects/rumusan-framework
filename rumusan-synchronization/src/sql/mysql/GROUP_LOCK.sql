create table GROUP_LOCK (
   GROUP_ID             numeric(19,0) not null,
   GROUP_NAME           varchar(5) not null,
   PROCESS_NAME         varchar(255),
   MACHINE_NAME         varchar(255),
   PROCESS_ID           varchar(36),
   LAST_UPDATE_TIME     timestamp,
   LAST_UPDATE_PROCESS_ID varchar(36),
   LAST_UPDATE_PROCESS_NAME varchar(255),
   primary key (GROUP_ID)
);

create unique index IDX_GRP_LCK_GRP_NM on GROUP_LOCK (GROUP_NAME);
create index IDX_GRP_LCK_PRC_NM on GROUP_LOCK (PROCESS_NAME);
create index IDX_GRP_LCK_MCH_NM on GROUP_LOCK (MACHINE_NAME);
create unique index IDX_GRP_LCK_PRC_ID on GROUP_LOCK (PROCESS_ID);
create index IDX_GRP_LCK_LST_UPD_TM on GROUP_LOCK (LAST_UPDATE_TIME);
create index IDX_GRP_LCK_LST_UPD_PRC_NM on GROUP_LOCK (LAST_UPDATE_PROCESS_NAME);
create index IDX_GRP_LCK_LST_UPD_PRC_ID on GROUP_LOCK (LAST_UPDATE_PROCESS_ID);

insert into GROUP_LOCK (group_id, group_name) values (1, 'A');
commit;