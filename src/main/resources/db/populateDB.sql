DELETE FROM HISTORY;
DELETE FROM ANSWER;
DELETE FROM QUESTION;
DELETE FROM QUIZ;
DELETE FROM ACTIVITY;
DELETE FROM USER_ROLE;
DELETE FROM USER_DATA;
DELETE FROM role_data;

INSERT INTO quiz (id, date, description, published, title)
VALUES (100000, '2021-07-01 10:00:00','Description Quiz 1', false, 'Quiz 1'),
       (100001,'2021-07-01 10:00:00','Description Quiz 2', true, 'Quiz 2'),
       (100002,'2021-07-01 10:00:00','Description Quiz 3', false, 'Quiz 3');

INSERT INTO question (id, title, description, quiz_id)
VALUES (100000, 'Question 1', 'What?', 100000),
       (100001, 'Question 2', 'Who?', 100000),
       (100002, 'Question 3', 'Which?', 100000);

INSERT INTO answer (id, title, description, is_correct, question_id)
VALUES (100000, 'Answer 1', 'One', false, 100000),
       (100001, 'Answer 2', 'Two', true, 100000),
       (100002, 'Answer 3', 'Three', false, 100000),
       (100003, 'Answer 1', 'One', false, 100001),
       (100004, 'Answer 2', 'Two', true, 100001),
       (100005, 'Answer 3', 'Three', false, 100001),
       (100006, 'Answer 1', 'One', false, 100002),
       (100007, 'Answer 2', 'Two', false, 100002),
       (100008, 'Answer 3', 'Three', true, 100002);

INSERT INTO USER_DATA (ID, USERNAME, EMAIL, PASSWORD)
VALUES (1, 'USER1', 'user1@gmail.com', 'password'),
       (2, 'USER2', 'user2@gmail.com', 'password'),
       (3, 'USER3', 'user3@gmail.com', 'password');

INSERT INTO ROLE_DATA (id, name)
VALUES (100000, 'USER'),
       (100001, 'ADMIN');

INSERT INTO USER_ROLE (user_id, role_id)
VALUES (1, 100000);

INSERT INTO ACTIVITY (ACTIVATED, UUID, USER_ID)
VALUES (1, 111, 1);

INSERT INTO HISTORY (ID, DATE, IS_CORRECT, ANSWER_ID, QUESTION_ID, QUIZ_ID, USER_ID)
VALUES (100000, '2021-07-01 10:00:00', false, 100000,  100000, 100000, 1),
       (100001, '2021-07-01 10:00:10', true, 100001,  100000, 100000, 1),
       (100002, '2021-07-01 10:00:20', false, 100002,  100000, 100000, 1);
