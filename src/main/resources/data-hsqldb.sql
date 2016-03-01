
insert into ACCESS_RULE (id ,
  uri,
  http_method,
  permissionrule_requiredpermissions,
  permissionrule_lenient,
  numberoftransactionsrule_permissiontoken ,
  numberoftransactionsrule_maxnumberoftransactionsallowed ,
  numberoftransactionsrule_timeallowedinseconds) VALUES
  ('cedd8d45-0b4f-493d-85fb-763c59055639', '/customers', 'GET', 'READ_APP_1', false, 'READ_APP_1', 10, 60);


insert into ACCESS_RULE (id ,
  uri,
  http_method,
  permissionrule_requiredpermissions,
  permissionrule_lenient,
  numberoftransactionsrule_permissiontoken ,
  numberoftransactionsrule_maxnumberoftransactionsallowed ,
  numberoftransactionsrule_timeallowedinseconds) VALUES
  ('dedd8d45-0b4f-493d-85fb-763c59055639', '/deliveryhubs', 'GET', 'READ_APP_1', false, 'READ_APP_1', 10, 60);

  insert into ACCESS_RULE (id ,
    uri,
    http_method,
    permissionrule_requiredpermissions,
    permissionrule_lenient,
    numberoftransactionsrule_permissiontoken ,
    numberoftransactionsrule_maxnumberoftransactionsallowed ,
    numberoftransactionsrule_timeallowedinseconds) VALUES
    ('fedd8d45-0b4f-493d-85fb-763c59055639', '/order/{orderId}', 'GET', 'READ_APP_2', false, 'READ_APP_2', 10, 60);

    insert into ACCESS_RULE (id ,
        uri,
        http_method,
        permissionrule_requiredpermissions,
        permissionrule_lenient,
        numberoftransactionsrule_permissiontoken ,
        numberoftransactionsrule_maxnumberoftransactionsallowed ,
        numberoftransactionsrule_timeallowedinseconds) VALUES
        ('ghdd8d45-0b4f-493d-85fb-763c59055639', '/order', 'POST', 'WRITE_APP_1', false, 'WRITE_APP_1', 10, 60);
