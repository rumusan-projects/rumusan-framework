create table GROUP_LOCK (
	group_id					numeric(19,0) not null,
	group_name					varchar(5) not null,
	process_name				varchar(255),
	machine_name				varchar(255),
	process_id					varchar(36),
	last_update_time			timestamp(6),
	last_update_process_id		varchar(36),
	last_update_process_name	varchar(255)
);

create unique index IDX_GRP_LCK_GRP_ID on GROUP_LOCK (GROUP_ID);
create unique index IDX_GRP_LCK_GRP_NM on GROUP_LOCK (GROUP_NAME);
create index IDX_GRP_LCK_PRC_NM on GROUP_LOCK (PROCESS_NAME);
create index IDX_GRP_LCK_MCH_NM on GROUP_LOCK (MACHINE_NAME);
create unique index IDX_GRP_LCK_PRC_ID on GROUP_LOCK (PROCESS_ID);
create index IDX_GRP_LCK_LST_UPD_TM on GROUP_LOCK (LAST_UPDATE_TIME);
create index IDX_GRP_LCK_LST_UPD_PRC_NM on GROUP_LOCK (LAST_UPDATE_PROCESS_NAME);
create index IDX_GRP_LCK_LST_UPD_PRC_ID on GROUP_LOCK (LAST_UPDATE_PROCESS_ID);

insert into GROUP_LOCK (group_id, group_name) values (1, 'A');