CREATE TABLE transaction_log(
  user_id  VARCHAR(150),
  uri   VARCHAR(450),
  verb   VARCHAR(30),
  numberOfTransactions         INTEGER,
  START_TIME       TIMESTAMP,
  END_TIME       TIMESTAMP
);

CREATE TABLE access_rule(
  id VARCHAR(100),
  uri   VARCHAR(450),
  http_method   VARCHAR(50),
  permissionrule_requiredpermissions         VARCHAR(800),
  permissionrule_lenient         BOOLEAN,
  numberoftransactionsrule_permissiontoken         VARCHAR(300),
  numberoftransactionsrule_maxnumberoftransactionsallowed         INTEGER,
  numberoftransactionsrule_timeallowedinseconds         INTEGER,
  PRIMARY KEY(uri, http_method)
);
