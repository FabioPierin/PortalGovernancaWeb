--Inicio SERVERS

CREATE TABLE servers
(
id number(5) NOT NULL,
server_name varchar2(150),
ip varchar2(50) NOT NULL,
port number(6) NOT NULL,
adm_user varchar2(20) NOT NULL,
adm_password varchar2(250)
);

ALTER TABLE servers ADD (
  CONSTRAINT server_pk PRIMARY KEY (ID));
  
CREATE SEQUENCE server_seq START WITH 1;

CREATE OR REPLACE TRIGGER server_id_trigger 
BEFORE INSERT ON servers 
FOR EACH ROW

BEGIN
  SELECT server_seq.NEXTVAL
  INTO   :new.id
  FROM   dual;
END;
/

commit;
--Fim SERVERS

--Inicio STATUS
create table status 
(
id number(20) not null,
CHANGED_AT TIMESTAMP(6) DEFAULT SYSDATE,
status varchar2(1) not null,
APPLICATION_ID number(5) NOT NULL CONSTRAINT APPLICATION_FK REFERENCES APPLICATIONS
);

ALTER TABLE STATUS ADD (
  CONSTRAINT status_pk PRIMARY KEY (ID));

CREATE SEQUENCE status_seq START WITH 1;

CREATE OR REPLACE TRIGGER status_id_trigger 
BEFORE INSERT ON STATUS 
FOR EACH ROW

BEGIN
  SELECT status_seq.NEXTVAL
  INTO   :new.id
  FROM   dual;
END;
/
commit;
--Fim STATUS


--Inicio APPLICATIONS
create table applications
(
id number(5) NOT NULL CONSTRAINT app_pk PRIMARY KEY,
app_name varchar2(100) NOT NULL,
uri varchar2(250) NOT NULL,
description varchar2(1000) NOT NULL,
server_id number(5) NOT NULL CONSTRAINT SERVER_FK REFERENCES SERVERS,
INCLUSION_DATE DATE NOT NULL DEFAULT SYSDATE
);

CREATE SEQUENCE applications_seq start with 1;

CREATE OR REPLACE TRIGGER applications_id_trigger 
BEFORE INSERT ON APPLICATIONS 
FOR EACH ROW

BEGIN
  SELECT applications_seq.NEXTVAL
  INTO   :new.id
  FROM   dual;
END;
/

CREATE OR REPLACE TRIGGER TRIGGER_SET_NEW_APP_ACTIVE 
AFTER INSERT ON APPLICATIONS 
FOR EACH ROW 
BEGIN
  INSERT INTO STATUS (APPLICATION_ID, STATUS)
    values (:new.id, 'A');
END;
/
commit;
--Fim APPLICATIONS