CREATE TABLE Zoo_Employee
(
    Employee_ID varchar(10),
    Name        varchar(100)  NOT NULL,
    Start_Date  date    NOT NULL,
    On_Duty     char(1) NOT  ,
    Address     varchar(400),
    PRIMARY KEY (Employee_ID)
);

CREATE TABLE Zookeeper_Employee
(
    Employee_ID varchar(10),
    Event_Duty  char(1),
    PRIMARY KEY (Employee_ID),
    FOREIGN KEY (Employee_ID) REFERENCES Zoo_Employee
        ON DELETE CASCADE
);

CREATE TABLE Vet_Employee
(
    Employee_ID    varchar(10),
    On_Call        char(1),
    Experience     number NOT NULL,
    Specialization varchar(30),
    Phone_Number number,
    PRIMARY KEY (Employee_ID),
    FOREIGN KEY (Employee_ID) REFERENCES Zoo_Employee (Employee_ID)
        ON DELETE CASCADE
);

CREATE TABLE Manager_Employee
(
    Employee_ID varchar(10),
    In_Office   char(1),
    Office_#    number,
    PRIMARY KEY (Employee_ID),
    FOREIGN KEY (Employee_ID) REFERENCES Zoo_Employee (Employee_ID)
        ON DELETE CASCADE
);

CREATE TABLE Area
(
    Area_ID char,
    Num_Pens number NOT NULL,
    Name    varchar(30) NOT NULL,
    Type    varchar(20),
    PRIMARY KEY (Area_ID)
);

CREATE TABLE AreaTypes
(
    Area_ID char,
    Type    varchar(20),
    PRIMARY KEY (Area_ID),
    FOREIGN KEY (Area_ID) REFERENCES Area
        ON DELETE CASCADE
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
    Pen_Number                number,
    Area_ID               char,
    Description           varchar(300),
    PenSize                number NOT NULL,
    Occupancy             number,
    Date_of_Last_Cleaning Date,
    PRIMARY KEY (Pen_Number, Area_ID),
    FOREIGN KEY (Area_ID) REFERENCES Area
        ON DELETE CASCADE
);

CREATE TABLE Pen_Cleaning
(
    Employee_ID      varchar(10),
    Pen_Number           number,
    Area_ID          char(1),
    Date_of_cleaning date NOT NULL,
    PRIMARY KEY (Employee_ID, Pen_Number, Area_ID),
    FOREIGN KEY (Employee_ID) REFERENCES Zookeeper_Employee (Employee_ID),
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

CREATE TABLE Health_Checkup
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
    FOREIGN KEY (Employee_ID) REFERENCES Vet_Employee
        ON DELETE SET NULL,
    FOREIGN KEY (Animal_ID) REFERENCES Animals
        ON DELETE CASCADE
);

CREATE TABLE Animal_Relocation
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
    FOREIGN KEY (Employee_ID) REFERENCES Manager_Employee (Employee_ID)
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
    FOREIGN KEY (Employee_ID) REFERENCES Zookeeper_Employee
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

CREATE TABLE Donation_Approval
(
    Donation_ID   varchar(10),
    Employee_ID   varchar(10),
    Visitor_ID    varchar(10),
    Approval_Date timestamp,
    PRIMARY KEY (Donation_ID, Employee_ID, Visitor_ID),
    FOREIGN KEY (Donation_ID) REFERENCES Donation,
    FOREIGN KEY (Visitor_ID) REFERENCES Visitor
        ON DELETE SET NULL,
    FOREIGN KEY (Employee_ID) REFERENCES Manager_Employee
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

CREATE TABLE Event_Attendance
(
    Visitor_ID varchar(10),
    Event_ID   varchar(10),
    PRIMARY KEY (Visitor_ID, Event_ID),
    FOREIGN KEY (Visitor_ID) REFERENCES Visitor
        ON DELETE SET NULL,
    FOREIGN KEY (Event_ID) REFERENCES EventInfo
);

CREATE TABLE Event_Feature
(
    Event_ID    varchar(10),
    Animal_ID   varchar(10),
    Employee_ID varchar(10),
    PRIMARY KEY (Event_ID, Animal_ID, Employee_ID),
    FOREIGN KEY (Event_ID) REFERENCES EventInfo,
    FOREIGN KEY (Animal_ID) REFERENCES Animals
        ON DELETE SET NULL,
    FOREIGN KEY (Employee_ID) REFERENCES Zookeeper_Employee
        ON DELETE SET NULL
);

CREATE TABLE FoodPreferences
(
    Species   varchar(30),
    Food_Type varchar(20) NOT NULL,
    PRIMARY KEY (Food_Type)
);
