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

CREATE UNIQUE INDEX IDX_GRP_LCK_GRP_ID ON GROUP_LOCK (GROUP_ID);
CREATE UNIQUE INDEX IDX_GRP_LCK_GRP_NM ON GROUP_LOCK (GROUP_NAME);
CREATE INDEX IDX_GRP_LCK_PRC_NM ON GROUP_LOCK (PROCESS_NAME);
CREATE INDEX IDX_GRP_LCK_MCH_NM ON GROUP_LOCK (MACHINE_NAME);
CREATE UNIQUE INDEX IDX_GRP_LCK_PRC_ID ON GROUP_LOCK (PROCESS_ID);
CREATE INDEX IDX_GRP_LCK_LST_UPD_TM ON GROUP_LOCK (LAST_UPDATE_TIME);
CREATE INDEX IDX_GRP_LCK_LST_UPD_PRC_NM ON GROUP_LOCK (LAST_UPDATE_PROCESS_NAME);
CREATE INDEX IDX_GRP_LCK_LST_UPD_PRC_ID ON GROUP_LOCK (LAST_UPDATE_PROCESS_ID);