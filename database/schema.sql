BEGIN TRANSACTION;

CREATE TABLE lists (
    list_name varchar (50) NOT NULL UNIQUE,
    CONSTRAINT PK_list_name PRIMARY KEY (list_name)
);

CREATE TABLE tickers (
    ticker varchar (10) NOT NULL UNIQUE,
    high money,
    average money,
    low money,
    CONSTRAINT PK_ticker PRIMARY KEY (ticker)
);

CREATE TABLE options (
	option_id serial,
	ticker varchar (10),
	expiration date,
	strike money,
	bid money,
	ask money,
	last money,
	CONSTRAINT PK_option_id PRIMARY KEY (option_id),
	CONSTRAINT FK_ticker FOREIGN KEY (ticker) REFERENCES tickers(ticker)
);

CREATE TABLE lists_tickers (
    list_name varchar (50) NOT NULL,
    ticker varchar (10) NOT NULL,
    CONSTRAINT PK_list_ticker PRIMARY KEY (list_name, ticker),
    CONSTRAINT FK_list_name FOREIGN KEY (list_name) REFERENCES lists(list_name),
    CONSTRAINT FK_ticker FOREIGN KEY (ticker) REFERENCES tickers(ticker)
);

COMMIT TRANSACTION;