INSERT INTO ZooEmployee VALUES ('E1', 'John', TO_DATE('2016-02-19', 'YYYY-MM-DD'), TO_DATE('2018-02-19', 'YYYY-MM-DD'), 'F');
INSERT INTO ZooEmployee VALUES ('E2', 'Deere', TO_DATE('2017-02-18', 'YYYY-MM-DD'), TO_DATE('2019-02-18', 'YYYY-MM-DD'), 'F');
INSERT INTO ZooEmployee VALUES ('E3', 'Matt', TO_DATE('2018-02-19', 'YYYY-MM-DD'), TO_DATE('2019-02-19', 'YYYY-MM-DD'), 'T');
INSERT INTO ZooEmployee VALUES ('E4', 'Scarlett', TO_DATE('2018-02-19', 'YYYY-MM-DD'), TO_DATE('2019-04-20', 'YYYY-MM-DD'), 'T');
INSERT INTO ZooEmployee VALUES ('E5', 'Colin', TO_DATE('2019-02-17', 'YYYY-MM-DD'), TO_DATE('2020-02-17', 'YYYY-MM-DD'), 'T');
INSERT INTO ZooEmployee VALUES ('E6', 'Jeff', TO_DATE('2020-02-18', 'YYYY-MM-DD'), NULL, 'F');
INSERT INTO ZooEmployee VALUES ('E7', 'Dakota', TO_DATE('2020-02-16', 'YYYY-MM-DD'), NULL, 'F');
INSERT INTO ZooEmployee VALUES ('E8', 'Mark', TO_DATE('2020-02-15', 'YYYY-MM-DD'), NULL,  'T');
INSERT INTO ZooEmployee VALUES ('E9', 'Saoirse', TO_DATE('2020-02-12', 'YYYY-MM-DD'), NULL, 'T');
INSERT INTO ZooEmployee VALUES ('E10', 'Cody', TO_DATE('2020-02-14', 'YYYY-MM-DD'), NULL, 'T');
INSERT INTO ZooEmployee VALUES ('E11', 'Jack', TO_DATE('2020-02-13', 'YYYY-MM-DD'), NULL, 'F');
INSERT INTO ZooEmployee VALUES ('E12', 'Dan', TO_DATE('2020-02-19', 'YYYY-MM-DD'), NULL, 'F');
INSERT INTO ZooEmployee VALUES ('E13', 'Michelle', TO_DATE('2020-02-14', 'YYYY-MM-DD'), NULL, 'T');
INSERT INTO ZooEmployee VALUES ('E14', 'Sally', TO_DATE('2020-02-16', 'YYYY-MM-DD'), NULL, 'T');
INSERT INTO ZooEmployee VALUES ('E15', 'Cody', TO_DATE('2020-02-20', 'YYYY-MM-DD'), NULL, 'T');

INSERT INTO ZookeeperEmployee VALUES ('E1', 'F');
INSERT INTO ZookeeperEmployee VALUES ('E2', 'F');
INSERT INTO ZookeeperEmployee VALUES ('E3', 'T');
INSERT INTO ZookeeperEmployee VALUES ('E4', 'F');
INSERT INTO ZookeeperEmployee VALUES ('E5', 'T');

INSERT INTO VetEmployee VALUES ('E6', 'F', 0, 'None', '604-123-2222');
INSERT INTO VetEmployee VALUES ('E7', 'F', 1, 'Deers', '805-222-1233');
INSERT INTO VetEmployee VALUES ('E8', 'T', 0, 'Turtles', '123-456-7890');
INSERT INTO VetEmployee VALUES ('E9', 'T', 3, 'Mammals', '456-789-1234');
INSERT INTO VetEmployee VALUES ('E10', 'T', 5, 'Urchins', NULL);

INSERT INTO ManagerEmployee VALUES ('E11', 'F', 4);
INSERT INTO ManagerEmployee VALUES ('E12', 'F', 2);
INSERT INTO ManagerEmployee VALUES ('E13', 'T', 42);
INSERT INTO ManagerEmployee VALUES ('E14', 'T', 42);
INSERT INTO ManagerEmployee VALUES ('E15', 'T', 1);

INSERT INTO Area VALUES ('A', 12, 'Jungle Adventure', 'Tropical');
INSERT INTO Area VALUES ('B', 17, 'Under The Sea', 'Aquatic');
INSERT INTO Area VALUES ('C', 12, 'Arctic Expedition', 'Tundra');
INSERT INTO Area VALUES ('D', 8, 'Down on the Farm', 'Farm');
INSERT INTO Area VALUES ('E', 15, 'African Safari', 'Savanna');

INSERT INTO PenHabitats VALUES ('A', 1, 'Tropical');
INSERT INTO PenHabitats VALUES ('B', 4, 'Aquatic');
INSERT INTO PenHabitats VALUES ('C', 1, 'Tundra');
INSERT INTO PenHabitats VALUES ('D', 8, 'Farm');
INSERT INTO PenHabitats VALUES ('E', 12, 'Savanna');

INSERT INTO PenInfo VALUES (1, 'B', 1000, 20, TO_DATE('2020-03-19', 'YYYY-MM-DD'));
INSERT INTO PenInfo VALUES (2, 'A', 2000, 10, TO_DATE('2020-03-18', 'YYYY-MM-DD'));
INSERT INTO PenInfo VALUES (3, 'C', 2000, 3, TO_DATE('2020-03-24', 'YYYY-MM-DD'));
INSERT INTO PenInfo VALUES (4, 'D', 1500, 2, TO_DATE('2020-03-25', 'YYYY-MM-DD'));
INSERT INTO PenInfo VALUES (6, 'E', 4000, 6, TO_DATE('2020-03-27', 'YYYY-MM-DD'));
INSERT INTO PenInfo VALUES (1, 'E', 2500, 2, TO_DATE('2020-03-20', 'YYYY-MM-DD'));
INSERT INTO PenInfo VALUES (5, 'E', 3000, 6, TO_DATE('2020-03-26', 'YYYY-MM-DD'));
INSERT INTO PenInfo VALUES (3, 'E', 800, 10, TO_DATE('2020-03-15', 'YYYY-MM-DD'));

INSERT INTO PenCleaning VALUES ('E4', 1, 'B', TO_DATE('2020-03-19', 'YYYY-MM-DD'));
INSERT INTO PenCleaning VALUES ('E3', 2, 'A', TO_DATE('2020-03-18', 'YYYY-MM-DD'));
INSERT INTO PenCleaning VALUES ('E1', 3, 'C', TO_DATE('2020-03-24', 'YYYY-MM-DD'));
INSERT INTO PenCleaning VALUES ('E5', 4, 'D', TO_DATE('2020-03-25', 'YYYY-MM-DD'));
INSERT INTO PenCleaning VALUES ('E2', 6, 'E', TO_DATE('2020-03-27', 'YYYY-MM-DD'));

INSERT INTO Animals VALUES ('A1', 'Mammal', 'F', 'Giraffe', 14, 'Melman', 6, 'E');
INSERT INTO Animals VALUES ('A2', 'Mammal', 'F', 'Cow', 4, 'Bessie', 4, 'D');
INSERT INTO Animals VALUES ('A3', 'Avian', 'M', 'Parrot', 27, 'Dirty Dan', 2, 'A');
INSERT INTO Animals VALUES ('A4', 'Mammal', 'M', 'Lion', 2, 'Leo', 5, 'E');
INSERT INTO Animals VALUES ('A5', 'Mammal', 'F', 'Polar Bear', 6, 'Bernie', 3, 'C');
INSERT INTO Animals VALUES ('A6', 'Reptile', 'M', 'Chameleon', 59, 'Leon', 3, 'E');
INSERT INTO Animals VALUES ('A7', 'Aquatic', 'M', 'Sea Sponge', 2, 'Spongebob', 1, 'B');
INSERT INTO Animals VALUES ('A8', 'Mammal', 'F', 'Elephant', 4, 'Ellie', 6, 'E');

INSERT INTO HealthCheckup VALUES ('CH1', 'E6', 'A1', 120, 'Great', TO_DATE('2020-02-21', 'YYYY-MM-DD'));
INSERT INTO HealthCheckup VALUES ('CH2', 'E7', 'A2', 30, 'Good', TO_DATE('2020-02-09', 'YYYY-MM-DD'));
INSERT INTO HealthCheckup VALUES ('CH3', 'E8', 'A6', 60, 'Moderate', TO_DATE('2020-02-05', 'YYYY-MM-DD'));
INSERT INTO HealthCheckup VALUES ('CH4', 'E9', 'A4', 100, 'Poor', TO_DATE('2020-02-15', 'YYYY-MM-DD'));
INSERT INTO HealthCheckup VALUES ('CH5', 'E10', 'A5', 500, 'Good', TO_DATE('2020-02-21', 'YYYY-MM-DD'));

INSERT INTO AnimalRelocation VALUES ('R1', 'E11', 'A1', 6, 'E', 3, 'E', TO_DATE('2020-05-21', 'YYYY-MM-DD'));
INSERT INTO AnimalRelocation VALUES ('R2', 'E12', 'A2', 4, 'D', 1, 'B', TO_DATE('2020-05-21', 'YYYY-MM-DD'));
INSERT INTO AnimalRelocation VALUES ('R3', 'E13', 'A3', 2, 'A', 6, 'E', TO_DATE('2020-02-05', 'YYYY-MM-DD'));
INSERT INTO AnimalRelocation VALUES ('R4', 'E14', 'A4', 5, 'E', 6, 'E', TO_DATE('2020-02-15', 'YYYY-MM-DD'));
INSERT INTO AnimalRelocation VALUES ('R5', 'E15', 'A5', 3, 'C', 4, 'D', TO_DATE('2020-02-15', 'YYYY-MM-DD'));

INSERT INTO Food VALUES ('F1', 'Pellets', 1000);
INSERT INTO Food VALUES ('F2', 'Steak', 90);
INSERT INTO Food VALUES ('F3', 'Ferns', 300);
INSERT INTO Food VALUES ('F4', 'Carrots', 250);
INSERT INTO Food VALUES ('F5', 'Squid', 148);
INSERT INTO Food VALUES ('F6', 'Fruit and Seed Mix', 509);
INSERT INTO Food VALUES ('F7', 'Dried Insects', 94);
INSERT INTO Food VALUES ('F8', 'Liver', 650);

INSERT INTO FoodPreferences VALUES ('Giraffe', 'Ferns');
INSERT INTO FoodPreferences VALUES ('Parrot', 'Fruit and Seed Mix');
INSERT INTO FoodPreferences VALUES ('Lion', 'Steak');
INSERT INTO FoodPreferences VALUES ('Polar Bear', 'Liver');
INSERT INTO FoodPreferences VALUES ('Chameleon', 'Dried Insects');

INSERT INTO Feeding VALUES ('F3', 'A1', 'E1', 100, TO_TIMESTAMP('2020-03-27 19:33:00', 'YYYY-MM-DD HH24:MI:SS'));
INSERT INTO Feeding VALUES ('F3', 'A1', 'E2', 50, TO_TIMESTAMP('2020-03-25 19:34:00', 'YYYY-MM-DD HH24:MI:SS'));
INSERT INTO Feeding VALUES ('F6', 'A3', 'E3', 59, TO_TIMESTAMP('2020-03-27 19:35:00', 'YYYY-MM-DD HH24:MI:SS'));
INSERT INTO Feeding VALUES ('F6', 'A3', 'E4', 23, TO_TIMESTAMP('2020-03-26 19:32:00', 'YYYY-MM-DD HH24:MI:SS'));
INSERT INTO Feeding VALUES ('F2', 'A5', 'E5', 222, TO_TIMESTAMP('2020-03-25 19:33:06', 'YYYY-MM-DD HH24:MI:SS'));
INSERT INTO Feeding VALUES ('F2', 'A4', 'E1', 100, TO_TIMESTAMP('2020-03-23 19:33:24', 'YYYY-MM-DD HH24:MI:SS'));
INSERT INTO Feeding VALUES ('F7', 'A6', 'E2', 8, TO_TIMESTAMP('2020-03-24 19:34:30', 'YYYY-MM-DD HH24:MI:SS'));