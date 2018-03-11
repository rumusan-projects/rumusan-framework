create table GROUP_LOCK (
	group_id					number(19,0) not null,
	group_name					varchar2(5 char),
	process_name				varchar2(255 char),
	machine_name				varchar2(255 char),
	process_id					varchar2(36 char),
	last_update_time			timestamp(6),
	last_update_process_id		varchar2(36 char),
	last_update_process_name	varchar2(255 char)
);