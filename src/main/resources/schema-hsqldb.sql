CREATE TABLE transaction_log(
  user_id  VARCHAR(150),
  uri   VARCHAR(450),
  numberOfTransactions         INTEGER,
  until_time       DATE
);

CREATE TABLE access_rule(
  id VARCHAR(100),
  uri   VARCHAR(450) PRIMARY KEY,
  permissionrule_requiredpermissions         VARCHAR(800),
  permissionrule_minimumnumberofpermissionsmatch         INTEGER,
  tokenexpirationrule_permissiontoken         VARCHAR(200),
  tokenexpirationrule_expirationtimeinseconds         INTEGER,
  numberoftransactionsrule_permissiontoken         VARCHAR(300),
  numberoftransactionsrule_maxnumberoftransactionsallowed         INTEGER,
  numberoftransactionsrule_untiltime         INTEGER
);
