create table GROUP_LOCK (
	group_id					number(19,0) not null,
	group_name					varchar2(5 char) not null,
	process_name				varchar2(255 char),
	machine_name				varchar2(255 char),
	process_id					varchar2(36 char),
	last_update_time			timestamp(6),
	last_update_process_id		varchar2(36 char),
	last_update_process_name	varchar2(255 char)
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
commit;