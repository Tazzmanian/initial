INSERT INTO ROLE (ROLE_ID, NAME) VALUES ('1', 'ADMIN');
INSERT INTO ROLE (ROLE_ID, NAME) VALUES ('2', 'USER');

INSERT INTO T_USER (USER_ID, USERNAME, PASSWORD, FIRSTNAME, LASTNAME, ENABLED, ROLE_ID) VALUES ('1', 'test', 'test', 'test', 'test', true, '1');
INSERT INTO T_USER (USER_ID, USERNAME, PASSWORD, FIRSTNAME, LASTNAME, ENABLED, ROLE_ID) VALUES ('2', 'asdf', 'asdf', 'asdf', 'asdf', true, '2');
INSERT INTO T_USER (USER_ID, USERNAME, PASSWORD, FIRSTNAME, LASTNAME, ENABLED, ROLE_ID) VALUES ('3', 'billy', '123', 'billy', 'herrington', true, '1');
INSERT INTO T_USER (USER_ID, USERNAME, PASSWORD, FIRSTNAME, LASTNAME, ENABLED, ROLE_ID) VALUES ('4', 'dungeon_master', 'artist', 'van', 'darkholme', true, '2');
INSERT INTO T_USER (USER_ID, USERNAME, PASSWORD, FIRSTNAME, LASTNAME, ENABLED, ROLE_ID) VALUES ('5', 'kazuya', 'asdf', 'kazuya', 'kazuya', true, '2');
INSERT INTO T_USER (USER_ID, USERNAME, PASSWORD, FIRSTNAME, LASTNAME, ENABLED, ROLE_ID) VALUES ('6', 'mark', 'wolf', 'mark', 'wolf', true, '2');
INSERT INTO T_USER (USER_ID, USERNAME, PASSWORD, FIRSTNAME, LASTNAME, ENABLED, ROLE_ID) VALUES ('7', 'mmm', 'mmm', 'mmm', 'mmm', true, '2');
INSERT INTO T_USER (USER_ID, USERNAME, PASSWORD, FIRSTNAME, LASTNAME, ENABLED, ROLE_ID) VALUES ('8', 'bored', 'asdf', 'bored', 'bored' ,true, '2');
INSERT INTO T_USER (USER_ID, USERNAME, PASSWORD, FIRSTNAME, LASTNAME, ENABLED, ROLE_ID) VALUES ('9', 'of', 'asdf', 'of', 'of', true, '2');
INSERT INTO T_USER (USER_ID, USERNAME, PASSWORD, FIRSTNAME, LASTNAME, ENABLED, ROLE_ID) VALUES ('10', 'making', 'asdf', 'making', 'making', true, '2');
INSERT INTO T_USER (USER_ID, USERNAME, PASSWORD, FIRSTNAME, LASTNAME, ENABLED, ROLE_ID) VALUES ('11', 'these', 'asdf', 'these', 'these', true, '2');

INSERT INTO PRIVILEGE (PRIVILEGE_ID, NAME) VALUES ('1', 'ADMIN');
INSERT INTO PRIVILEGE (PRIVILEGE_ID, NAME) VALUES ('2', 'PERM_VIEW_USER');
INSERT INTO PRIVILEGE (PRIVILEGE_ID, NAME) VALUES ('3', 'PERM_DELETE_USER');
INSERT INTO PRIVILEGE (PRIVILEGE_ID, NAME) VALUES ('4', 'PERM_EDIT_USER');
INSERT INTO PRIVILEGE (PRIVILEGE_ID, NAME) VALUES ('5', 'PERM_CHANGE_ACTIVE_USER');
INSERT INTO PRIVILEGE (PRIVILEGE_ID, NAME) VALUES ('6', 'PERM_VIEW_USERFLAG');

INSERT INTO ROLE_PRIVILEGE (ROLE_ID, PRIVILEGE_ID) VALUES ('1', '1');
INSERT INTO ROLE_PRIVILEGE (ROLE_ID, PRIVILEGE_ID) VALUES ('1', '2');
INSERT INTO ROLE_PRIVILEGE (ROLE_ID, PRIVILEGE_ID) VALUES ('1', '3');
INSERT INTO ROLE_PRIVILEGE (ROLE_ID, PRIVILEGE_ID) VALUES ('1', '4');
INSERT INTO ROLE_PRIVILEGE (ROLE_ID, PRIVILEGE_ID) VALUES ('1', '5');
INSERT INTO ROLE_PRIVILEGE (ROLE_ID, PRIVILEGE_ID) VALUES ('1', '6');
INSERT INTO ROLE_PRIVILEGE (ROLE_ID, PRIVILEGE_ID) VALUES ('2', '2');


INSERT INTO EMPLOYER (EMPLOYER_ID, USER_ID) VALUES('1' ,'3');
INSERT INTO EMPLOYER (EMPLOYER_ID, USER_ID) VALUES('2' ,'4');
INSERT INTO EMPLOYER (EMPLOYER_ID, USER_ID) VALUES('3' ,'5');

INSERT INTO EMPLOYEE (EMPLOYEE_ID, USER_ID) VALUES('1' ,'6');
INSERT INTO EMPLOYEE (EMPLOYEE_ID, USER_ID) VALUES('2' ,'7');
INSERT INTO EMPLOYEE (EMPLOYEE_ID, USER_ID) VALUES('3' ,'8');

INSERT INTO EMPLOYEE_EMPLOYER(EMPLOYEE_ID, EMPLOYER_ID) VALUES('1','1');
INSERT INTO EMPLOYEE_EMPLOYER(EMPLOYEE_ID, EMPLOYER_ID) VALUES('2','1');
INSERT INTO EMPLOYEE_EMPLOYER(EMPLOYEE_ID, EMPLOYER_ID) VALUES('3','2');

INSERT INTO TASK(TASK_ID,TITLE) VALUES ('1','LULW');
INSERT INTO TASK(TASK_ID,TITLE) VALUES ('2','PagChomp');
INSERT INTO TASK(TASK_ID,TITLE) VALUES ('3','KaRappa');
INSERT INTO TASK(TASK_ID,TITLE) VALUES ('4','eShrug');
INSERT INTO TASK(TASK_ID,TITLE) VALUES ('5','forsen1');
INSERT INTO TASK(TASK_ID,TITLE) VALUES ('6','TriHard');

INSERT INTO EMPLOYEE_TASKS(EMPLOYEE_ID, TASK_ID) VALUES ('1','1');
INSERT INTO EMPLOYEE_TASKS(EMPLOYEE_ID, TASK_ID) VALUES ('2','1');
INSERT INTO EMPLOYEE_TASKS(EMPLOYEE_ID, TASK_ID) VALUES ('1','2');
INSERT INTO EMPLOYEE_TASKS(EMPLOYEE_ID, TASK_ID) VALUES ('1','3');
INSERT INTO EMPLOYEE_TASKS(EMPLOYEE_ID, TASK_ID) VALUES ('3','4');
INSERT INTO EMPLOYEE_TASKS(EMPLOYEE_ID, TASK_ID) VALUES ('3','5');



