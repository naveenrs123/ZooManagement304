INSERT INTO ZooEmployee VALUES ('E1', 'John', '2020-02-19', 'F');
INSERT INTO ZooEmployee VALUES ('E2', 'Deere', '2020-02-18', 'F');
INSERT INTO ZooEmployee VALUES ('E3', 'Matt', '2020-02-19', 'T');
INSERT INTO ZooEmployee VALUES ('E4', 'Scarlett', '2020-02-19', 'T');
INSERT INTO ZooEmployee VALUES ('E5', 'Colin', '2020-02-17', 'T');
INSERT INTO ZooEmployee VALUES ('E6', 'Jeff', '2020-02-18', 'F');
INSERT INTO ZooEmployee VALUES ('E7', 'Dakota', '2020-02-16', 'F');
INSERT INTO ZooEmployee VALUES ('E8', 'Mark', '2020-02-15', 'T');
INSERT INTO ZooEmployee VALUES ('E9', 'Saoirse', '2020-02-12', 'T');
INSERT INTO ZooEmployee VALUES ('E10', 'Cody', '2020-02-14', 'T');
INSERT INTO ZooEmployee VALUES ('E11', 'Jack', '2020-02-13', 'F');
INSERT INTO ZooEmployee VALUES ('E12', 'Dan', '2020-02-19', 'F');
INSERT INTO ZooEmployee VALUES ('E13', 'Michelle', '2020-02-14', 'T');
INSERT INTO ZooEmployee VALUES ('E14', 'Sally', '2020-02-16', 'T');
INSERT INTO ZooEmployee VALUES ('E15', 'Cody', '2020-02-20', 'T');

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

INSERT INTO PenInfo VALUES (1, 'B', 1000, 20, '2020-03-19');
INSERT INTO PenInfo VALUES (2, 'A', 2000, 10, '2020-03-18');
INSERT INTO PenInfo VALUES (3, 'C', 2000, 3, '2020-03-24');
INSERT INTO PenInfo VALUES (4, 'D', 1500, 2, '2020-03-25');
INSERT INTO PenInfo VALUES (6, 'E', 4000, 6, '2020-03-27');
INSERT INTO PenInfo VALUES (1, 'E', 2500, 2, '2020-03-20');
INSERT INTO PenInfo VALUES (5, 'E', 3000, 6, '2020-03-26');
INSERT INTO PenInfo VALUES (3, 'E', 800, 10, '2020-03-15');

INSERT INTO PenCLeaning VALUES ('E4', 1, 'B', '2020-03-19');
INSERT INTO PenCLeaning VALUES ('E3', 2, 'A', '2020-03-18');
INSERT INTO PenCLeaning VALUES ('E1', 3, 'C', '2020-03-24');
INSERT INTO PenCLeaning VALUES ('E5', 4, 'D', '2020-03-25');
INSERT INTO PenCLeaning VALUES ('E2', 6, 'E', '2020-03-27');

INSERT INTO Animals VALUES ('A1', 'Mammal', 'F', 'Giraffe', 14, 'Melman', 6, 'E');
INSERT INTO Animals VALUES ('A2', 'Mammal', 'F', 'Cow', 4, 'Bessie', 4, 'D');
INSERT INTO Animals VALUES ('A3', 'Avian', 'M', 'Parrot', 27, 'Dirty Dan', 2, 'A');
INSERT INTO Animals VALUES ('A4', 'Mammal', 'M', 'Lion', 2, 'Leo', 5, 'E');
INSERT INTO Animals VALUES ('A5', 'Mammal', 'F', 'Polar Bear', 6, 'Bernie', 3, 'C');
INSERT INTO Animals VALUES ('A6', 'Reptile', 'M', 'Chameleon', 59, 'Leon', 3, 'E');
INSERT INTO Animals VALUES ('A7', 'Aquatic', 'M', 'Sea Sponge', 2, 'Spongebob', 1, 'B');
INSERT INTO Animals VALUES ('A8', 'Mammal', 'F', 'Elephant', 4, 'Ellie', 6, 'E');

INSERT INTO HealthCheckup VALUES ('CH1', 'E6', 'A1', 120, 'Great', '2020-02-21');
INSERT INTO HealthCheckup VALUES ('CH2', 'E7', 'A2', 30, 'Good', '2020-02-09');
INSERT INTO HealthCheckup VALUES ('CH3', 'E8', 'A6', 60, 'Moderate', '2020-02-05');
INSERT INTO HealthCheckup VALUES ('CH4', 'E9', 'A4', 100, 'Poor', '2020-02-15');
INSERT INTO HealthCheckup VALUES ('CH5', 'E10', 'A5', 500, 'Good', '2020-02-21');

INSERT INTO AnimalRelocation VALUES ('R1', 'E11', 'A1', 6, 'E', 3, 'E', '2020-05-21');
INSERT INTO AnimalRelocation VALUES ('R2', 'E12', 'A2', 4, 'D', 1, 'B', '2020-05-21');
INSERT INTO AnimalRelocation VALUES ('R3', 'E13', 'A3', 2, 'A', 6, 'E', '2020-02-05');
INSERT INTO AnimalRelocation VALUES ('R4', 'E14', 'A4', 5, 'E', 6, 'E', '2020-02-15');
INSERT INTO AnimalRelocation VALUES ('R5', 'E15', 'A5', 3, 'C', 4, 'D', '2020-02-15');

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

INSERT INTO Feeding VALUES ('F3', 'A1', 'E1', 100, '2020-03-27 19:33:00');
INSERT INTO Feeding VALUES ('F3', 'A1', 'E2', 50, '2020-03-25 19:34:00');
INSERT INTO Feeding VALUES ('F6', 'A3', 'E3', 59, '2020-03-27 19:35:00');
INSERT INTO Feeding VALUES ('F6', 'A3', 'E4', 23, '2020-03-26 19:32:00');
INSERT INTO Feeding VALUES ('F2', 'A5', 'E5', 222, '2020-03-25 19:33:06');
INSERT INTO Feeding VALUES ('F2', 'A4', 'E1', 100, '2020-03-23 19:33:24');
INSERT INTO Feeding VALUES ('F7', 'A6', 'E2', 8, '2020-03-24 19:34:30');