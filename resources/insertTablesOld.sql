INSERT INTO ZooEmployee VALUES ('E1', 'John', '2020-02-19', 'F', '100 Academy Way, Vancouver');
INSERT INTO ZooEmployee VALUES ('E2', 'Deere', '2020-02-18', 'F', '200 Birchgrove Avenue, Vancouver');
INSERT INTO ZooEmployee VALUES ('E3', 'Matt', '2020-02-19', 'T', '150 Nelson Street, Burnaby');
INSERT INTO ZooEmployee VALUES ('E4', 'Scarlett', '2020-02-19', 'T', '150 Nelson Street, Burnaby');
INSERT INTO ZooEmployee VALUES ('E5', 'Colin', '2020-02-17', 'T', '300 Winnipeg Road, Vancouver');
INSERT INTO ZooEmployee VALUES ('E6', 'Jeff', '2020-02-18', 'F', '600 Winnipeg Road, Burnaby');
INSERT INTO ZooEmployee VALUES ('E7', 'Dakota', '2020-02-16', 'F', '700 Pine Street, Surrey');
INSERT INTO ZooEmployee VALUES ('E8', 'Mark', '2020-02-15', 'T', '850 Cherrywood Lane, Surrey');
INSERT INTO ZooEmployee VALUES ('E9', 'Saoirse', '2020-02-12', 'T', '950 Chocolate Drive, Richmond');
INSERT INTO ZooEmployee VALUES ('E10', 'Cody', '2020-02-14', 'T', '990 Clinton Road, Vancouver');
INSERT INTO ZooEmployee VALUES ('E11', 'Jack', '2020-02-13', 'F', '601 Sunnyvale Crescent, Burnaby');
INSERT INTO ZooEmployee VALUES ('E12', 'Dan', '2020-02-19', 'F', '701 Woodward Grove, Richmond');
INSERT INTO ZooEmployee VALUES ('E13', 'Michelle', '2020-02-14', 'T', '851 Stone Street, Vancouver');
INSERT INTO ZooEmployee VALUES ('E14', 'Sally', '2020-02-16', 'T', '951 Stone Street, Vancouver');
INSERT INTO ZooEmployee VALUES ('E15', 'Cody', '2020-02-20', 'T', '990 Breakwater Crescent, Coquitlam');

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

INSERT INTO PenInfo VALUES (1, 'B', 'Pen for Fish', 1000, 20, '2020-03-19');
INSERT INTO PenInfo VALUES (2, 'A', 'Pen for Birds', 2000, 10, '2020-03-18');
INSERT INTO PenInfo VALUES (3, 'C', 'Polar Bear Pen', 2000, 3, '2020-03-24');
INSERT INTO PenInfo VALUES (4, 'D', 'Cow Pen', 1500, 2, '2020-03-25');
INSERT INTO PenInfo VALUES (6, 'E', 'Giraffes and Elephants', 4000, 6, '2020-03-27');
INSERT INTO PenInfo VALUES (1, 'E', 'Pen for Lions', 2500, 2, '2020-03-20');
INSERT INTO PenInfo VALUES (5, 'E', 'Zebra Pen', 3000, 6, '2020-03-26');
INSERT INTO PenInfo VALUES (3, 'E', 'Reptiles', 800, 10, '2020-03-15');

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

INSERT INTO HealthCheckup VALUES ('CH1', 'E6', 'A1', 120, 'Healthy', NULL, 'Basic checkup with optimal results.', '2020-02-21');
INSERT INTO HealthCheckup VALUES ('CH2', 'E7', 'A2', 30, 'Healthy', NULL, 'Basic checkup with optimal results.', '2020-02-09');
INSERT INTO HealthCheckup VALUES ('CH3', 'E8', 'A6', 60, 'Unhealthy', 'Amantadine (100mg)', 'Basic checkup with suboptimal results.', '2020-02-05');
INSERT INTO HealthCheckup VALUES ('CH4', 'E9', 'A4', 100, 'Unhealthy', NULL, 'Basic checkup with suboptimal results.', '2020-02-15');
INSERT INTO HealthCheckup VALUES ('CH5', 'E10', 'A5', 500, 'Unhealthy', NULL, 'Basic checkup with optimal results.', '2020-02-21');

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

INSERT INTO Feeding VALUES ('F3', 'A1', 'E1', 100, '2020-03-27 19:33:00');
INSERT INTO Feeding VALUES ('F3', 'A1', 'E2', 50, '2020-03-25 19:34:00');
INSERT INTO Feeding VALUES ('F6', 'A3', 'E3', 59, '2020-03-27 19:35:00');
INSERT INTO Feeding VALUES ('F6', 'A3', 'E4', 23, '2020-03-26 19:32:00');
INSERT INTO Feeding VALUES ('F2', 'A5', 'E5', 222, '2020-03-25 19:33:06');
INSERT INTO Feeding VALUES ('F2', 'A4', 'E1', 100, '2020-03-23 19:33:24');
INSERT INTO Feeding VALUES ('F7', 'A6', 'E2', 8, '2020-03-24 19:34:30');

INSERT INTO FoodPreferences VALUES ('Giraffe', 'Ferns');
INSERT INTO FoodPreferences VALUES ('Parrot', 'Fruit and Seed Mix');
INSERT INTO FoodPreferences VALUES ('Lion', 'Steak');
INSERT INTO FoodPreferences VALUES ('Polar Bear', 'Liver');
INSERT INTO FoodPreferences VALUES ('Chameleon', 'Dried Insects');

INSERT INTO Visitor VALUES ('V1', 'Elon Musk', 'elon@elonmusk.com', '2020-02-22');
INSERT INTO Visitor VALUES ('V2', 'Frodo Baggins', 'frodo@theshire.co.nz', '2020-01-25');
INSERT INTO Visitor VALUES ('V3', 'Bernie Sanders', 'bernie@feelthebern.com', NULL);
INSERT INTO Visitor VALUES ('V4', 'Janis McKenna', 'janis@physics.ubc.ca', '2019-05-04');
INSERT INTO Visitor VALUES ('V5', 'Hazra Imran', 'hazra@cs.ubc.ca', '2020-02-09');

INSERT INTO Donation VALUES ('D1', '2020-02-08', 4000, 'Pending');
INSERT INTO Donation VALUES ('D2', '2019-04-09', 50, 'Approved');
INSERT INTO Donation VALUES ('D3', '2019-09-14', 1000, 'Pending');
INSERT INTO Donation VALUES ('D4', '2019-12-05', 35, 'Approved');
INSERT INTO Donation VALUES ('D5', '2020-02-27', 66, 'Pending');

INSERT INTO DonationApproval VALUES ('D1', 'E11', 'V1', '2020-02-09');
INSERT INTO DonationApproval VALUES ('D2', 'E11', 'V2', '2019-05-10');
INSERT INTO DonationApproval VALUES ('D3', 'E12', 'V2', '2019-09-26');
INSERT INTO DonationApproval VALUES ('D4', 'E13', 'V3', '2019-06-12');
INSERT INTO DonationApproval VALUES ('D5', 'E14', 'V5', '2020-03-01');

INSERT INTO EventPrices VALUES ('Common', 10);
INSERT INTO EventPrices VALUES ('Free', 0);
INSERT INTO EventPrices VALUES ('Kids', 1);
INSERT INTO EventPrices VALUES ('Adults', 33);
INSERT INTO EventPrices VALUES ('Special', 7.50);

INSERT INTO EventInfo VALUES ('EV1', 'Amphibian Show', '2020-02-27', '2020-03-05', 10);
INSERT INTO EventInfo VALUES ('EV2', 'Feed a Herbivore', '2020-01-22', '2020-02-28', 80);
INSERT INTO EventInfo VALUES ('EV3', 'Dog Show', '2019-09-27', '2020-02-28', 150);
INSERT INTO EventInfo VALUES ('EV4', 'Omnivore Show', '2020-09-27', '2020-10-10', 100);
INSERT INTO EventInfo VALUES ('EV5', 'Pet the Horse', '2020-02-27', '2020-03-05', 50);

INSERT INTO EventAttendance VALUES ('V1', 'EV1');
INSERT INTO EventAttendance VALUES ('V2', 'EV1');
INSERT INTO EventAttendance VALUES ('V3', 'EV2');
INSERT INTO EventAttendance VALUES ('V4', 'EV2');
INSERT INTO EventAttendance VALUES ('V5', 'EV1');

INSERT INTO EventFeature VALUES ('EV1', 'A1', 'E1');
INSERT INTO EventFeature VALUES ('EV2', 'A1', 'E2');
INSERT INTO EventFeature VALUES ('EV3', 'A2', 'E4');
INSERT INTO EventFeature VALUES ('EV4', 'A2', 'E3');
INSERT INTO EventFeature VALUES ('EV5', 'A1', 'E5');