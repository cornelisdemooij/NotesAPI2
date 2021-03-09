DROP TABLE IF EXISTS note;

CREATE TABLE note (
    id INT NOT NULL AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    body TEXT,
    creation DATETIME NOT NULL,
    modified DATETIME NOT NULL,

    UNIQUE (id),
    PRIMARY KEY (id),
    UNIQUE INDEX(title)
);
