CREATE TABLE servers
(
id number(5) NOT NULL CONSTRAINT pk_id_servers PRIMARY KEY,
server_name varchar2(150),
ip varchar2(50) NOT NULL,
port number(6),
adm_user varchar2(20) NOT NULL,
adm_password varchar2(250) NOT NULL
);

CREATE SEQUENCE server_seq START WITH 1;

commit;

CREATE OR REPLACE TRIGGER server_id_trigger 
BEFORE INSERT ON servers 
FOR EACH ROW

BEGIN
  SELECT server_seq.NEXTVAL
  INTO   :new.id
  FROM   dual;
END;
/



create table status 
(
id_status number(20) not null CONSTRAINT STATUS_ID_PK PRIMARY KEY,
date_loaded TIMESTAMP(6) WITH TIME ZONE,
status varchar2(1) not null
);
commit;

create table applications
(
app_id number(5) NOT NULL CONSTRAINT pk_app_id PRIMARY KEY,
app_name varchar2(100) NOT NULL,
uri varchar2(250) NOT NULL,
server_id number(5) NOT NULL CONSTRAINT SERVER_FK REFERENCES SERVERS,
status number(20) not null constraint status_fk references status
);
commit;