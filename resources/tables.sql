CREATE TABLE ZooEmployee
(
    Employee_ID varchar(10),
    Name        varchar(100)  NOT NULL,
    Start_Date  date    NOT NULL,
    On_Duty     char(1) NOT NULL,
    Address     varchar(400),
    PRIMARY KEY (Employee_ID)
);

CREATE TABLE ZookeeperEmployee
(
    Employee_ID varchar(10),
    Event_Duty  char(1),
    PRIMARY KEY (Employee_ID),
    FOREIGN KEY (Employee_ID) REFERENCES ZooEmployee
        ON DELETE CASCADE
);

CREATE TABLE VetEmployee
(
    Employee_ID    varchar(10),
    On_Call        char(1),
    Experience     number NOT NULL,
    Specialization varchar(30),
    Phone_Number   varchar(12),
    PRIMARY KEY (Employee_ID),
    FOREIGN KEY (Employee_ID) REFERENCES ZooEmployee (Employee_ID)
        ON DELETE CASCADE
);

CREATE TABLE ManagerEmployee
(
    Employee_ID varchar(10),
    In_Office   char(1),
    Office_#    number,
    PRIMARY KEY (Employee_ID),
    FOREIGN KEY (Employee_ID) REFERENCES ZooEmployee (Employee_ID)
        ON DELETE CASCADE
);

CREATE TABLE Area
(
    Area_ID  char,
    Num_Pens number NOT NULL,
    Name     varchar(30) NOT NULL,
    Type     varchar(20),
    PRIMARY KEY (Area_ID)
);

CREATE TABLE PenHabitats
(
    Area_ID    char,
    Pen_Number number,
    Habitat    varchar(30),
    PRIMARY KEY (Area_ID, Pen_Number),
    FOREIGN KEY (Area_ID) REFERENCES Area
);

CREATE TABLE PenInfo
(
    Pen_Number            number,
    Area_ID               char,
    Description           varchar(300),
    PenSize               number NOT NULL,
    Occupancy             number,
    Date_of_Last_Cleaning Date,
    PRIMARY KEY (Pen_Number, Area_ID),
    FOREIGN KEY (Area_ID) REFERENCES Area
        ON DELETE CASCADE
);

CREATE TABLE PenCleaning
(
    Employee_ID      varchar(10),
    Pen_Number       number,
    Area_ID          char(1),
    Date_of_cleaning date NOT NULL,
    PRIMARY KEY (Employee_ID, Pen_Number, Area_ID),
    FOREIGN KEY (Employee_ID) REFERENCES ZookeeperEmployee (Employee_ID),
    FOREIGN KEY (Pen_Number, Area_ID) REFERENCES PenInfo
        ON DELETE CASCADE
);

CREATE TABLE Animals
(
    Animal_ID  varchar(10),
    Type       varchar(20) NOT NULL,
    Sex        char(1),
    Species    varchar(30) NOT NULL,
    Age        number  NOT NULL,
    Name       varchar(30) NOT NULL,
    Pen_Number number,
    Area_ID    char,
    PRIMARY KEY (Animal_ID),
    FOREIGN KEY (Pen_Number, Area_ID) REFERENCES PenInfo
);

CREATE TABLE HealthCheckup
(
    Checkup_ID    varchar(10),
    Employee_ID   varchar(10),
    Animal_ID     varchar(10),
    Weight        number  NOT NULL,
    Health_Status varchar(10) NOT NULL,
    Medication    varchar(500),
    Comments      varchar(1000),
    CheckupDate          date NOT NULL,
    PRIMARY KEY (Checkup_ID),
    FOREIGN KEY (Employee_ID) REFERENCES VetEmployee
        ON DELETE SET NULL,
    FOREIGN KEY (Animal_ID) REFERENCES Animals
        ON DELETE CASCADE
);

CREATE TABLE AnimalRelocation
(
    Relocation_ID varchar(10),
    Employee_ID   varchar(10),
    Animal_ID     varchar(10),
    from_Pen_ID   number,
    from_Area_ID  char(1),
    to_Pen_ID     number,
    to_Area_ID    char(1),
    RelocationDate          date NOT NULL,
    PRIMARY KEY (Relocation_ID),
    FOREIGN KEY (Employee_ID) REFERENCES ManagerEmployee (Employee_ID)
        ON DELETE SET NULL,
    FOREIGN KEY (Animal_ID) REFERENCES Animals
        ON DELETE SET NULL,
    FOREIGN KEY (from_Pen_ID, from_Area_ID) REFERENCES PenInfo,
    FOREIGN KEY (to_Pen_ID, to_Area_ID) REFERENCES PenInfo
);

CREATE TABLE Food
(
    Food_ID          varchar(10),
    Type             varchar(20) NOT NULL,
    Inventory_Amount number,
    PRIMARY KEY (Food_ID)
);

CREATE TABLE Feeding
(
    Food_ID              varchar(10),
    Animal_ID            varchar(10),
    Employee_ID          varchar(10),
    Amount               number      NOT NULL,
    Date_Time_Of_Feeding timestamp NOT NULL,
    PRIMARY KEY (Food_ID, Animal_ID, Employee_ID),
    FOREIGN KEY (Food_ID) REFERENCES Food,
    FOREIGN KEY (Animal_ID) REFERENCES Animals
        ON DELETE CASCADE,
    FOREIGN KEY (Employee_ID) REFERENCES ZookeeperEmployee
        ON DELETE SET NULL
);

CREATE TABLE Visitor
(
    Visitor_ID      varchar(10),
    Name            varchar(100) NOT NULL,
    Email           varchar(100) NOT NULL,
    Last_Visit_Date date,
    PRIMARY KEY (Visitor_ID)
);

CREATE TABLE Donation
(
    Donation_ID varchar(10),
    DonationDate        date,
    Amount      number,
    Status      varchar(20),
    PRIMARY KEY (Donation_ID)
);

CREATE TABLE DonationApproval
(
    Donation_ID   varchar(10),
    Employee_ID   varchar(10),
    Visitor_ID    varchar(10),
    Approval_Date timestamp,
    PRIMARY KEY (Donation_ID, Employee_ID, Visitor_ID),
    FOREIGN KEY (Donation_ID) REFERENCES Donation,
    FOREIGN KEY (Visitor_ID) REFERENCES Visitor
        ON DELETE SET NULL,
    FOREIGN KEY (Employee_ID) REFERENCES ManagerEmployee
        ON DELETE SET NULL
);

CREATE TABLE EventPrices
(
    Type   varchar(20),
    Ticket_Price number NOT NULL,
    PRIMARY KEY (Type)
);

CREATE TABLE EventTypes
(
    Name varchar(30),
    Type varchar(20),
    PRIMARY KEY (Name),
    FOREIGN KEY (Type) REFERENCES EventPrices
        ON DELETE SET NULL
);

CREATE TABLE EventInfo (
    Event_ID varchar(10),
    Name varchar(30),
    StartDate date,
    EndDate date,
    Capacity number,
    PRIMARY KEY (Event_ID),
    FOREIGN KEY (Name) REFERENCES EventTypes
);

CREATE TABLE EventAttendance
(
    Visitor_ID varchar(10),
    Event_ID   varchar(10),
    PRIMARY KEY (Visitor_ID, Event_ID),
    FOREIGN KEY (Visitor_ID) REFERENCES Visitor
        ON DELETE SET NULL,
    FOREIGN KEY (Event_ID) REFERENCES EventInfo
);

CREATE TABLE EventFeature
(
    Event_ID    varchar(10),
    Animal_ID   varchar(10),
    Employee_ID varchar(10),
    PRIMARY KEY (Event_ID, Animal_ID, Employee_ID),
    FOREIGN KEY (Event_ID) REFERENCES EventInfo,
    FOREIGN KEY (Animal_ID) REFERENCES Animals
        ON DELETE SET NULL,
    FOREIGN KEY (Employee_ID) REFERENCES ZookeeperEmployee
        ON DELETE SET NULL
);

CREATE TABLE FoodPreferences
(
    Species   varchar(30),
    Food_Type varchar(20) NOT NULL,
    PRIMARY KEY (Food_Type)
);

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

/*
DROP TABLE FOODPREFERENCES;
DROP TABLE EVENTATTENDANCE;
DROP TABLE EVENTINFO;
DROP TABLE EVENTTYPES;
DROP TABLE EVENTPRICES;
DROP TABLE DONATIONAPPROVAL;
DROP TABLE DONATION;
DROP TABLE VISITOR;
DROP TABLE FEEDING;
DROP TABLE FOOD;
DROP TABLE HEALTHCHECKUP;
DROP TABLE ANIMALS;
DROP TABLE PENCLEANING;
DROP TABLE PENINFO;
DROP TABLE PENHABITATS;
DROP TABLE AREA;
DROP TABLE MANAGEREMPLOYEE;
DROP TABLE VETEMPLOYEE;
DROP TABLE ZOOKEEPEREMPLOYEE;
DROP TABLE ZOOEMPLOYEE;
DROP TABLE EVENTFEATURE;
DROP TABLE ANIMALRELOCATION;
 */